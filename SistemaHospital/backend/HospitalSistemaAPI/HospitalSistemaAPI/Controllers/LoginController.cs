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

            // Buscar usuario por nombre y contraseña (ojo: en producción usar hash y salting)
            var usuario = _context.Usuarios
                .Include(u => u.Rol) // Incluir el rol del usuario para autorización
                .FirstOrDefault(u => u.NombreUsuario == loginDto.NombreUsuario && u.Contrasena == loginDto.Contrasena);

            if (usuario != null)
            {
                // Crear claims para el token (incluyendo rol)
                var claims = new[]
                {
                    new Claim(JwtRegisteredClaimNames.Sub, usuario.NombreUsuario),
                    new Claim("role", usuario.Rol.NombreRol), // rol del usuario para autorización
                    new Claim(JwtRegisteredClaimNames.Jti, Guid.NewGuid().ToString())
                };

                // Crear clave y credenciales para firmar el token
                var key = new SymmetricSecurityKey(Encoding.UTF8.GetBytes(_jwtSettings.Key));
                var creds = new SigningCredentials(key, SecurityAlgorithms.HmacSha256);

                // Crear token con configuraciones de issuer, audience, expiración, claims y firma
                var token = new JwtSecurityToken(
                    issuer: _jwtSettings.Issuer,
                    audience: _jwtSettings.Audience,
                    claims: claims,
                    expires: DateTime.UtcNow.AddMinutes(_jwtSettings.ExpireMinutes),
                    signingCredentials: creds
                );

                // Retornar token serializado, tiempo de expiración, usuario y rol
                return Ok(new
                {
                    token = new JwtSecurityTokenHandler().WriteToken(token),
                    expiration = token.ValidTo,
                    username = usuario.NombreUsuario,
                    role = usuario.Rol
                });
            }
            else
            {
                // Usuario o contraseña incorrectos
                return Unauthorized(new { message = "Usuario o contraseña incorrectos" });
            }
        }
    }
}
