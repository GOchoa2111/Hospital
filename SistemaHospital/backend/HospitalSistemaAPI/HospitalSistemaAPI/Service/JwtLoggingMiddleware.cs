using Microsoft.AspNetCore.Http;
using System.Linq;
using System.Threading.Tasks;
using System;

namespace HospitalSistemaAPI.Middleware
{
    public class JwtLoggingMiddleware
    {
        private readonly RequestDelegate _next;

        public JwtLoggingMiddleware(RequestDelegate next)
        {
            _next = next;
        }

        public async Task InvokeAsync(HttpContext context)
        {
            var token = context.Request.Headers["Authorization"].FirstOrDefault();

            if (!string.IsNullOrEmpty(token))
            {
                Console.WriteLine($"Token recibido: {token}");

                if (context.User.Identity?.IsAuthenticated == true)
                {
                    var username = context.User.Identity.Name;
                    var role = context.User.FindFirst("role")?.Value;

                    Console.WriteLine($"Usuario autenticado: {username}");
                    Console.WriteLine($"Rol: {role}");
                }
            }
            else
            {
                Console.WriteLine("No se envió token.");
            }

            await _next(context);
        }
    }

    // Clase de extensión para usarlo en Program.cs de forma elegante
    public static class JwtLoggingMiddlewareExtensions
    {
        public static IApplicationBuilder UseJwtLogging(this IApplicationBuilder builder)
        {
            return builder.UseMiddleware<JwtLoggingMiddleware>();
        }
    }
}
