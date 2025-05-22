using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace HospitalSistemaAPI.Models
{
    [Table("cita")]
    public class Cita
    {
        [Key]
        [Column("id_cita")]
        public int IdCita { get; set; }

        [Required]
        [Column("fecha_hora")]
        public DateTime FechaHora { get; set; }

        [Required]
        [Column("id_servicio")]
        public int IdServicio { get; set; }

        [Required]
        [Column("id_paciente")]
        public int IdPaciente { get; set; }

        [Required]
        [Column("id_doctor")]
        public int IdDoctor { get; set; }

        // Propiedades de navegación (Relaciones de las tablas)
        [ForeignKey("IdServicio")]
        public virtual Servicio? Servicio { get; set; }
        [ForeignKey("IdPaciente")]
        public virtual Paciente? Paciente { get; set; }
        [ForeignKey("IdDoctor")]
        public virtual Doctor? Doctor { get; set; }

        // public virtual Consulta? Consulta { get; set; } // relación uno a uno con consulta
    }
}
