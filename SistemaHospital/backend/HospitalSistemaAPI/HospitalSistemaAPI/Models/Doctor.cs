using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace HospitalSistemaAPI.Models
{
    [Table("doctor")]
    public class Doctor
    {
        [Key]
        [Column("id_doctor")]
        public int IdDoctor { get; set; }

        [Required]
        [Column("nombre")]
        [StringLength(100)]
        public string Nombre { get; set; } = string.Empty;

        [Required]
        [Column("apellido")]
        [StringLength(100)]
        public string Apellido { get; set; } = string.Empty;

        [Required]
        [Column("especialidad")]
        [StringLength(100)]
        public string Especialidad { get; set; } = string.Empty;

        [Column("telefono")]
        [StringLength(20)]
        public string? Telefono { get; set; }

        [Column("correo")]
        [StringLength(100)]
        [EmailAddress]
        public string? Correo { get; set; }

        [Column("id_usuario")]
        public int? IdUsuario { get; set; }

        [Required]
        [Column("creado_por")]
        public int CreadoPor { get; set; }

        [Required]
        [Column("fecha_creacion")]
        public DateTime FechaCreacion { get; set; } = DateTime.Now;

        [Column("estado")]
        public bool Estado { get; set; } = true;

        // Relaciones de navegación (comentadas)
        // public virtual Usuario UsuarioVinculado { get; set; }
        // public virtual Usuario UsuarioCreador { get; set; }
        // public virtual ICollection<HorarioDoctor> Horarios { get; set; }
        // public virtual ICollection<Cita> Citas { get; set; }
    }
}
