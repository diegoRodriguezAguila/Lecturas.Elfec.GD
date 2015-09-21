package com.elfec.lecturas.gd.business_logic;

import java.net.ConnectException;
import java.sql.SQLException;
import java.util.List;

import com.elfec.lecturas.gd.business_logic.data_exchange.DataImporter;
import com.elfec.lecturas.gd.helpers.util.text.AttributePicker;
import com.elfec.lecturas.gd.helpers.util.text.ObjectListToSQL;
import com.elfec.lecturas.gd.model.ReadingGeneralInfo;
import com.elfec.lecturas.gd.model.ReadingMeter;
import com.elfec.lecturas.gd.model.data_exchange.ImportSource;
import com.elfec.lecturas.gd.model.events.DataImportListener;
import com.elfec.lecturas.gd.model.exceptions.ReadingAndMeterUnmatchException;
import com.elfec.lecturas.gd.model.results.TypedResult;
import com.elfec.lecturas.gd.remote_data_access.ReadingMeterRDA;

/**
 * Provee de una capa de lógica de negocio para la información de medidores de
 * lecturas
 * 
 * @author drodriguez
 *
 */
public class ReadingMeterManager {
	/**
	 * Importa toda la información de medidores de las lecturas de las rutas
	 * asignadas al usuario para la fecha actual.<br>
	 * <b>Nota.-</b> La importación incluye la consulta remota y el guardado
	 * local de los datos
	 * 
	 * @param username
	 * @param password
	 * @param dataImportListener
	 *            {@link DataImportListener}
	 * @return {@link TypedResult} con el resultado de la los medidores de las
	 *         lecturas
	 */
	public TypedResult<List<ReadingMeter>> importReadingMeters(
			final String username, final String password,
			List<ReadingGeneralInfo> readingsGeneralInfo,
			DataImportListener dataImportListener) {
		if (dataImportListener != null)
			dataImportListener.onImportInitialized();
		final String readingsInClause = getReadingsInClause(readingsGeneralInfo);
		ReadingMeter.deleteReadingMeters(readingsInClause.replace(
				"IDLECTURAGD", "ReadingRemoteId"));
		TypedResult<List<ReadingMeter>> result = new DataImporter()
				.importData(new ImportSource<ReadingMeter>() {
					@Override
					public List<ReadingMeter> requestData()
							throws ConnectException, SQLException {
						return new ReadingMeterRDA().requestReadingMeters(
								username, password, readingsInClause);
					}
				});
		if (result.getResult().size() < readingsGeneralInfo.size())// tamaños
			result.addError(new ReadingAndMeterUnmatchException(// no coinciden
					readingsGeneralInfo.size(), result.getResult().size()));
		if (dataImportListener != null)
			dataImportListener.onImportFinished(result);
		return result;
	}

	/**
	 * Obtiene la clausula In de la información general de lecturas
	 * 
	 * @param readingsGeneralInfo
	 * @return
	 */
	private String getReadingsInClause(
			List<ReadingGeneralInfo> readingsGeneralInfo) {
		return ObjectListToSQL.convertToSQL(readingsGeneralInfo, "IDLECTURAGD",
				new AttributePicker<String, ReadingGeneralInfo>() {
					@Override
					public String pickAttribute(
							ReadingGeneralInfo readingGeneralInfo) {
						return "" + readingGeneralInfo.getReadingRemoteId();
					}
				});
	}
}
