
package services;

/**
 *
 * @author Carlos Orozco
 */
import models.Paciente;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PacienteService {

    private static final String API_URL = "http://localhost:5000/api/Paciente";

    public static boolean guardarPaciente(Paciente paciente) {
        try {
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            JSONObject json = new JSONObject();
            json.put("nombre", paciente.getNombre());
            json.put("apellido", paciente.getApellido());
            json.put("fechaNacimiento", paciente.getFechaNacimiento());
            json.put("genero", paciente.getGenero());
            json.put("direccion", paciente.getDireccion());
            json.put("telefono", paciente.getTelefono());
            json.put("correo", paciente.getCorreo());

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = json.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();
            return responseCode == HttpURLConnection.HTTP_CREATED;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<Paciente> obtenerPacientes() {
        List<Paciente> lista = new ArrayList<>();
        try {
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String linea;
            while ((linea = in.readLine()) != null) {
                response.append(linea);
            }

            JSONArray array = new JSONArray(response.toString());
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                Paciente p = new Paciente();
                p.setIdPaciente(obj.getInt("idPaciente"));
                p.setNombre(obj.getString("nombre"));
                p.setApellido(obj.getString("apellido"));
                p.setFechaNacimiento(obj.getString("fechaNacimiento"));
                p.setGenero(obj.getString("genero"));
                p.setDireccion(obj.getString("direccion"));
                p.setTelefono(obj.getString("telefono"));
                p.setCorreo(obj.getString("correo"));
                lista.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}
