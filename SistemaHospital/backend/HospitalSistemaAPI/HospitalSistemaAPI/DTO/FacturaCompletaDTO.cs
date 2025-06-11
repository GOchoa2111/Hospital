using System;
using System.Collections.Generic;

namespace HospitalSistemaAPI.DTOs
{
    // DTO para representar cada fila del detalle de la factura
    public class DetalleFacturaDTO
    {
        // ID del servicio que se está facturando
        public int IdServicio { get; set; }

        // Cantidad del servicio prestado
        public int Cantidad { get; set; }

        // Subtotal calculado por cada servicio (precio x cantidad)
        public decimal Subtotal { get; set; }
    }

    // DTO principal para registrar una factura completa con sus detalles
    public class FacturaCompletaDTO
    {
        // Fecha en que se emite la factura
        public DateTime Fecha { get; set; }

        // ID del paciente que recibe la factura
        public int IdPaciente { get; set; }

        // ID del usuario (administrativo o doctor) que registra la factura
        public int IdUsuario { get; set; }

        // Total de la factura (la suma de todos los subtotales)
        public decimal Total { get; set; }

        // Lista de todos los servicios incluidos en esta factura
        public List<DetalleFacturaDTO> Detalles { get; set; }
    }
}
