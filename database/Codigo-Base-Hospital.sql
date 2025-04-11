-- SCRIPT Base Hospital

CREATE SCHEMA IF NOT EXISTS `HospitalSistema` DEFAULT CHARACTER SET utf8;
USE `HospitalSistema`;

-- Estado
CREATE TABLE IF NOT EXISTS `Estado` (
  `idEstado` INT(11) NOT NULL AUTO_INCREMENT,
  `Estadocol` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idEstado`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Usuarios
CREATE TABLE IF NOT EXISTS `Usuarios` (
  `idUsuarios` INT(11) NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(45) NOT NULL,
  `Usuario` VARCHAR(45) NOT NULL,
  `Contraseña` VARCHAR(12) NOT NULL,
  PRIMARY KEY (`idUsuarios`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Rol
CREATE TABLE IF NOT EXISTS `Rol` (
  `idRol` INT(11) NOT NULL AUTO_INCREMENT,
  `NombreRol` VARCHAR(45) NOT NULL,
  `Usuarios_idUsuarios` INT(11) NOT NULL,
  PRIMARY KEY (`idRol`),
  INDEX `fk_Rol_Usuarios1_idx` (`Usuarios_idUsuarios`),
  CONSTRAINT `fk_Rol_Usuarios1` FOREIGN KEY (`Usuarios_idUsuarios`) REFERENCES `Usuarios` (`idUsuarios`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Paciente
CREATE TABLE IF NOT EXISTS `Paciente` (
  `idPaciente` INT(11) NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(45) NOT NULL,
  `Apellido` VARCHAR(45) NOT NULL,
  `FechaNacimiento` DATE NOT NULL,
  `TipoSangre` VARCHAR(3) NOT NULL,
  `Direccion` VARCHAR(120) NOT NULL,
  `Telefono` VARCHAR(45) NOT NULL,
  `CorreoElectronico` VARCHAR(120) NOT NULL,
  PRIMARY KEY (`idPaciente`),
  UNIQUE INDEX `CorreoElectronico_UNIQUE` (`CorreoElectronico`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- EmpleadosHospital
CREATE TABLE IF NOT EXISTS `EmpleadosHospital` (
  `idEmpleadosHospital` INT(11) NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(45),
  `Apellido` VARCHAR(45),
  `FechaDeIngreso` VARCHAR(45) NOT NULL,
  `Telefono` VARCHAR(45) NOT NULL,
  `Direccion` VARCHAR(120) NOT NULL,
  `Usuarios_idUsuarios` INT(11) NOT NULL,
  PRIMARY KEY (`idEmpleadosHospital`),
  INDEX `fk_EmpleadosHospital_Usuarios1_idx` (`Usuarios_idUsuarios`),
  CONSTRAINT `fk_EmpleadosHospital_Usuarios1` FOREIGN KEY (`Usuarios_idUsuarios`) REFERENCES `Usuarios` (`idUsuarios`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Especialidad
CREATE TABLE IF NOT EXISTS `Especialidad` (
  `idEspecialidad` INT(11) NOT NULL AUTO_INCREMENT,
  `NombreEspecialidad` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idEspecialidad`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Medico
CREATE TABLE IF NOT EXISTS `Medico` (
  `idMedico` INT(11) NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(45) NOT NULL,
  `Apellido` VARCHAR(45) NOT NULL,
  `Id_Especialidad` VARCHAR(45) NOT NULL,
  `Telefono` VARCHAR(45) NOT NULL,
  `CorreoElectronico` VARCHAR(45) NOT NULL,
  `Colegiado` INT(11) NOT NULL,
  `HorariosAtencion` VARCHAR(45),
  PRIMARY KEY (`idMedico`),
  UNIQUE INDEX `Colegiado_UNIQUE` (`Colegiado`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- MedicoEspecialidad
CREATE TABLE IF NOT EXISTS `MedicoEspecialidad` (
  `Medico_idMedico` INT(11) NOT NULL,
  `Especialidad_idEspecialidad` INT(11) NOT NULL,
  PRIMARY KEY (`Medico_idMedico`, `Especialidad_idEspecialidad`),
  INDEX `fk_Medico_has_Especialidad_Especialidad1_idx` (`Especialidad_idEspecialidad`),
  CONSTRAINT `fk_Medico_has_Especialidad_Medico1` FOREIGN KEY (`Medico_idMedico`) REFERENCES `Medico` (`idMedico`),
  CONSTRAINT `fk_Medico_has_Especialidad_Especialidad1` FOREIGN KEY (`Especialidad_idEspecialidad`) REFERENCES `Especialidad` (`idEspecialidad`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- CitasProgramadas
CREATE TABLE IF NOT EXISTS `CitasProgramadas` (
  `Id_Citas` INT(11) NOT NULL AUTO_INCREMENT,
  `Paciente_idPaciente` INT(11) NOT NULL,
  `Medico_idMedico` INT(11) NOT NULL,
  `FechaCita` DATE NOT NULL,
  `HoraCita` DATETIME NOT NULL,
  `Motivo` VARCHAR(45) NOT NULL,
  `Estado_id` INT(11) NOT NULL,
  PRIMARY KEY (`Id_Citas`),
  INDEX `fk_Paciente_has_Medico_Medico1_idx` (`Medico_idMedico`),
  INDEX `fk_Paciente_has_Medico_Paciente_idx` (`Paciente_idPaciente`),
  INDEX `Estado_id_idx` (`Estado_id`),
  CONSTRAINT `fk_Paciente_has_Medico_Paciente` FOREIGN KEY (`Paciente_idPaciente`) REFERENCES `Paciente` (`idPaciente`),
  CONSTRAINT `fk_Paciente_has_Medico_Medico1` FOREIGN KEY (`Medico_idMedico`) REFERENCES `Medico` (`idMedico`),
  CONSTRAINT `Estado_id` FOREIGN KEY (`Estado_id`) REFERENCES `Estado` (`idEstado`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- HistorialClinico
CREATE TABLE IF NOT EXISTS `HistorialClinico` (
  `idHistorialClinico` INT(11) NOT NULL AUTO_INCREMENT,
  `Paciente_idPaciente` INT(11) NOT NULL,
  `Medico_idMedico` INT(11) NOT NULL,
  `FechaHistorial` DATETIME,
  `Diagnostico` LONGTEXT,
  `Tratamiento` LONGTEXT,
  `NotasMedico` LONGTEXT,
  PRIMARY KEY (`idHistorialClinico`),
  INDEX `fk_Paciente_has_Medico_Medico2_idx` (`Medico_idMedico`),
  INDEX `fk_Paciente_has_Medico_Paciente1_idx` (`Paciente_idPaciente`),
  CONSTRAINT `fk_Paciente_has_Medico_Paciente1` FOREIGN KEY (`Paciente_idPaciente`) REFERENCES `Paciente` (`idPaciente`),
  CONSTRAINT `fk_Paciente_has_Medico_Medico2` FOREIGN KEY (`Medico_idMedico`) REFERENCES `Medico` (`idMedico`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- RecetaMedica
CREATE TABLE IF NOT EXISTS `RecetaMedica` (
  `idRecetaMedica` INT(11) NOT NULL AUTO_INCREMENT,
  `HistorialClinico_idHistorialClinico` INT(11) NOT NULL,
  `RecetaMedicacol` VARCHAR(45) NOT NULL,
  `FrecuenciaMedicamento` VARCHAR(45) NOT NULL,
  `DuracionAplicacion` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idRecetaMedica`),
  INDEX `fk_HistorialClinico_has_Medicamento_HistorialClinico1_idx` (`HistorialClinico_idHistorialClinico`),
  CONSTRAINT `fk_HistorialClinico_has_Medicamento_HistorialClinico1` FOREIGN KEY (`HistorialClinico_idHistorialClinico`) REFERENCES `HistorialClinico` (`idHistorialClinico`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Medicamento
CREATE TABLE IF NOT EXISTS `Medicamento` (
  `NombreMedicamento` VARCHAR(45) NOT NULL,
  `DescripcionMedicamento` LONGTEXT NOT NULL,
  `RecetaMedica_idRecetaMedica` INT(11) NOT NULL,
  INDEX `fk_Medicamento_RecetaMedica1_idx` (`RecetaMedica_idRecetaMedica`),
  CONSTRAINT `fk_Medicamento_RecetaMedica1` FOREIGN KEY (`RecetaMedica_idRecetaMedica`) REFERENCES `RecetaMedica` (`idRecetaMedica`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Servicios
CREATE TABLE IF NOT EXISTS `Servicios` (
  `idServicios` INT(11) NOT NULL AUTO_INCREMENT,
  `NombreServicio` VARCHAR(45),
  `DescripcionServicio` LONGTEXT NOT NULL,
  `Precio` FLOAT(11) NOT NULL,
  PRIMARY KEY (`idServicios`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Facturacion
CREATE TABLE IF NOT EXISTS `Facturacion` (
  `idFactura` INT(11) NOT NULL AUTO_INCREMENT,
  `Paciente_idPaciente` INT(11) NOT NULL,
  `FechaFacturacion` DATETIME NOT NULL,
  `Total` INT(11),
  `Estado_idEstado` INT(11) NOT NULL,
  PRIMARY KEY (`idFactura`),
  INDEX `fk_Usuarios_has_Paciente_Paciente1_idx` (`Paciente_idPaciente`),
  INDEX `fk_Facturacion_Estado1_idx` (`Estado_idEstado`),
  CONSTRAINT `fk_Usuarios_has_Paciente_Paciente1` FOREIGN KEY (`Paciente_idPaciente`) REFERENCES `Paciente` (`idPaciente`),
  CONSTRAINT `fk_Facturacion_Estado1` FOREIGN KEY (`Estado_idEstado`) REFERENCES `Estado` (`idEstado`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- DetalleFactura
CREATE TABLE IF NOT EXISTS `DetalleFactura` (
  `idDetalleFactura` INT(11) NOT NULL AUTO_INCREMENT,
  `Facturacion_idFactura` INT(11) NOT NULL,
  `Servicios_idServicios` INT(11) NOT NULL,
  `PrecioServicio` FLOAT(11) NOT NULL,
  `SubtotalServicio` FLOAT(11),
  PRIMARY KEY (`idDetalleFactura`),
  INDEX `fk_Facturacion_has_Precio_Facturacion1_idx` (`Facturacion_idFactura`),
  INDEX `fk_DetalleFactura_Servicios1_idx` (`Servicios_idServicios`),
  CONSTRAINT `fk_Facturacion_has_Precio_Facturacion1` FOREIGN KEY (`Facturacion_idFactura`) REFERENCES `Facturacion` (`idFactura`),
  CONSTRAINT `fk_DetalleFactura_Servicios1` FOREIGN KEY (`Servicios_idServicios`) REFERENCES `Servicios` (`idServicios`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Modulo (Permisos)
CREATE TABLE IF NOT EXISTS `Modulo` (
  `idPermisos` INT(11) NOT NULL AUTO_INCREMENT,
  `Modulo` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idPermisos`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Rol_has_Permisos
CREATE TABLE IF NOT EXISTS `Rol_has_Permisos` (
  `Rol_idRol` INT(11) NOT NULL,
  `Permisos_idPermisos` INT(11) NOT NULL,
  `ver` VARCHAR(45),
  `Escribir` TINYINT(4),
  `Modificar` TINYINT(4),
  `Eliminar` TINYINT(4),
  PRIMARY KEY (`Rol_idRol`, `Permisos_idPermisos`),
  INDEX `fk_Rol_has_Permisos_Permisos1_idx` (`Permisos_idPermisos`),
  INDEX `fk_Rol_has_Permisos_Rol1_idx` (`Rol_idRol`),
  CONSTRAINT `fk_Rol_has_Permisos_Rol1` FOREIGN KEY (`Rol_idRol`) REFERENCES `Rol` (`idRol`),
  CONSTRAINT `fk_Rol_has_Permisos_Permisos1` FOREIGN KEY (`Permisos_idPermisos`) REFERENCES `Modulo` (`idPermisos`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
