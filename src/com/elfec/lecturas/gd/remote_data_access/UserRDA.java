package com.elfec.lecturas.gd.remote_data_access;

import java.net.ConnectException;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.elfec.lecturas.gd.model.User;
import com.elfec.lecturas.gd.model.enums.UserStatus;
import com.elfec.lecturas.gd.remote_data_access.connection.OracleDatabaseConnector;

/**
 * Provee una capa de acceso remoto a la base de datos oracle
 * 
 * @author drodriguez
 *
 */
public class UserRDA {

	/**
	 * Obtiene remotamente el usuario especificado, se requiere password y
	 * nombre de usuario para la conexión
	 * 
	 * @param username
	 * @param password
	 * @return
	 * @throws SQLException
	 * @throws ConnectException
	 */
	public static User requestUser(String username, String password)
			throws ConnectException, SQLException {
		ResultSet rs = OracleDatabaseConnector.instance(username, password)
				.executeSelect(
						"SELECT  USUARIO, PERFIL, SESION_DIAS, ESTADO "
								+ "FROM MOVILES.USUARIO_APP MU "
								+ "WHERE upper(MU.USUARIO) = upper('"
								+ username + "') "
								+ "AND MU.APLICACION = 'Lecturas Movil GD'");
		while (rs.next()) {
			return new User(username, rs.getString("PERFIL"),
					rs.getInt("SESION_DIAS"), UserStatus.get(
							rs.getShort("ESTADO")));
		}
		rs.close();
		return null;
	}
}
