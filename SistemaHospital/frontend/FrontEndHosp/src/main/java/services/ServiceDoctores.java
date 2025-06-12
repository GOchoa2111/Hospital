package services;

import com.google.gson.JsonObject;
import com.google.gson.Gson;
import models.ModelDoctores;
import util.MapperDoctor;

import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ServiceDoctores {

    private static final String DOCTORES = "http://localhost:5132/api/Doctors";
    private final Gson gson = new Gson();

    public List<ModelDoctores> obtenerDoctores(String token) {
        String json = obtenerDoctoresJson(token);
        if (json != null && !json.isEmpty()) {
            return MapperDoctor.fromJsonToList(json);
        }
        return new ArrayList<>();
    }

    private String obtenerDoctoresJson(String token) {
        StringBuilder resultado = new StringBuilder();
        try {
            URL url = new URL(DOCTORES);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

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

    public boolean agregarDoctor(ModelDoctores doctor, String token) {
        try {
            URL url = new URL(DOCTORES);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setDoOutput(true);

            String jsonInput = MapperDoctor.getGson().toJson(doctor);

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

    public boolean actualizarDoctor(ModelDoctores doctor, String token) {
        try {
            URL url = new URL(DOCTORES + "/" + doctor.getIdDoctor());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setDoOutput(true);

            String jsonInput = MapperDoctor.getGson().toJson(doctor);

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

    public boolean eliminarDoctor(int idDoctor, String token) {
        try {
            URL url = new URL(DOCTORES + "/" + idDoctor);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");
            conn.setRequestProperty("Authorization", "Bearer " + token);

            int responseCode = conn.getResponseCode();
            return responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_NO_CONTENT;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Nuevo método para cambiar estado
    public boolean cambiarEstadoDoctor(int idDoctor, boolean estado, String token) {
        try {
            URL url = new URL(DOCTORES + "/" + idDoctor + "/estado");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setDoOutput(true);

            JsonObject jsonEstado = new JsonObject();
            jsonEstado.addProperty("estado", estado);
            String jsonInput = jsonEstado.toString();

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
}