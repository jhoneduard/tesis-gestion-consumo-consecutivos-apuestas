/**
 * 
 */
package com.app.gestion.consecutivos.apuestas.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.gestion.consecutivos.apuestas.request.AsignacionManualConsecutivosRequest;
import com.app.gestion.consecutivos.apuestas.request.FiltrarAsignacionConsecutivoRequest;
import com.app.gestion.consecutivos.apuestas.response.AsignacionConsecutivoResponse;
import com.app.gestion.consecutivos.apuestas.response.PaginadorResponse;
import com.app.gestion.consecutivos.apuestas.response.StringResponse;
import com.app.gestion.consecutivos.apuestas.service.AsignacionConsecutivosService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;;

/**
 * 
 */
@RestController
@RequestMapping("/api-consecutivos")
public class AsignacionConsecutivosController {
	
	@Autowired
	private AsignacionConsecutivosService asignacionConsecutivosService;

	@PostMapping(value = "/asignacion-manual", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StringResponse> asignacionManual(@RequestBody AsignacionManualConsecutivosRequest request)
			throws Exception {
		return new ResponseEntity<>(asignacionConsecutivosService.asignacionManual(request),
				HttpStatus.OK);
	}

	@PostMapping(value = "/filtrar-asignacion-consecutivo", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PaginadorResponse<AsignacionConsecutivoResponse>> filtrarAsignacionesConsecutivos(
			@RequestBody FiltrarAsignacionConsecutivoRequest request) throws Exception {
		return new ResponseEntity<>(asignacionConsecutivosService.filtrarAsignacionesConsecutivos(request),
				HttpStatus.OK);
	}

	@GetMapping(value = "/asignacion-automatica", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StringResponse> asignacionAutomatica(@RequestParam Long cedulaVendedor)
			throws Exception {
		return new ResponseEntity<>(asignacionConsecutivosService.asignacionAutomatica(cedulaVendedor),
				HttpStatus.OK);
	}

	@GetMapping(value = "/validar-consecutivo-vendedor")
	public ResponseEntity<Boolean> validarConsecutivoVendedor(@RequestParam Long cedulaVendedor)
			throws Exception {
		return new ResponseEntity<>(asignacionConsecutivosService.validarConsecutivoVendedor(cedulaVendedor),
				HttpStatus.OK);
	}
}
