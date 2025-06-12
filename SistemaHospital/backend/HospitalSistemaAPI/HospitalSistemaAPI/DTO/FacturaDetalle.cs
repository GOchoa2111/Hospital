// FacturaDetalleDto.cs
namespace HospitalSistemaAPI.DTOs
{
    public class FacturaDetalleDto
    {
        public int IdFactura { get; set; }
        public DateTime Fecha { get; set; }
        public int IdUsuario { get; set; }
        public int IdPaciente { get; set; }
        public decimal Total { get; set; }

        // Datos del paciente
        public string NombrePaciente { get; set; }
        public string ApellidoPaciente { get; set; }

        // Lista de detalles
        public List<DetalleDto> Detalles { get; set; } = new List<DetalleDto>();
    }

    public class DetalleDto
    {
        public int IdDetalle { get; set; }
        public int IdServicio { get; set; }
        public int Cantidad { get; set; }
        public decimal Subtotal { get; set; }

        // Datos del servicio
        public string NombreServicio { get; set; }
        public decimal PrecioServicio { get; set; }
    }
}