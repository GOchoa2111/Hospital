using Microsoft.AspNetCore.Mvc;
using HospitalSistemaAPI.DTOs;

namespace HospitalSistemaAPI.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class LoginController : ControllerBase
    {
        [HttpPost("login")]
        public IActionResult Login([FromBody] LoginDTO loginDto)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            // Aquí iría la lógica para validar usuario y contraseña
            // Por ejemplo, buscar usuario en base de datos, verificar contraseña, etc.

            // Por ahora retornamos OK para pruebas
            return Ok(new { message = "Login exitoso" });
        }
    }
}
