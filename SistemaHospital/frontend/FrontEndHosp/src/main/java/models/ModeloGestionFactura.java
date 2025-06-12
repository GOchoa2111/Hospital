package models;

import java.util.Date;
import java.util.List;

public class ModeloGestionFactura {
    private int idFactura;
    private Date fecha;
    private int idUsuario;
    private int idPaciente;
    private double total;
    private String nombrePaciente;
    private String apellidoPaciente;
    private String nombreUsuario;
    private String apellidoUsuario;
    private double subtotal;
    private double iva;
    private String estado; // "Activo" o "Inactivo"
    private List<Detalle> detalles;

    // Getters y setters

    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public String getApellidoPaciente() {
        return apellidoPaciente;
    }

    public void setApellidoPaciente(String apellidoPaciente) {
        this.apellidoPaciente = apellidoPaciente;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getApellidoUsuario() {
        return apellidoUsuario;
    }

    public void setApellidoUsuario(String apellidoUsuario) {
        this.apellidoUsuario = apellidoUsuario;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<Detalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<Detalle> detalles) {
        this.detalles = detalles;
    }

    // Clase interna para Detalle
    public static class Detalle {
        private int idDetalle;
        private int idServicio;
        private int cantidad;
        private double subtotal;
        private String nombreServicio;
        private double precioServicio;

        // Getters y setters

        public int getIdDetalle() {
            return idDetalle;
        }

        public void setIdDetalle(int idDetalle) {
            this.idDetalle = idDetalle;
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

        public String getNombreServicio() {
            return nombreServicio;
        }

        public void setNombreServicio(String nombreServicio) {
            this.nombreServicio = nombreServicio;
        }

        public double getPrecioServicio() {
            return precioServicio;
        }

        public void setPrecioServicio(double precioServicio) {
            this.precioServicio = precioServicio;
        }
    }
}