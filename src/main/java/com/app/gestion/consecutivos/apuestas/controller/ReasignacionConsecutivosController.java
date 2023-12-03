/**
 * 
 */
package com.app.gestion.consecutivos.apuestas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.gestion.consecutivos.apuestas.request.FiltrarConfiguracionesPRRequest;
import com.app.gestion.consecutivos.apuestas.request.ReasignarConsecutivoRequest;
import com.app.gestion.consecutivos.apuestas.request.TerminarPapeleriaRequest;
import com.app.gestion.consecutivos.apuestas.response.ConfiguracionesPendientePorReasignarResponse;
import com.app.gestion.consecutivos.apuestas.response.PaginadorResponse;
import com.app.gestion.consecutivos.apuestas.response.StringResponse;
import com.app.gestion.consecutivos.apuestas.service.ReasignacionConsecutivosService;

/**
 * 
 */
@RestController
@RequestMapping("/api-consecutivos")
public class ReasignacionConsecutivosController {
	
	@Autowired
	private ReasignacionConsecutivosService reasignacionConsecutivosService;

	@PostMapping(value = "/terminar-papeleria", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StringResponse> terminarPapeleria(@RequestBody TerminarPapeleriaRequest request)
			throws Exception {
		return new ResponseEntity<>(reasignacionConsecutivosService.terminarPapeleria(request),
				HttpStatus.OK);
	}

	@PostMapping(value = "/reasignar-consecutivo", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StringResponse> reasignarConsecutivo(@RequestBody ReasignarConsecutivoRequest request)
			throws Exception {
		return new ResponseEntity<>(reasignacionConsecutivosService.reasignarConsecutivo(request),
				HttpStatus.OK);
	}

	@PostMapping(value = "/listar-configuraciones-pendientes-por-reasignar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PaginadorResponse<ConfiguracionesPendientePorReasignarResponse>> listarConfigPendientePorReasignar(
			@RequestBody FiltrarConfiguracionesPRRequest request) throws Exception {
		return new ResponseEntity<>(reasignacionConsecutivosService.listarConfigPendientePorReasignar(request),
				HttpStatus.OK);
	}

}
