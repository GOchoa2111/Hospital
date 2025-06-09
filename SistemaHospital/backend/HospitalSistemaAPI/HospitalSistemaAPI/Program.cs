using Microsoft.EntityFrameworkCore;
using HospitalSistemaAPI.Data;
using HospitalSistemaAPI.Configuration;
using Microsoft.AspNetCore.Authentication.JwtBearer;  // Para autenticación JWT
using Microsoft.IdentityModel.Tokens;                  // Para validación de tokens
using System.Text;
using HospitalSistemaAPI.Middleware;
using HospitalSistemaAPI.Services;                                      // Para codificar la clave secreta

var builder = WebApplication.CreateBuilder(args);

// 1. Configurar para que la clase JwtSettings lea los valores de "Jwt" en appsettings.json
builder.Services.Configure<JwtSettings>(builder.Configuration.GetSection("Jwt"));

//configurar envio de correo electronico
builder.Services.Configure<SmtpSettings>(builder.Configuration.GetSection("SmtpSettings"));
builder.Services.AddTransient<EmailService>();

// 2. Obtener la configuración Jwt para usarla más adelante
var jwtSettings = builder.Configuration.GetSection("Jwt").Get<JwtSettings>();
var key = Encoding.UTF8.GetBytes(jwtSettings.Key);  // Convertir la clave secreta a bytes

// 3. Agregar el servicio de autenticación JWT para validar tokens en las peticiones
builder.Services.AddAuthentication(options =>
{
    // Configurar esquema predeterminado de autenticación como JWT Bearer
    options.DefaultAuthenticateScheme = JwtBearerDefaults.AuthenticationScheme;
    options.DefaultChallengeScheme = JwtBearerDefaults.AuthenticationScheme;
})
.AddJwtBearer(options =>
{
    // Parámetros para validar el token JWT entrante
    options.TokenValidationParameters = new TokenValidationParameters
    {
        ValidateIssuer = true,                // Validar el emisor (Issuer)
        ValidateAudience = true,              // Validar el destinatario (Audience)
        ValidateLifetime = true,              // Validar que el token no esté expirado
        ValidateIssuerSigningKey = true,      // Validar la firma con la clave secreta
        ValidIssuer = jwtSettings.Issuer,    // Emisor válido según configuración
        ValidAudience = jwtSettings.Audience,// Audiencia válida según configuración
        IssuerSigningKey = new SymmetricSecurityKey(key) // Clave para validar firma
    };
});

// 4. Registrar servicios MVC para los controladores (API REST)
builder.Services.AddControllers();

// 5. Configurar Swagger para documentar la API (solo en desarrollo)
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

// 6. Configurar conexión a base de datos SQL Server
builder.Services.AddDbContext<AppDbContext>(options =>
    options.UseSqlServer(builder.Configuration.GetConnectionString("DefaultConnection")));

var app = builder.Build();

// 7. Middleware para habilitar Swagger solo en desarrollo
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.UseHttpsRedirection();  // Redirigir HTTP a HTTPS

// 8. Importante: habilitar middleware de autenticación
app.UseAuthentication();    // Aquí se activa la validación del token en cada petición

app.UseAuthorization();     // Middleware para autorizaciones basadas en roles o políticas

app.UseJwtLogging(); // Middleware personalizado para registrar información del JWT en consola

app.MapControllers();       // Mapear rutas a los controladores (endpoints de API)

app.Run();                  // Ejecutar la aplicación
