package com.elfec.lecturas.gd.presenter.views.observers;

import java.util.List;

/**
 * Representa una vista o cualquier observador que quiera recibir actualizaciones
 * durante un evento de importaci�n de datos
 * @author drodriguez
 *
 */
public interface IDataImportationObserver {
	/**
	 * Muestra un mensaje de espera al usuario
	 * 
	 * @param titleStrId
	 *            id del recurso string del titulo
	 * @param msgStrId
	 *            id del recurso string del mensaje
	 * @param iconDrawableId
	 *            id del recurso drawable del icono del dialogo
	 */
	public void showWaiting(int titleStrId, int msgStrId, int iconDrawableId);

	/**
	 * Acualiza el mensaje de espera del usuario
	 * 
	 * @param msgStrId
	 *            id del recurso string del mensaje
	 */
	public void updateWaiting(int msgStrId);

	/**
	 * Esconde el mensaje de espera
	 */
	public void hideWaiting();

	/**
	 * Muestra un mensaje de error al usuario en caso de haber
	 * 
	 * @param titleStrId
	 *            id del recurso string del titulo del mensaje de error
	 * @param errors
	 */
	public void showErrors(int titleStrId, int iconDrawableId,
			List<Exception> errors);

	/**
	 * Indica al usuario que la importaci�n fu� exitosa
	 */
	public void notifySuccessfullyImportation();
}
