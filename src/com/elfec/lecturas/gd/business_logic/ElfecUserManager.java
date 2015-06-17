package com.elfec.lecturas.gd.business_logic;

import java.net.ConnectException;
import java.sql.SQLException;

import com.elfec.lecturas.gd.model.User;
import com.elfec.lecturas.gd.model.enums.DeviceStatus;
import com.elfec.lecturas.gd.model.enums.UserStatus;
import com.elfec.lecturas.gd.model.exceptions.InvalidPasswordException;
import com.elfec.lecturas.gd.model.exceptions.SyncDateOutOfRangeException;
import com.elfec.lecturas.gd.model.exceptions.UnabledDeviceException;
import com.elfec.lecturas.gd.model.exceptions.UnactiveUserException;
import com.elfec.lecturas.gd.model.results.DataAccessResult;
import com.elfec.lecturas.gd.remote_data_access.DeviceRDA;
import com.elfec.lecturas.gd.remote_data_access.UserRDA;
import com.elfec.lecturas.gd.remote_data_access.connection.OracleDatabaseConnector;

/**
 * Se encarga de las operaciones de lógica de negocio sobre el usuario
 * 
 * @author drodriguez
 *
 */
public class ElfecUserManager {

	/**
	 * Valida a un usuario, ya sea local o remotamente según el caso
	 * 
	 * @param username
	 * @param password
	 * @param IMEI
	 * @return El resultado de la validación, que incluye al usuario obtenido y
	 *         la lista de errores
	 */
	public static DataAccessResult<User> validateUser(String username,
			String password, String IMEI) {
		DataAccessResult<User> result = new DataAccessResult<User>();
		User localUser = User.findByUserName(username);
		if (localUser == null) {
			OracleDatabaseConnector.disposeInstance();
			validateRemoteUser(username, password, IMEI, result);
		} else {
			validateLocalUser(localUser, password, result);
		}
		return result;
	}

	/**
	 * Realiza las validaciones del usuario a nivel remoto
	 * 
	 * @param username
	 * @param password
	 * @param IMEI
	 * @param result
	 */
	private static void validateRemoteUser(String username, String password,
			String IMEI, DataAccessResult<User> result) {
		result.setRemoteDataAccess(true);
		try {
			result.addErrors(RoleAccessManager.enableMobileCollectionRole(
					username, password).getErrors());// habilitando el rol
			result.addErrors(ServerDateSyncManager.validateSysDate(username,
					password).getErrors());
			if (!result.hasErrors()) {
				User remoteUser = UserRDA.requestUser(username, password);
				if (remoteUser == null
						|| remoteUser.getStatus() != UserStatus.ACTIVE)
					result.addError(new UnactiveUserException(username));
				if (DeviceRDA.requestDeviceStatus(username, password, IMEI) == DeviceStatus.UNABLED)
					result.addError(new UnabledDeviceException());
				if (!result.hasErrors())
					result.setResult(remoteUser.synchronizeUser(password));
			}
		} catch (ConnectException e) {
			result.addError(e);
		} catch (SQLException e) {
			e.printStackTrace();
			result.addError(e);
		}
	}

	/**
	 * Realiza las validaciones del usuario a nivel local
	 * 
	 * @param password
	 * @param result
	 * @param localUser
	 */
	private static void validateLocalUser(User localUser, String password,
			DataAccessResult<User> result) {
		result.setRemoteDataAccess(false);
		if (!localUser.passwordMatch(password))
			result.addError(new InvalidPasswordException());
		if (!localUser.isSyncDateInRange())
			result.addError(new SyncDateOutOfRangeException());
		result.setResult(localUser);
	}
}
