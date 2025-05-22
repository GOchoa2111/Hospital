using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using HospitalSistemaAPI.Models;
using HospitalSistemaAPI.DTO;
using HospitalSistemaAPI.DTO.TecnoSolucionesAPI.DTOs;
using HospitalSistemaAPI.Data;


namespace HospitalSistemaAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class detalleCitaController : ControllerBase
    {
        private readonly AppDbContext _context;
        public detalleCitaController(AppDbContext context)
        {
            _context = context;
        }
        [HttpGet]
        public async Task<ActionResult<IEnumerable<CitaDetalleDTO>>> GetCitas()
        {
            var citas = await _context.Citas
                .Include(c => c.Servicio)
                .Include(c => c.Paciente)
                .Include(c => c.Doctor)
                .Select(c => new CitaDetalleDTO
                {
                    IdCita = c.IdCita,
                    FechaHora = c.FechaHora,
                    Servicio = c.Servicio != null ? c.Servicio.Nombre : "Sin servicio",
                    Paciente = c.Paciente != null ? c.Paciente.Nombre + "  " + c.Paciente.Apellido : "Sin paciente",
                    Doctor = c.Doctor != null ? c.Doctor.Nombre + "  " + c.Doctor.Apellido : "Sin doctor"
                })
                .ToListAsync();
            return Ok(citas);
        }
    }
}
