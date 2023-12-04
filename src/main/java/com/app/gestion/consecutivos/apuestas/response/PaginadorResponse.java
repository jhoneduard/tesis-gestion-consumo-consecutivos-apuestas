/**
 * 
 */
package com.app.gestion.consecutivos.apuestas.response;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 */
@Setter
@Getter
@NoArgsConstructor
public class PaginadorResponse<T> {
	private transient List<T> listado;
	
	private Integer pagina;

	private Integer totalRegistros;
}
