package com.vehiculo.vehiculo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Aplicación principal para la API de Gestión de Vehículos
 * 
 * Esta aplicación proporciona endpoints REST para realizar operaciones CRUD
 * sobre vehículos usando MongoDB como base de datos.
 * 
 * @author Equipo de Desarrollo
 * @version 1.0.0
 */
@SpringBootApplication
public class VehiculoApplication {

	public static void main(String[] args) {
		SpringApplication.run(VehiculoApplication.class, args);
	}

}
