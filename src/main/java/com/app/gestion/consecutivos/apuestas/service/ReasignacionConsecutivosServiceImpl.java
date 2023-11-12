package com.app.gestion.consecutivos.apuestas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.gestion.consecutivos.apuestas.exception.CodesaException;
import com.app.gestion.consecutivos.apuestas.persistencia.ReasignacionConsecutivosRepository;
import com.app.gestion.consecutivos.apuestas.request.FiltrarConfiguracionesPRRequest;
import com.app.gestion.consecutivos.apuestas.request.ReasignarConsecutivoRequest;
import com.app.gestion.consecutivos.apuestas.request.TerminarPapeleriaRequest;
import com.app.gestion.consecutivos.apuestas.response.ConfiguracionesPendientePorReasignarResponse;
import com.app.gestion.consecutivos.apuestas.response.PaginadorResponse;
import com.app.gestion.consecutivos.apuestas.response.StringResponse;

@Service
public class ReasignacionConsecutivosServiceImpl implements ReasignacionConsecutivosService {

	
	@Override
	public StringResponse terminarPapeleria(TerminarPapeleriaRequest request) throws CodesaException {
		try {
			return null;
		} catch (CodesaException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new CodesaException(ex.getMessage());
		}
	}

	@Override
	public StringResponse reasignarConsecutivo(ReasignarConsecutivoRequest request) throws CodesaException {
		try {
			return null;
		} catch (CodesaException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new CodesaException(ex.getMessage());
		}
	}

	@Override
	public PaginadorResponse<ConfiguracionesPendientePorReasignarResponse> listarConfigPendientePorReasignar(
			FiltrarConfiguracionesPRRequest request) throws CodesaException {
		try {
			return null;
		} catch (CodesaException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new CodesaException(ex.getMessage());
		}
	}

}
