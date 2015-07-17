package com.elfec.lecturas.gd.model.validations;

import com.elfec.lecturas.gd.model.exceptions.ValidationException;

/**
 * Provee de una interfaz para reglas de validaci�n
 * 
 * @author Diego
 *
 * @param <T>
 */
public interface IValidationRule<T> {

	/**
	 * Valida si es que el objeto cumple con los requisitos de la regla de
	 * validaci�n
	 * 
	 * @param objectToValidate
	 * @return
	 */
	public boolean isValid(T objectToValidate, Object... params);

	/**
	 * Devuelve una cadena que describe el error en caso de incumplimiento de la
	 * regla de validaci�n utilizando el <b>fieldName</b> como par�metro
	 * 
	 * @param fieldName
	 * @param isMaleGender
	 *            sirve para mostrar el mensaje correcto seg�n el g�nero
	 * @return
	 */
	public ValidationException getError(String fieldName, boolean isMaleGender);
}
