package models;

public class ModelUsuario {
    private int idUsuario;
    private String nombre;
    private String apellido;
    private String nombreUsuario;
    private String contrasena;
    private String correo;
    private int rolId;
    private String tokenRecuperacion;
    private String fechaExpiracionToken; // formato ISO 8601

    // Getters y Setters
    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public int getRolId() { return rolId; }
    public void setRolId(int rolId) { this.rolId = rolId; }

    public String getTokenRecuperacion() { return tokenRecuperacion; }
    public void setTokenRecuperacion(String tokenRecuperacion) { this.tokenRecuperacion = tokenRecuperacion; }

    public String getFechaExpiracionToken() { return fechaExpiracionToken; }
    public void setFechaExpiracionToken(String fechaExpiracionToken) { this.fechaExpiracionToken = fechaExpiracionToken; }
}
