package com.app.gestion.consecutivos.apuestas.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FiltrarAsignacionConsecutivoRequest {

	private Long vendedorId;

	private Long zonaId;

	private Long oficinaId;

	private String serieInicial;

	private String estado;

	@NotNull
	private Integer pagina;

	@NotNull
	private Integer totalRegistrosPorPagina;

}
