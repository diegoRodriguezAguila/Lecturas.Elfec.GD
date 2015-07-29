package com.elfec.lecturas.gd.presenter.views.observers;

/**
 * Representa una vista o cualquier observador que quiera recibir
 * actualizaciones durante un evento de exportaci�n de datos
 * 
 * @author drodriguez
 *
 */
public interface IDataExportationObserver {
	/**
	 * Indica al usuario que la exportaci�n fu� exitosa
	 */
	public void notifySuccessfullyExportation();
}
