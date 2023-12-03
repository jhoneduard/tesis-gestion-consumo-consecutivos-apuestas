package com.app.gestion.consecutivos.apuestas.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlTypeValue;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import com.app.gestion.consecutivos.apuestas.request.AsignacionManualConsecutivosRequest;
import com.app.gestion.consecutivos.apuestas.request.FiltrarAsignacionConsecutivoRequest;
import com.app.gestion.consecutivos.apuestas.response.AsignacionConsecutivoResponse;
import com.app.gestion.consecutivos.apuestas.response.PaginadorResponse;
import com.app.gestion.consecutivos.apuestas.response.StringResponse;
import com.app.gestion.consecutivos.apuestas.utils.BDUtils;

import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import oracle.jdbc.OracleTypes;
import oracle.jdbc.driver.OracleConnection;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;

@SuppressWarnings("deprecation")
@Log4j2
@Repository
public class AsignacionConsecutivosDAOImpl extends BDUtils implements AsignacionConsecutivosDAO {

	@Autowired
	private DataSource data;

	@PostConstruct
	public void init() {
		/*
		 * Procedimiento para la asignacion manual
		 */
		llamarProcedimientoBD("PRO_ASIGNACION_MANUAL", "PRO_ASIGNACION_MANUAL", "PKG_ASIGNACION_CONSECUTIVOS");
		getLllamadoBD("PRO_ASIGNACION_MANUAL").withoutProcedureColumnMetaDataAccess().declareParameters(
				new SqlParameter("PAR_LISTA_VENDEDORES_IN", OracleTypes.ARRAY),
				new SqlOutParameter("PAR_MENSAJE_SALIDA_OUT", Types.VARCHAR));

		/*
		 * Procedimiento para la asignacion automatica
		 */
		llamarProcedimientoBD("PRO_ASIGNACION_AUTOMATICA", "PRO_ASIGNACION_AUTOMATICA", "PKG_ASIGNACION_CONSECUTIVOS");
		getLllamadoBD("PRO_ASIGNACION_AUTOMATICA").withoutProcedureColumnMetaDataAccess().declareParameters(
				new SqlParameter("PAR_CEDULA_VENDEDOR_IN", OracleTypes.NUMERIC),
				new SqlOutParameter("PAR_MENSAJE_SALIDA_OUT", Types.VARCHAR));

		/*
		 * Procedimiento para validar si el vendedor tiene consecutivos
		 */
		llamarProcedimientoBD("PRO_VALIDAR_CONSECUTIVOS_VENDEDOR", "PRO_VALIDAR_CONSECUTIVOS_VENDEDOR",
				"PKG_ASIGNACION_CONSECUTIVOS");
		getLllamadoBD("PRO_VALIDAR_CONSECUTIVOS_VENDEDOR").withoutProcedureColumnMetaDataAccess().declareParameters(
				new SqlParameter("PAR_CEDULA_VENDEDOR_IN", OracleTypes.NUMERIC),
				new SqlOutParameter("PAR_MENSAJE_SALIDA_OUT", Types.VARCHAR),
				new SqlOutParameter("PAR_VALIDACION_CONSECUTIVO_OUT", Types.BOOLEAN));

	}

	@Override
	public StringResponse asignacionManual(AsignacionManualConsecutivosRequest request) throws Exception {
		StringResponse response = null;
		try {
			final Connection con = DataSourceUtils.getConnection(this.data);
			final OracleConnection oc = (OracleConnection) con.unwrap(OracleConnection.class);
			final Long[] arrayVendedores = request.getListaVendedores().toArray(new Long[] {});

			Map<String, Object> resultadoProcedimiento = ejecutarProcedimiento("PRO_ASIGNACION_MANUAL",
					new MapSqlParameterSource("PAR_LISTA_VENDEDORES_IN", new SqlTypeValue() {
						@Override
						public void setTypeValue(PreparedStatement ps, int paramIndex, int sqlType, String typeName)
								throws SQLException {
							ArrayDescriptor des = ArrayDescriptor.createDescriptor(typeName, oc);
							ARRAY a = new ARRAY(des, oc, arrayVendedores);
							ps.setArray(paramIndex, a);
						}
					}));
			String mensajeSalida = (String) resultadoProcedimiento.get("PAR_MENSAJE_SALIDA_OUT");
			response = StringResponse.builder().respuesta(mensajeSalida).build();

		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
		return response;
	}

	@Override
	public PaginadorResponse<AsignacionConsecutivoResponse> filtrarAsignacionesConsecutivos(
			FiltrarAsignacionConsecutivoRequest request) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StringResponse asignacionAutomatica(Long cedulaVendedor) throws Exception {
		StringResponse response = null;
		try {
			Map<String, Object> resultadoProcedimiento = ejecutarProcedimiento("PRO_ASIGNACION_AUTOMATICA",
					new MapSqlParameterSource("PAR_CEDULA_VENDEDOR_IN", cedulaVendedor));
			String mensajeSalida = (String) resultadoProcedimiento.get("PAR_MENSAJE_SALIDA_OUT");
			response = StringResponse.builder().respuesta(mensajeSalida).build();
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
		return response;
	}

	@Override
	public Boolean validarConsecutivoVendedor(Long cedulaVendedor) throws Exception {
		Boolean vendedorTieneConsecutivos = null;
		try {
			Map<String, Object> resultadoProcedimiento = ejecutarProcedimiento("PRO_VALIDAR_CONSECUTIVOS_VENDEDOR",
					new MapSqlParameterSource("PAR_CEDULA_VENDEDOR_IN", cedulaVendedor));

			String mensajeSalida = (String) resultadoProcedimiento.get("PAR_MENSAJE_SALIDA_OUT");
			log.info("Mensaje salida del procedimiento PRO_VALIDAR_CONSECUTIVOS_VENDEDOR : {} ", mensajeSalida);
			Integer validacionConsecutivos = (Integer) resultadoProcedimiento.get("PAR_VALIDACION_CONSECUTIVO_OUT");
			vendedorTieneConsecutivos = validacionConsecutivos.equals(1) ? true : false;
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
		return vendedorTieneConsecutivos;
	}

}
