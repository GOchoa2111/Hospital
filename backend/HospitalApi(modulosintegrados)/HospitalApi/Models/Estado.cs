namespace HospitalApi.Models
{
    public class Estado
    {
        public int IdEstado { get; set; }
        public string Estadocol { get; set; }

        public ICollection<CitasProgramadas> CitasProgramadas { get; set; }
        public ICollection<Facturacion> Facturas { get; set; }
    }
}
