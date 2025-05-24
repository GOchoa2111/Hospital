 
package services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import models.ModelServicios;
import util.MapperServicios;

/**
 *
 * @author C-Orozco
 */
public class ServiceServicios {
    private static final String SERVICIOS = "http://localhost:5132/api/Servicios";

    public List<ModelServicios> obtenerServicios() {
        String json = obtenerDoctoresJson();
        if (json != null && !json.isEmpty()) {
            
            return MapperServicios.fromJsonToList(json);//implementación del convertidor a gson
        }
        return new ArrayList<>(); // Retorna una lista vacía si no hay datos
    }

    private String obtenerDoctoresJson() {
        StringBuilder resultado = new StringBuilder();
        try {
            URL url = new URL(SERVICIOS);
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
}