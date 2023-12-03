package com.app.gestion.consecutivos.apuestas.persistencia;

import com.app.gestion.consecutivos.apuestas.request.AsignacionManualConsecutivosRequest;
import com.app.gestion.consecutivos.apuestas.request.FiltrarAsignacionConsecutivoRequest;
import com.app.gestion.consecutivos.apuestas.response.AsignacionConsecutivoResponse;
import com.app.gestion.consecutivos.apuestas.response.PaginadorResponse;
import com.app.gestion.consecutivos.apuestas.response.StringResponse;

public interface AsignacionConsecutivosDAO {
	StringResponse asignacionManual(AsignacionManualConsecutivosRequest request)
			throws Exception;
	
	PaginadorResponse<AsignacionConsecutivoResponse> filtrarAsignacionesConsecutivos(FiltrarAsignacionConsecutivoRequest request)
			throws Exception;
	
	StringResponse asignacionAutomatica(Long cedulaVendedor) throws Exception;
	
	Boolean validarConsecutivoVendedor(Long cedulaVendedor)
			throws Exception;
}
