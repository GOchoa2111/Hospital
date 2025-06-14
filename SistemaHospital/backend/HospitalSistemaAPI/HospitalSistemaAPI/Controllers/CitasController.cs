using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using HospitalSistemaAPI.Data;
using HospitalSistemaAPI.Models;
using Microsoft.AspNetCore.Authorization;
using HospitalSistemaAPI.Services;

namespace HospitalSistemaAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    //[Authorize]
    public class CitasController : ControllerBase
    {
        private readonly AppDbContext _context;
        private readonly EmailService _emailService;

        public CitasController(AppDbContext context, EmailService emailService)
        {
            _context = context;
            _emailService = emailService;
        }

        // GET: api/Citas
        [HttpGet]
        public async Task<ActionResult<IEnumerable<Cita>>> GetCitas()
        {
            return await _context.Citas.ToListAsync();
        }

        // GET: api/Citas/5
        [HttpGet("{id}")]
        public async Task<ActionResult<Cita>> GetCita(int id)
        {
            var cita = await _context.Citas.FindAsync(id);

            if (cita == null)
            {
                return NotFound();
            }

            return cita;
        }

        // PUT: api/Citas/5
        [HttpPut("{id}")]
        public async Task<IActionResult> PutCita(int id, Cita cita)
        {
            if (id != cita.IdCita)
            {
                return BadRequest();
            }

            _context.Entry(cita).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!CitaExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return NoContent();
        }

        // POST: api/Citas
        [HttpPost]
        public async Task<ActionResult<Cita>> PostCita(Cita cita)
        {
            _context.Citas.Add(cita);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetCita", new { id = cita.IdCita }, cita);
        }

        // DELETE: api/Citas/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteCita(int id)
        {
            var cita = await _context.Citas.FindAsync(id);
            if (cita == null)
            {
                return NotFound();
            }

            _context.Citas.Remove(cita);
            await _context.SaveChangesAsync();

            return NoContent();
        }
        // POST: api/Citas/5/notificar
        [HttpPost("{id}/notificar")]
        public async Task<IActionResult> NotificarCita(int id)
        {
            // Buscamos la cita, pero también incluimos los datos del Paciente asociado.
            var cita = await _context.Citas
                                     .Include(c => c.Paciente)
                                     .FirstOrDefaultAsync(c => c.IdCita == id);

            if (cita == null)
            {
                return NotFound(new { message = "La cita no fue encontrada." });
            }

            if (string.IsNullOrEmpty(cita.Paciente.Correo))
            {
                return BadRequest(new { message = "El paciente no tiene un correo electrónico registrado." });
            }

            //Contenido del correo.
            string asunto = $"Recordatorio de Cita Médica - {cita.FechaHora:dd/MM/yyyy}";
            string mensaje = $"Hola {cita.Paciente.Nombre},<br><br> este correo es para recordarle su cita programada para el día <strong>{cita.FechaHora:dd/MM/yyyy}</strong> a las <strong>{cita.FechaHora:hh:mm tt}</strong>.<br><br>Gracias por su preferencia,<br>Hospital La Salud.";

            try
            {
                _emailService.SendEmail(cita.Paciente.Correo, asunto, mensaje);

                return Ok(new { message = "Notificación enviada exitosamente." });
            }
            catch (Exception ex)
            {
                return StatusCode(500, new { message = "Error al enviar el correo.", error = ex.Message });
            }
        }

        private bool CitaExists(int id)
        {
            return _context.Citas.Any(e => e.IdCita == id);
        }
    }
}
