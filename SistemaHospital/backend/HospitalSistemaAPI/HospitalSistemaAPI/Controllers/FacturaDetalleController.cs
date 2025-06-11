using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using HospitalSistemaAPI.Data;
using HospitalSistemaAPI.DTOs;
using HospitalSistemaAPI.Models;

namespace HospitalSistemaAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class FacturaDetalleController : ControllerBase
    {
        private readonly AppDbContext _context;

        public FacturaDetalleController(AppDbContext context)
        {
            _context = context;
        }

        /// <summary>
        /// Registra una factura con sus detalles en una sola operación.
        /// </summary>
        /// <param name="facturaDto">DTO que contiene la factura y sus detalles</param>
        /// <returns>Resultado de la operación</returns>
        [HttpPost("RegistrarCompleta")]
        public async Task<IActionResult> RegistrarFacturaCompleta([FromBody] FacturaCompletaDTO facturaDto)
        {
            // Validar que el DTO no sea nulo
            if (facturaDto == null || facturaDto.Detalles == null || !facturaDto.Detalles.Any())
            {
                return BadRequest("La factura debe tener al menos un detalle.");
            }

            // Crear la entidad factura
            var factura = new Factura
            {
                Fecha = facturaDto.Fecha,
                IdPaciente = facturaDto.IdPaciente,
                IdUsuario = facturaDto.IdUsuario,
                Total = facturaDto.Total
            };

            // Agregar la factura a la base de datos
            _context.Facturas.Add(factura);
            await _context.SaveChangesAsync();

            // Recorrer los detalles y agregarlos con el ID de la factura recién guardada
            foreach (var detalleDto in facturaDto.Detalles)
            {
                // Verificar si el servicio existe
                var servicioExiste = await _context.Servicios.AnyAsync(s => s.IdServicio == detalleDto.IdServicio);
                if (!servicioExiste)
                {
                    return BadRequest($"El servicio con ID {detalleDto.IdServicio} no existe.");
                }

                var detalle = new DetalleFactura
                {
                    IdFactura = factura.IdFactura,
                    IdServicio = detalleDto.IdServicio,
                    Cantidad = detalleDto.Cantidad,
                    Subtotal = detalleDto.Subtotal
                };

                _context.DetallesFactura.Add(detalle);
            }

            // Guardar los detalles
            await _context.SaveChangesAsync();

            // Retornar la factura creada
            return Ok(new
            {
                mensaje = "Factura registrada correctamente",
                factura.IdFactura
            });
        }
    }
}
