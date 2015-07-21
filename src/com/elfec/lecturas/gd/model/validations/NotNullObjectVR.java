package com.elfec.lecturas.gd.model.validations;

import com.elfec.lecturas.gd.model.exceptions.ValidationException;

public class NotNullObjectVR<T> implements IValidationRule<T> {

	@Override
	public boolean isValid(T objectToValidate, Object... params) {
		return objectToValidate != null;
	}

	@Override
	public ValidationException getError(String fieldName, boolean isMaleGender) {
		return new ValidationException((isMaleGender ? "El " : "La ") + "<b>"
				+ fieldName + "</b> es un campo obligatorio");
	}
}
