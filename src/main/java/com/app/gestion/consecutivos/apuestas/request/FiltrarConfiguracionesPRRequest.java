package com.app.gestion.consecutivos.apuestas.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FiltrarConfiguracionesPRRequest {

	@NotNull
	private Integer pagina;

	@NotNull
	private Integer totalRegistrosPorPagina;

}
