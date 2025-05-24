 
package services;

import Main.LocalDateAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import models.ModelServicios;
import util.MapperServicios;

/**
 *
 * @author C-Orozco
 */
public class ServiceServicios {
    private static final String SERVICIOS = "http://localhost:5132/api/Servicios";

    public List<ModelServicios> obtenerServicios() {
        String json = obtenerDoctoresJson();
        if (json != null && !json.isEmpty()) {
            
            return MapperServicios.fromJsonToList(json);//implementación del convertidor a gson
        }
        return new ArrayList<>(); // Retorna una lista vacía si no hay datos
    }

    private String obtenerDoctoresJson() {
        StringBuilder resultado = new StringBuilder();
        try {
            URL url = new URL(SERVICIOS);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000); // Timeout de 5 segundos
            conn.setReadTimeout(5000);    // Timeout de lectura de 5 segundos

            int responseCode = conn.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String linea;
                while ((linea = reader.readLine()) != null) {
                    resultado.append(linea);
                }
                reader.close();
                return resultado.toString();
            } else {
                System.out.println("Error en la conexión: " + responseCode);
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    // registrar paciente
    
    public boolean agregarPaciente(ModelServicios servicio) {
    try {
        URL url = new URL(SERVICIOS); // Endpoint
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; utf-8");
        conn.setDoOutput(true);

        // Gson con adaptador para LocalDate
        Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();

        String json = gson.toJson(servicio);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = json.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        int responseCode = conn.getResponseCode();
        return responseCode == 201 || responseCode == 200;

    } catch (Exception e) {
        System.out.println("Error en registrarPaciente: " + e.getMessage());
        return false;
    }
}

   
    //actualizar Servicio
    
    public boolean actualizarServicio(ModelServicios servicio) {
        try {
            URL url = new URL(SERVICIOS + "/" + servicio.getIdServicio()); // EndPoint
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setDoOutput(true);

            // Gson con adaptador para fechas
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                    .create();

            String json = gson.toJson(servicio);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = json.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();
            return responseCode == 200 || responseCode == 204;

        } catch (Exception e) {
            System.out.println("Error al actualizar Servicio: " + e.getMessage());
            return false;
        }
    }
}