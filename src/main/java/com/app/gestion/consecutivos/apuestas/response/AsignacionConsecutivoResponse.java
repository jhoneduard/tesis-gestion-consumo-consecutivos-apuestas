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
public class AsignacionConsecutivoResponse {

	private Long id;

	private Long vendedorId;

	private Long oficinaId;

	private Long zonaId;

	private String prefijo;

	private String consecutivoInicial;

	private String consecutivoFinal;

	private String consecutivoActual;

	private String estado;

	private LocalDate fechaAsigna;

	private String horaAsigna;

}
