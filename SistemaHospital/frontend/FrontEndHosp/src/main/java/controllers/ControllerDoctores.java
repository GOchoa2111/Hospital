// ControllerDoctores.java
package controllers;

import java.time.LocalDateTime;
import java.util.List;
import models.ModelDoctores;
import services.ServiceDoctores;

public class ControllerDoctores {

    private final ServiceDoctores serviceDoctores;

    public ControllerDoctores() {
        this.serviceDoctores = new ServiceDoctores();
    }

    public List<ModelDoctores> obtenerDoctores(String token) {
        return serviceDoctores.obtenerDoctores(token);
    }

    public boolean agregarDoctor(String nombre, String apellido, String especialidad,
            String telefono, String correo, int creadoPor, String token) {

        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (apellido == null || apellido.trim().isEmpty()) {
            throw new IllegalArgumentException("El apellido no puede estar vacío");
        }
        if (especialidad == null || especialidad.trim().isEmpty()) {
            throw new IllegalArgumentException("La especialidad no puede estar vacía");
        }
        if (telefono == null || telefono.trim().isEmpty()) {
            throw new IllegalArgumentException("El teléfono no puede estar vacío");
        }
        if (correo == null || correo.trim().isEmpty()) {
            throw new IllegalArgumentException("El correo no puede estar vacío");
        }

        ModelDoctores doctor = new ModelDoctores();
        doctor.setNombre(nombre);
        doctor.setApellido(apellido);
        doctor.setEspecialidad(especialidad);
        doctor.setTelefono(telefono);
        doctor.setCorreo(correo);
        doctor.setCreadoPor(creadoPor);
        doctor.setEstado(true);
        doctor.setFechaCreacion(LocalDateTime.now());

        return serviceDoctores.agregarDoctor(doctor, token);
    }

    // Actualizar doctor con estado incluido
    public boolean actualizarDoctor(int idDoctor, String nombre, String apellido, String especialidad,
            String telefono, String correo, boolean estado, int creadoPor, String token) {

        if (idDoctor <= 0) {
            throw new IllegalArgumentException("ID de doctor inválido");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (apellido == null || apellido.trim().isEmpty()) {
            throw new IllegalArgumentException("El apellido no puede estar vacío");
        }
        if (especialidad == null || especialidad.trim().isEmpty()) {
            throw new IllegalArgumentException("La especialidad no puede estar vacía");
        }
        if (telefono == null || telefono.trim().isEmpty()) {
            throw new IllegalArgumentException("El teléfono no puede estar vacío");
        }
        if (correo == null || correo.trim().isEmpty()) {
            throw new IllegalArgumentException("El correo no puede estar vacío");
        }

        ModelDoctores doctor = new ModelDoctores();
        doctor.setIdDoctor(idDoctor);
        doctor.setNombre(nombre);
        doctor.setApellido(apellido);
        doctor.setEspecialidad(especialidad);
        doctor.setTelefono(telefono);
        doctor.setCorreo(correo);
        doctor.setCreadoPor(creadoPor);
        doctor.setEstado(estado);  // Asignar estado aquí
        doctor.setFechaCreacion(LocalDateTime.now());

        return serviceDoctores.actualizarDoctor(doctor, token);
    }

    public boolean eliminarDoctor(int idDoctor, String token) {
        if (idDoctor <= 0) {
            throw new IllegalArgumentException("ID de doctor inválido");
        }
        return serviceDoctores.eliminarDoctor(idDoctor, token);
    }

    public ModelDoctores buscarDoctorPorId(int idDoctor, String token) {
        if (idDoctor <= 0) {
            throw new IllegalArgumentException("ID de doctor inválido");
        }
        List<ModelDoctores> doctores = obtenerDoctores(token);
        for (ModelDoctores doctor : doctores) {
            if (doctor.getIdDoctor() == idDoctor) {
                return doctor;
            }
        }
        return null;
    }

    // Eliminar método cambiarEstadoDoctor para evitar confusión
}
