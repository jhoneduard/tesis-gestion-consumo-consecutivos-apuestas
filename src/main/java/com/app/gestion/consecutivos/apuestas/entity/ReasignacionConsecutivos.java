/**
 * 
 */
package com.app.gestion.consecutivos.apuestas.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 */
@Entity
@Table(name = "reasignacion_consecutivos")
@Getter
@Setter
public class ReasignacionConsecutivos {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long configuracionId;

	private String prefijo;

	private String consecutivoInicial;

	private String consecutivoFinal;

	private String consecutivoActual;

	private String estado;

	private Long cedulaVendedor;

	private Long oficinaId;

	private String nombreOficina;

	private Long zonaId;

	private String nombreZona;
	
}
