package services;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import models.ModelLogin;

/**
 *
 * @author Carlos Orozco
 */
public class ServiceLogin {

    private static final String LOGIN = "http://localhost:5132/api/Login/login";

    private final Gson gson = new Gson();

    public boolean autenticar(ModelLogin modelLogin) {

        try {//Try principal encierra todo el proceso
            // Crear conexión
            URL url = new URL(LOGIN);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Configurar la solicitud
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Convertir el objeto LoginDTO a JSON
            String jsonInput = gson.toJson(modelLogin);

            // Enviar datos al endpoint
            try (OutputStream os = connection.getOutputStream()) {//captura cualquier error  que genere el envio de dato
                byte[] input = jsonInput.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Leer la respuesta
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;

                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }

                    // Procesos adicionales como token (validar despues de probar usuario y contraseña quemada)
                    System.out.println("Respuesta: " + response.toString());
                    return true;
                }
            } else {
                System.out.println("Error en login, código HTTP: " + responseCode);
                return false;
            }

        } catch (Exception e) {
            System.out.println("Excepción en login: " + e.getMessage());
            return false;
        }
    }
}
