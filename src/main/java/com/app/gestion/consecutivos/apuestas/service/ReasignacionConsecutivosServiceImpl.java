package com.app.gestion.consecutivos.apuestas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.gestion.consecutivos.apuestas.persistencia.ReasignacionConsecutivosDAO;
import com.app.gestion.consecutivos.apuestas.request.FiltrarConfiguracionesPRRequest;
import com.app.gestion.consecutivos.apuestas.request.ReasignarConsecutivoRequest;
import com.app.gestion.consecutivos.apuestas.request.TerminarPapeleriaRequest;
import com.app.gestion.consecutivos.apuestas.response.ConfiguracionesPendientePorReasignarResponse;
import com.app.gestion.consecutivos.apuestas.response.PaginadorResponse;
import com.app.gestion.consecutivos.apuestas.response.StringResponse;

@Service
public class ReasignacionConsecutivosServiceImpl implements ReasignacionConsecutivosService {

	@Autowired
	private ReasignacionConsecutivosDAO reasignacionConsecutivosDAO;
	
	@Override
	public StringResponse terminarPapeleria(TerminarPapeleriaRequest request) throws Exception {
		try {
			return reasignacionConsecutivosDAO.terminarPapeleria(request);
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

	@Override
	public StringResponse reasignarConsecutivo(ReasignarConsecutivoRequest request) throws Exception {
		try {
			return reasignacionConsecutivosDAO.reasignarConsecutivo(request);
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

	@Override
	public PaginadorResponse<ConfiguracionesPendientePorReasignarResponse> listarConfigPendientePorReasignar(
			FiltrarConfiguracionesPRRequest request) throws Exception {
		try {
			return reasignacionConsecutivosDAO.listarConfigPendientePorReasignar(request);
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

}
