/**
 * 
 */
package com.app.gestion.consecutivos.apuestas.exception;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

/**
 * 
 */
@Getter
@Log4j2
public class CodesaException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected final String mensaje;

	protected final String mensajeCliente;
	
	public CodesaException(String mensaje) {
		super();
		this.mensaje = mensaje;
		this.mensajeCliente = "";
		log.error(mensaje);
	}

	public CodesaException(String mensaje, String mensajeCliente) {
		super();
		this.mensaje = mensaje;
		this.mensajeCliente = mensajeCliente;
		log.error(mensaje);
	}
}
