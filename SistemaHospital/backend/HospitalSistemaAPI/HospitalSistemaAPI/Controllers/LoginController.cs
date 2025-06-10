using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Options;
using HospitalSistemaAPI.Configuration;
using HospitalSistemaAPI.Data;
using HospitalSistemaAPI.DTOs;
using Microsoft.IdentityModel.Tokens;
using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using System.Text;
using System;
using Microsoft.EntityFrameworkCore;
using HospitalSistemaAPI.Helpers; // Asegúrate de tener este using si PasswordHelper está en una carpeta Helpers

namespace HospitalSistemaAPI.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class LoginController : ControllerBase
    {
        private readonly AppDbContext _context;
        private readonly JwtSettings _jwtSettings;

        // Constructor que recibe DbContext y configuración de JwtSettings
        public LoginController(AppDbContext context, IOptions<JwtSettings> jwtSettings)
        {
            _context = context;
            _jwtSettings = jwtSettings.Value;
        }

        [HttpPost("login")]
        public IActionResult Login([FromBody] LoginDTO loginDto)
        {
            // Validar modelo recibido
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            // Buscar usuario solo por nombre de usuario
            var usuario = _context.Usuarios
                .Include(u => u.Rol)
                .FirstOrDefault(u => u.NombreUsuario == loginDto.NombreUsuario);

            // Si no se encuentra el usuario
            if (usuario == null)
            {
                return Unauthorized(new { message = "Usuario o contraseña incorrectos" });
            }

            // Validar la contraseña con hash
            bool contraseñaValida = PasswordHelper.VerifyPassword(usuario.Contrasena, loginDto.Contrasena);

            if (!contraseñaValida)
            {
                return Unauthorized(new { message = "Usuario o contraseña incorrectos" });
            }

            // Crear claims para el token
            var claims = new[]
            {
                new Claim(JwtRegisteredClaimNames.Sub, usuario.NombreUsuario),
                new Claim("role", usuario.Rol.NombreRol),
                new Claim(JwtRegisteredClaimNames.Jti, Guid.NewGuid().ToString())
            };

            // Crear clave y credenciales
            var key = new SymmetricSecurityKey(Encoding.UTF8.GetBytes(_jwtSettings.Key));
            var creds = new SigningCredentials(key, SecurityAlgorithms.HmacSha256);

            // Crear el token JWT
            var token = new JwtSecurityToken(
                issuer: _jwtSettings.Issuer,
                audience: _jwtSettings.Audience,
                claims: claims,
                expires: DateTime.UtcNow.AddMinutes(_jwtSettings.ExpireMinutes),
                signingCredentials: creds
            );

            // Retornar el token
            return Ok(new
            {
                token = new JwtSecurityTokenHandler().WriteToken(token),
                expiration = token.ValidTo,
                username = usuario.NombreUsuario,
                role = usuario.Rol
            });
        }
    }
}
