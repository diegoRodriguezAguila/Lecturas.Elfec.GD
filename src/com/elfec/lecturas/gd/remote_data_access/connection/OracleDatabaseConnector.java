package com.elfec.lecturas.gd.remote_data_access.connection;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import android.content.Context;

import com.elfec.lecturas.gd.settings.OracleDatabaseSettings;

/**
 * Se encarga de conectar remotamente a la base de datos oracle de la empresa
 * 
 * @author drodriguez
 *
 */
public class OracleDatabaseConnector {

	private static Context context;
	private static OracleDatabaseConnector dbConnectorInstance;

	private Connection conn = null;
	private Statement stmt = null;

	private OracleDatabaseConnector(String connectionUrl, String user,
			String password) throws ClassNotFoundException, SQLException {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		DriverManager.setLoginTimeout(15);
		conn = DriverManager.getConnection(connectionUrl, user, password);
		stmt = conn.createStatement();
	}

	/**
	 * Instancia u obtiene la instancia �nica del conector de base de datos
	 * oracle
	 * 
	 * @param context
	 * @param user
	 * @param password
	 * @return la instancia del conector, null si es que no se pudo lograr la
	 *         conexi�n
	 * @throws ConnectException
	 */
	public static OracleDatabaseConnector instance(Context context,
			String user, String password) throws ConnectException {
		try {
			if (dbConnectorInstance == null)
				dbConnectorInstance = new OracleDatabaseConnector(
						OracleDatabaseSettings.getConnectionString(context),
						user, password);
		} catch (ClassNotFoundException e) {
			throw new ConnectException(
					"No se pudo establecer conexi�n con el servidor, revise su nombre de usuario y contrase�a");
		} catch (SQLException e) {
			throw new ConnectException(
					"No se pudo establecer conexi�n con el servidor, revise su nombre de usuario y contrase�a");
		}
		return dbConnectorInstance;
	}

	/**
	 * Obtiene la instancia �nica del conector de base de datos oracle, si es
	 * que no se defini� un context o si no se instanci� previamente tira
	 * IllegalStateException
	 * 
	 * @param user
	 * @param password
	 * @return la instancia del conector, null si es que no se pudo lograr la
	 *         conexi�n
	 * @throws ConnectException
	 */
	public static OracleDatabaseConnector instance(String user, String password)
			throws ConnectException {
		if (dbConnectorInstance == null) {
			try {
				if (context == null)
					throw new IllegalStateException(
							"Debe llamar a instance() pasandole los par�metros por lo menos una vez");
				else
					dbConnectorInstance = new OracleDatabaseConnector(
							OracleDatabaseSettings.getConnectionString(context),
							user, password);
			} catch (ClassNotFoundException e) {
				throw new ConnectException(
						"No se pudo establecer conexi�n con el servidor, revise su nombre de usuario y contrase�a. �Asegurese que est� conectado a la red de la empresa!");
			} catch (SQLException e) {
				throw new ConnectException(
						"No se pudo establecer conexi�n con el servidor, revise su nombre de usuario y contrase�a. �Asegurese que est� conectado a la red de la empresa!");
			}
		}
		return dbConnectorInstance;
	}

	/**
	 * Asigna el conext para la instanciaci�n del objeto
	 * 
	 * @param context
	 */
	public static void initializeContext(Context context) {
		OracleDatabaseConnector.context = context;
	}

	/**
	 * Obtiene la clase con la que se realizan las consultas
	 * 
	 * @return
	 */
	public Statement getQuerier() {
		return stmt;
	}

	/**
	 * Obtiene una clase con la que se realizan las consultas independiente a la
	 * actual de la instancia, se debe cerrar manualmente
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Statement getNewQuerier() throws SQLException {
		return conn.createStatement();
	}

	/**
	 * Cierra la consutla actual
	 * 
	 * @throws SQLException
	 */
	public void closeQuerier() throws SQLException {
		if (stmt != null)
			stmt.close();
	}

	/**
	 * Ejecuta una consulta de tipo select
	 * 
	 * @param selectQuery
	 * @return ResultSet
	 * @throws SQLException
	 */
	public ResultSet executeSelect(String selectQuery) throws SQLException {
		closeQuerier();
		stmt = conn.createStatement();
		return stmt.executeQuery(selectQuery);
	}

	/**
	 * Ejecuta una consulta de tipo insert/update
	 * 
	 * @param updateQuery
	 * @return the count of updated rows, or 0 for a statement that returns
	 *         nothing
	 * @throws SQLException
	 */
	public int executeUpdate(String updateQuery) throws SQLException {
		closeQuerier();
		stmt = conn.createStatement();
		return stmt.executeUpdate(updateQuery);
	}

	/**
	 * Elimina la instancia del conector actual
	 */
	public static void disposeInstance() {
		try {
			if (dbConnectorInstance != null) {
				dbConnectorInstance.stmt.close();
				if (!dbConnectorInstance.conn.isClosed())
					dbConnectorInstance.conn.close();
			}
		} catch (SQLException e) {
		}
		dbConnectorInstance = null;
		System.gc();
	}

	/**
	 * Elimina la instancia del conector actual y el contexto
	 */
	public static void dispose() {
		if (dbConnectorInstance != null) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						if (!dbConnectorInstance.conn.isClosed())
							dbConnectorInstance.conn.close();
					} catch (SQLException e) {
					}
				}
			}).start();
		}
		dbConnectorInstance = null;
		context = null;
		System.gc();
	}
}
