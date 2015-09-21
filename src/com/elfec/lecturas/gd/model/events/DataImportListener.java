package com.elfec.lecturas.gd.model.events;

import com.elfec.lecturas.gd.model.results.VoidResult;

/**
 * Listener para los eventos que ocurren en la importaci�n de datos
 * @author drodriguez
 *
 */
public interface DataImportListener {
	/**
	 * La funci�n que se llama cuando se inicializ� una importaci�n de datos
	 * @param result
	 */
	public void onImportInitialized();
	/**
	 * La funci�n que se llama cuando finaliz� un evento de importaci�n de datos
	 * @param result
	 */
	public void onImportFinished(VoidResult result);
}
