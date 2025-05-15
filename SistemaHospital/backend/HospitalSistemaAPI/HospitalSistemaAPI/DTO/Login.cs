using System.ComponentModel.DataAnnotations;

namespace HospitalSistemaAPI.DTOs
{
    public class LoginDTO
    {
        [Required(ErrorMessage = "El nombre de usuario es obligatorio.")]
        [StringLength(50, ErrorMessage = "El nombre de usuario no puede superar los 50 caracteres.")]
        public string NombreUsuario { get; set; } = string.Empty;

        [Required(ErrorMessage = "La contraseña es obligatoria.")]
        [StringLength(255, ErrorMessage = "La contraseña no puede superar los 255 caracteres.")]
        public string Contrasena { get; set; } = string.Empty;
    }
}
