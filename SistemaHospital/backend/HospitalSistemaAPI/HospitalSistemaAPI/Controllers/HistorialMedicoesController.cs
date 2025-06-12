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

namespace HospitalSistemaAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    //[Authorize]
    public class HistorialMedicoesController : ControllerBase
    {
        private readonly AppDbContext _context;

        public HistorialMedicoesController(AppDbContext context)
        {
            _context = context;
        }

        // GET: api/HistorialMedicoes
        [HttpGet]
        public async Task<ActionResult<IEnumerable<HistorialMedico>>> GetHistorialesMedicos()
        {
            return await _context.HistorialesMedicos.ToListAsync();
        }

        // GET: api/HistorialMedicoes/5
        [HttpGet("{id}")]
        public async Task<ActionResult<HistorialMedico>> GetHistorialMedico(int id)
        {
            var historialMedico = await _context.HistorialesMedicos.FindAsync(id);

            if (historialMedico == null)
            {
                return NotFound();
            }

            return historialMedico;
        }

        // PUT: api/HistorialMedicoes/5
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPut("{id}")]
        public async Task<IActionResult> PutHistorialMedico(int id, HistorialMedico historialMedico)
        {
            if (id != historialMedico.IdHistorial)
            {
                return BadRequest();
            }

            _context.Entry(historialMedico).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!HistorialMedicoExists(id))
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

        // POST: api/HistorialMedicoes
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPost]
        public async Task<ActionResult<HistorialMedico>> PostHistorialMedico(HistorialMedico historialMedico)
        {
            _context.HistorialesMedicos.Add(historialMedico);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetHistorialMedico", new { id = historialMedico.IdHistorial }, historialMedico);
        }

        // DELETE: api/HistorialMedicoes/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteHistorialMedico(int id)
        {
            var historialMedico = await _context.HistorialesMedicos.FindAsync(id);
            if (historialMedico == null)
            {
                return NotFound();
            }

            _context.HistorialesMedicos.Remove(historialMedico);
            await _context.SaveChangesAsync();

            return NoContent();
        }

        private bool HistorialMedicoExists(int id)
        {
            return _context.HistorialesMedicos.Any(e => e.IdHistorial == id);
        }
    }
}
