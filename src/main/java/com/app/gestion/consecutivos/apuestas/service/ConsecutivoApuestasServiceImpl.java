package com.app.gestion.consecutivos.apuestas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.gestion.consecutivos.apuestas.persistencia.ConsecutivoApuestasDAO;
import com.app.gestion.consecutivos.apuestas.request.ActualizarConsecutivoRequest;
import com.app.gestion.consecutivos.apuestas.request.ConsecutivoFiltroRequest;
import com.app.gestion.consecutivos.apuestas.request.GuardarConsecutivoRequest;
import com.app.gestion.consecutivos.apuestas.response.ConsecutivoApuestaResponse;
import com.app.gestion.consecutivos.apuestas.response.PaginadorResponse;
import com.app.gestion.consecutivos.apuestas.response.StringResponse;

@Service
public class ConsecutivoApuestasServiceImpl implements ConsecutivoApuestasService {

	@Autowired
	private ConsecutivoApuestasDAO consecutivoApuestasDAO;
	
	@Override
	@Transactional(readOnly = true)
	public PaginadorResponse<ConsecutivoApuestaResponse> filtrarAsignacionesConsecutivos(
			ConsecutivoFiltroRequest request) throws Exception {
		try {
			return consecutivoApuestasDAO.filtrarAsignacionesConsecutivos(request);
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

	@Override
	@Transactional
	public StringResponse guardar(GuardarConsecutivoRequest request) throws Exception {
		try {
			return consecutivoApuestasDAO.guardar(request);
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

	@Override
	@Transactional
	public StringResponse actualizar(ActualizarConsecutivoRequest request) throws Exception {
		try {
			return consecutivoApuestasDAO.actualizar(request);
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

	@Override
	@Transactional
	public StringResponse eliminar(Long configuracionId) throws Exception {
		try {
			return consecutivoApuestasDAO.eliminar(configuracionId);
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

}
