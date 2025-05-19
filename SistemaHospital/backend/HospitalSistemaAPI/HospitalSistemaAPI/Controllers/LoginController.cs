using Microsoft.AspNetCore.Mvc;
using HospitalSistemaAPI.DTOs;
using HospitalSistemaAPI.Models;
using HospitalSistemaAPI.Data;

namespace HospitalSistemaAPI.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class LoginController : ControllerBase
    {
        private readonly AppDbContext _context;

        public LoginController(AppDbContext context)
        {
            _context = context;
        }

        [HttpPost("login")]
        public IActionResult Login([FromBody] LoginDTO loginDto)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var usuario = _context.Usuarios
                .FirstOrDefault(u => u.NombreUsuario == loginDto.NombreUsuario && u.Contrasena == loginDto.Contrasena);

            if (usuario != null)
            {
                return Ok(new { message = "Login exitoso" });
            }
            else
            {
                return Unauthorized(new { message = "Usuario o contraseña incorrectos" });
            }
        }
    }
}
