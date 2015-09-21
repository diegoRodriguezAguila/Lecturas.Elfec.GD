package com.elfec.lecturas.gd.business_logic;

import java.net.ConnectException;
import java.sql.SQLException;

import org.joda.time.DateTime;
import org.joda.time.Minutes;

import com.elfec.lecturas.gd.model.exceptions.SysdateUnsyncFromServerException;
import com.elfec.lecturas.gd.model.results.TypedResult;
import com.elfec.lecturas.gd.remote_data_access.ServerDateRDA;

/**
 * Se encarga de que la sincronización de hora y fecha con el servidor sea la correcta
 * @author drodriguez
 *
 */
public class ServerDateSyncManager {
	/**
	 * Valida si es que la fecha y hora del sistema coinciden con la del servidor
	 * @param username
	 * @param password
	 * @return Resultado de la validacion
	 */
	public TypedResult<Boolean> validateSysDate(String username, String password){
		TypedResult<Boolean> result = new TypedResult<Boolean>(false);
		try {
			DateTime serverDateTime = new ServerDateRDA().requestServerDateTime(username, password);
			boolean isDateClose = (Math.abs(Minutes.minutesBetween(serverDateTime, DateTime.now()).getMinutes())<=3);
			if(!isDateClose)
				result.addError(new SysdateUnsyncFromServerException(serverDateTime));
			result.setResult(isDateClose);
		} catch (ConnectException e) {
			result.addError(e);
		} catch (SQLException e) {
			result.addError(e);
		}	
		return result;
	}
}
