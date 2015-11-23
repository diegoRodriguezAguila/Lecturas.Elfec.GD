package com.elfec.lecturas.gd.presenter.views;

import java.util.List;

import android.content.Context;

/**
 * Abstracción de la vista de Login
 * 
 * @author drodriguez
 *
 */
public interface ILoginView {
	/**
	 * Obtiene el nombre de usuario
	 * 
	 * @return nombre de usuario
	 */
	public String getUsername();

	/**
	 * Obtiene el password
	 * 
	 * @return password
	 */
	public String getPassword();

	/**
	 * Obtiene el IMEI del dispositivo
	 * 
	 * @return IMEI del dispositivo
	 */
	public String getIMEI();

	/**
	 * Muestra errores en el campo del nombre de usuario
	 * 
	 * @param errors
	 */
	public void setUsernameFieldErrors(List<Exception> errors);

	/**
	 * Muestra errores en el campo de la contraseña
	 * 
	 * @param errors
	 */
	public void setPasswordFieldErrors(List<Exception> errors);

	/**
	 * Limpia el campo del password
	 */
	public void clearPassword();

	/**
	 * Notifica al usuario que existen errores en los campos
	 */
	public void notifyErrorsInFields();

	/**
	 * Indica al usuario que debe esperar
	 */
	public void showWaiting();

	/**
	 * Acualiza el mensaje de espera del usuario
	 */
	public void updateWaiting(int strId);

	/**
	 * Borra el mensaje de espera
	 */
	public void hideWaiting();

	/**
	 * Muestra los errores ocurridos durante el intento de login
	 * 
	 * @param validationErrors
	 */
	public void showLoginErrors(List<Exception> validationErrors);

	/**
	 * Cambia la vista actual a la de Inicio
	 */
	public void goToStart();

	/**
	 * Obtiene el contexto actual de la vista
	 * 
	 * @return Context
	 */
	public Context getContext();
}
