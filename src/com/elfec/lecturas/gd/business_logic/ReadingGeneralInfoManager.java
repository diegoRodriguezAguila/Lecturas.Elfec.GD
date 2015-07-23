package com.elfec.lecturas.gd.business_logic;

import java.net.ConnectException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.elfec.lecturas.gd.business_logic.data_exchange.DataImporter;
import com.elfec.lecturas.gd.model.ReadingGeneralInfo;
import com.elfec.lecturas.gd.model.RouteAssignment;
import com.elfec.lecturas.gd.model.data_exchange.ImportSource;
import com.elfec.lecturas.gd.model.events.DataImportListener;
import com.elfec.lecturas.gd.model.exceptions.RouteAssignmentWithNoReadingsException;
import com.elfec.lecturas.gd.model.results.TypedResult;
import com.elfec.lecturas.gd.remote_data_access.ReadingGeneralInfoRDA;

/**
 * Provee de una capa de lógica de negocio para la información general de
 * lecturas
 * 
 * @author drodriguez
 *
 */
public class ReadingGeneralInfoManager {
	/**
	 * Importa toda la información general de las lecturas de las rutas
	 * asignadas al usuario para la fecha actual.<br>
	 * <b>Nota.-</b> La importación incluye la consulta remota y el guardado
	 * local de los datos
	 * 
	 * @param username
	 * @param password
	 * @param dataImportListener
	 *            {@link DataImportListener}
	 * @return {@link TypedResult} con el resultado de la las lecturas de la
	 *         lista de rutas asignadas al usuario
	 */
	public TypedResult<List<ReadingGeneralInfo>> importAllAssignedReadingsGeneralInfo(
			final String username, final String password,
			List<RouteAssignment> assignedRoutes,
			DataImportListener dataImportListener) {
		TypedResult<List<ReadingGeneralInfo>> globalResult = new TypedResult<List<ReadingGeneralInfo>>(
				new ArrayList<ReadingGeneralInfo>());
		TypedResult<List<ReadingGeneralInfo>> result;
		ReadingGeneralInfoRDA readingGeneralInfoRDA = new ReadingGeneralInfoRDA();

		if (dataImportListener != null)
			dataImportListener.onImportInitialized();

		for (RouteAssignment assignedRoute : assignedRoutes) {
			result = importReadingsGeneralInfo(username, password,
					readingGeneralInfoRDA, assignedRoute);
			if (result.getResult().size() == 0)// no tiene lecturas en la ruta
				result.addError(new RouteAssignmentWithNoReadingsException(
						assignedRoute));
			globalResult.addErrors(result.getErrors()); // copiando errores
			globalResult.getResult().addAll(result.getResult()); // lecturas
			if (globalResult.hasErrors())// rompe ciclo si hay errores
				break;
		}

		if (dataImportListener != null)
			dataImportListener.onImportFinished(globalResult);
		return globalResult;
	}

	/**
	 * Importa la informacón general de lecturas de una ruta asignada
	 * 
	 * @param username
	 * @param password
	 * @param readingGeneralInfoRDA
	 * @param assignedRoute
	 * @return {@link TypedResult} con el resultado de las lecturas de la ruta
	 *         asignada al usuario
	 */
	private TypedResult<List<ReadingGeneralInfo>> importReadingsGeneralInfo(
			final String username, final String password,
			final ReadingGeneralInfoRDA readingGeneralInfoRDA,
			final RouteAssignment assignedRoute) {
		TypedResult<List<ReadingGeneralInfo>> result;
		ReadingGeneralInfo.deleteAssignedRouteReadingsInfo(assignedRoute);
		result = new DataImporter()
				.importData(new ImportSource<ReadingGeneralInfo>() {
					@Override
					public List<ReadingGeneralInfo> requestData()
							throws ConnectException, SQLException {
						return readingGeneralInfoRDA
								.requestReadingsGeneralInfo(username, password,
										assignedRoute);
					}
				});
		return result;
	}

	public TypedResult<ReadingGeneralInfo> searchReading(String accountNumber,
			String meter, int nus) {
		TypedResult<ReadingGeneralInfo> result = new TypedResult<>();
		try {
			// TODO search logic
		} catch (Exception e) {
			Log.error(ReadingGeneralInfoManager.class, e);
			e.printStackTrace();
			result.addError(e);
		}
		return result;
	}

}
