package utils;

public class SessionManager {

    private static String token;
    private static int idUsuario;
    private static String username;
    private static int idRol;
    private static String nombreRol;

    public static void setSession(String tokenValue, int userId, String userName, int rolId, String rolNombre) {
        token = tokenValue;
        idUsuario = userId;
        username = userName;
        idRol = rolId;
        nombreRol = rolNombre;
    }

    public static String getToken() {
        return token;
    }

    public static int getIdUsuario() {
        return idUsuario;
    }

    public static String getUsername() {
        return username;
    }

    public static int getIdRol() {
        return idRol;
    }

    public static String getNombreRol() {
        return nombreRol;
    }

    public static void cerrarSesion() {
        token = null;
        idUsuario = 0;
        username = null;
        idRol = 0;
        nombreRol = null;
    }
}
