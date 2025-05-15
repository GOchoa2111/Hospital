using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace HospitalSistemaAPI.Models
{
    [Table("consulta")]
    public class Consulta
    {
        [Key]
        [Column("id_consulta")]
        public int IdConsulta { get; set; }

        [Column("diagnostico")]
        [StringLength(500)]
        public string? Diagnostico { get; set; }

        [Column("tratamiento")]
        [StringLength(500)]
        public string? Tratamiento { get; set; }

        [Required]
        [Column("id_cita")]
        public int IdCita { get; set; }

        [Column("id_usuario")]
        public int? IdUsuario { get; set; }

        // Relaciones de navegación (comentadas)
        // public virtual Cita Cita { get; set; }
        // public virtual Usuario Usuario { get; set; }
    }
}
