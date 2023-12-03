package com.app.gestion.consecutivos.apuestas.persistencia;

import java.sql.Types;
import java.util.Map;

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

@SuppressWarnings("deprecation")
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

	@Override
	public PaginadorResponse<ConfiguracionesPendientePorReasignarResponse> listarConfigPendientePorReasignar(
			FiltrarConfiguracionesPRRequest request) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
