namespace HospitalApi.Models
{
    public class Paciente
    {
        public int IdPaciente { get; set; }
        public string Nombre { get; set; }
        public string Apellido { get; set; }
        public DateTime FechaNacimiento { get; set; }
        public string TipoSangre { get; set; }
        public string Dirección { get; set; }
        public string Telefono { get; set; }
        public string CorreoElectronico { get; set; }
    }
}