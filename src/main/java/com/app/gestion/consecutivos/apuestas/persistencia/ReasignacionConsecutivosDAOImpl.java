package com.app.gestion.consecutivos.apuestas.persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.app.gestion.consecutivos.apuestas.request.FiltrarConfiguracionesPRRequest;
import com.app.gestion.consecutivos.apuestas.request.ReasignarConsecutivoRequest;
import com.app.gestion.consecutivos.apuestas.request.TerminarPapeleriaRequest;
import com.app.gestion.consecutivos.apuestas.response.ConfiguracionesPendientePorReasignarResponse;
import com.app.gestion.consecutivos.apuestas.response.PaginadorResponse;
import com.app.gestion.consecutivos.apuestas.response.StringResponse;
import com.app.gestion.consecutivos.apuestas.utils.BDUtils;

import jakarta.annotation.PostConstruct;
import oracle.jdbc.OracleTypes;

@Repository
public class ReasignacionConsecutivosDAOImpl extends BDUtils implements ReasignacionConsecutivosDAO {

	@PostConstruct
	public void init() {
		/*
		 * Procedimiento para eliminar la configuracion de consecutivos
		 */
		llamarProcedimientoBD("PRO_TERMINAR_PAPELERIA", "PRO_TERMINAR_PAPELERIA", "PKG_ADM_CONSECUTIVOS");
		getLllamadoBD("PRO_TERMINAR_PAPELERIA").withoutProcedureColumnMetaDataAccess().declareParameters(
				new SqlParameter("PAR_CEDULA_VENDEDOR_IN", OracleTypes.NUMERIC),
				new SqlOutParameter("PAR_MENSAJE_SALIDA_OUT", Types.VARCHAR));

		/*
		 * Procedimiento para reasignar consecutivos a un vendedor activo
		 */
		llamarProcedimientoBD("PRO_REASIGNAR_CONSECUTIVO", "PRO_REASIGNAR_CONSECUTIVO", "PKG_ADM_CONSECUTIVOS");
		getLllamadoBD("PRO_REASIGNAR_CONSECUTIVO").withoutProcedureColumnMetaDataAccess().declareParameters(
				new SqlParameter("PAR_CEDULA_VENDEDOR_IN", OracleTypes.NUMERIC),
				new SqlParameter("PAR_CONFIGURACION_ID_IN", OracleTypes.NUMERIC),
				new SqlOutParameter("PAR_MENSAJE_SALIDA_OUT", Types.VARCHAR));

		/*
		 * Procedimiento para el filtrar los consecutivos pendiente pór reasignar
		 */
		llamarProcedimientoBD("PRO_FILTRAR_CONSECUTIVOS_PR", "PRO_FILTRAR_CONSECUTIVOS_PR", "PKG_ADM_CONSECUTIVOS");
		getLllamadoBD("PRO_FILTRAR_CONSECUTIVOS_PR")
				.returningResultSet("PAR_RESULTADO_OUT", new ConsecutivosPendientePorReasignarRowMapper())
				.withoutProcedureColumnMetaDataAccess()
				.declareParameters(new SqlParameter("PAR_PAGINA_IN", OracleTypes.INTEGER),
						new SqlParameter("PAR_TOTAL_REGISTROS_POR_PAGINA_IN", OracleTypes.INTEGER),
						new SqlOutParameter("PAR_TOTAL_REGISTROS_OUT", Types.INTEGER));
	}

	@Override
	public StringResponse terminarPapeleria(TerminarPapeleriaRequest request) throws Exception {
		StringResponse response = null;
		try {
			Map<String, Object> resultadoProcedimiento = ejecutarProcedimiento("PRO_TERMINAR_PAPELERIA",
					new MapSqlParameterSource("PAR_CEDULA_VENDEDOR_IN", request.getCedulaVendedor()));
			String mensajeSalida = (String) resultadoProcedimiento.get("PAR_MENSAJE_SALIDA_OUT");
			response = StringResponse.builder().respuesta(mensajeSalida).build();
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
		return response;
	}

	@Override
	public StringResponse reasignarConsecutivo(ReasignarConsecutivoRequest request) throws Exception {
		StringResponse response = null;
		try {
			Map<String, Object> resultadoProcedimiento = ejecutarProcedimiento("PRO_REASIGNAR_CONSECUTIVO",
					new MapSqlParameterSource("PAR_CEDULA_VENDEDOR_IN", request.getCedulaVendedor())
							.addValue("PAR_CONFIGURACION_ID_IN", request.getConfiguracionId()));

			String mensajeSalida = (String) resultadoProcedimiento.get("PAR_MENSAJE_SALIDA_OUT");
			response = StringResponse.builder().respuesta(mensajeSalida).build();
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
		return response;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PaginadorResponse<ConfiguracionesPendientePorReasignarResponse> listarConfigPendientePorReasignar(
			FiltrarConfiguracionesPRRequest request) throws Exception {
		PaginadorResponse<ConfiguracionesPendientePorReasignarResponse> paginador = new PaginadorResponse<ConfiguracionesPendientePorReasignarResponse>();
		try {
			Map<String, Object> resultado = ejecutarProcedimiento("PRO_FILTRAR_CONSECUTIVOS_PR",
					new MapSqlParameterSource("PAR_PAGINA_IN", request.getPagina())
							.addValue("PAR_TOTAL_REGISTROS_POR_PAGINA_IN", request.getTotalRegistrosPorPagina()));

			List<ConfiguracionesPendientePorReasignarResponse> registros = (List<ConfiguracionesPendientePorReasignarResponse>) resultado
					.get("PAR_RESULTADO_OUT");

			if (registros.isEmpty()) {
				throw new Exception("No se encuentra registros para los filtros suministrados");
			}

			Integer totalRegistros = (Integer) resultado.get("PAR_TOTAL_REGISTROS_OUT");
			paginador.setListado(registros);
			paginador.setPagina(request.getPagina());
			paginador.setTotalRegistros(totalRegistros);
			return paginador;
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

	class ConsecutivosPendientePorReasignarRowMapper
			implements RowMapper<ConfiguracionesPendientePorReasignarResponse> {

		@Override
		public ConfiguracionesPendientePorReasignarResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
			return ConfiguracionesPendientePorReasignarResponse.builder()
					.configuracionId(rs.getLong("CONFIGURACION_ID")).prefijo(rs.getString("PREFIJO"))
					.consecutivoInicial(rs.getString("CONSECUTIVO_INICIAL"))
					.consecutivoFinal(rs.getString("CONSECUTIVO_FINAL"))
					.consecutivoActual(rs.getString("CONSECUTIVO_ACTUAL")).estado(rs.getString("ESTADO"))
					.cedulaVendedor(rs.getLong("CEDULA_VENDEDOR")).oficinaId(rs.getLong("OFICINA_ID"))
					.nombreOficina(rs.getString("NOMBRE_OFICINA")).zonaId(rs.getLong("ZONA_ID"))
					.nombreZona(rs.getString("NOMBRE_ZONA")).build();
		}
	}
}
