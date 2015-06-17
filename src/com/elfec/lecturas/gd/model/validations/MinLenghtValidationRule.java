package com.elfec.lecturas.gd.model.validations;

import com.elfec.lecturas.gd.model.exceptions.ValidationException;

/**
 * Valida si el tamaño de una cadena es válido, es decir si su longitud es mayor o igual que el tamaño
 * mínimo requerido
 * @author Diego
 *
 */
public class MinLenghtValidationRule implements IValidationRule<String> {

	private int minLenght = 0;
	@Override
	public boolean isValid(String objectToValidate, String... params) {
		minLenght = Integer.parseInt(params[0]);
		return  objectToValidate!=null && objectToValidate.length()>=minLenght;
	}

	@Override
	public ValidationException getError(String fieldName, boolean isMaleGender) {
		return new ValidationException((isMaleGender?"El ":"La ")+fieldName+" tiene que ser de al menos "+minLenght+" caracteres.");
	}

}
