package com.elfec.lecturas.gd.remote_data_access;

import java.net.ConnectException;
import java.sql.SQLException;
import java.sql.Statement;

import com.elfec.lecturas.gd.model.ReadingOrdenative;
import com.elfec.lecturas.gd.remote_data_access.connection.OracleDatabaseConnector;

/**
 * Provee una capa de acceso remoto a los datos de medidor de las lecturas de
 * gran demanda de la tabla <b>ERP_ELFEC.SGC_MOVIL_ORDENATIVOS_GD</b>
 * 
 * @author drodriguez
 *
 */
public class ReadingOrdenativeRDA {
	/**
	 * Inserta remotamente un ordenativo de una lectura tomada
	 * 
	 * @param username
	 * @param password
	 * @param readingOrdenative
	 *            a insertar
	 * @throws ConnectException
	 * @throws SQLException
	 */
	public int insertReadingOrdenative(String username, String password,
			ReadingOrdenative readingOrdenative) throws ConnectException,
			SQLException {
		Statement stmt = OracleDatabaseConnector.instance(username, password)
				.getNewQuerier();
		int result = stmt.executeUpdate(readingOrdenative.toRemoteInsertSQL());
		stmt.close();
		return result;
	}
}
