package models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelDetalleFactura {
    
    @Expose
    @SerializedName("IdServicio")
    private int idServicio;
    
    @Expose
    @SerializedName("Cantidad")
    private int cantidad;
    
    @Expose
    @SerializedName("Subtotal")
    private double subtotal;

    // Constructor vacío
    public ModelDetalleFactura() {
    }

    // Constructor con parámetros
    public ModelDetalleFactura(int idServicio, int cantidad, double subtotal) {
        this.idServicio = idServicio;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
    }

    // Getters y setters
    public int getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
}
