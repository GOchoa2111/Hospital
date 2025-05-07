USE hospital_salud;

-- Usuarios
INSERT INTO usuario (nombre, apellido, nombre_usuario, contrasena, rol, correo)
VALUES 
('Admin', 'Principal', 'admin1', 'admin123', 'administrador', 'admin@example.com'),
('Laura', 'Mendez', 'drlaura', 'doc123', 'doctor', 'laura@example.com'),
('Carlos', 'Reyes', 'reception', 'recep123', 'recepcionista', 'carlos@example.com');

-- Pacientes
INSERT INTO paciente (nombre, apellido, fecha_nacimiento, genero, tipo_sangre, direccion, telefono, correo, creado_por)
VALUES 
('Luis', 'Paredes', '1985-04-12', 'Masculino', 'O+', 'Calle Lima 123', '999888777', 'luisp@example.com', 1),
('Ana', 'Delgado', '1990-09-23', 'Femenino', 'A-', 'Av. Perú 456', '988776655', 'anad@example.com', 1);

-- Servicios
INSERT INTO servicio (nombre, descripcion, precio)
VALUES
('Consulta General', 'Atención médica general', 50.00),
('Ecografía', 'Ecografía abdominal', 150.00);

-- Doctores
INSERT INTO doctor (nombre, apellido, especialidad, telefono, correo, id_usuario, creado_por)
VALUES
('Laura', 'Mendez', 'Medicina General', '999001122', 'laura@example.com', 2, 1);

-- Horario del doctor
INSERT INTO horario_doctor (id_doctor, dia_semana, hora_inicio, hora_fin, creado_por)
VALUES
(1, 'Lunes', '08:00', '12:00', 1),
(1, 'Miércoles', '14:00', '18:00', 1);

-- Citas
INSERT INTO cita (fecha_hora, id_servicio, id_paciente, id_doctor)
VALUES
('2025-05-10 09:00', 1, 1, 1),
('2025-05-11 10:30', 2, 2, 1);

-- Consultas
INSERT INTO consulta (diagnostico, tratamiento, id_cita, id_usuario)
VALUES
('Gripe común', 'Paracetamol cada 8h por 3 días', 1, 2),
('Dolor abdominal', 'Ecografía, dieta liviana', 2, 2);

-- Historial médico
INSERT INTO historial_medico (id_paciente, descripcion, fecha)
VALUES
(1, 'Consulta por gripe común', '2025-05-10'),
(2, 'Consulta por dolor abdominal', '2025-05-11');

-- Facturas
INSERT INTO factura (fecha, id_paciente, id_usuario, total)
VALUES
('2025-05-10', 1, 3, 50.00),
('2025-05-11', 2, 3, 150.00);

-- Detalles de factura
INSERT INTO detalle_factura (id_factura, id_servicio, cantidad, subtotal)
VALUES
(1, 1, 1, 50.00),
(2, 2, 1, 150.00);
