using HospitalApi.Data;
using Microsoft.EntityFrameworkCore;

var builder = WebApplication.CreateBuilder(args);

// Registrar el contexto de la base de datos con la cadena de conexión
builder.Services.AddDbContext<HospitalContext>(options =>
    options.UseSqlServer(builder.Configuration.GetConnectionString("HospitalConnection")));

builder.Services.AddControllers();

var app = builder.Build();

app.MapControllers();

app.Run();