using HospitalSistemaAPI.Data;
using HospitalSistemaAPI.Models;
using Microsoft.AspNetCore.Mvc;

namespace HospitalSistemaAPI.Controllers
{

    
    [Route("api/[controller]")]
    [ApiController]
    public class PacienteController : ControllerBase
    {
        private readonly HospitalContext _context;

        public PacienteController(HospitalContext context)
        {

            _context = context;
        }

        //POST: api / pacientes

        [HttpPost]
        public async Task<ActionResult<Paciente>> PostPaceinte(Paciente paciente)
        {
            _context.Paciente.Add(paciente);
            await _context.SaveChangesAsync();
            return CreatedAtAction(nameof(GetPaciente), new { id = paciente.idPaciente });
        }

        //GET: api/pacientes/5

        [HttpGet("{id}")]
        public async Task<ActionResult<Paciente>> GetPaciente(int id)
        {
            var paciente = await _context.Paciente.FindAsync(id);
            if (paciente == null)
            {
                return NotFound();
              
            } return Ok(paciente);

        }
    }

}
