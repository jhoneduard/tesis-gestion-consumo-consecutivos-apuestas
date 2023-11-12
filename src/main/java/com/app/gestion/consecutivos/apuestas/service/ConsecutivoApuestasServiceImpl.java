package com.app.gestion.consecutivos.apuestas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.gestion.consecutivos.apuestas.entity.ConsecutivoApuesta;
import com.app.gestion.consecutivos.apuestas.exception.CodesaException;
import com.app.gestion.consecutivos.apuestas.mapper.ConsecutivoApuestaMapper;
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

	@Override
	public ConsecutivoApuestaResponse filtrarAsignacionesConsecutivos(
			ConsecutivoFiltroRequest request) throws CodesaException {
		try {
			Pageable pageRequest = PageRequest.of(request.getPagina(),
					request.getTotalRegistrosPorPagina());
			// TODO ajustar consulta
			Page<ConsecutivoApuesta> consecutivos = null;
			
			List<ConsecutivoApuesta> listaConsecutivosApuestas = consecutivos.getContent();
			List<ConsecutivoApuestaResponse.ConsecutivoApuesta> contenido = consecutivoApuestaMapper.toListConsecutivoApuesta(listaConsecutivosApuestas);
			
			if (contenido.isEmpty()) {
				throw new CodesaException("No hay consecutivos disponibles");
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
			
			
		} catch (CodesaException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new CodesaException(ex.getMessage());
		}
	}

	@Override
	public StringResponse guardar(GuardarConsecutivoRequest request) throws CodesaException {
		try {
			ConsecutivoApuesta consecutivoApuestaNuevo = consecutivoApuestaMapper.toConsecutivoApuesta(request);
			return StringResponse.builder().respuesta("Se ha guardado con exito la configuracion").build();
		} catch (Exception ex) {
			throw new CodesaException(ex.getMessage());
		}
	}

	@Override
	public StringResponse actualizar(ActualizarConsecutivoRequest request) throws CodesaException {
		try {
			ConsecutivoApuesta consecutivoApuesta = consecutivoApuestaMapper.toConsecutivoApuesta(request);
			return StringResponse.builder().respuesta("Se ha actualizado con exito la configuracion").build();
		} catch (Exception ex) {
			throw new CodesaException(ex.getMessage());
		}
	}

	@Override
	public StringResponse eliminar(Long configuracionId) throws CodesaException {
		try {
			return StringResponse.builder().respuesta("Consecutivo de apuestas eliminado con exito").build();
		} catch (Exception ex) {
			throw new CodesaException(ex.getMessage());
		}
	}

}
