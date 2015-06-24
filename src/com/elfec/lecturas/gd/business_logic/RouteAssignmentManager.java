package com.elfec.lecturas.gd.business_logic;

import java.net.ConnectException;
import java.sql.SQLException;
import java.util.List;

import org.joda.time.DateTime;

import com.elfec.lecturas.gd.business_logic.data_exchange.DataImporter;
import com.elfec.lecturas.gd.model.RouteAssignment;
import com.elfec.lecturas.gd.model.data_exchange.ImportSource;
import com.elfec.lecturas.gd.model.events.DataImportListener;
import com.elfec.lecturas.gd.model.exceptions.NoRoutesAsignedException;
import com.elfec.lecturas.gd.model.results.TypedResult;
import com.elfec.lecturas.gd.remote_data_access.RouteAssignmentRDA;

/**
 * Clase que provee una capa de logica de negocio para las asignaciones de rutas
 * MOVILES.USUARIO_ASIGNACION
 * 
 * @author drodriguez
 *
 */
public class RouteAssignmentManager {

	/**
	 * Importa las rutas asignadas al usuario para la fecha actual.<br>
	 * <b>Nota.-</b> La importación incluye la consulta remota y el guardado
	 * local de los datos
	 * 
	 * @param username
	 * @param password
	 * @param dataImportListener
	 *            {@link DataImportListener}
	 * @return {@link TypedResult} con el resultado de la lista de rutas
	 *         asignadas al usuario
	 */
	public TypedResult<List<RouteAssignment>> importUserRouteAssignments(
			final String username, final String password,
			DataImportListener dataImportListener) {
		if (dataImportListener != null)
			dataImportListener.onImportInitialized();
		RouteAssignment.deleteAllUserRouteAssignments(SessionManager.getLoggedInUsername());
		TypedResult<List<RouteAssignment>> result = new DataImporter()
				.importData(new ImportSource<RouteAssignment>() {
					@Override
					public List<RouteAssignment> requestData()
							throws ConnectException, SQLException {
						return new RouteAssignmentRDA().requestUserRouteAssignments(
								username, password, DateTime.now());
					}
				});
		if(result.getResult().size()==0)//no tiene rutas asignadas
			result.addError(new NoRoutesAsignedException(username));
		if (dataImportListener != null)
			dataImportListener.onImportFinished(result);
		return result;
	}
}
