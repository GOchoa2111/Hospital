using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace HospitalSistemaAPI.Models
{
    [Table("usuario")]
    public class Usuario
    {
        [Key]
        [Column("id_usuario")]
        public int IdUsuario { get; set; }

        [Required]
        [Column("nombre")]
        [StringLength(100)]
        public string Nombre { get; set; } = string.Empty;

        [Required]
        [Column("apellido")]
        [StringLength(100)]
        public string Apellido { get; set; } = string.Empty;

        [Required]
        [Column("nombre_usuario")]
        [StringLength(50)]
        public string NombreUsuario { get; set; } = string.Empty;

        [Required]
        [Column("contrasena")]
        [StringLength(255)]
        public string Contrasena { get; set; } = string.Empty;  

        [Required]
        [Column("id_rol")]
        [StringLength(50)]
        [RegularExpression("^(administrador|medico|recepcionista)$", ErrorMessage = "Rol inválido")]
        public int Rol { get; set; }

        [Required]
        [Column("correo")]
        [StringLength(100)]
        [EmailAddress]
        public string Correo { get; set; } = string.Empty;

        [Column("token_recuperacion")]
        [StringLength(100)]
        public string? TokenRecuperacion { get; set; }

        [Column("fecha_expiracion_token")]
        public DateTime? FechaExpiracionToken { get; set; }

        // Relaciones de navegación (comentadas)
        // public virtual ICollection<Paciente> PacientesCreados { get; set; }
        // public virtual ICollection<Doctor> DoctoresCreados { get; set; }
        // public virtual ICollection<HorarioDoctor> HorariosCreados { get; set; }
        // public virtual ICollection<Consulta> ConsultasRealizadas { get; set; }
        // public virtual ICollection<Factura> FacturasEmitidas { get; set; }
    }
}
