/**
 * 
 */
package com.app.gestion.consecutivos.apuestas.response;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 */
@Getter
@Setter
@Builder
public class ConsecutivoApuestaResponse {

	private Long configuracionId;

	private String observacion;

	private LocalDate fechaAsigna;

	private String prefijo;

	private String consecutivoInicial;

	private String consecutivoFinal;

	private String consecutivoActual;

	private Integer cantidadDisponible;

	private String estado;

	private Integer cantidad;
}
