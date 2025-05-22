using HospitalSistemaAPI.DTO.TecnoSolucionesAPI.DTOs;
using HospitalSistemaAPI.Models;
using Microsoft.EntityFrameworkCore;
using Microsoft.VisualStudio.Web.CodeGenerators.Mvc.Templates.BlazorIdentity.Pages;

namespace HospitalSistemaAPI.Data
{
    public class AppDbContext : DbContext
    {
        public AppDbContext(DbContextOptions options) : base(options) { }

        public DbSet<Paciente> pacientes { get; set; }
        public DbSet<Servicio> Servicios { get; set; }
        public DbSet<Factura> Facturas { get; set; }
        public DbSet<Usuario> Usuarios { get; set; }
        public DbSet<Cita> Citas { get; set; }
        public DbSet<HistorialMedico> HistorialesMedicos { get; set; }
        public DbSet<Consulta> Consultas { get; set; }
        public DbSet<DetalleFactura> DetallesFactura { get; set; }
        public DbSet<HorarioDoctor> HorariosDoctor { get; set; }
        public DbSet<Doctor> Doctores { get; set; }
        //public DbSet<CitaDetalleDTO> CitaDetalleDTO { get; set; } // DTO para la cita con detalles

        //public DbSet<Login> Logins { get; set; }

    }
}
