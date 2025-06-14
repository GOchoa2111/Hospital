using HospitalSistemaAPI.Data;
using HospitalSistemaAPI.DTOs;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace HospitalSistemaAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ReportesController : ControllerBase
    {
        private readonly AppDbContext _context;

        public ReportesController(AppDbContext context)
        {
            _context = context;
        }

        // GET: api/Reportes/HistorialClinico/5
        [HttpGet("HistorialClinico/{pacienteId}")]
        public async Task<ActionResult<IEnumerable<HistorialReporteDTO>>> GetHistorialPorPaciente(int pacienteId)
        {
            // Verificamos si el paciente existe
            var pacienteExiste = await _context.pacientes.AnyAsync(p => p.IdPaciente == pacienteId);
            if (!pacienteExiste)
            {
                return NotFound($"No se encontró un paciente con el ID {pacienteId}.");
            }

            // Aquí ocurre la magia: Usamos LINQ para unir las tablas y crear el reporte
            var historial = await (from cita in _context.Citas
                                   join doctor in _context.Doctores on cita.IdDoctor equals doctor.IdDoctor
                                   join servicio in _context.Servicios on cita.IdServicio equals servicio.IdServicio
                                   join consulta in _context.Consultas on cita.IdCita equals consulta.IdCita
                                   where cita.IdPaciente == pacienteId
                                   orderby cita.FechaHora descending // Ordenamos del más reciente al más antiguo
                                   select new HistorialReporteDTO
                                   {
                                       FechaCita = cita.FechaHora,
                                       NombreDoctor = doctor.Nombre + " " + doctor.Apellido,
                                       NombreServicio = servicio.Nombre,
                                       Diagnostico = consulta.Diagnostico,
                                       Tratamiento = consulta.Tratamiento
                                   }).ToListAsync();

            return Ok(historial);
        }
    }
}