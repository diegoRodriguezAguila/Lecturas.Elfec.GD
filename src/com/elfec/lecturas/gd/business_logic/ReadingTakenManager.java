package com.elfec.lecturas.gd.business_logic;

import com.elfec.lecturas.gd.model.ReadingGeneralInfo;
import com.elfec.lecturas.gd.model.ReadingTaken;
import com.elfec.lecturas.gd.model.enums.ReadingStatus;
import com.elfec.lecturas.gd.model.results.VoidResult;

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
}
