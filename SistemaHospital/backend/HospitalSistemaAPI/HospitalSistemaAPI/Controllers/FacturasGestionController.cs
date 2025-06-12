using HospitalSistemaAPI.Data;
using HospitalSistemaAPI.DTOs;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace HospitalSistemaAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    //[Authorize]
    public class FacturasGestionController : ControllerBase
    {
        private readonly AppDbContext _context;

        public FacturasGestionController(AppDbContext context)
        {
            _context = context;
        }



        // GET: api/FacturasGestion
        [HttpGet]
        public async Task<ActionResult<List<FacturaGestionDto>>> GetAllFacturas()
        {
            var facturas = await (from f in _context.Facturas
                                  join p in _context.pacientes on f.IdPaciente equals p.IdPaciente
                                  join u in _context.Usuarios on f.IdUsuario equals u.IdUsuario
                                  select new FacturaGestionDto
                                  {
                                      IdFactura = f.IdFactura,
                                      Fecha = f.Fecha,
                                      IdUsuario = f.IdUsuario,
                                      IdPaciente = f.IdPaciente,
                                      Total = f.Total,
                                      NombrePaciente = p.Nombre,
                                      ApellidoPaciente = p.Apellido,
                                      NombreUsuario = u.Nombre,
                                      ApellidoUsuario = u.Apellido,
                                      Estado = f.Estado ? "Activo" : "Inactivo"
                                  }).ToListAsync();

            return Ok(facturas);
        }




        // GET: api/FacturasGestion/5
        [HttpGet("{id}")]
        public async Task<ActionResult<FacturaGestionDto>> GetFacturaGestion(int id)
        {
            // Consulta para obtener la factura con datos del paciente y usuario
            var facturaData = await (from f in _context.Facturas
                                     join p in _context.pacientes on f.IdPaciente equals p.IdPaciente
                                     join u in _context.Usuarios on f.IdUsuario equals u.IdUsuario
                                     where f.IdFactura == id
                                     select new FacturaGestionDto
                                     {
                                         IdFactura = f.IdFactura,
                                         Fecha = f.Fecha,
                                         IdUsuario = f.IdUsuario,
                                         IdPaciente = f.IdPaciente,
                                         Total = f.Total,
                                         NombrePaciente = p.Nombre,
                                         ApellidoPaciente = p.Apellido,
                                         NombreUsuario = u.Nombre,
                                         ApellidoUsuario = u.Apellido,
                                         Estado = f.Estado ? "Activo" : "Inactivo" // Nuevo campo estado (bit)
                                     }).FirstOrDefaultAsync();

            if (facturaData == null)
            {
                return NotFound();
            }

            // Consulta para obtener los detalles con datos del servicio
            var detalles = await (from d in _context.DetallesFactura
                                  join s in _context.Servicios on d.IdServicio equals s.IdServicio
                                  where d.IdFactura == id
                                  select new DetalleDto
                                  {
                                      IdDetalle = d.IdDetalle,
                                      IdServicio = s.IdServicio,
                                      Cantidad = d.Cantidad,
                                      Subtotal = d.Subtotal,
                                      NombreServicio = s.Nombre,
                                      PrecioServicio = s.Precio
                                  }).ToListAsync();

            facturaData.Detalles = detalles;

            // Calcular subtotal e IVA
            decimal total = facturaData.Total;
            decimal iva = Math.Round(total / 1.12m * 0.12m, 2); // Asumiendo IVA del 12%
            decimal subtotal = Math.Round(total - iva, 2);

            facturaData.Subtotal = subtotal;
            facturaData.Iva = iva;
            facturaData.Total = total;

            return Ok(facturaData);
        }

        // PUT: api/FacturasGestion/Anular/5
        // Endpoint para anular (cambiar estado) de una factura
        [HttpPut("Anular/{id}")]
        public async Task<IActionResult> AnularFactura(int id)
        {
            var factura = await _context.Facturas.FindAsync(id);
            if (factura == null)
            {
                return NotFound();
            }

            // Cambiar estado a false (anulado)
            factura.Estado = false;

            _context.Entry(factura).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!_context.Facturas.Any(e => e.IdFactura == id))
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
    }
}