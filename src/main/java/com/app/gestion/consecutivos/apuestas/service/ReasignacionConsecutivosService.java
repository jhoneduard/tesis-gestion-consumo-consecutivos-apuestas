package com.app.gestion.consecutivos.apuestas.service;

import com.app.gestion.consecutivos.apuestas.exception.CodesaException;
import com.app.gestion.consecutivos.apuestas.request.FiltrarConfiguracionesPRRequest;
import com.app.gestion.consecutivos.apuestas.request.ReasignarConsecutivoRequest;
import com.app.gestion.consecutivos.apuestas.request.TerminarPapeleriaRequest;
import com.app.gestion.consecutivos.apuestas.response.ConfiguracionesPendientePorReasignarResponse;
import com.app.gestion.consecutivos.apuestas.response.PaginadorResponse;
import com.app.gestion.consecutivos.apuestas.response.StringResponse;

public interface ReasignacionConsecutivosService {
	StringResponse terminarPapeleria(TerminarPapeleriaRequest request)
			throws CodesaException;
	
	StringResponse reasignarConsecutivo(ReasignarConsecutivoRequest request)
			throws CodesaException;
	
	PaginadorResponse<ConfiguracionesPendientePorReasignarResponse> listarConfigPendientePorReasignar(
			 FiltrarConfiguracionesPRRequest request) throws CodesaException;
}
