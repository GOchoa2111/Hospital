-- =========================
-- REGISTROS DE PRUEBA
-- =========================

-- Usuarios
INSERT INTO usuario (nombre, apellido, nombre_usuario, contrasena, id_rol, correo)
VALUES
('Ana', 'Pérez', 'admin1', 'admin123', 1, 'ana.perez@hospital.com'),
('Carlos', 'Ríos', 'medico1', 'medico123', 2, 'carlos.rios@hospital.com'),
('Laura', 'Gómez', 'recepcion1', 'recep123', 3, 'laura.gomez@hospital.com');

-- Servicios
INSERT INTO servicio (nombre, descripcion, precio)
VALUES 
('Consulta general', 'Consulta médica básica', 50.00),
('Examen de sangre', 'Análisis de laboratorio', 30.00);

-- Pacientes
INSERT INTO paciente (nombre, apellido, fecha_nacimiento, genero, tipo_sangre, direccion, telefono, correo, creado_por)
VALUES 
('María', 'López', '1990-05-10', 'Femenino', 'O+', 'Calle 123', '555-6789', 'maria.lopez@email.com', 3),
('José', 'Martínez', '1985-09-20', 'Masculino', 'A-', 'Av. Central', '555-9876', 'jose.martinez@email.com', 3);

-- Doctor
INSERT INTO doctor (nombre, apellido, especialidad, telefono, correo, creado_por)
VALUES ('Carlos', 'Ríos', 'Pediatría', '555-1234', 'carlos.rios@hospital.com',1);

-- Horario del doctor
INSERT INTO horario_doctor (id_doctor, dia_semana, hora_inicio, hora_fin, creado_por)
VALUES (1, 'Lunes', '08:00', '12:00', 1);

-- Cita
INSERT INTO cita (fecha_hora, id_servicio, id_paciente, id_doctor)
VALUES ('2025-05-20 09:00:00', 1, 1, 1);

-- Consulta
INSERT INTO consulta (diagnostico, tratamiento, id_cita, id_usuario)
VALUES ('Resfriado común', 'Paracetamol cada 8 horas', 1, 2);

-- Factura
INSERT INTO factura (fecha, id_paciente, id_usuario, total)
VALUES ('2025-05-20', 1, 1, 50.00);

-- Detalle de factura
INSERT INTO detalle_factura (id_factura, id_servicio, cantidad, subtotal)
VALUES (1, 1, 1, 50.00);