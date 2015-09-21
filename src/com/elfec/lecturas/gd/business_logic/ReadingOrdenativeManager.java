package com.elfec.lecturas.gd.business_logic;

import java.net.ConnectException;
import java.sql.SQLException;
import java.util.List;

import com.elfec.lecturas.gd.business_logic.data_exchange.DataExporter;
import com.elfec.lecturas.gd.model.ReadingGeneralInfo;
import com.elfec.lecturas.gd.model.ReadingOrdenative;
import com.elfec.lecturas.gd.model.data_exchange.ExportSpecs;
import com.elfec.lecturas.gd.model.events.DataExportListener;
import com.elfec.lecturas.gd.model.results.DataAccessResult;
import com.elfec.lecturas.gd.model.results.VoidResult;
import com.elfec.lecturas.gd.remote_data_access.ReadingOrdenativeRDA;

/**
 * Provee de una capa de lógica de negocio para los ordenativos asignados a las
 * lecturas
 * 
 * @author drodriguez
 *
 */
public class ReadingOrdenativeManager {
	/**
	 * Exporta todos los ordenativos de las lecturas tomadas
	 * 
	 * @return resultado del acceso remoto a datos
	 */
	public DataAccessResult<Boolean> exportAllReadingOrdenatives(
			final String username, final String password,
			DataExportListener exportListener) {
		final ReadingOrdenativeRDA readingOrdenativeRDA = new ReadingOrdenativeRDA();
		return new DataExporter().exportData(
				new ExportSpecs<ReadingOrdenative>() {

					@Override
					public int exportData(ReadingOrdenative readingOrdenative)
							throws ConnectException, SQLException {
						return readingOrdenativeRDA.insertReadingOrdenative(
								username, password, readingOrdenative);
					}

					@Override
					public List<ReadingOrdenative> requestDataToExport() {
						return ReadingOrdenative
								.getExportPendingReadingOrdenatives();

					}
				}, exportListener);
	}

	/**
	 * Elimina todos los ordenativos asignados a una lectura
	 * 
	 * @param reading
	 * @return resultado de la operación
	 */
	public VoidResult deleteReadingAssignedOrdenatives(
			ReadingGeneralInfo reading) {
		VoidResult result = new VoidResult();
		try {
			ReadingOrdenative.deleteByReadingRemoteId(reading
					.getReadingRemoteId());
		} catch (Exception e) {
			Log.error(ReadingTakenManager.class, e);
			e.printStackTrace();
			result.addError(e);
		}
		return result;
	}
}
