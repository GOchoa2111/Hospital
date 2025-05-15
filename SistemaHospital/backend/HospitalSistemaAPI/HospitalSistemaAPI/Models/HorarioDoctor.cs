using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace HospitalSistemaAPI.Models
{
    [Table("horario_doctor")]
    public class HorarioDoctor
    {
        [Key]
        [Column("id_horario")]
        public int IdHorario { get; set; }

        [Required]
        [Column("id_doctor")]
        public int IdDoctor { get; set; }

        [Required]
        [Column("dia_semana")]
        [StringLength(15)]
        public string DiaSemana { get; set; } = string.Empty;

        [Required]
        [Column("hora_inicio")]
        public TimeSpan HoraInicio { get; set; }

        [Required]
        [Column("hora_fin")]
        public TimeSpan HoraFin { get; set; }

        [Required]
        [Column("creado_por")]
        public int CreadoPor { get; set; }

        [Required]
        [Column("fecha_creacion")]
        public DateTime FechaCreacion { get; set; }

        // Relaciones de navegación (comentadas)
        // public virtual Doctor Doctor { get; set; }
        // public virtual Usuario UsuarioCreador { get; set; }
    }
}
