package com.elfec.lecturas.gd.business_logic;

import java.net.ConnectException;
import java.sql.SQLException;
import java.util.List;

import com.elfec.lecturas.gd.business_logic.data_exchange.DataImporter;
import com.elfec.lecturas.gd.model.Ordenative;
import com.elfec.lecturas.gd.model.ReadingGeneralInfo;
import com.elfec.lecturas.gd.model.data_exchange.ImportSource;
import com.elfec.lecturas.gd.model.events.DataImportListener;
import com.elfec.lecturas.gd.model.results.TypedResult;
import com.elfec.lecturas.gd.model.results.VoidResult;
import com.elfec.lecturas.gd.remote_data_access.OrdenativeRDA;
import com.elfec.lecturas.gd.settings.AppPreferences;

/**
 * Provee de una capa de lógica de negocio para los Ordenativos
 * 
 * @author drodriguez
 *
 */
public class OrdenativeManager {
	/**
	 * Importa los ordenativos del erp si es que no se importaron ya
	 * previamente.<br>
	 * <b>Nota.-</b> La importación incluye la consulta remota y el guardado
	 * local de los datos
	 * 
	 * @param username
	 * @param password
	 * @param dataImportListener
	 *            {@link DataImportListener}
	 * @return {@link VoidResult}
	 */
	public VoidResult importOrdenatives(final String username,
			final String password, DataImportListener dataImportListener) {
		VoidResult result = new VoidResult();
		if (!AppPreferences.instance().isOrdenativesImported()) {
			if (dataImportListener != null)
				dataImportListener.onImportInitialized();
			Ordenative.deleteAllOrdenatives();
			result = new DataImporter()
					.importData(new ImportSource<Ordenative>() {
						@Override
						public List<Ordenative> requestData()
								throws ConnectException, SQLException {
							return new OrdenativeRDA().requestOrdenatives(
									username, password);
						}
					});
			AppPreferences.instance().setOrdenativesImported(
					!result.hasErrors());
			if (dataImportListener != null)
				dataImportListener.onImportFinished(result);
		}
		return result;
	}

	/**
	 * Obtiene la lista de ordenativos que aun no fueron asignados a la lectura
	 * indicada
	 * 
	 * @param reading
	 * @return {@link TypedResult} con el resultado de la lista de ordenativos
	 *         (los ordenativos son de tipo manual)
	 */
	public static TypedResult<List<Ordenative>> getReadingUnassignedOrdenatives(
			ReadingGeneralInfo reading) {
		TypedResult<List<Ordenative>> result = new TypedResult<>();
		try {
			List<Ordenative> manualOrdenatives = Ordenative
					.getTypeOrdenatives(Ordenative.MANUAL);
			manualOrdenatives.removeAll(reading.getAssignedOrdenatives());
			result.setResult(manualOrdenatives);
		} catch (Exception e) {
			Log.error(ReadingGeneralInfoManager.class, e);
			e.printStackTrace();
			result.addError(e);
		}
		return result;
	}
}
