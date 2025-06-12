package controllers;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import models.ModelPaciente;
import models.ModelServicios;

import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ControllerFacturaDetalle {

    private static final String API_BASE = "http://localhost:5132/api";

    // Adaptador para LocalDate
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
                @Override
                public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
                    return LocalDate.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE);
                }
            })
            .create();

    public List<ModelPaciente> obtenerPacientes() throws Exception {
        URL url = new URL(API_BASE + "/Pacientes");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        //conn.setRequestProperty("Authorization", "Bearer " + token);
        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Error al obtener pacientes: " + conn.getResponseCode());
        }

        Type listType = new TypeToken<List<ModelPaciente>>() {
        }.getType();
        List<ModelPaciente> pacientes = gson.fromJson(new InputStreamReader(conn.getInputStream()), listType);
        conn.disconnect();
        return pacientes;
    }

    public List<ModelServicios> obtenerServicios() throws Exception {
        URL url = new URL(API_BASE + "/Servicios");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        //conn.setRequestProperty("Authorization", "Bearer " + token);
        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Error al obtener servicios: " + conn.getResponseCode());
        }

        Type listType = new TypeToken<List<ModelServicios>>() {
        }.getType();
        List<ModelServicios> servicios = gson.fromJson(new InputStreamReader(conn.getInputStream()), listType);
        conn.disconnect();
        return servicios;
    }
}
