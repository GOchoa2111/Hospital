/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import models.ModelHistorialReporte;

public class ServiceReportes {
    private final String REPORTES_API_URL = "http://localhost:5132/api/Reportes";

    public List<ModelHistorialReporte> obtenerHistorialPorPaciente(int pacienteId, String token) throws Exception {
        // Construimos la URL completa con el ID del paciente
        URL url = new URL(REPORTES_API_URL + "/HistorialClinico/" + pacienteId);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", "Bearer " + token);

        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Error en la petición: Código " + conn.getResponseCode());
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        // Usamos Gson para convertir la respuesta JSON en una lista de nuestros objetos de reporte
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        Type listType = new TypeToken<ArrayList<ModelHistorialReporte>>(){}.getType();

        List<ModelHistorialReporte> historial = gson.fromJson(br, listType);
        conn.disconnect();

        return historial;
    }
}