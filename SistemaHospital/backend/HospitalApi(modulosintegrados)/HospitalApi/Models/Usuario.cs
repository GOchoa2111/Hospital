namespace HospitalApi.Models
{
    public class Usuario
    {
        public int IdUsuarios { get; set; }
        public string Nombre { get; set; }
        public string UsuarioNombre { get; set; }
        public string Contraseña { get; set; }

        public ICollection<Rol> Roles { get; set; }
    }
}
