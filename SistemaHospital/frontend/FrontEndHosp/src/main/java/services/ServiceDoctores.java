/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

/**
 *
 * @author Carlos Orozco
 */
import models.ModelDoctores;
import util.MapperDoctor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ServiceDoctores {

    private static final String DOCTORES = "http://localhost:5132/api/Doctors";

    public List<ModelDoctores> obtenerDoctores() {
        String json = obtenerDoctoresJson();
        if (json != null && !json.isEmpty()) {
            return MapperDoctor.fromJsonToList(json);//implementación del convertidor a gson
        }
        return new ArrayList<>(); // Retorna una lista vacía si no hay datos
    }

    private String obtenerDoctoresJson() {
        StringBuilder resultado = new StringBuilder();
        try {
            URL url = new URL(DOCTORES);
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

    // Métodos para operaciones CRUD
    public boolean agregarDoctor(ModelDoctores doctor) {
        try {  
            URL url = new URL(DOCTORES);  
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
            conn.setRequestMethod("POST");  
            conn.setRequestProperty("Content-Type", "application/json; utf-8");  
            conn.setDoOutput(true);  
  
            // Usar el Gson centralizado del Mapper  
            String jsonInput = MapperDoctor.getGson().toJson(doctor);  
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

    public boolean actualizarDoctor(ModelDoctores doctor) {
        try {  
            URL url = new URL(DOCTORES + "/" + doctor.getIdDoctor());  
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
            conn.setRequestMethod("PUT");  
            conn.setRequestProperty("Content-Type", "application/json; utf-8");  
            conn.setDoOutput(true);  
   
            String jsonInput = MapperDoctor.getGson().toJson(doctor); 
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

    public boolean eliminarDoctor(int idDoctor) {
        try {
            URL url = new URL(DOCTORES + "/" + idDoctor);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");

            int responseCode = conn.getResponseCode();
            return responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_NO_CONTENT;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
}
