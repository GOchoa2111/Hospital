using HospitalSistemaAPI.Data;
using HospitalSistemaAPI.DTO.TecnoSolucionesAPI.DTOs;
using HospitalSistemaAPI.Helpers;
using HospitalSistemaAPI.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace HospitalSistemaAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class UsuarioController : ControllerBase
    {
        private readonly AppDbContext _context;

        public UsuarioController(AppDbContext context)
        {
            _context = context;
        }

        // POST: api/Usuario
        [HttpPost]
        public async Task<ActionResult<UsuarioDTO>> PostUsuario(UsuarioDTO dto)
        {
            try
            {
                var usuario = new Usuario
                {
                    Nombre = dto.Nombre,
                    Apellido = dto.Apellido,
                    NombreUsuario = dto.NombreUsuario,
                    Contrasena = PasswordHelper.HashPassword(dto.Contrasena),
                    Correo = dto.Correo,
                    RolId = dto.RolId,
                    TokenRecuperacion = dto.TokenRecuperacion,
                    FechaExpiracionToken = dto.FechaExpiracionToken
                };

                _context.Usuarios.Add(usuario);
                await _context.SaveChangesAsync();

                dto.IdUsuario = usuario.IdUsuario;
                return CreatedAtAction(nameof(GetUsuario), new { id = dto.IdUsuario }, dto);
            }
            catch (Exception ex)
            {
                return BadRequest($"Error al crear usuario: {ex.Message}");
            }
        }

        // GET: api/Usuario/5
        [HttpGet("{id}")]
        public async Task<ActionResult<UsuarioDTO>> GetUsuario(int id)
        {
            var usuario = await _context.Usuarios.FindAsync(id);
            if (usuario == null)
            {
                return NotFound();
            }

            var dto = new UsuarioDTO
            {
                IdUsuario = usuario.IdUsuario,
                Nombre = usuario.Nombre,
                Apellido = usuario.Apellido,
                NombreUsuario = usuario.NombreUsuario,
                Correo = usuario.Correo,
                RolId = usuario.RolId,
                TokenRecuperacion = usuario.TokenRecuperacion,
                FechaExpiracionToken = usuario.FechaExpiracionToken
            };

            return dto;
        }

        // PUT: api/Usuario/5
        [HttpPut("{id}")]
        public async Task<IActionResult> PutUsuario(int id, UsuarioDTO dto)
        {
            if (id != dto.IdUsuario)
                return BadRequest("ID del usuario no coincide");

            var usuario = await _context.Usuarios.FindAsync(id);
            if (usuario == null)
                return NotFound();

            usuario.Nombre = dto.Nombre;
            usuario.Apellido = dto.Apellido;
            usuario.NombreUsuario = dto.NombreUsuario;
            usuario.Correo = dto.Correo;
            usuario.RolId = dto.RolId;
            usuario.TokenRecuperacion = dto.TokenRecuperacion;
            usuario.FechaExpiracionToken = dto.FechaExpiracionToken;

            // Solo actualizar contraseña si se envía una nueva
            if (!string.IsNullOrWhiteSpace(dto.Contrasena))
            {
                usuario.Contrasena = PasswordHelper.HashPassword(dto.Contrasena);
            }

            await _context.SaveChangesAsync();

            return NoContent();
        }

        // DELETE: api/Usuario/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteUsuario(int id)
        {
            var usuario = await _context.Usuarios.FindAsync(id);
            if (usuario == null)
                return NotFound();

            _context.Usuarios.Remove(usuario);
            await _context.SaveChangesAsync();

            return NoContent();
        }
    }
}
