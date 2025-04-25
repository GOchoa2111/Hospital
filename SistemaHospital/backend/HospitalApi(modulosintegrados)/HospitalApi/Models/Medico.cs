namespace HospitalApi.Models
{
    public class Medico
    {
        public int IdMedico { get; set; }
        public string Nombre { get; set; }
        public string Apellido { get; set; }
        public string Id_Especialidad { get; set; }
        public string Telefono { get; set; }
        public string CorreoElectronico { get; set; }
        public int Colegiado { get; set; }
        public string HorariosAtencion { get; set; }

        public ICollection<CitasProgramadas> CitasProgramadas { get; set; }
        public ICollection<HistorialClinico> HistorialClinico { get; set; }
    }
}