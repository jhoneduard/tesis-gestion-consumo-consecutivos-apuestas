package com.app.gestion.consecutivos.apuestas.service;

import com.app.gestion.consecutivos.apuestas.exception.CodesaException;
import com.app.gestion.consecutivos.apuestas.request.ActualizarConsecutivoRequest;
import com.app.gestion.consecutivos.apuestas.request.ConsecutivoFiltroRequest;
import com.app.gestion.consecutivos.apuestas.request.GuardarConsecutivoRequest;
import com.app.gestion.consecutivos.apuestas.response.ConsecutivoApuestaResponse;
import com.app.gestion.consecutivos.apuestas.response.StringResponse;

public interface ConsecutivoApuestasService {
	ConsecutivoApuestaResponse filtrarAsignacionesConsecutivos(
			ConsecutivoFiltroRequest request) throws CodesaException;
	
	StringResponse guardar(GuardarConsecutivoRequest request)
			throws CodesaException;
	
	StringResponse actualizar(ActualizarConsecutivoRequest request)
			throws CodesaException;
	
	StringResponse eliminar(Long configuracionId)
			throws CodesaException;
}
