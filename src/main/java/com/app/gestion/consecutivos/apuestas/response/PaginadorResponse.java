/**
 * 
 */
package com.app.gestion.consecutivos.apuestas.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 */
@Setter
@Getter
@Builder
public class PaginadorResponse<T> {
	private transient List<T> listado;

	private Integer pagina;

	private Integer totalRegistrosPorPagina;

	private Integer totalRegistros;

	private Integer getTotalPaginas() {
		if (totalRegistrosPorPagina == null) {
			return 0;
		}
		return (int) Math.ceil((float) totalRegistros / (float) totalRegistrosPorPagina);
	}
}
