/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author Carlos Orozco
 */
public class ModelDoctores {

    private int idDoctor;
    private String nombre;
    private String apellido;
    private String especialidad;
    private String telefono;
    private String correo;
    private int idUsuario;
    private int creadoPor;
    private LocalDateTime fechaCreacion;
    private boolean estado;

    // Constructor vacío
    public ModelDoctores() {
    }

    // Constructor con parámetros
    public ModelDoctores(int idDoctor, String nombre, String apellido, String especialidad,
            String telefono, String correo, int idUsuario, int creadoPor,
            LocalDateTime fechaCreacion, boolean estado) {
        this.idDoctor = idDoctor;
        this.nombre = nombre;
        this.apellido = apellido;
        this.especialidad = especialidad;
        this.telefono = telefono;
        this.correo = correo;
        this.idUsuario = idUsuario;
        this.creadoPor = creadoPor;
        this.fechaCreacion = this.fechaCreacion;
        this.estado = estado;
    }

    // Getters y Setters
    public int getIdDoctor() {
        return idDoctor;
    }

    public void setIdDoctor(int idDoctor) {
        this.idDoctor = idDoctor;
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

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
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

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(int creadoPor) {
        this.creadoPor = creadoPor;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

@Override
public String toString() {
    return nombre + " " + apellido;
}
}
