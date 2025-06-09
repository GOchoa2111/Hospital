using System.ComponentModel.DataAnnotations;

public class EmailRequestDTO
{
    [Required]
    [EmailAddress]
    public string Destinatario { get; set; } = string.Empty;

    [Required]
    public string Asunto { get; set; } = string.Empty;

    [Required]
    public string Mensaje { get; set; } = string.Empty;
}
