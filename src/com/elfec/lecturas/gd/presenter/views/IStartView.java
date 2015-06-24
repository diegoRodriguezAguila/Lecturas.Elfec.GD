package com.elfec.lecturas.gd.presenter.views;

import java.util.List;

import android.content.Context;

/**
 * Abstracción de la vista de Start
 * 
 * @author drodriguez
 *
 */
public interface IStartView {

	/**
	 * Asigna el campo del usuario logeado actual
	 * 
	 * @param username
	 */
	public void setCurrentUser(String username);

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
	 * Muestra un mensaje de error al usuario
	 * 
	 * @param titleStrId
	 *            id del recurso string del titulo del mensaje de error
	 * @param errors
	 */
	public void showErrors(int titleStrId, int iconDrawableId,
			List<Exception> errors);

	/**
	 * Indica al usuario que la importación fué exitosa
	 */
	public void notifySuccessfullyImportation();

	/**
	 * Obtiene el contexto actual de la vista
	 * 
	 * @return Context
	 */
	public Context getContext();
}
