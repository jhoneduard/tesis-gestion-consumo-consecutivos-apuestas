/**
 * 
 */
package com.app.gestion.consecutivos.apuestas.request;

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
public class AsignacionManualConsecutivosRequest {
	private List<Long> listaVendedores;

}
