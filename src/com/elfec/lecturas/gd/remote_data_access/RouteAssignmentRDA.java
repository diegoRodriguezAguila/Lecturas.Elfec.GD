package com.elfec.lecturas.gd.remote_data_access;

import java.net.ConnectException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.elfec.lecturas.gd.model.RouteAssignment;
import com.elfec.lecturas.gd.model.enums.RouteAssignmentStatus;
import com.elfec.lecturas.gd.remote_data_access.connection.OracleDatabaseConnector;

/**
 * Provee de una capa de acceso a datos remota para las asignaciones de rutas
 * 
 * @author drodriguez
 *
 */
public class RouteAssignmentRDA {
	/**
	 * Obtiene remotamente la lista de rutas asignadas al usuario para la fecha
	 * de la tabla MOVILES.USUARIO_ASIGNACION solicitada
	 * 
	 * @param username
	 * @param password
	 * @param assignmentDate
	 * @return Lista de rutas asignadas al usuario
	 * @throws ConnectException
	 * @throws SQLException
	 */
	public List<RouteAssignment> requestUserRouteAssignments(String username,
			String password, DateTime assignmentDate) throws ConnectException,
			SQLException {
		List<RouteAssignment> routeAssignments = new ArrayList<RouteAssignment>();
		String query = "SELECT * FROM MOVILES.USUARIO_ASIGNACION WHERE UPPER(USUARIO)=UPPER('%s') AND DIA_ASIG_CARGA=%d AND MES=%d AND ANIO=%d AND ESTADO IN(%d, %d)";
		ResultSet rs = OracleDatabaseConnector.instance(username, password)
				.executeSelect(
						String.format(query, username, assignmentDate
								.getDayOfMonth(), assignmentDate
								.getMonthOfYear(), assignmentDate.getYear(),
								RouteAssignmentStatus.ASSIGNED.toShort(),
								RouteAssignmentStatus.RE_READING_ASSIGNED
										.toShort()));
		while (rs.next()) {
			routeAssignments.add(new RouteAssignment(username, rs
					.getInt("RUTA"), (short) assignmentDate.getDayOfMonth(), rs
					.getShort("DIA"), rs.getShort("MES"), rs.getInt("ANIO"), rs
					.getInt("ORDEN_INICIO"), rs.getInt("ORDEN_FIN"),
					RouteAssignmentStatus.get(rs.getShort("ESTADO")), rs
							.getInt("CANT_LEC")));
		}
		rs.close();
		return routeAssignments;
	}

	/**
	 * Ejecuta remotamente una consulta de update para la asignación de rutas de
	 * la tabla MOVILES.USUARIO_ASIGNACION
	 * 
	 * @param username
	 * @param password
	 * @param routeAssignment
	 * @return
	 * @throws ConnectException
	 * @throws SQLException
	 */
	public int remoteUpdateUserRouteAssignment(String username,
			String password, RouteAssignment routeAssignment)
			throws ConnectException, SQLException {
		Statement stmt = OracleDatabaseConnector.instance(username, password)
				.getNewQuerier();
		int result = stmt.executeUpdate(routeAssignment.toUpdateSQL());
		stmt.close();
		return result;
	}
}
