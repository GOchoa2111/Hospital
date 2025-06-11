package models;



import java.time.LocalDateTime;

public class ModelUsuario {
    private int idUsuario;
    private String nombre;
    private String apellido;
    private String nombreUsuario;
    private String contrasena;
    private String correo;
    private String tokenRecuperacion;
    private LocalDateTime fechaExpiracionToken;
    private ModelRoles rol; // objeto anidado

    public ModelUsuario() {}

    public ModelUsuario(int idUsuario, String nombre, String apellido, String nombreUsuario, String contrasena,
                   String correo, String tokenRecuperacion, LocalDateTime fechaExpiracionToken, ModelRoles rol) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.correo = correo;
        this.tokenRecuperacion = tokenRecuperacion;
        this.fechaExpiracionToken = fechaExpiracionToken;
        this.rol = rol;
    }

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

    public String getTokenRecuperacion() { return tokenRecuperacion; }
    public void setTokenRecuperacion(String tokenRecuperacion) { this.tokenRecuperacion = tokenRecuperacion; }

    public LocalDateTime getFechaExpiracionToken() { return fechaExpiracionToken; }
    public void setFechaExpiracionToken(LocalDateTime fechaExpiracionToken) {
        this.fechaExpiracionToken = fechaExpiracionToken;
    }

    public ModelRoles getRol() { return rol; }
    public void setRol(ModelRoles rol) { this.rol = rol; }

    @Override
    public String toString() {
        return nombre + " " + apellido;
    }
}
