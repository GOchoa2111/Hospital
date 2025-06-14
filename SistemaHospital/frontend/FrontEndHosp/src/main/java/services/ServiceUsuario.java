package services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import models.ModelUsuario;
import utils.SessionManager;

import java.io.*;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class ServiceUsuario {

    private static final String USUARIO = "http://localhost:5132/api/Usuario";
    private static final String USUARIOS = "http://localhost:5132/api/Usuarios";
    
    private final Gson gson;

    public ServiceUsuario() {
        // Configuración del Gson con manejo de fechas
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
    }

    // Listar todos los usuarios
    public List<ModelUsuario> listarUsuarios() throws IOException {
        HttpURLConnection conn = createConnection(USUARIOS, "GET");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            Type listType = new TypeToken<List<ModelUsuario>>() {
            }.getType();
            return gson.fromJson(reader, listType);
        }
    }

    // Registrar un nuevo usuario
    public boolean registrarUsuario(ModelUsuario usuario) throws IOException {
        HttpURLConnection conn = createConnection(USUARIO, "POST");
        conn.setDoOutput(true);

        String json = gson.toJson(usuario);
        System.out.println("Json enviado" + json);
        try (OutputStream os = conn.getOutputStream()) {
            os.write(json.getBytes());
        }

        int responseCode = conn.getResponseCode();
        return responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED;
    }

    // Actualizar usuario
    public boolean actualizarUsuario(ModelUsuario usuario) throws IOException {
        String urlConId = USUARIO + "/" + usuario.getIdUsuario();
        HttpURLConnection conn = createConnection(urlConId, "PUT");
        conn.setDoOutput(true);

        String json = gson.toJson(usuario);
        System.out.println("Json enviado" + json);
        try (OutputStream os = conn.getOutputStream()) {
            os.write(json.getBytes());
        }

        return conn.getResponseCode() == HttpURLConnection.HTTP_OK;
    }

    // Eliminar usuario
    public boolean eliminarUsuario(int id) throws IOException {
        String urlConId = USUARIO + "/" + id;
        HttpURLConnection conn = createConnection(urlConId, "DELETE");

        return conn.getResponseCode() == HttpURLConnection.HTTP_OK;
    }

    // Método común para crear la conexión HTTP con token
    private HttpURLConnection createConnection(String urlStr, String method) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(method);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", "Bearer " + SessionManager.getToken());
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);
        return conn;
    }
}
