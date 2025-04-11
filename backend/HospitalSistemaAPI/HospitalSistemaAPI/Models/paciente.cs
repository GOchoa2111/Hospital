using System;

namespace HospitalSistemaAPI.Models
{
    public class Paciente
    {
        public required int idPaciente { get; set; }
        public required string Nombre { get; set; }
        public required string Genero { get; set; }
        public required string FechaIngreso { get; set;}
        public required string TipoSangre  {get; set;}
        public required string Direccion { get; set; }
        public required string Telefono { get; set; }
        public required string CorreoElectronico { get; set; }

    }

}