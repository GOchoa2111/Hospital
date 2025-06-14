package services;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import models.ModeloGestionFactura;




public class ServiceFacturaGestion {

    private final String baseUrl;
    private final String token;
    private final ObjectMapper objectMapper;

    public ServiceFacturaGestion(String baseUrl, String token) {
        this.baseUrl = baseUrl;
        this.token = token;
        this.objectMapper = new ObjectMapper();
    }

    // Obtener factura por id
    public ModeloGestionFactura getFacturaById(int id) throws Exception {
        String urlString = baseUrl + "/api/FacturasGestion/" + id;
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", "Bearer " + token);
        conn.setRequestProperty("Accept", "application/json");

        int responseCode = conn.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = in.readLine()) != null) {
                    response.append(line);
                }
                System.out.println("JSON Response" + response);

                // Convertir JSON a objeto FacturaGestion
                return objectMapper.readValue(response.toString(), ModeloGestionFactura.class);
            }
        } else {
            throw new RuntimeException("Error en la petición GET: Código " + responseCode);
        }
    }

    // Obtener todas las facturas
    public List<ModeloGestionFactura> getAllFacturas() throws Exception {
        String urlString = baseUrl + "/api/FacturasGestion"; // Endpoint correcto
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", "Bearer " + token);
        conn.setRequestProperty("Accept", "application/json");

        int responseCode = conn.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = in.readLine()) != null) {
                    response.append(line);
                }
                System.out.println("JSON Response" + response);
                // Convertir JSON a lista de FacturaGestion
                return objectMapper.readValue(response.toString(), new TypeReference<List<ModeloGestionFactura>>() {});
            }
        } else {
            throw new RuntimeException("Error en la petición GET: Código " + responseCode);
        }
    }

    // Anular factura (cambiar estado)
    public void anularFactura(int id) throws Exception {
        String urlString = baseUrl + "/api/FacturasGestion/Anular/" + id; // Endpoint correcto
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("PUT");
        conn.setRequestProperty("Authorization", "Bearer " + token);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        // No enviamos cuerpo porque el endpoint solo cambia estado

        int responseCode = conn.getResponseCode();

        if (responseCode != HttpURLConnection.HTTP_NO_CONTENT) {
            throw new RuntimeException("Error en la petición PUT: Código " + responseCode);
        }
    }
}