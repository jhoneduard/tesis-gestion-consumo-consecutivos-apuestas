package com.app.gestion.consecutivos.apuestas.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ConfiguracionesPendientePorReasignarResponse {
	private Long configuracionId;

	private String prefijo;

	private String consecutivoInicial;

	private String consecutivoFinal;

	private String consecutivoActual;

	private String estado;

	private Long cedulaVendedor;

	private Long oficinaId;

	private String nombreOficina;

	private Long zonaId;

	private String nombreZona;

}
