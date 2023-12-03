package com.app.gestion.consecutivos.apuestas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.gestion.consecutivos.apuestas.persistencia.AsignacionConsecutivosDAO;
import com.app.gestion.consecutivos.apuestas.request.AsignacionManualConsecutivosRequest;
import com.app.gestion.consecutivos.apuestas.request.FiltrarAsignacionConsecutivoRequest;
import com.app.gestion.consecutivos.apuestas.response.AsignacionConsecutivoResponse;
import com.app.gestion.consecutivos.apuestas.response.PaginadorResponse;
import com.app.gestion.consecutivos.apuestas.response.StringResponse;

@Service
public class AsignacionConsecutivosServiceImpl implements AsignacionConsecutivosService {

	@Autowired
	private AsignacionConsecutivosDAO asignacionConsecutivosDAO;
	
	@Override
	@Transactional
	public StringResponse asignacionManual(AsignacionManualConsecutivosRequest request) 
			throws Exception{
		try {
			return asignacionConsecutivosDAO.asignacionManual(request);
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

	@Override
	@Transactional(readOnly = true)
	public PaginadorResponse<AsignacionConsecutivoResponse> filtrarAsignacionesConsecutivos(
			FiltrarAsignacionConsecutivoRequest request) throws Exception{
		try {
			return asignacionConsecutivosDAO.filtrarAsignacionesConsecutivos(request);
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

	@Override
	@Transactional
	public StringResponse asignacionAutomatica(Long cedulaVendedor) throws Exception{
		try {
			return asignacionConsecutivosDAO.asignacionAutomatica(cedulaVendedor);
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Boolean validarConsecutivoVendedor(Long cedulaVendedor) throws Exception{
		try {
			return asignacionConsecutivosDAO.validarConsecutivoVendedor(cedulaVendedor);
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

}
