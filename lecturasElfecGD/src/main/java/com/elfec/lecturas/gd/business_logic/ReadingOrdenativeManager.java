package com.elfec.lecturas.gd.business_logic;

import java.net.ConnectException;
import java.sql.SQLException;
import java.util.List;

import org.joda.time.DateTime;

import com.activeandroid.ActiveAndroid;
import com.elfec.lecturas.gd.business_logic.data_exchange.DataExporter;
import com.elfec.lecturas.gd.model.Ordenative;
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
	 * Agrega ordenativos a una lectura
	 * 
	 * @param reading
	 * @param ordenatives
	 * @return {@link VoidResult} resultado de el guardado de ordenativos
	 */
	public static VoidResult addOrdenativesToReading(
			ReadingGeneralInfo reading, List<Ordenative> ordenatives) {
		VoidResult result = new VoidResult();
		try {
			ActiveAndroid.beginTransaction();
			String currentUser = SessionManager.getLoggedInUsername();
			ReadingOrdenative readOrd;
			for (Ordenative ordenative : ordenatives) {
				readOrd = new ReadingOrdenative(reading.getReadingRemoteId(),
						reading.getSupplyId(), ordenative.getCode(),
						DateTime.now(), currentUser);
				readOrd.save();
				TextBackupManager.saveBackup(readOrd);
			}
			ActiveAndroid.setTransactionSuccessful();
		} catch (Exception e) {
			Log.error(ReadingGeneralInfoManager.class, e);
			e.printStackTrace();
			result.addError(e);
		} finally {
			if (ActiveAndroid.inTransaction())
				ActiveAndroid.endTransaction();
		}
		return result;
	}

	/**
	 * Elimina todos los ordenativos asignados a una lectura
	 * 
	 * @param reading
	 * @return resultado de la operación
	 */
	public static VoidResult deleteReadingAssignedOrdenatives(
			ReadingGeneralInfo reading) {
		VoidResult result = new VoidResult();
		try {
			deleteReadingOrdenativesBackup(reading);
			ReadingOrdenative.deleteByReadingRemoteId(reading
					.getReadingRemoteId());
		} catch (Exception e) {
			Log.error(ReadingTakenManager.class, e);
			e.printStackTrace();
			result.addError(e);
		}
		return result;
	}

	/**
	 * Se encarga de hacer que el backup de ordenativos tenga los eliminar
	 * necesarios
	 * 
	 * @param reading
	 */
	private static void deleteReadingOrdenativesBackup(
			final ReadingGeneralInfo reading) {
		List<ReadingOrdenative> ordenatives = ReadingOrdenative
				.findByReadingRemoteId(reading.getReadingRemoteId());
		for (ReadingOrdenative readOrd : ordenatives) {
			TextBackupManager.deleteBackup(readOrd);
		}
	}

}
