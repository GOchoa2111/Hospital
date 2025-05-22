/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

/**
 *
 * @author Carlos Orozco
 */
import java.time.LocalDateTime;
import models.ModelDoctores;
import services.ServiceDoctores;

import java.util.List;

public class ControllerDoctores {

    private final ServiceDoctores serviceDoctores;

    public ControllerDoctores() {
        this.serviceDoctores = new ServiceDoctores();
    }

    /**
     * Obtiene la lista de todos los doctores
     *
     * @return Lista de doctores
     */
    public List<ModelDoctores> obtenerDoctores() {
        return serviceDoctores.obtenerDoctores();
    }

    /**
     * Agrega un nuevo doctor
     *
     * @param nombre Nombre del doctor
     * @param apellido Apellido del doctor
     * @param especialidad Especialidad del doctor
     * @param telefono Teléfono del doctor
     * @param correo Correo electrónico del doctor
     * @param idUsuario ID del usuario asociado
     * @param creadoPor ID del usuario que crea el registro
     * @return true si se agregó correctamente, false en caso contrario
     */
    public boolean agregarDoctor(String nombre, String apellido, String especialidad,
            String telefono, String correo, int idUsuario, int creadoPor) {

        // Validaciones básicas
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

        // Crear objeto doctor
        ModelDoctores doctor = new ModelDoctores();
        doctor.setNombre(nombre);
        doctor.setApellido(apellido);
        doctor.setEspecialidad(especialidad);
        doctor.setTelefono(telefono);
        doctor.setCorreo(correo);
        //doctor.setIdUsuario(idUsuario);
        doctor.setCreadoPor(creadoPor);
        doctor.setEstado(true);
        doctor.setFechaCreacion(LocalDateTime.now());

        // Llamar al servicio para agregar el doctor
        return serviceDoctores.agregarDoctor(doctor);
    }

    /**
     * Actualiza un doctor existente
     *
     * @param idDoctor ID del doctor a actualizar
     * @param nombre Nombre del doctor
     * @param apellido Apellido del doctor
     * @param especialidad Especialidad del doctor
     * @param telefono Teléfono del doctor
     * @param correo Correo electrónico del doctor
     * @param creadoPor ID del usuario asociado
     * @return true si se actualizó correctamente, false en caso contrario
     */
    public boolean actualizarDoctor(int idDoctor, String nombre, String apellido, String especialidad,
            String telefono, String correo, int creadoPor) {

        // Validaciones básicas
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

        // Crear objeto doctor con los datos actualizados
        ModelDoctores doctor = new ModelDoctores();
        doctor.setIdDoctor(idDoctor);
        doctor.setNombre(nombre);
        doctor.setApellido(apellido);
        doctor.setEspecialidad(especialidad);
        doctor.setTelefono(telefono);
        doctor.setCorreo(correo);
        doctor.setCreadoPor(creadoPor);
        doctor.setEstado(true);
        doctor.setFechaCreacion(LocalDateTime.now());

        // Llamar al servicio para actualizar el doctor
        return serviceDoctores.actualizarDoctor(doctor);
    }

    /**
     * Elimina un doctor (desactivación lógica)
     *
     * @param idDoctor ID del doctor a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     */
    public boolean eliminarDoctor(int idDoctor) {
        if (idDoctor <= 0) {
            throw new IllegalArgumentException("ID de doctor inválido");
        }

        return serviceDoctores.eliminarDoctor(idDoctor);
    }

    /**
     * Busca un doctor por su ID
     *
     * @param idDoctor ID del doctor a buscar
     * @return El doctor encontrado o null si no existe
     */
    public ModelDoctores buscarDoctorPorId(int idDoctor) {
        if (idDoctor <= 0) {
            throw new IllegalArgumentException("ID de doctor inválido");
        }

        List<ModelDoctores> doctores = obtenerDoctores();
        for (ModelDoctores doctor : doctores) {
            if (doctor.getIdDoctor() == idDoctor) {
                return doctor;
            }
        }

        return null;
    }
}

