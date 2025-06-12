package services;

import Main.LocalDateAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import models.ModelFactura;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;

public class ServiceFacturaDetalle {

    private final String FACTURA_API = "http://localhost:5132/api/Facturas"; // Endpoint

    // Obtener lista de facturas
    public ArrayList<ModelFactura> obtenerFacturas(String token) {
        ArrayList<ModelFactura> listaFacturas = new ArrayList<>();

        try {
            URL url = new URL(FACTURA_API);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            // Agregar token en el header Authorization  
            conn.setRequestProperty("Authorization", "Bearer " + token);

            if (conn.getResponseCode() == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = br.readLine()) != null) {
                    response.append(line);
                }

                br.close();
                conn.disconnect();

                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                        .create();

                Type listType = new TypeToken<ArrayList<ModelFactura>>() {
                }.getType();
                listaFacturas = gson.fromJson(response.toString(), listType);

            } else {
                System.out.println("Error al obtener facturas. Código: " + conn.getResponseCode());
            }
        } catch (Exception e) {
            System.out.println("Error en obtenerFacturas: " + e.getMessage());
        }

        return listaFacturas;
    }

    // Registrar factura (con detalles)
    public boolean registrarFactura(ModelFactura factura, String token) {
        try {
            URL url = new URL(FACTURA_API);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            // Agregar token en el header Authorization  
            conn.setRequestProperty("Authorization", "Bearer " + token);

            conn.setDoOutput(true);

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                    .create();

            String json = gson.toJson(factura);
            // Imprimir JSON para depuración  
            System.out.println("JSON a enviar:");
            System.out.println(json);
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = json.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();
            return responseCode == 201 || responseCode == 200;

        } catch (Exception e) {
            System.out.println("Error en registrarFactura: " + e.getMessage());
            return false;
        }
    }

    // Actualizar factura
    public boolean actualizarFactura(ModelFactura factura, String token) {
        try {
            URL url = new URL(FACTURA_API + "/" + factura.getIdFactura());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            // Agregar token en el header Authorization  
            conn.setRequestProperty("Authorization", "Bearer " + token);

            conn.setDoOutput(true);

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                    .create();

            String json = gson.toJson(factura);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = json.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();
            return responseCode == 200 || responseCode == 204;

        } catch (Exception e) {
            System.out.println("Error en actualizarFactura: " + e.getMessage());
            return false;
        }
    }

    // Eliminar factura
    public boolean eliminarFactura(int idFactura, String token) {
        try {
            URL url = new URL(FACTURA_API + "/" + idFactura);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");
            // Agregar token en el header Authorization  
            conn.setRequestProperty("Authorization", "Bearer " + token);
            int responseCode = conn.getResponseCode();
            System.out.println("Código de respuesta: " + responseCode);

            return responseCode == 200 || responseCode == 204;

        } catch (Exception e) {
            System.out.println("Error en eliminarFactura: " + e.getMessage());
            return false;
        }
    }
}
