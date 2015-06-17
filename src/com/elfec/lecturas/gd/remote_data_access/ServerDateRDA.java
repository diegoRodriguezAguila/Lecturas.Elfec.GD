package com.elfec.lecturas.gd.remote_data_access;

import java.net.ConnectException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.joda.time.DateTime;

import com.elfec.lecturas.gd.remote_data_access.connection.OracleDatabaseConnector;

/**
 * Se encarga de las conexiones remotas a oracle para realizar tareas sobre la
 * fecha del servidor
 * 
 * @author drodriguez
 *
 */
public class ServerDateRDA {
	/**
	 * Obtiene remotamente la fecha y hora del servidor
	 * 
	 * @param username
	 * @param password
	 * @return fecha y hora del servidor , null en caso de no haber podido
	 *         contactar al server
	 * @throws SQLException
	 * @throws ConnectException
	 */
	public static DateTime requestServerDateTime(String username,
			String password) throws ConnectException, SQLException {
		ResultSet rs = OracleDatabaseConnector.instance(username, password)
				.executeSelect("SELECT SYSDATE FECHA_SERVER FROM DUAL");
		DateTime serverDate = null;
		while (rs.next()) {
			serverDate = new DateTime(rs.getTimestamp("FECHA_SERVER"));
		}
		rs.close();
		return serverDate;
	}
}
