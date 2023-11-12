package com.app.gestion.consecutivos.apuestas.service;

import com.app.gestion.consecutivos.apuestas.exception.CodesaException;
import com.app.gestion.consecutivos.apuestas.request.AsignacionManualConsecutivosRequest;
import com.app.gestion.consecutivos.apuestas.request.FiltrarAsignacionConsecutivoRequest;
import com.app.gestion.consecutivos.apuestas.response.AsignacionConsecutivoResponse;
import com.app.gestion.consecutivos.apuestas.response.PaginadorResponse;
import com.app.gestion.consecutivos.apuestas.response.StringResponse;

public interface AsignacionConsecutivosService {
	StringResponse asignacionManual(AsignacionManualConsecutivosRequest request)
			throws CodesaException;
	
	PaginadorResponse<AsignacionConsecutivoResponse> filtrarAsignacionesConsecutivos(FiltrarAsignacionConsecutivoRequest request)
			throws CodesaException;
	
	StringResponse asignacionAutomatica(Long cedulaVendedor)
			throws CodesaException;
	
	Boolean validarConsecutivoVendedor(Long cedulaVendedor)
			throws CodesaException;
}
