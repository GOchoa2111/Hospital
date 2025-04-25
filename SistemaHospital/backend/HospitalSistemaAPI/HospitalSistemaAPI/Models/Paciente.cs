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
        [Column("fecha_ingreso")]
        public DateOnly FechaIngreso { get; set; }
        [Column("nombre")]
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
        public string CorreoElectronico { get; set; } = string.Empty;

    }

}