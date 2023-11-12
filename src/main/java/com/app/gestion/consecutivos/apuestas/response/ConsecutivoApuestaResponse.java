/**
 * 
 */
package com.app.gestion.consecutivos.apuestas.response;

import java.time.LocalDate;
import java.util.List;

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

	private List<ConsecutivoApuesta> contenido;

    private int numeroPagina;
    
    private int tamañoPagina;
    
    private long totalElementos;
    
    private int totalPaginas;
    
    private boolean ultimo;
    
	@Getter
	@Setter
	@Builder
	public static class ConsecutivoApuesta {
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
}
