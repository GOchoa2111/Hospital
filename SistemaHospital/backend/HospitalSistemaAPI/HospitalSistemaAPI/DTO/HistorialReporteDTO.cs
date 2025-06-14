// DTOs/HistorialReporteDTO.cs
namespace HospitalSistemaAPI.DTOs
{
    public class HistorialReporteDTO
    {
        public DateTime FechaCita { get; set; }
        public string NombreDoctor { get; set; }
        public string NombreServicio { get; set; }
        public string Diagnostico { get; set; }
        public string Tratamiento { get; set; }
    }
}