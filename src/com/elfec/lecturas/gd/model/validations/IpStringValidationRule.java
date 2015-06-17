package com.elfec.lecturas.gd.model.validations;

import org.apache.commons.validator.routines.InetAddressValidator;

import com.elfec.lecturas.gd.model.exceptions.ValidationException;

public class IpStringValidationRule implements IValidationRule<String> {

	@Override
	public boolean isValid(String objectToValidate, String... params) {
		return InetAddressValidator.getInstance().isValidInet4Address(
				objectToValidate);
	}

	@Override
	public ValidationException getError(String fieldName, boolean isMaleGender) {
		return new ValidationException((isMaleGender ? "El " : "La ")
				+ fieldName + " tiene que ser una IPv4 válid"
				+ (isMaleGender ? "o" : "a"));
	}

}
