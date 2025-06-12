using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace HospitalSistemaAPI.Models

{
    [Table("factura")]
    public class Factura
    {
        public Factura()
        {
            Fecha = DateTime.Now.Date; // Asigna la fecha actual sin la hora,
                                       // para evitar icompatibilidad en el formato
        }

        [Key]
        [Column("id_factura")]
        public int IdFactura { get; set; }

        [Column("fecha")]
        public DateTime Fecha { get;  set; }

        [Required]
        [Column("id_usuario")]
        public int IdUsuario { get; set; }

        [Required]
        [Column("id_paciente")]
        public int IdPaciente { get; set; }

        [Range(0, double.MaxValue, ErrorMessage = "El total del valor debe ser positio. ")] //validar que el valor del total sea positivo
        [Column("total")]
        public decimal Total { get; set; }
        [Column("estado")]
        public bool Estado { get; set; } = true; // Estado de la factura (Activo/Inactivo), por defecto activo

        //relaciones con usuario y paciente

        //[ForeignKey("IdUsuario")]
        //public Usuario usuario { get; set; } = new Usuario(); //clase no creada aún

        //[ForeignKey("IdPaciente")]
        //public Paciente paciente { get; set; } = new Paciente();

    }
}


