using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace HospitalSistemaAPI.Models
{
    [Table("detalle_factura")]
    public class DetalleFactura
    {
        [Key]
        [Column("id_detalle")]
        public int IdDetalle { get; set; }

        [Required]
        [Column("id_factura")]
        public int IdFactura { get; set; }

        [Required]
        [Column("id_servicio")]
        public int IdServicio { get; set; }

        [Required]
        [Column("cantidad")]
        [Range(1, int.MaxValue, ErrorMessage = "La cantidad debe ser mayor que cero.")]
        public int Cantidad { get; set; }

        [Required]
        [Column("subtotal")]
        [Range(0, double.MaxValue, ErrorMessage = "El subtotal debe ser un valor positivo.")]
        public decimal Subtotal { get; set; }  // subtotal validar en front end para evitar errores de contenido

        // Relaciones de navegación (comentadas)
        // public virtual Factura Factura { get; set; }
        // public virtual Servicio Servicio { get; set; }
    }
}
