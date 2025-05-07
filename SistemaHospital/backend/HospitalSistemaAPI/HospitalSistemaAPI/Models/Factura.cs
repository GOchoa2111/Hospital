using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace HospitalSistemaAPI.Models

{
    [Table("factura")]
    public class Factura
    {

        [Key]
        [Column("id_factura")]
        public int IdFactura { get; set; }

        [Column("fecha")]
        public DateOnly Fecha { get; private set; }

        [Required]
        [Column("id_usuario")]
        public int IdUsuario { get; set; }

        [Required]
        [Column("id_paciente")]
        public int IdPaciente { get; set; }

        [Range(0, double.MaxValue, ErrorMessage = "El total del valor debe ser positio. ")] //validar que el valor del total sea positivo
        [Column("total")]
        public decimal Total { get; set; }

        //relaciones con usuario y paciente

        //[ForeignKey("IdUsuario")]
        //public Usuario usuario { get; set; } = new Usuario(); //clase no creada aún

        //[ForeignKey("IdPaciente")]
        //public Paciente paciente { get; set; } = new Paciente();

        public Factura()
        {
           Fecha = DateOnly.FromDateTime(DateTime.Now); //enviar fecha actual
        }

    }
}


