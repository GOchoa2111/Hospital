// src/services/ServicePaciente.java
package services;

import Main.LocalDateAdapter;
import Main.LocalDateTimeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import models.ModelPaciente;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ServicePaciente {

    private final String PACIENTE = "http://localhost:5132/api/Pacientes"; // Endpoint pacientes

    public ArrayList<ModelPaciente> obtenerPacientes() {
        ArrayList<ModelPaciente> listaPacientes = new ArrayList<>();

        try {
            URL url = new URL(PACIENTE);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = br.readLine()) != null) {
                    response.append(line);
                }

                br.close();
                conn.disconnect();

                System.out.println("string" + response);//imprimir el json para validar la info
                // Convertir JSON a lista de objetos ModelPaciente
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter()) // 👈 Necesario para fechaNacimiento
                        .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()) // Ya lo tienes para fechaCreacion
                        .create();

                Type listType = new TypeToken<ArrayList<ModelPaciente>>() {
                }.getType();
                listaPacientes = gson.fromJson(response.toString(), listType);

            } else {
                System.out.println("Error al obtener pacientes. Código: " + conn.getResponseCode());
            }

        } catch (Exception e) {
            System.out.println("Error en obtenerPacientes: " + e.getMessage());
        }

        return listaPacientes;
    }
    
    // registrar paciente
    
    public boolean registrarPaciente(ModelPaciente paciente) {
    try {
        URL url = new URL(PACIENTE); // Endpoint
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; utf-8");
        conn.setDoOutput(true);

        // Gson con adaptador para LocalDate
        Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();

        String json = gson.toJson(paciente);

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

    

    //actualizar Paciente
    public boolean actualizarPaciente(ModelPaciente paciente) {
        try {
            URL url = new URL(PACIENTE + "/" + paciente.getIdPaciente()); // EndPoint
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setDoOutput(true);

            // Gson con adaptador para fechas
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                    .create();

            String json = gson.toJson(paciente);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = json.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();
            return responseCode == 200 || responseCode == 204;

        } catch (Exception e) {
            System.out.println("Error en actualizarPaciente: " + e.getMessage());
            return false;
        }
    }
    
//Eliminar Paciente
    public boolean eliminarPaciente(int idPaciente) {
    try {
        URL url = new URL(PACIENTE + "/" + idPaciente);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("DELETE");

        int responseCode = conn.getResponseCode();
        System.out.println("Código de respuesta: " + responseCode); // Imprimir para depurar

        if (responseCode == 200 || responseCode == 204) {
            return true;
        } else {
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            String inputLine;
            StringBuilder errorResponse = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                errorResponse.append(inputLine);
            }
            in.close();
            System.err.println("Error al eliminar paciente: " + errorResponse.toString()); // Mostrar error
        }

    } catch (IOException e) {
        System.err.println("Excepción al eliminar paciente: " + e.getMessage());
        e.printStackTrace(); // 👈 Esto sí lanza error en consola
    }
    return false;
}


}
