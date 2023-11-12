package com.app.gestion.consecutivos.apuestas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.gestion.consecutivos.apuestas.exception.CodesaException;
import com.app.gestion.consecutivos.apuestas.persistencia.AsignacionConsecutivosRepository;
import com.app.gestion.consecutivos.apuestas.request.AsignacionManualConsecutivosRequest;
import com.app.gestion.consecutivos.apuestas.request.FiltrarAsignacionConsecutivoRequest;
import com.app.gestion.consecutivos.apuestas.response.AsignacionConsecutivoResponse;
import com.app.gestion.consecutivos.apuestas.response.PaginadorResponse;
import com.app.gestion.consecutivos.apuestas.response.StringResponse;

@Service
public class AsignacionConsecutivosServiceImpl implements AsignacionConsecutivosService {

	@Override
	@Transactional
	public StringResponse asignacionManual(AsignacionManualConsecutivosRequest request) throws CodesaException {
		try {
			return null;
		} catch (CodesaException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new CodesaException(ex.getMessage());
		}
	}

	@Override
	@Transactional(readOnly = true)
	public PaginadorResponse<AsignacionConsecutivoResponse> filtrarAsignacionesConsecutivos(
			FiltrarAsignacionConsecutivoRequest request) throws CodesaException {
		try {
			return null;
		} catch (CodesaException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new CodesaException(ex.getMessage());
		}
	}

	@Override
	@Transactional
	public StringResponse asignacionAutomatica(Long cedulaVendedor) throws CodesaException {
		try {
			return null;
		} catch (CodesaException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new CodesaException(ex.getMessage());
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Boolean validarConsecutivoVendedor(Long cedulaVendedor) throws CodesaException {
		try {
			return null;
		} catch (CodesaException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new CodesaException(ex.getMessage());
		}
	}

}
