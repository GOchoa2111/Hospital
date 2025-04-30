using Microsoft.EntityFrameworkCore;
using HospitalApi.Models;
using Microsoft.Identity.Client;

namespace HospitalApi.Context
{
    public class HospitalContext : DbContext
    {
        public HospitalContext(DbContextOptions<HospitalContext> options) : base(options)
        {

           Public DbSet<Paciente> Pacientes { get; set; }
        }

    }
}
