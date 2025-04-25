using HospitalSistemaAPI.Models;
using Microsoft.EntityFrameworkCore;

namespace HospitalSistemaAPI.Data
{
    public class AppDbContext : DbContext
    {
        public AppDbContext(DbContextOptions options) : base(options) { }

        public DbSet<Paciente> pacientes { get; set; }
        public DbSet<Servicio> Servicios { get; set; }  

    }
}
