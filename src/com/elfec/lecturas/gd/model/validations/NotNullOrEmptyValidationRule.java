package com.elfec.lecturas.gd.model.validations;

import com.elfec.lecturas.gd.model.exceptions.ValidationException;

public class NotNullOrEmptyValidationRule implements IValidationRule<String> {

	@Override
	public boolean isValid(String objectToValidate, Object... params) {
		return objectToValidate != null && objectToValidate.length() > 0
				&& objectToValidate.trim().length() > 0;
	}

	@Override
	public ValidationException getError(String fieldName, boolean isMaleGender) {
		return new ValidationException((isMaleGender ? "El " : "La ") + "<b>"
				+ fieldName + "</b> no puede estar "
				+ (isMaleGender ? "vacío." : "vacía."));
	}

}
