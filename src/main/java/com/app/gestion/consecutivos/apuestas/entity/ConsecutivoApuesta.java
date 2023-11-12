package com.app.gestion.consecutivos.apuestas.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "consecutivos_apuestas")
@Getter
@Setter
public class ConsecutivoApuesta {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long configuracionId;

	private String observacion;

	private LocalDate fechaAsigna;

	private String prefijo;

	private String consecutivoInicial;

	private String consecutivoFinal;

	private String consecutivoActual;

	private Integer cantidadDisponible;

	private String estado;

	private Integer cantidad;
}
