public class UsuarioDTO
{
    public int IdUsuario { get; set; }
    public string Nombre { get; set; }
    public string Apellido { get; set; }
    public string NombreUsuario { get; set; }
    public string Contrasena { get; set; }
    public int RolId { get; set; }
    public string Correo { get; set; }
    public string TokenRecuperacion { get; set; }
    public DateTime? FechaExpiracionToken { get; set; }
}
