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

import com.app.gestion.consecutivos.apuestas.request.ActualizarConsecutivoRequest;
import com.app.gestion.consecutivos.apuestas.request.ConsecutivoFiltroRequest;
import com.app.gestion.consecutivos.apuestas.request.GuardarConsecutivoRequest;
import com.app.gestion.consecutivos.apuestas.response.ConsecutivoApuestaResponse;
import com.app.gestion.consecutivos.apuestas.response.PaginadorResponse;
import com.app.gestion.consecutivos.apuestas.response.StringResponse;
import com.app.gestion.consecutivos.apuestas.utils.BDUtils;
import com.app.gestion.consecutivos.apuestas.utils.FechaUtils;

import jakarta.annotation.PostConstruct;
import oracle.jdbc.OracleTypes;

@Repository
public class ConsecutivoApuestasDAOImpl extends BDUtils implements ConsecutivoApuestasDAO {

	@PostConstruct
	public void init() {

		/*
		 * Procedimiento para el filtrar los consecutivos de apuestas
		 */
		llamarProcedimientoBD("PRO_FILTRAR_CONSECUTIVOS", "PRO_FILTRAR_CONSECUTIVOS", "PKG_ADM_CONSECUTIVOS");
		getLllamadoBD("PRO_FILTRAR_CONSECUTIVOS")
				.returningResultSet("PAR_RESULTADO_OUT", new ConsecutivoApuestaRowMapper())
				.withoutProcedureColumnMetaDataAccess()
				.declareParameters(new SqlParameter("PAR_OBSERVACION_IN", OracleTypes.VARCHAR),
						new SqlParameter("PAR_FECHA_ASIGNA_IN", Types.DATE),
						new SqlParameter("PAR_PREFIJO_IN", Types.VARCHAR),
						new SqlParameter("PAR_CONSECUTIVO_INICIAL_IN", Types.VARCHAR),
						new SqlParameter("PAR_CONSECUTIVO_FINAL_IN", Types.VARCHAR),
						new SqlParameter("PAR_PAGINA_IN", OracleTypes.INTEGER),
						new SqlParameter("PAR_TOTAL_REGISTROS_POR_PAGINA_IN", OracleTypes.INTEGER),
						new SqlParameter("PAR_ESTADO_IN", OracleTypes.VARCHAR),
						new SqlOutParameter("PAR_TOTAL_REGISTROS_OUT", Types.INTEGER));

		/*
		 * Procedimiento para la guardar la configuracion de consecutivos
		 */
		llamarProcedimientoBD("PRO_GUARDAR_CONSECUTIVO", "PRO_GUARDAR_CONSECUTIVO", "PKG_ADM_CONSECUTIVOS");
		getLllamadoBD("PRO_GUARDAR_CONSECUTIVO").withoutProcedureColumnMetaDataAccess().declareParameters(
				new SqlParameter("PAR_OBSERVACION_IN", Types.VARCHAR),
				new SqlParameter("PAR_FECHA_ASIGNA_IN", Types.DATE), new SqlParameter("PAR_PREFIJO_IN", Types.VARCHAR),
				new SqlParameter("PAR_CONSECUTIVO_INICIAL_IN", Types.VARCHAR),
				new SqlParameter("PAR_CONSECUTIVO_FINAL_IN", Types.VARCHAR),
				new SqlParameter("PAR_CANTIDAD_IN", Types.INTEGER),
				new SqlOutParameter("PAR_MENSAJE_SALIDA_OUT", Types.VARCHAR));

		/*
		 * Procedimiento para la actualizar la configuracion de consecutivos
		 */
		llamarProcedimientoBD("PRO_ACTUALIZAR_CONSECUTIVO", "PRO_ACTUALIZAR_CONSECUTIVO", "PKG_ADM_CONSECUTIVOS");
		getLllamadoBD("PRO_ACTUALIZAR_CONSECUTIVO").withoutProcedureColumnMetaDataAccess().declareParameters(
				new SqlParameter("PAR_OBSERVACION_IN", Types.VARCHAR),
				new SqlParameter("PAR_FECHA_ASIGNA_IN", Types.DATE), new SqlParameter("PAR_PREFIJO_IN", Types.VARCHAR),
				new SqlParameter("PAR_CONSECUTIVO_INICIAL_IN", Types.VARCHAR),
				new SqlParameter("PAR_CONSECUTIVO_FINAL_IN", Types.VARCHAR),
				new SqlParameter("PAR_CANTIDAD_IN", Types.INTEGER),
				new SqlParameter("PAR_CONFIGURACION_ID_IN", Types.NUMERIC),
				new SqlOutParameter("PAR_MENSAJE_SALIDA_OUT", Types.VARCHAR));

		/*
		 * Procedimiento para eliminar la configuracion de consecutivos
		 */
		llamarProcedimientoBD("PRO_ELIMINAR_CONSECUTIVO", "PRO_ELIMINAR_CONSECUTIVO", "PKG_ADM_CONSECUTIVOS");
		getLllamadoBD("PRO_ELIMINAR_CONSECUTIVO").withoutProcedureColumnMetaDataAccess().declareParameters(
				new SqlParameter("PAR_CONFIGURACION_ID_IN", OracleTypes.NUMERIC),
				new SqlOutParameter("PAR_MENSAJE_SALIDA_OUT", Types.VARCHAR));

	}

	@SuppressWarnings("unchecked")
	@Override
	public PaginadorResponse<ConsecutivoApuestaResponse> filtrarAsignacionesConsecutivos(
			ConsecutivoFiltroRequest request) throws Exception {
		PaginadorResponse<ConsecutivoApuestaResponse> paginador = new PaginadorResponse<ConsecutivoApuestaResponse>();
		try {
			Map<String, Object> resultado = ejecutarProcedimiento("PRO_FILTRAR_CONSECUTIVOS",
					new MapSqlParameterSource("PAR_OBSERVACION_IN", request.getObservacion())
							.addValue("PAR_FECHA_ASIGNA_IN",
									FechaUtils.convertLocalDateToDate(request.getFechaAsigna()))
							.addValue("PAR_PREFIJO_IN", request.getPrefijo())
							.addValue("PAR_CONSECUTIVO_INICIAL_IN", request.getConsecutivoInicial())
							.addValue("PAR_CONSECUTIVO_FINAL_IN", request.getConsecutivoFinal())
							.addValue("PAR_PAGINA_IN", request.getPagina())
							.addValue("PAR_TOTAL_REGISTROS_POR_PAGINA_IN", request.getTotalRegistrosPorPagina())
							.addValue("PAR_ESTADO_IN", request.getEstado()));

			List<ConsecutivoApuestaResponse> registros = (List<ConsecutivoApuestaResponse>) resultado
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

	@Override
	public StringResponse guardar(GuardarConsecutivoRequest request) throws Exception {
		StringResponse response = null;
		try {
			Map<String, Object> resultadoProcedimiento = ejecutarProcedimiento("PRO_GUARDAR_CONSECUTIVO",
					new MapSqlParameterSource("PAR_OBSERVACION_IN", request.getObservacion())
							.addValue("PAR_FECHA_ASIGNA_IN",
									FechaUtils.convertLocalDateToDate(request.getFechaAsigna()))
							.addValue("PAR_PREFIJO_IN", request.getPrefijo())
							.addValue("PAR_CONSECUTIVO_INICIAL_IN", request.getConsecutivoInicial())
							.addValue("PAR_CONSECUTIVO_FINAL_IN", request.getConsecutivoFinal())
							.addValue("PAR_CANTIDAD_IN", request.getCantidad()));

			String mensajeSalida = (String) resultadoProcedimiento.get("PAR_MENSAJE_SALIDA_OUT");
			response = StringResponse.builder().respuesta(mensajeSalida).build();
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
		return response;
	}

	@Override
	public StringResponse actualizar(ActualizarConsecutivoRequest request) throws Exception {
		StringResponse response = null;
		try {
			Map<String, Object> resultadoProcedimiento = ejecutarProcedimiento("PRO_GUARDAR_CONSECUTIVO",
					new MapSqlParameterSource("PAR_OBSERVACION_IN", request.getObservacion())
							.addValue("PAR_FECHA_ASIGNA_IN",
									FechaUtils.convertLocalDateToDate(request.getFechaAsigna()))
							.addValue("PAR_PREFIJO_IN", request.getPrefijo())
							.addValue("PAR_CONSECUTIVO_INICIAL_IN", request.getConsecutivoInicial())
							.addValue("PAR_CONSECUTIVO_FINAL_IN", request.getConsecutivoFinal())
							.addValue("PAR_CANTIDAD_IN", request.getCantidad())
							.addValue("PAR_CONFIGURACION_ID_IN", request.getConfiguracionId()));

			String mensajeSalida = (String) resultadoProcedimiento.get("PAR_MENSAJE_SALIDA_OUT");
			response = StringResponse.builder().respuesta(mensajeSalida).build();
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
		return response;
	}

	@Override
	public StringResponse eliminar(Long configuracionId) throws Exception {
		StringResponse response = null;
		try {
			Map<String, Object> resultadoProcedimiento = ejecutarProcedimiento("PRO_ELIMINAR_CONSECUTIVO",
					new MapSqlParameterSource("PAR_CONFIGURACION_ID_IN", configuracionId));
			String mensajeSalida = (String) resultadoProcedimiento.get("PAR_MENSAJE_SALIDA_OUT");
			response = StringResponse.builder().respuesta(mensajeSalida).build();
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
		return response;
	}

	class ConsecutivoApuestaRowMapper implements RowMapper<ConsecutivoApuestaResponse> {
		@Override
		public ConsecutivoApuestaResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
			return ConsecutivoApuestaResponse.builder().configuracionId(rs.getLong("CONFIGURACION_ID"))
					.observacion(rs.getString("OBSERVACION")).fechaAsigna(rs.getDate("FECHA_ASIGNA").toLocalDate())
					.prefijo(rs.getString("PREFIJO")).consecutivoInicial(rs.getString("CONSECUTIVO_INICIAL"))
					.consecutivoFinal(rs.getString("CONSECUTIVO_FINAL"))
					.consecutivoActual(rs.getString("CONSECUTIVO_ACTUAL"))
					.cantidadDisponible(rs.getInt("CANTIDAD_DISPONIBLE")).estado(rs.getString("ESTADO"))
					.cantidad(rs.getInt("CANTIDAD")).build();
		}

	}
}
