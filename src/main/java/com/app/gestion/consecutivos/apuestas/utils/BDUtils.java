package com.app.gestion.consecutivos.apuestas.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import jakarta.annotation.PostConstruct;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class BDUtils {

	private HashMap<String, SimpleJdbcCall> llamadoBD;

	@Autowired
	@Setter
	private DataSource dataSource;

	@PostConstruct
	private void init() {
		setDataSource(dataSource);
		llamadoBD = new HashMap<>();
	}

	public void llamarProcedimientoBD(String nombreLlamado, String nombreProcedimiento, String nombrePaquete) {
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate();
			SimpleJdbcCall llamado = new SimpleJdbcCall(jdbcTemplate).withCatalogName(nombrePaquete)
					.withProcedureName(nombreProcedimiento);
			llamadoBD.put(nombreLlamado, llamado);
			log.info("Se llamo el procedimiento {} del paquete {} ", nombreProcedimiento, nombrePaquete);
		} catch (Exception ex) {
			log.error(ex.getMessage());
		}
	}

	public SimpleJdbcCall getLllamadoBD(String nombreLlamado) {
		SimpleJdbcCall llamado = null;
		try {
			llamado = llamadoBD.get(nombreLlamado);
		} catch (Exception ex) {
			log.info(ex.getMessage());
		}
		return llamado;
	}

	public Map<String, Object> ejecutarProcedimiento(String nombreLlamado, MapSqlParameterSource sqlParameterSource) {
		try {
			log.info("Se llama al procedimiento almacenado {} ", nombreLlamado);
			Map<String, Object> respuesta = getLllamadoBD(nombreLlamado).execute(sqlParameterSource);
			log.info("El procedimiento almacenado retorno : {} ", respuesta);
			return respuesta;
		} catch (Exception ex) {
			log.info(ex.getMessage());
		}
		return Collections.emptyMap();
	}

}
