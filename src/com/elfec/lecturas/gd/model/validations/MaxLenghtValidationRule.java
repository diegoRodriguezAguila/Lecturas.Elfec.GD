package com.elfec.lecturas.gd.model.validations;

import com.elfec.lecturas.gd.model.exceptions.ValidationException;

/**
 * Valida si el tamaño de una cadena es válido, es decir si su longitud es menor
 * o hasta igual que el tamaño máximo establecido
 * 
 * @author Diego
 *
 */
public class MaxLenghtValidationRule implements IValidationRule<String> {

	private int maxLenght = 0;

	@Override
	public boolean isValid(String objectToValidate, String... params) {
		maxLenght = Integer.parseInt(params[0]);
		return objectToValidate != null
				&& objectToValidate.length() <= maxLenght;
	}

	@Override
	public ValidationException getError(String fieldName, boolean isMaleGender) {
		return new ValidationException((isMaleGender ? "El " : "La ")
				+ fieldName + " tiene que tener menos de " + maxLenght
				+ " caracteres.");
	}

}
