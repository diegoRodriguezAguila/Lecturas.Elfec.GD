package com.elfec.lecturas.gd.business_logic;

import java.net.ConnectException;
import java.sql.SQLException;
import java.util.List;

import org.joda.time.DateTime;

import com.elfec.lecturas.gd.business_logic.data_exchange.DataImporter;
import com.elfec.lecturas.gd.model.RouteAssignment;
import com.elfec.lecturas.gd.model.data_exchange.ImportSource;
import com.elfec.lecturas.gd.model.enums.RouteAssignmentStatus;
import com.elfec.lecturas.gd.model.events.DataImportListener;
import com.elfec.lecturas.gd.model.exceptions.NoRoutesAsignedException;
import com.elfec.lecturas.gd.model.results.TypedResult;
import com.elfec.lecturas.gd.model.results.VoidResult;
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

		RouteAssignment.deleteAllNonImportedUserRouteAssignments(SessionManager
				.getLoggedInUsername());
		TypedResult<List<RouteAssignment>> result = new DataImporter()
				.importData(new ImportSource<RouteAssignment>() {
					@Override
					public List<RouteAssignment> requestData()
							throws ConnectException, SQLException {
						return new RouteAssignmentRDA()
								.requestUserRouteAssignments(username,
										password, DateTime.now());
					}
				});
		if (result.getResult().size() == 0)// no tiene rutas asignadas
			result.addError(new NoRoutesAsignedException(username));

		if (dataImportListener != null)
			dataImportListener.onImportFinished(result);
		return result;
	}

	/**
	 * Asigna remota y localmente el estado de CARGADAS a la lista de rutas. Si
	 * ocurre un error al realizar el update remoto aquellas rutas que se hayan
	 * logrado guardar estarán marcadas como exportadas localmente
	 * 
	 * @param assignedRoutes
	 * @return {@link VoidResult} lista de errores del proceso
	 */
	public VoidResult setRoutesSuccessfullyImported(String username,
			String password, List<RouteAssignment> assignedRoutes) {
		VoidResult result = new VoidResult();
		RouteAssignmentRDA routeAssignmentRDA = new RouteAssignmentRDA();
		try {
			for (RouteAssignment route : assignedRoutes) {
				if (route.isAssigned()) {
					route.setStatus(route.getStatus() == RouteAssignmentStatus.ASSIGNED ? RouteAssignmentStatus.IMPORTED
							: RouteAssignmentStatus.RE_READING_IMPORTED);
					routeAssignmentRDA.remoteUpdateUserRouteAssignment(
							username, password, route);
					route.save();
				}
			}
		} catch (ConnectException e) {
			result.addError(e);
		} catch (SQLException e) {
			e.printStackTrace();
			result.addError(e);
		} catch (Exception e) {
			Log.error(RouteAssignmentManager.class, e);
			e.printStackTrace();
			result.addError(e);
		}
		return result;
	}

	/**
	 * Asigna remota y localmente el estado de DESCARGADAS a la lista de rutas.
	 * Si ocurre un error al realizar el update remoto aquellas rutas que se
	 * hayan logrado guardar estarán marcadas como exportadas localmente
	 * 
	 * @param routes
	 * @return {@link VoidResult} lista de errores del proceso
	 */
	public VoidResult setRoutesSuccessfullyExported(String username,
			String password, List<RouteAssignment> routes) {
		VoidResult result = new VoidResult();
		RouteAssignmentRDA routeAssignmentRDA = new RouteAssignmentRDA();
		try {
			for (RouteAssignment route : routes) {
				if (route.isImported()) {
					route.setStatus(route.getStatus() == RouteAssignmentStatus.IMPORTED ? RouteAssignmentStatus.EXPORTED
							: RouteAssignmentStatus.RE_READING_EXPORTED);
					routeAssignmentRDA.remoteUpdateUserRouteAssignment(
							username, password, route);
					route.save();
				}
			}
		} catch (ConnectException e) {
			result.addError(e);
		} catch (SQLException e) {
			e.printStackTrace();
			result.addError(e);
		} catch (Exception e) {
			Log.error(RouteAssignmentManager.class, e);
			e.printStackTrace();
			result.addError(e);
		}
		return result;
	}

	/**
	 * Restaura el estado que tenían las rutas antes de ser importadas al
	 * telefono local y remotamente
	 * 
	 * @param routes
	 * @return {@link VoidResult} lista de errores del proceso
	 */
	public VoidResult restoreRouteAssignments(String username, String password,
			List<RouteAssignment> routes) {
		VoidResult result = new VoidResult();
		RouteAssignmentRDA routeAssignmentRDA = new RouteAssignmentRDA();
		try {
			for (RouteAssignment route : routes) {
				if (route.isImported()) {
					route.setStatus(route.getStatus() == RouteAssignmentStatus.IMPORTED ? RouteAssignmentStatus.ASSIGNED
							: RouteAssignmentStatus.RE_READING);
					routeAssignmentRDA.remoteUpdateUserRouteAssignment(
							username, password, route);
					route.save();
				}
			}
		} catch (ConnectException e) {
			result.addError(e);
		} catch (SQLException e) {
			e.printStackTrace();
			result.addError(e);
		} catch (Exception e) {
			Log.error(RouteAssignmentManager.class, e);
			e.printStackTrace();
			result.addError(e);
		}
		return result;
	}
}
