/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import util.MapperRepoCitas;
import models.ModelRepoCitas;

/**
 * Servicio para obtener citas detalladas desde la API REST
 */
public class ServiceRepoCitas {

    private static final String REPOCITAS = "http://localhost:5132/api/detalleCita";

    // Método público para obtener la lista de citas detalladas
    public List<ModelRepoCitas> obtModelRepoCitas() {
        
        String json = obtenerJsonDesdeAPI();
        if (json != null && !json.isEmpty()) {
            return MapperRepoCitas.fromJsonToList(json); // Conversión desde JSON a lista
        }
        return new ArrayList<>(); // Si falla, retorna lista vacía
    }

    // Método privado para hacer la solicitud HTTP GET
    private String obtenerJsonDesdeAPI() {
        
        StringBuilder resultado = new StringBuilder();
        try {
            URL url = new URL(REPOCITAS);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            int responseCode = conn.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String linea;
                while ((linea = reader.readLine()) != null) {
                    resultado.append(linea);
                }
                System.out.println("JSON recibido desde la API:");
                System.out.println("Response" + responseCode);
                System.out.println(resultado.toString());

                reader.close();
                return resultado.toString();

            } else {
                System.out.println("Error en la conexión: " + responseCode);
                return null;
            }
        } catch (Exception e) {
            System.err.println("Excepción al conectar con la API: " + e.getMessage());
            return null;
        }
    }
}
