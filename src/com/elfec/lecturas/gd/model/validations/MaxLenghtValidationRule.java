package com.elfec.lecturas.gd.model.validations;

import com.elfec.lecturas.gd.model.exceptions.ValidationException;

/**
 * Valida si el tama�o de una cadena es v�lido, es decir si su longitud es menor
 * o hasta igual que el tama�o m�ximo establecido
 * 
 * @author Diego
 *
 */
public class MaxLenghtValidationRule implements IValidationRule<String> {

	private int maxLenght = 0;

	@Override
	public boolean isValid(String objectToValidate, Object... params) {
		maxLenght = Integer.parseInt(params[0].toString());
		return objectToValidate != null
				&& objectToValidate.length() <= maxLenght;
	}

	@Override
	public ValidationException getError(String fieldName, boolean isMaleGender) {
		return new ValidationException((isMaleGender ? "El " : "La ") + "<b>"
				+ fieldName + "</b> tiene que tener menos de " + maxLenght
				+ " caracteres.");
	}

}
