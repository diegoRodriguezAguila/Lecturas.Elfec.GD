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
	 * validación
	 * 
	 * @param objectToValidate
	 * @return boolean, true si es válido
	 */
	boolean isValid(T objectToValidate, Object... params);

	/**
	 * Devuelve una cadena que describe el error en caso de incumplimiento de la
	 * regla de validación utilizando el <b>fieldName</b> como parámetro
	 * 
	 * @param fieldName
	 * @param isMaleGender
	 *            sirve para mostrar el mensaje correcto según el género
	 * @return error en la validación
	 */
	ValidationException getError(String fieldName, boolean isMaleGender);
}
