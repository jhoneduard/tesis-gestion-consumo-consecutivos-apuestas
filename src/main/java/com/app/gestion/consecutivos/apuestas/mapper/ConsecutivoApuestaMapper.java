package com.app.gestion.consecutivos.apuestas.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.app.gestion.consecutivos.apuestas.entity.ConsecutivoApuesta;
import com.app.gestion.consecutivos.apuestas.request.ActualizarConsecutivoRequest;
import com.app.gestion.consecutivos.apuestas.request.GuardarConsecutivoRequest;
import com.app.gestion.consecutivos.apuestas.response.ConsecutivoApuestaResponse;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ConsecutivoApuestaMapper {

	ConsecutivoApuesta toConsecutivoApuesta(GuardarConsecutivoRequest request);
	
	ConsecutivoApuesta toConsecutivoApuesta(ActualizarConsecutivoRequest request);
	
	List<ConsecutivoApuestaResponse.ConsecutivoApuesta> toListConsecutivoApuesta(List<ConsecutivoApuesta> listaConsecutivos);
}
