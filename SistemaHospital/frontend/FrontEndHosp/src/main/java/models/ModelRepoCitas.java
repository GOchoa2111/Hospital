/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author C-Orozco
 */
import java.time.LocalDateTime;

public class ModelRepoCitas {

    private int idCita;
    private LocalDateTime fechaHora;
    private String servicio;
    private String paciente;
    private String doctor;

    // Constructor vacío
    public ModelRepoCitas() {
    }

    // Constructor con parámetros
    public ModelRepoCitas(int idCita, LocalDateTime fechaHora, String servicio, String paciente, String doctor) {

        this.idCita = idCita;
        this.fechaHora = fechaHora;
        this.servicio = servicio;
        this.paciente = paciente;
        this.doctor = doctor;
    }

    // Getters y setters
    public int getIdCita() {
        return idCita;
    }

    public void setIdCita(int idCita) {
        this.idCita = idCita;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getPaciente() {
        return paciente;
    }

    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    @Override
    public String toString() {
        return "CitaDetalle{"
                + "idCita=" + idCita
                + ", fechaHora=" + fechaHora
                + ", servicio='" + servicio + '\''
                + ", paciente='" + paciente + '\''
                + ", doctor='" + doctor + '\''
                + '}';
    }
}
