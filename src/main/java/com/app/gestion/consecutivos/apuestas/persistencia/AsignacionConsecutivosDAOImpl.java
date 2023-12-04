package com.app.gestion.consecutivos.apuestas.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
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
		 * Procedimiento para el filtro de asignacion de consecutivos
		 */
		llamarProcedimientoBD("PRO_FILTRAR_ASIGNACION_CONSECUTIVOS", "PRO_FILTRAR_ASIGNACION_CONSECUTIVOS",
				"PKG_ASIGNACION_CONSECUTIVOS");
		getLllamadoBD("PRO_FILTRAR_ASIGNACION_CONSECUTIVOS")
				.returningResultSet("PAR_RESULTADO_OUT", new AsignacionConsecutivosRowMapper())
				.withoutProcedureColumnMetaDataAccess()
				.declareParameters(new SqlParameter("PAR_VENDEDOR_ID_IN", OracleTypes.NUMERIC),
						new SqlParameter("PAR_ZONA_ID_IN", OracleTypes.NUMERIC),
						new SqlParameter("PAR_OFICINA_ID_IN", OracleTypes.NUMERIC),
						new SqlParameter("PAR_SERIE_INICIAL_IN", OracleTypes.VARCHAR),
						new SqlParameter("PAR_ESTADO_IN", OracleTypes.VARCHAR),
						new SqlParameter("PAR_PAGINA_IN", OracleTypes.INTEGER),
						new SqlParameter("PAR_TOTAL_REGISTROS_POR_PAGINA_IN", OracleTypes.INTEGER),
						new SqlOutParameter("PAR_TOTAL_REGISTROS_OUT", Types.INTEGER));

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
	@SuppressWarnings("unchecked")
	public PaginadorResponse<AsignacionConsecutivoResponse> filtrarAsignacionesConsecutivos(
			FiltrarAsignacionConsecutivoRequest request) throws Exception {
		PaginadorResponse<AsignacionConsecutivoResponse> paginador = new PaginadorResponse<AsignacionConsecutivoResponse>();
		try {
			Map<String, Object> resultado = ejecutarProcedimiento("PRO_FILTRAR_ASIGNACION_CONSECUTIVOS",
					new MapSqlParameterSource("PAR_VENDEDOR_ID_IN", request.getVendedorId())
							.addValue("PAR_ZONA_ID_IN", request.getZonaId())
							.addValue("PAR_OFICINA_ID_IN", request.getOficinaId())
							.addValue("PAR_SERIE_INICIAL_IN", request.getSerieInicial())
							.addValue("PAR_ESTADO_IN", request.getEstado())
							.addValue("PAR_PAGINA_IN", request.getPagina())
							.addValue("PAR_TOTAL_REGISTROS_POR_PAGINA_IN", request.getTotalRegistrosPorPagina()));

			List<AsignacionConsecutivoResponse> registros = (List<AsignacionConsecutivoResponse>) resultado
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

	class AsignacionConsecutivosRowMapper implements RowMapper<AsignacionConsecutivoResponse> {
		@Override
		public AsignacionConsecutivoResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
			return AsignacionConsecutivoResponse.builder().id(rs.getLong("ID")).vendedorId(rs.getLong("VENDEDOR_ID"))
					.oficinaId(rs.getLong("OFICINA_ID")).zonaId(rs.getLong("ZONA_ID")).prefijo(rs.getString("PREFIJO"))
					.consecutivoInicial(rs.getString("CONSECUTIVO_INICIAL"))
					.consecutivoFinal(rs.getString("CONSECUTIVO_FINAL"))
					.consecutivoActual(rs.getString("CONSECUTIVO_ACTUAL")).estado(rs.getString("ESTADO"))
					.fechaAsigna(rs.getDate("FECHA_ASIGNA").toLocalDate()).horaAsigna(rs.getString("HORA_ASIGNA"))
					.build();
		}
	}
}
