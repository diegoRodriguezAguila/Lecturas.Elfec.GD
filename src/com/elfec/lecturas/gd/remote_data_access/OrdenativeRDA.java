package com.elfec.lecturas.gd.remote_data_access;

import java.net.ConnectException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.elfec.lecturas.gd.model.Ordenative;
import com.elfec.lecturas.gd.remote_data_access.connection.OracleDatabaseConnector;

/**
 * Provee una capa de acceso remoto a los datos de Ordenativos de la tabla
 * <b>ERP_ELFEC.TIPOS_NOV_SUM</b>
 * 
 * @author drodriguez
 *
 */
public class OrdenativeRDA {
	
	/**
	 * Obtiene todos los ordenativos disponibles
	 * @param username
	 * @param password
	 * @return {@link List}<{@link Ordenative}> Lista de ordenativos
	 * @throws ConnectException
	 * @throws SQLException
	 */
	public List<Ordenative> requestOrdenatives(String username,
			String password) throws ConnectException, SQLException {
		{
			List<Ordenative> ordenatives = new ArrayList<Ordenative>();
			ResultSet rs = OracleDatabaseConnector.instance(username, password)
					.executeSelect("SELECT * FROM ERP_ELFEC.TIPOS_NOV_SUM WHERE ESTADO='A'");
			while (rs.next()) {
				ordenatives.add(new Ordenative(rs.getInt("IDNOVEDAD"), rs
						.getString("DESCRIPCION"), rs.getString("TIPO_NOV_MOV")));
			}
			return ordenatives;
		}
	}
}
