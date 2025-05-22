namespace HospitalSistemaAPI.DTO
{
    namespace TecnoSolucionesAPI.DTOs
    {
        public class CitaDetalleDTO
        {
            public int IdCita { get; set; }
            public DateTime FechaHora { get; set; }
            public string? Servicio { get; set; }
            public string? Paciente { get; set; }
            public string? Doctor { get; set; }
        }
    }

}
