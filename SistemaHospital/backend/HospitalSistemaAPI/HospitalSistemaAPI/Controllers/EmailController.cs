using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Options;
using HospitalSistemaAPI.Data;
using HospitalSistemaAPI.DTOs;
using System.Net.Mail;
using System.Net;
using System.Linq;
using Microsoft.AspNetCore.Authorization;



[ApiController]
[Route("api/[controller]")]
//[Authorize]
public class EmailController : ControllerBase
{
    private readonly AppDbContext _context;
    private readonly SmtpSettings _smtpSettings;

    public EmailController(AppDbContext context, IOptions<SmtpSettings> smtpSettings)
    {
        _context = context;
        _smtpSettings = smtpSettings.Value;
    }

    [HttpPost("send")]
    public IActionResult SendEmail([FromBody] EmailRequestDTO request)
    {
        // Validar si el correo está en alguna tabla
        bool correoExiste = _context.Usuarios.Any(u => u.Correo == request.Destinatario) ||
                            _context.pacientes.Any(p => p.Correo == request.Destinatario);
                            

        if (!correoExiste)
        {
            return BadRequest(new { message = "El correo no está registrado en el sistema." });
        }

        try
        {
            var smtpClient = new SmtpClient(_smtpSettings.SmtpHost)
            {
                Port = _smtpSettings.SmtpPort,
                Credentials = new NetworkCredential(_smtpSettings.SmtpUser, _smtpSettings.SmtpPass),
                EnableSsl = true,
            };

            var mailMessage = new MailMessage
            {
                From = new MailAddress(_smtpSettings.SmtpUser),
                Subject = request.Asunto,
                Body = request.Mensaje,
                IsBodyHtml = false,
            };

            mailMessage.To.Add(request.Destinatario);
            smtpClient.Send(mailMessage);

            return Ok(new { message = "Correo enviado exitosamente" });
        }
        catch (Exception ex)
        {
            return StatusCode(500, new { message = "Error enviando el correo", error = ex.Message });
        }
    }
}
