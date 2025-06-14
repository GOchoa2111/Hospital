package models;

import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Expose;
import java.time.LocalDate;
import java.util.List;

public class ModelFactura {

    // Conservamos idFactura para Java pero no lo exponemos en JSON
    @Expose(serialize = true, deserialize = false)
    private int idFactura;
    
    @Expose
    @SerializedName("Fecha")
    private LocalDate fecha;
    
    @Expose
    @SerializedName("IdPaciente")
    private int idPaciente;
    
    @Expose
    @SerializedName("IdUsuario")
    private int idUsuario;
    
    @Expose
    @SerializedName("Total")
    private double total;
    
    @Expose
    @SerializedName("Detalles")
    private List<ModelDetalleFactura> detalles;

    public ModelFactura() {
    }

    public ModelFactura(int idFactura, LocalDate fecha, int idPaciente, int idUsuario, double total, List<ModelDetalleFactura> detalles) {
        this.idFactura = idFactura;
        this.fecha = fecha;
        this.idPaciente = idPaciente;
        this.idUsuario = idUsuario;
        this.total = total;
        this.detalles = detalles;
    }

    // Getter y Setter para idFactura para que el servicio Java funcione
    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    // Getters y setters para los demás campos
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
