-- Crear la base de datos
CREATE DATABASE hospital_salud;
GO

-- Usar la base de datos
USE hospital_salud;
GO

-- Tabla de usuarios
CREATE TABLE usuario (
    id_usuario INT PRIMARY KEY IDENTITY(1,1),
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    nombre_usuario VARCHAR(50) UNIQUE NOT NULL,
    contrasena VARCHAR(255) NOT NULL,
    rol VARCHAR(50) NOT NULL CHECK (rol IN ('administrador', 'doctor', 'recepcionista')),
    correo VARCHAR(100) UNIQUE NOT NULL,
    token_recuperacion VARCHAR(100),
    fecha_expiracion_token DATETIME
);

-- Tabla de pacientes (con auditoría simple)
CREATE TABLE paciente (
    id_paciente INT PRIMARY KEY IDENTITY(1,1),
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    genero VARCHAR(10),
    tipo_sangre VARCHAR(10),
    direccion VARCHAR(200),
    telefono VARCHAR(20),
    correo VARCHAR(100),
    creado_por INT NOT NULL,
    fecha_creacion DATETIME NOT NULL DEFAULT GETDATE(),
	estado BIT DEFAULT 1,
	
    FOREIGN KEY (creado_por) REFERENCES usuario(id_usuario)
);

-- Tabla de servicios
CREATE TABLE servicio (
    id_servicio INT PRIMARY KEY IDENTITY(1,1),
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255),
    precio DECIMAL(10,2) NOT NULL CHECK (precio >= 0)
);

-- Tabla de doctores (con auditoría simple)
CREATE TABLE doctor (
    id_doctor INT PRIMARY KEY IDENTITY(1,1),
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    especialidad VARCHAR(100) NOT NULL,
    telefono VARCHAR(20),
    correo VARCHAR(100),
    id_usuario INT UNIQUE,
    creado_por INT NOT NULL,
    fecha_creacion DATETIME NOT NULL DEFAULT GETDATE(),
	estado BIT DEFAULT 1,
	
	-- LLaves foraneas
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario),
    FOREIGN KEY (creado_por) REFERENCES usuario(id_usuario)
);

-- Tabla de horarios de doctores (con auditoría simple)
CREATE TABLE horario_doctor (
    id_horario INT PRIMARY KEY IDENTITY(1,1),
    id_doctor INT NOT NULL,
    dia_semana VARCHAR(15) NOT NULL,
    hora_inicio TIME NOT NULL,
    hora_fin TIME NOT NULL,
    creado_por INT NOT NULL,
    fecha_creacion DATETIME NOT NULL DEFAULT GETDATE(),
	
	-- LLaves foraneas
    FOREIGN KEY (id_doctor) REFERENCES doctor(id_doctor),
    FOREIGN KEY (creado_por) REFERENCES usuario(id_usuario)
);

-- Tabla de citas médicas (ya modificada con id_servicio)
CREATE TABLE cita (
    id_cita INT PRIMARY KEY IDENTITY(1,1),
    fecha_hora DATETIME NOT NULL,
    id_servicio INT NOT NULL,
    id_paciente INT NOT NULL,
    id_doctor INT NOT NULL,
    FOREIGN KEY (id_paciente) REFERENCES paciente(id_paciente),
    FOREIGN KEY (id_doctor) REFERENCES doctor(id_doctor),
    FOREIGN KEY (id_servicio) REFERENCES servicio(id_servicio)
);

-- Tabla de consultas médicas
CREATE TABLE consulta (
    id_consulta INT PRIMARY KEY IDENTITY(1,1),
    diagnostico VARCHAR(500),
    tratamiento VARCHAR(500),
    id_cita INT NOT NULL UNIQUE,
    id_usuario INT,
    FOREIGN KEY (id_cita) REFERENCES cita(id_cita) ON DELETE CASCADE,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
);

-- Tabla de historial médico
CREATE TABLE historial_medico (
    id_historial INT PRIMARY KEY IDENTITY(1,1),
    id_paciente INT NOT NULL,
    descripcion VARCHAR(500),
    fecha DATE NOT NULL,
    FOREIGN KEY (id_paciente) REFERENCES paciente(id_paciente) ON DELETE CASCADE
);

-- Tabla de facturas
CREATE TABLE factura (
    id_factura INT PRIMARY KEY IDENTITY(1,1),
    fecha DATE NOT NULL,
	id_paciente INT NOT NULL,
    id_usuario INT NOT NULL,
    total DECIMAL(10,2) NOT NULL CHECK (total >= 0), -- validación de datos para que no sean nulos
    FOREIGN KEY (id_paciente) REFERENCES paciente(id_paciente),
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
);

-- Tabla de detalle de facturas
CREATE TABLE detalle_factura (
    id_detalle INT PRIMARY KEY IDENTITY(1,1),
    id_factura INT NOT NULL,
    id_servicio INT NOT NULL,
    cantidad INT NOT NULL CHECK (cantidad > 0),
    subtotal DECIMAL(10,2) NOT NULL CHECK (subtotal >= 0),
    FOREIGN KEY (id_factura) REFERENCES factura(id_factura) ON DELETE CASCADE,
    FOREIGN KEY (id_servicio) REFERENCES servicio(id_servicio)
);
