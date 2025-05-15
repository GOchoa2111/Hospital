using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace HospitalSistemaAPI.Models
{
    [Table("historial_medico")]
    public class HistorialMedico
    {
        [Key]
        [Column("id_historial")]
        public int IdHistorial { get; set; }

        [Required]
        [Column("id_paciente")]
        public int IdPaciente { get; set; }

        [Column("descripcion")]
        [StringLength(500)]
        public string? Descripcion { get; set; }

        [Required]
        [Column("fecha")]
        public DateTime Fecha { get; set; }

        // Relaciones de navegación (comentadas)
        // public virtual Paciente Paciente { get; set; }
    }
}
