package com.app.gestion.consecutivos.apuestas.persistencia;

import com.app.gestion.consecutivos.apuestas.request.FiltrarConfiguracionesPRRequest;
import com.app.gestion.consecutivos.apuestas.request.ReasignarConsecutivoRequest;
import com.app.gestion.consecutivos.apuestas.request.TerminarPapeleriaRequest;
import com.app.gestion.consecutivos.apuestas.response.ConfiguracionesPendientePorReasignarResponse;
import com.app.gestion.consecutivos.apuestas.response.PaginadorResponse;
import com.app.gestion.consecutivos.apuestas.response.StringResponse;

public interface ReasignacionConsecutivosDAO {
	StringResponse terminarPapeleria(TerminarPapeleriaRequest request)
			throws Exception;
	
	StringResponse reasignarConsecutivo(ReasignarConsecutivoRequest request)
			throws Exception;
	
	PaginadorResponse<ConfiguracionesPendientePorReasignarResponse> listarConfigPendientePorReasignar(
			 FiltrarConfiguracionesPRRequest request) throws Exception;
}
