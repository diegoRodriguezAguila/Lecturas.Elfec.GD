package com.elfec.lecturas.gd.presenter.views.observers;

/**
 * Representa una vista o cualquier observador que quiera recibir
 * actualizaciones durante un evento de exportación de datos
 * 
 * @author drodriguez
 *
 */
public interface IDataExportationObserver {
	/**
	 * Indica al usuario que la exportación fué exitosa
	 */
	public void notifySuccessfullyExportation();
}
