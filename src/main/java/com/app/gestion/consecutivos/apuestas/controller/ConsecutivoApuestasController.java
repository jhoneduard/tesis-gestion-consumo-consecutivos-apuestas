package com.app.gestion.consecutivos.apuestas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.gestion.consecutivos.apuestas.exception.CodesaException;
import com.app.gestion.consecutivos.apuestas.request.ActualizarConsecutivoRequest;
import com.app.gestion.consecutivos.apuestas.request.ConsecutivoFiltroRequest;
import com.app.gestion.consecutivos.apuestas.request.GuardarConsecutivoRequest;
import com.app.gestion.consecutivos.apuestas.response.ConsecutivoApuestaResponse;
import com.app.gestion.consecutivos.apuestas.response.StringResponse;
import com.app.gestion.consecutivos.apuestas.service.ConsecutivoApuestasService;

@RestController
@RequestMapping("/api-consecutivos")
public class ConsecutivoApuestasController {

	@Autowired
	private ConsecutivoApuestasService consecutivoApuestasService;
	
	@PostMapping(value = "/listar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ConsecutivoApuestaResponse> filtrarAsignacionesConsecutivos(
			@RequestBody ConsecutivoFiltroRequest request) throws CodesaException {
		return new ResponseEntity<>(consecutivoApuestasService.filtrarAsignacionesConsecutivos(request),
				HttpStatus.OK);
	}

	@PostMapping(value = "/guardar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StringResponse> guardar(@RequestBody GuardarConsecutivoRequest request)
			throws CodesaException {
		return new ResponseEntity<>(consecutivoApuestasService.guardar(request),
				HttpStatus.OK);
	}

	@PutMapping(value = "/actualizar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StringResponse> actualizar(@RequestBody ActualizarConsecutivoRequest request)
			throws CodesaException {
		return new ResponseEntity<>(consecutivoApuestasService.actualizar(request),
				HttpStatus.OK);
	}

	@DeleteMapping(value = "/eliminar")
	public ResponseEntity<StringResponse> eliminar(@RequestParam Long configuracionId) throws CodesaException {
		return new ResponseEntity<>(consecutivoApuestasService.eliminar(configuracionId),
				HttpStatus.OK);
	}

}
