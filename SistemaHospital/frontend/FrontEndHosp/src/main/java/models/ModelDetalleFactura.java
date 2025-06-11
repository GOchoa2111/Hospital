
package models;


public class ModelDetalleFactura {

    private int idDetalle;
    private int idFactura;
    private int idServicio;
    private int cantidad;
    private double subtotal;

    // Constructor vacío
    public ModelDetalleFactura() {
    }

    // Constructor con atributos
    public ModelDetalleFactura(int idDetalle, int idFactura, int idServicio, int cantidad, double subtotal) {
        this.idDetalle = idDetalle;
        this.idFactura = idFactura;
        this.idServicio = idServicio;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
    }

    // Getters y Setters
    public int getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(int idDetalle) {
        this.idDetalle = idDetalle;
    }

    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

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

