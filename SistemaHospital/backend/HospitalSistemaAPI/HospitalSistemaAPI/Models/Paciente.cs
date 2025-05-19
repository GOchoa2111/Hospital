using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace HospitalSistemaAPI.Models
{
    [Table("paciente")]
    public class Paciente
    {
        [Key]
        [Column("id_paciente")]
        public int IdPaciente { get; set; }
        public string Nombre { get; set; } = string.Empty;
        [Column("apellido")]
        public string Apellido {  get; set; } = string.Empty;
        [Column("fecha_nacimiento")]
        public DateOnly FechaNacimiento { get; set; }
        [Column("genero")]
        public string Genero { get; set; } = string.Empty;
        [Column("tipo_sangre")]
        public string TipoSangre { get; set; } = string.Empty;
        
        [Column("direccion")]
        public string Direccion { get; set; } = string.Empty;
        [Column("telefono")]
        public string Telefono { get; set; } = string.Empty;
        [Column("correo")]
        [EmailAddress]
        public string Correo { get; set; } = string.Empty;
        [Column("creado_por")]
        public int CreadoPor { get; set; }
        [Column("fecha_creacion")]
        public DateOnly FechaCreacion { get; set; }
        [Column("estado")]
        public bool Estado { get; set; } = true;



        // Relaciones de navegación (comentadas)
        // public virtual Usuario UsuarioCreador { get; set; }
        // public virtual ICollection<Cita> Citas { get; set; }
        // public virtual ICollection<HistorialMedico> Historiales { get; set; }
        // public virtual ICollection<Factura> Facturas { get; set; }

    }

}