package com.elfec.lecturas.gd.business_logic;

import java.net.ConnectException;
import java.sql.SQLException;
import java.util.List;

import com.elfec.lecturas.gd.business_logic.data_exchange.DataExporter;
import com.elfec.lecturas.gd.model.ReadingGeneralInfo;
import com.elfec.lecturas.gd.model.ReadingTaken;
import com.elfec.lecturas.gd.model.data_exchange.ExportSpecs;
import com.elfec.lecturas.gd.model.enums.ReadingStatus;
import com.elfec.lecturas.gd.model.events.DataExportListener;
import com.elfec.lecturas.gd.model.results.DataAccessResult;
import com.elfec.lecturas.gd.model.results.VoidResult;
import com.elfec.lecturas.gd.remote_data_access.ReadingTakenRDA;

/**
 * Se encarga de las operaciones de lógica de negocio para las lecturas tomadas
 * 
 * @author drodriguez
 *
 */
public class ReadingTakenManager {
	/**
	 * Registra la lectura tomada y el estado indicado. Posteriormente se guarda
	 * esta lectura con el nuevo estado. Si ya se tenía una lectura tomada para
	 * la lectura indicada se reemplaza la información con la nueva lectura
	 * 
	 * @param reading
	 * @param readingTaken
	 * @param status
	 *            {@link ReadingStatus#READ} o {@link ReadingStatus#IMPEDED}
	 *            caso contrario lanza excepción
	 *            {@link IllegalArgumentException}
	 */
	public static VoidResult registerReadingTaken(ReadingGeneralInfo reading,
			ReadingTaken readingTaken, ReadingStatus status) {
		VoidResult result = new VoidResult();
		try {
			if (reading.getReadingTaken() != null) {
				readingTaken.assignId(reading.getReadingTaken());
			}
			readingTaken.save();
			reading.assignReadingTaken(readingTaken, status);
			reading.save();
		} catch (Exception e) {
			Log.error(ReadingTakenManager.class, e);
			e.printStackTrace();
			result.addError(e);
		}
		return result;
	}

	/**
	 * Exporta todas las lecturas tomadas
	 * 
	 * @return resultado del acceso remoto a datos
	 */
	public static DataAccessResult<Boolean> exportAllReadingsTaken(
			final String username, final String password,
			DataExportListener exportListener) {
		final ReadingTakenRDA readingTakenRDA = new ReadingTakenRDA();
		return new DataExporter().exportData(new ExportSpecs<ReadingTaken>() {

			@Override
			public int exportData(ReadingTaken readingTaken)
					throws ConnectException, SQLException {
				return readingTakenRDA.insertReadingTaken(username, password,
						readingTaken);
			}

			@Override
			public List<ReadingTaken> requestDataToExport() {
				return ReadingTaken.getExportPendingReadingsTaken();
			}
		}, exportListener);
	}
}
