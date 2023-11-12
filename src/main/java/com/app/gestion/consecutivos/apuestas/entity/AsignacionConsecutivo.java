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
@Table(name = "asignacion_consecutivos_vendedores")
@Getter
@Setter
public class AsignacionConsecutivo {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long vendedorId;

	private Long oficinaId;

	private Long zonaId;

	private String prefijo;

	private String consecutivoInicial;

	private String consecutivoFinal;

	private String consecutivoActual;

	private String estado;

	private LocalDate fechaAsigna;

	private String horaAsigna;
}
