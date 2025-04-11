
using HospitalSistemaAPI.Models;
using Microsoft.EntityFrameworkCore;
namespace HospitalSistemaAPI.Data
{
    public class HospitalContext : DbContext
    {

        public HospitalContext(DbContextOptions<HospitalContext> options) : base (options) { } 
        
        

        public DbSet<Paciente> Paciente { get; set; } = null!;
    }
}
