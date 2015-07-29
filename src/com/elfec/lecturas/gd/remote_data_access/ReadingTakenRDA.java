package com.elfec.lecturas.gd.remote_data_access;

import java.net.ConnectException;
import java.sql.SQLException;
import java.sql.Statement;

import com.elfec.lecturas.gd.model.ReadingTaken;
import com.elfec.lecturas.gd.remote_data_access.connection.OracleDatabaseConnector;

/**
 * Provee una capa de acceso remoto a los datos de medidor de las lecturas de
 * gran demanda de la tabla <b>ERP_ELFEC.SGC_MOVIL_LECTURAS_GD</b>
 * 
 * @author drodriguez
 *
 */
public class ReadingTakenRDA {
	/**
	 * Inserta remotamente una lectura tomada
	 * 
	 * @param username
	 * @param password
	 * @param readingTaken
	 *            a insertar
	 * @throws ConnectException
	 * @throws SQLException
	 */
	public int insertReadingTaken(String username, String password,
			ReadingTaken readingTaken) throws ConnectException, SQLException {
		Statement stmt = OracleDatabaseConnector.instance(username, password)
				.getNewQuerier();
		int result = stmt.executeUpdate(readingTaken.toRemoteInsertSQL());
		stmt.close();
		return result;
	}
}
