using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using HospitalSistemaAPI.Data;
using HospitalSistemaAPI.DTOs;
using HospitalSistemaAPI.Models;
using Microsoft.AspNetCore.Authorization;

namespace HospitalSistemaAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    [Authorize] // Asegúrate de que el token de autenticación sea válido y se esté enviando
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

            try
            {
                await _context.SaveChangesAsync(); // Primera llamada a SaveChanges: guarda la factura principal
            }
            catch (DbUpdateException ex) // Captura excepciones relacionadas con la base de datos
            {
                // Este bloque capturará el error exacto de la base de datos
                Console.WriteLine($"DbUpdateException durante el primer guardado de factura: {ex.Message}");
                if (ex.InnerException != null)
                {
                    Console.WriteLine($"Inner Exception: {ex.InnerException.Message}");
                    // Puedes devolver el mensaje de la InnerException que a menudo es más específico
                    return StatusCode(500, $"Error al guardar la factura principal: {ex.InnerException.Message}");
                }
                return StatusCode(500, $"Error desconocido al guardar la factura principal: {ex.Message}");
            }
            catch (Exception ex) // Captura otras excepciones inesperadas
            {
                Console.WriteLine($"Excepción inesperada durante el primer guardado de factura: {ex.Message}");
                return StatusCode(500, $"Error inesperado al guardar la factura principal: {ex.Message}");
            }

            // Recorrer los detalles y agregarlos con el ID de la factura recién guardada
            foreach (var detalleDto in facturaDto.Detalles)
            {
                // Verificar si el servicio existe
                var servicioExiste = await _context.Servicios.AnyAsync(s => s.IdServicio == detalleDto.IdServicio);
                if (!servicioExiste)
                {
                    // Si el servicio no existe, se revierte la transacción implícita si ya se guardó la factura principal
                    // o se limpia el contexto si es necesario. Para una API simple, un BadRequest puede ser suficiente.
                    return BadRequest($"El servicio con ID {detalleDto.IdServicio} no existe.");
                }

                var detalle = new DetalleFactura
                {
                    IdFactura = factura.IdFactura, // Usa el ID de la factura que acaba de ser guardada
                    IdServicio = detalleDto.IdServicio,
                    Cantidad = detalleDto.Cantidad,
                    Subtotal = detalleDto.Subtotal
                };

                _context.DetallesFactura.Add(detalle);
            }

            try
            {
                await _context.SaveChangesAsync(); // Segunda llamada a SaveChanges: guarda los detalles
            }
            catch (DbUpdateException ex)
            {
                Console.WriteLine($"DbUpdateException durante el guardado de detalles: {ex.Message}");
                if (ex.InnerException != null)
                {
                    Console.WriteLine($"Inner Exception: {ex.InnerException.Message}");
                    return StatusCode(500, $"Error al guardar los detalles de la factura: {ex.InnerException.Message}");
                }
                return StatusCode(500, $"Error desconocido al guardar los detalles: {ex.Message}");
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Excepción inesperada durante el guardado de detalles: {ex.Message}");
                return StatusCode(500, $"Error inesperado al guardar los detalles: {ex.Message}");
            }

            // Retornar la factura creada
            return Ok(new
            {
                mensaje = "Factura registrada correctamente",
                factura.IdFactura
            });
        }
    }
}