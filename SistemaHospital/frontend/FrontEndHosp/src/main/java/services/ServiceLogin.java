package services;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import models.ModelLogin;

public class ServiceLogin {

    private String baseUrl;
    private static final String LOGIN = "http://localhost:5132/api/Login/login";
    private final Gson gson = new Gson();

    public ServiceLogin(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public ModelLogin autenticar(ModelLogin modelLogin) {
        try {
            URL url = new URL(LOGIN);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            String jsonInput = gson.toJson(modelLogin);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInput.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;

                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }

                    System.out.println("Respuesta login: " + response.toString());

                    JsonObject jsonObject = JsonParser.parseString(response.toString()).getAsJsonObject();

                    int idUsuario = jsonObject.get("idUsuario").getAsInt();
                    String username = jsonObject.get("username").getAsString();
                    String token = jsonObject.get("token").getAsString();

                    // ✅ Extraer el nombre del rol desde el objeto anidado
                    JsonObject roleObject = jsonObject.getAsJsonObject("role");
                    String nombreRol = roleObject.get("nombreRol").getAsString();

                    // ✅ Crear y devolver objeto ModelLogin con todos los datos
                    ModelLogin usuario = new ModelLogin();
                    usuario.setIdUsuario(idUsuario);
                    usuario.setNombreUsuario(username);
                    usuario.setToken(token);
                    usuario.setRole(nombreRol); // ← Aquí se asigna "administrador", "medico", etc.

                    return usuario;
                }
            } else {
                System.out.println("Error en login, código HTTP: " + responseCode);
                return null;
            }

        } catch (Exception e) {
            System.out.println("Excepción en login: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
