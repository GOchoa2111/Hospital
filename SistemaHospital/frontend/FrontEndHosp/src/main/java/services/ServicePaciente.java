// ServicePaciente.java
package services;

import Main.LocalDateAdapter;
import Main.LocalDateTimeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import models.ModelPaciente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ServicePaciente {

    private final String PACIENTE = "http://localhost:5132/api/Pacientes";

    public ArrayList<ModelPaciente> obtenerPacientes(String token) {
        ArrayList<ModelPaciente> listaPacientes = new ArrayList<>();

        try {
            URL url = new URL(PACIENTE);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
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
                        .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
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

    public boolean registrarPaciente(ModelPaciente paciente, String token) {
        try {
            URL url = new URL(PACIENTE);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setDoOutput(true);

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

    public boolean actualizarPaciente(ModelPaciente paciente, String token) {
        try {
            URL url = new URL(PACIENTE + "/" + paciente.getIdPaciente());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setDoOutput(true);

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

    public boolean eliminarPaciente(int idPaciente, String token) {
        try {
            URL url = new URL(PACIENTE + "/" + idPaciente);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");
            conn.setRequestProperty("Authorization", "Bearer " + token);

            int responseCode = conn.getResponseCode();
            System.out.println("Código de respuesta: " + responseCode);

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
                System.err.println("Error al eliminar paciente: " + errorResponse.toString());
            }

        } catch (IOException e) {
            System.err.println("Excepción al eliminar paciente: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}
