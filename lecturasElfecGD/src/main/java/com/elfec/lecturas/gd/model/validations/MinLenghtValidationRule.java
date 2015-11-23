package com.elfec.lecturas.gd.model.validations;

import com.elfec.lecturas.gd.model.exceptions.ValidationException;

/**
 * Valida si el tama�o de una cadena es v�lido, es decir si su longitud es mayor
 * o igual que el tama�o m�nimo requerido
 * 
 * @author Diego
 *
 */
public class MinLenghtValidationRule implements IValidationRule<String> {

	private int minLenght = 0;

	@Override
	public boolean isValid(String objectToValidate, Object... params) {
		minLenght = Integer.parseInt(params[0].toString());
		return objectToValidate != null
				&& objectToValidate.length() >= minLenght;
	}

	@Override
	public ValidationException getError(String fieldName, boolean isMaleGender) {
		return new ValidationException((isMaleGender ? "El " : "La ") + "<b>"
				+ fieldName + "</b> tiene que ser de al menos " + minLenght
				+ " caracteres.");
	}

}
