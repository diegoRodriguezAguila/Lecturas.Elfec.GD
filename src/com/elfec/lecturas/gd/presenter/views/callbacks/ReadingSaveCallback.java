package com.elfec.lecturas.gd.presenter.views.callbacks;

import java.util.List;

import com.elfec.lecturas.gd.model.ReadingGeneralInfo;

/**
 * Callback para el proceso de guardado de una lectura
 * 
 * @author drodriguez
 *
 */
public interface ReadingSaveCallback {
	/**
	 * Se llama cuando el proceso de guardado de la lectura fue exitoso
	 * 
	 * @param savedReading
	 * @param wasInEditionMode
	 */
	public void onReadingSavedSuccesfully(ReadingGeneralInfo savedReading,
			boolean wasInEditionMode);

	/**
	 * Se llama cuando el proceso de guardado de una lectura para reintentar fue
	 * exitoso
	 * 
	 * @param savedReading
	 */
	public void onRetryReadingSavedSuccesfully(ReadingGeneralInfo savedReading);

	/**
	 * Se llama cuando el proceso de guardado de la lectura tuvo errores
	 * 
	 * @param errors
	 */
	public void onReadingSaveErrors(List<Exception> errors);
}
