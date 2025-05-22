/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

/**
 *
 * @author Carlos Orozco
 */
import Models.ModelServicios;
import util.MapperServicios;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ServiceServicios {

    private static final String SERVICIOS = "http://localhost:5132/api/Servicios";

    public List<ModelServicios> obtenerServicios() {
        String json = obtenerServiciosJson();
        if (json != null && !json.isEmpty()) {
            return MapperServicios.fromJsonToList(json);//implementación del convertidor a gson
        }
        return new ArrayList<>(); // Retorna una lista vacía si no hay datos
    }

    private String obtenerServiciosJson() {
        StringBuilder resultado = new StringBuilder();
        try {
            URL url = new URL(SERVICIOS);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000); // Timeout de 5 segundos
            conn.setReadTimeout(5000);    // Timeout de lectura de 5 segundos

            int responseCode = conn.getResponseCode();
            
            System.out.println("codigo generado" + responseCode);

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

    // Métodos para operaciones CRUD
    public boolean agregarServicio(ModelServicios servicio) {
        try {  
            URL url = new URL(SERVICIOS);  
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
            conn.setRequestMethod("POST");  
            conn.setRequestProperty("Content-Type", "application/json; utf-8");  
            conn.setDoOutput(true);  
  
            // Usar el Gson centralizado del Mapper  
            String jsonInput = MapperServicios.getGson().toJson(servicio);  
            System.out.println("JSON enviado" + jsonInput);
  
            try (OutputStream os = conn.getOutputStream()) {  
                byte[] input = jsonInput.getBytes("utf-8");  
                os.write(input, 0, input.length);  
            }  
  
            int responseCode = conn.getResponseCode();  
            return responseCode == HttpURLConnection.HTTP_CREATED || responseCode == HttpURLConnection.HTTP_OK;  
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;  
        }
    }

    public boolean actualizarServicio(ModelServicios servicio) {
        try {  
            URL url = new URL(SERVICIOS + "/" + servicio.getIdServicio());  
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
            conn.setRequestMethod("PUT");  
            conn.setRequestProperty("Content-Type", "application/json; utf-8");  
            conn.setDoOutput(true);  
   
            String jsonInput = MapperServicios.getGson().toJson(servicio); 
            System.out.println("JSON ENVIADO" + jsonInput);
  
            try (OutputStream os = conn.getOutputStream()) {  
                byte[] input = jsonInput.getBytes("utf-8");  
                os.write(input, 0, input.length);  
            }  
  
            int responseCode = conn.getResponseCode();  
            return responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_NO_CONTENT;  
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;  
        }  
    }

    public boolean eliminarServicio(int idServicio) {
        try {
            URL url = new URL(SERVICIOS + "/" + idServicio);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");

            int responseCode = conn.getResponseCode();
            return responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_NO_CONTENT;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    //metodo para prueba de conexión con la api
    public static void main(String[] args) {
    ServiceServicios servicio = new ServiceServicios();
    String json = servicio.obtenerServiciosJson();

    if (json != null && !json.isEmpty()) {
        System.out.println("[JSON RECIBIDO]:\n" + json);
    } else {
        System.out.println("[ERROR] No se pudo obtener datos desde la API.");
    }
}

}