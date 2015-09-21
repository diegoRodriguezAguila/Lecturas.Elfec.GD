package com.elfec.lecturas.gd.model.events;

import com.elfec.lecturas.gd.model.results.VoidResult;

/**
 * Listener para los eventos que ocurren en la importación de datos
 * @author drodriguez
 *
 */
public interface DataImportListener {
	/**
	 * La función que se llama cuando se inicializó una importación de datos
	 * @param result
	 */
	public void onImportInitialized();
	/**
	 * La función que se llama cuando finalizó un evento de importación de datos
	 * @param result
	 */
	public void onImportFinished(VoidResult result);
}
