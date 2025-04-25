using Microsoft.EntityFrameworkCore;

namespace HospitalSistemaAPI.Models
{
    public class AppDbContext : DbContext
    {
        public AppDbContext(DbContextOptions options) : base(options) { }

        public DbSet<Paciente> pacientes { get; set; }
       
    }
}
