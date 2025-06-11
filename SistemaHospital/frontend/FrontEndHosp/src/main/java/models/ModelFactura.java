package models;

import java.time.LocalDate;
import java.util.List;

public class ModelFactura {

    private int idFactura;
    private LocalDate fecha;
    private int idPaciente;
    private int idUsuario;
    private double total;

    // Para manejar el detalle, agregamos una lista de detalles de factura
    private List<ModelDetalleFactura> detalles;

    // Constructor vacío
    public ModelFactura() {
    }

    // Constructor con atributos (sin detalles)
    public ModelFactura(int idFactura, LocalDate fecha, int idPaciente, int idUsuario, double total) {
        this.idFactura = idFactura;
        this.fecha = fecha;
        this.idPaciente = idPaciente;
        this.idUsuario = idUsuario;
        this.total = total;
    }

    // Constructor con detalles
    public ModelFactura(int idFactura, LocalDate fecha, int idPaciente, int idUsuario, double total,
                        List<ModelDetalleFactura> detalles) {
        this.idFactura = idFactura;
        this.fecha = fecha;
        this.idPaciente = idPaciente;
        this.idUsuario = idUsuario;
        this.total = total;
        this.detalles = detalles;
    }

    // Getters y Setters
    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<ModelDetalleFactura> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<ModelDetalleFactura> detalles) {
        this.detalles = detalles;
    }
}
