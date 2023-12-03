package com.app.gestion.consecutivos.apuestas.persistencia;

import java.sql.Types;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.app.gestion.consecutivos.apuestas.request.ActualizarConsecutivoRequest;
import com.app.gestion.consecutivos.apuestas.request.ConsecutivoFiltroRequest;
import com.app.gestion.consecutivos.apuestas.request.GuardarConsecutivoRequest;
import com.app.gestion.consecutivos.apuestas.response.ConsecutivoApuestaResponse;
import com.app.gestion.consecutivos.apuestas.response.StringResponse;
import com.app.gestion.consecutivos.apuestas.utils.BDUtils;
import com.app.gestion.consecutivos.apuestas.utils.FechaUtils;

import jakarta.annotation.PostConstruct;
import oracle.jdbc.OracleTypes;

@SuppressWarnings("deprecation")
@Repository
public class ConsecutivoApuestasDAOImpl extends BDUtils implements ConsecutivoApuestasDAO {

	@Autowired
	private DataSource data;

	@PostConstruct
	public void init() {

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

	@Override
	public ConsecutivoApuestaResponse filtrarAsignacionesConsecutivos(ConsecutivoFiltroRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
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
}
