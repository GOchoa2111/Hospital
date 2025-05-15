using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace HospitalSistemaAPI.Models
{
    [Table("servicio")]
    public class Servicio
    {
        [Key]
        [Column("id_servicio")]
        public int IdServicio { get; set; }
        [Column("id_usuario")]
        public int IdUsuario { get; set; }  

        [Column("nombre")]
        public string Nombre { get; set; } = string.Empty;

        [Column("descripcion")]
        [StringLength(255)]
        public string Descripcion { get; set; } = string.Empty;

        [Column("precio")]
        [Range(0, double.MaxValue, ErrorMessage ="El precio debe ser mayor o igual a cero")]
        public decimal Precio { get; set; }

        // Relaciones de navegación (comentadas)
        // public virtual ICollection<Cita> Citas { get; set; }
        // public virtual ICollection<DetalleFactura> DetallesFactura { get; set; }


    }
}
