package com.elfec.lecturas.gd.model.validations;

import com.elfec.lecturas.gd.model.exceptions.ValidationException;

/**
 * Valida si es que una cadena es transformable a un Int
 * @author Diego
 *
 */
public class NumericValidationRule implements IValidationRule<String> {

	@Override
	public boolean isValid(String objectToValidate, String... params) {
		try
		{
			Integer.parseInt(objectToValidate);
		}
		catch(NumberFormatException e)
		{
			return false;
		}
		return true;
	}

	@Override
	public ValidationException getError(String fieldName, boolean isMaleGender) {
		return new ValidationException((isMaleGender?"El ":"La ")+fieldName+" tiene que ser "+(isMaleGender?"numérico-":"numérica."));
	}

}
