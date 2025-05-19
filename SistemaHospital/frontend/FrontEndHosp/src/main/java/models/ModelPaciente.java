package models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ModelPaciente {

    private int idPaciente;
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private String genero;
    private String tipoSangre;
    private String direccion;
    private String telefono;
    private String correo;
    private int creadoPor;
    private LocalDate fechaCreacion;
    private boolean estado;
    
    //constructor vacio
    public ModelPaciente(){};
    
    //Constructor con atributos
    public ModelPaciente(int idPaciente, String nombre, String apellido, LocalDate fechaNacimiento,
                     String genero, String tipoSangre, String direccion, String telefono,
                     String correo, int creadoPor, LocalDate fechaCreacion, boolean estado) {
    this.idPaciente = idPaciente;
    this.nombre = nombre;
    this.apellido = apellido;
    this.fechaNacimiento = fechaNacimiento;
    this.genero = genero;
    this.tipoSangre = tipoSangre;
    this.direccion = direccion;
    this.telefono = telefono;
    this.correo = correo;
    this.creadoPor = creadoPor;
    this.fechaCreacion = fechaCreacion;
    this.estado = estado;
}


    // Getters y Setters
    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getTipoSangre() {
        return tipoSangre;
    }

    public void setTipoSangre(String tipoSangre) {
        this.tipoSangre = tipoSangre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(int creadoPor) {
        this.creadoPor = creadoPor;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getEstadoComoTexto() {
        return estado ? "Activo" : "Inactivo";
    }
}
