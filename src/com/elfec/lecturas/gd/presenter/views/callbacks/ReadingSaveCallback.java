package com.elfec.lecturas.gd.presenter.views.callbacks;

import java.util.List;

/**
 * Callback para el proceso de guardado de una lectura
 * 
 * @author drodriguez
 *
 */
public interface ReadingSaveCallback {
	/**
	 * Se llama cuando el proceso de guardado de la lectura fue exitoso
	 */
	public void onReadingSavedSuccesfully();

	/**
	 * Se llama cuando el proceso de guardado de la lectura tuvo errores
	 * 
	 * @param errors
	 */
	public void onReadingSaveErrors(List<Exception> errors);
}
