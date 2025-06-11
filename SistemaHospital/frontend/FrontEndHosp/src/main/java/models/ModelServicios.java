package models;

public class ModelServicios {

    private int idServicio;
    private String nombre;
    private String descripcion;
    private double precio;

    // Constructor vacío
    public ModelServicios() {
    }

    // Constructor con parámetros
    public ModelServicios(int idServicio, String nombre, String descripcion, double precio) {
        this.idServicio = idServicio;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    // Getters y setters
    public int getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    //toString para mostrar todos los campos de la tabla servicios
    /*@Override
    public String toString() {
        return "ModeloServicio{" +
                "idServicio=" + idServicio +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precio=" + precio +
                '}';
    }*/
    
    //toString para mostrar nombre de servicio
    @Override
    public String toString() {
        return nombre; // así solo muestra el nombre en el combo
    }

}
