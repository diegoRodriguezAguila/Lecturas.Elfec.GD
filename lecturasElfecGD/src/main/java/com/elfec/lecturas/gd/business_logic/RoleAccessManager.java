package com.elfec.lecturas.gd.business_logic;

import java.net.ConnectException;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import com.elfec.lecturas.gd.model.enums.ConnectionParam;
import com.elfec.lecturas.gd.model.results.VoidResult;
import com.elfec.lecturas.gd.remote_data_access.RoleAccessRDA;
import com.elfec.lecturas.gd.settings.AppPreferences;
import com.elfec.lecturas.gd.settings.OracleDatabaseSettings;

import android.nfc.FormatException;

/**
 * Se encarga de la lógica de negocio relacionada a roles
 * 
 * @author drodriguez
 *
 */
public class RoleAccessManager {

	/**
	 * Habilita el rol MOVIL_COBRANZA
	 * 
	 * @param username
	 * @param password
	 * @return resultado del acceso remoto a datos
	 */
	public VoidResult enableMobileCollectionRole(String username,
			String password) {
		VoidResult result = new VoidResult();
		String errorWhileEnablingRole = "Error al activar el rol: ";
		JSONObject settings;
		try {
			settings = OracleDatabaseSettings
					.getJSONConnectionSettings(AppPreferences
							.getApplicationContext());
			new RoleAccessRDA().enableRole(username, password,
					settings.getString(ConnectionParam.ROLE.toString()),
					settings.getString(ConnectionParam.PASSWORD.toString()));
		} catch (JSONException e) {
			result.addError(new FormatException(
					errorWhileEnablingRole
							+ "Los parámetros de la configuración de conexión a la base de datos tienen un formato incorrecto!"));
			e.printStackTrace();
		} catch (ConnectException e) {
			result.addError(e);
		} catch (SQLException e) {
			result.addError(new SQLException(errorWhileEnablingRole
					+ e.getMessage()));
		} catch (Exception e) {
			result.addError(new SQLException(errorWhileEnablingRole
					+ e.getMessage()));
		}
		return result;
	}
}
