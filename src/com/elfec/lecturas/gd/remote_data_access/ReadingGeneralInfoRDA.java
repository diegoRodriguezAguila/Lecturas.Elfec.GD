package com.elfec.lecturas.gd.remote_data_access;

import java.net.ConnectException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.elfec.lecturas.gd.model.ReadingGeneralInfo;
import com.elfec.lecturas.gd.model.RouteAssignment;
import com.elfec.lecturas.gd.remote_data_access.connection.OracleDatabaseConnector;

/**
 * Provee una capa de acceso remoto a los datos generales de las lecturas de
 * gran demanda de la tabla <b>MOVILES.LECTURASGD</b>
 * 
 * @author drodriguez
 *
 */
public class ReadingGeneralInfoRDA {
	/**
	 * Obtiene la información general de lecturas, correspondiente a la
	 * asignación de ruta proporcionada
	 * 
	 * @param username
	 * @param password
	 * @param routeAssignment
	 * @return {@link List}<{@link ReadingGeneralInfo}> Lista de info general de
	 *         lecturas
	 * @throws ConnectException
	 * @throws SQLException
	 */
	public List<ReadingGeneralInfo> requestReadingsGeneralInfo(String username,
			String password, RouteAssignment routeAssignment)
			throws ConnectException, SQLException {
		{
			List<ReadingGeneralInfo> readingsGeneralInfo = new ArrayList<ReadingGeneralInfo>();
			ResultSet rs = OracleDatabaseConnector
					.instance(username, password)
					.executeSelect(
							String.format(
									"SELECT * FROM MOVILES.LECTURASGD WHERE ANIO = %d AND MES = %d AND DIA = %d AND IDRUTA = %d",
									routeAssignment.getYear(),
									routeAssignment.getMonth(),
									routeAssignment.getDay(),
									routeAssignment.getRoute()));
			while (rs.next()) {
				readingsGeneralInfo.add(new ReadingGeneralInfo(rs
						.getLong("IDLECTURAGD"), rs.getInt("ANIO"), rs
						.getShort("MES"), rs.getShort("DIA"), rs
						.getInt("IDRUTA"), rs.getInt("IDCLIENTE"), rs
						.getInt("IDSUMINISTRO"), rs.getString("NROSUM"), rs
						.getString("NOMBRE"), rs.getString("DIRECCION"), rs
						.getString("NIT"), rs.getString("IDCATEGORIA"), rs
						.getString("DESCRIP_CATEG"), rs.getInt("IDMEDIDOR"), rs
						.getShort("IMPRIMIR_AVISO"), rs
						.getBigDecimal("IMPORTE_DEUDA"), rs
						.getString("MESES_DEUDA"), rs
						.getBigDecimal("PERDIDAS_FE"), rs
						.getBigDecimal("PERDIDAS_CU"), rs
						.getBigDecimal("FACTOR_CARGA"), rs.getInt("HORAS_MES"),
						rs.getShort("TIPO_ASEO"), rs
								.getBigDecimal("PORCENTAJE_AP"), rs
								.getShort("TIPO_AP"), rs
								.getInt("POT_PUNTA_CONTRATADA"), rs
								.getInt("POT_FPUNTA_CONTRATADA"), new DateTime(
								rs.getTimestamp("FECHA_PASIBLE_CORTE")),
						new DateTime(rs.getTimestamp("FECHA_VENCIMIENTO"))));
			}
			return readingsGeneralInfo;
		}
	}
}
