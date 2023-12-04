package com.app.gestion.consecutivos.apuestas.service;

import com.app.gestion.consecutivos.apuestas.request.ActualizarConsecutivoRequest;
import com.app.gestion.consecutivos.apuestas.request.ConsecutivoFiltroRequest;
import com.app.gestion.consecutivos.apuestas.request.GuardarConsecutivoRequest;
import com.app.gestion.consecutivos.apuestas.response.ConsecutivoApuestaResponse;
import com.app.gestion.consecutivos.apuestas.response.PaginadorResponse;
import com.app.gestion.consecutivos.apuestas.response.StringResponse;

public interface ConsecutivoApuestasService {
	PaginadorResponse<ConsecutivoApuestaResponse> filtrarAsignacionesConsecutivos(
			ConsecutivoFiltroRequest request) throws Exception;
	
	StringResponse guardar(GuardarConsecutivoRequest request)
			throws Exception;
	
	StringResponse actualizar(ActualizarConsecutivoRequest request)
			throws Exception;
	
	StringResponse eliminar(Long configuracionId)
			throws Exception;
}
