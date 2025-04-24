-- Crear la base de datos

CREATE DATABASE HospitalSalud;

-- seleccionar la base de datos

USE HospitalSalud;


-- Tabla de Pacientes
CREATE TABLE paciente (
    id_paciente INT PRIMARY KEY IDENTITY(1,1),
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    genero VARCHAR(10),
    direccion VARCHAR(200),
    telefono VARCHAR(20),
    correo VARCHAR(100)
);

-- Tabla de Doctores
CREATE TABLE doctor (
    id_doctor INT PRIMARY KEY IDENTITY(1,1),
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    especialidad VARCHAR(100) NOT NULL,
    telefono VARCHAR(20),
    correo VARCHAR(100)
);

-- Tabla de Horarios de los doctores
CREATE TABLE horario_doctor (
    id_horario INT PRIMARY KEY IDENTITY(1,1),
    id_doctor INT NOT NULL,
    dia_semana VARCHAR(15),
    hora_inicio TIME,
    hora_fin TIME,
    FOREIGN KEY (id_doctor) REFERENCES doctor(id_doctor)
);

-- Tabla de Citas Médicas
CREATE TABLE cita (
    id_cita INT PRIMARY KEY IDENTITY(1,1),
    fecha_hora DATETIME NOT NULL,
    motivo VARCHAR(255),
    id_paciente INT NOT NULL,
    id_doctor INT NOT NULL,
    FOREIGN KEY (id_paciente) REFERENCES paciente(id_paciente),
    FOREIGN KEY (id_doctor) REFERENCES doctor(id_doctor)
);

-- Tabla de Consultas Médicas
CREATE TABLE consulta (
    id_consulta INT PRIMARY KEY IDENTITY(1,1),
    diagnostico VARCHAR(500),
    tratamiento VARCHAR(500),
    id_cita INT NOT NULL,
    FOREIGN KEY (id_cita) REFERENCES cita(id_cita)
);

-- Tabla de Historial Médico
CREATE TABLE historial_medico (
    id_historial INT PRIMARY KEY IDENTITY(1,1),
    id_paciente INT NOT NULL,
    descripcion VARCHAR(500),
    fecha DATE NOT NULL,
    FOREIGN KEY (id_paciente) REFERENCES paciente(id_paciente)
);

-- Tabla de Usuarios
CREATE TABLE usuario (
    id_usuario INT PRIMARY KEY IDENTITY(1,1),
    nombre_usuario VARCHAR(50) UNIQUE NOT NULL,
    contrasena VARCHAR(255) NOT NULL,
    rol VARCHAR(20) NOT NULL
);

-- Tabla de Servicios
CREATE TABLE servicio (
    id_servicio INT PRIMARY KEY IDENTITY(1,1),
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255),
    precio DECIMAL(10,2) NOT NULL
);

-- Tabla de Facturas
CREATE TABLE factura (
    id_factura INT PRIMARY KEY IDENTITY(1,1),
    fecha DATE NOT NULL,
    total DECIMAL(10,2) NOT NULL,
    id_paciente INT NOT NULL,
    FOREIGN KEY (id_paciente) REFERENCES paciente(id_paciente)
);

-- Tabla de Detalles de Factura
CREATE TABLE detalle_factura (
    id_detalle INT PRIMARY KEY IDENTITY(1,1),
    id_factura INT NOT NULL,
    id_servicio INT NOT NULL,
    cantidad INT DEFAULT 1,
    subtotal AS (cantidad * (SELECT precio FROM servicio WHERE id_servicio = detalle_factura.id_servicio)) PERSISTED,
    FOREIGN KEY (id_factura) REFERENCES factura(id_factura),
    FOREIGN KEY (id_servicio) REFERENCES servicio(id_servicio)
);
