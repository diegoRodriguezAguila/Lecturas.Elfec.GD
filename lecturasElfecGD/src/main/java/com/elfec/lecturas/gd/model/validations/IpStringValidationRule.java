package com.elfec.lecturas.gd.model.validations;

import com.elfec.lecturas.gd.model.exceptions.ValidationException;

import org.apache.commons.validator.routines.InetAddressValidator;

public class IpStringValidationRule implements IValidationRule<String> {

	@Override
	public boolean isValid(String objectToValidate, Object... params) {
		return InetAddressValidator.getInstance().isValidInet4Address(
				objectToValidate);
	}

	@Override
	public ValidationException getError(String fieldName, boolean isMaleGender) {
		return new ValidationException((isMaleGender ? "El " : "La ") + "<b>"
				+ fieldName + "</b> tiene que ser una IPv4 válid"
				+ (isMaleGender ? "o" : "a"));
	}

}
