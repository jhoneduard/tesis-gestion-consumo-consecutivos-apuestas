package com.app.gestion.consecutivos.apuestas.request;

import java.time.LocalDate;

import com.app.gestion.consecutivos.apuestas.utils.LocalDateFormatDDMMYY;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GuardarConsecutivoRequest {
	private String observacion;

	@LocalDateFormatDDMMYY
	private LocalDate fechaAsigna;

	private String prefijo;

	private String consecutivoInicial;

	private String consecutivoFinal;

	@NotNull
	private Integer cantidad;
}
