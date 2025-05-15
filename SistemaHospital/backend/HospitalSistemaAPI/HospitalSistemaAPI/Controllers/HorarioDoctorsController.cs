using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using HospitalSistemaAPI.Data;
using HospitalSistemaAPI.Models;

namespace HospitalSistemaAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class HorarioDoctorsController : ControllerBase
    {
        private readonly AppDbContext _context;

        public HorarioDoctorsController(AppDbContext context)
        {
            _context = context;
        }

        // GET: api/HorarioDoctors
        [HttpGet]
        public async Task<ActionResult<IEnumerable<HorarioDoctor>>> GetHorariosDoctor()
        {
            return await _context.HorariosDoctor.ToListAsync();
        }

        // GET: api/HorarioDoctors/5
        [HttpGet("{id}")]
        public async Task<ActionResult<HorarioDoctor>> GetHorarioDoctor(int id)
        {
            var horarioDoctor = await _context.HorariosDoctor.FindAsync(id);

            if (horarioDoctor == null)
            {
                return NotFound();
            }

            return horarioDoctor;
        }

        // PUT: api/HorarioDoctors/5
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPut("{id}")]
        public async Task<IActionResult> PutHorarioDoctor(int id, HorarioDoctor horarioDoctor)
        {
            if (id != horarioDoctor.IdHorario)
            {
                return BadRequest();
            }

            _context.Entry(horarioDoctor).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!HorarioDoctorExists(id))
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

        // POST: api/HorarioDoctors
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPost]
        public async Task<ActionResult<HorarioDoctor>> PostHorarioDoctor(HorarioDoctor horarioDoctor)
        {
            _context.HorariosDoctor.Add(horarioDoctor);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetHorarioDoctor", new { id = horarioDoctor.IdHorario }, horarioDoctor);
        }

        // DELETE: api/HorarioDoctors/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteHorarioDoctor(int id)
        {
            var horarioDoctor = await _context.HorariosDoctor.FindAsync(id);
            if (horarioDoctor == null)
            {
                return NotFound();
            }

            _context.HorariosDoctor.Remove(horarioDoctor);
            await _context.SaveChangesAsync();

            return NoContent();
        }

        private bool HorarioDoctorExists(int id)
        {
            return _context.HorariosDoctor.Any(e => e.IdHorario == id);
        }
    }
}
