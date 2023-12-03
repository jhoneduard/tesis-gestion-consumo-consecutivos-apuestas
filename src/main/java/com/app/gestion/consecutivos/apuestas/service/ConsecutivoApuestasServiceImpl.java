package com.app.gestion.consecutivos.apuestas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.gestion.consecutivos.apuestas.entity.ConsecutivoApuesta;
import com.app.gestion.consecutivos.apuestas.mapper.ConsecutivoApuestaMapper;
import com.app.gestion.consecutivos.apuestas.persistencia.ConsecutivoApuestasDAO;
import com.app.gestion.consecutivos.apuestas.request.ActualizarConsecutivoRequest;
import com.app.gestion.consecutivos.apuestas.request.ConsecutivoFiltroRequest;
import com.app.gestion.consecutivos.apuestas.request.GuardarConsecutivoRequest;
import com.app.gestion.consecutivos.apuestas.response.ConsecutivoApuestaResponse;
import com.app.gestion.consecutivos.apuestas.response.StringResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


@Service
public class ConsecutivoApuestasServiceImpl implements ConsecutivoApuestasService {

	@Autowired
	private ConsecutivoApuestaMapper consecutivoApuestaMapper;

	@Autowired
	private ConsecutivoApuestasDAO consecutivoApuestasDAO;
	
	@Override
	@Transactional(readOnly = true)
	public ConsecutivoApuestaResponse filtrarAsignacionesConsecutivos(
			ConsecutivoFiltroRequest request) throws Exception {
		try {
			Pageable pageRequest = PageRequest.of(request.getPagina(),
					request.getTotalRegistrosPorPagina());
			// TODO ajustar consulta
			Page<ConsecutivoApuesta> consecutivos = null;
			
			List<ConsecutivoApuesta> listaConsecutivosApuestas = consecutivos.getContent();
			List<ConsecutivoApuestaResponse.ConsecutivoApuesta> contenido = consecutivoApuestaMapper.toListConsecutivoApuesta(listaConsecutivosApuestas);
			
			if (contenido.isEmpty()) {
				throw new Exception("No hay consecutivos disponibles");
			}
			
			return ConsecutivoApuestaResponse
					.builder()
					.contenido(contenido)
					.numeroPagina(consecutivos.getNumber())
					.tamañoPagina(consecutivos.getSize())
					.totalElementos(consecutivos.getTotalElements())
					.totalPaginas(consecutivos.getTotalPages())
					.ultimo(consecutivos.isLast())
					.build();
			
			
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
