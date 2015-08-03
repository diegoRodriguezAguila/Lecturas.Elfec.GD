package com.elfec.lecturas.gd.view.listeners;

import android.view.View;

/**
 * Listener para el click del boton de guardar lectura
 * 
 * @author drodriguez
 *
 */
public interface OnReadingSaveClickListener {
	/**
	 * Se ejecuta cuando el boton de guardar lectura se clickeó
	 * 
	 * @param v
	 * @param callback
	 *            puede ser nulo, se llama en el proceso de guardado de lectura
	 */
	public void readingSaveClicked(View v);
}
