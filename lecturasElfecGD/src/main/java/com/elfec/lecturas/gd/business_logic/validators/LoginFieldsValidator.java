package com.elfec.lecturas.gd.business_logic.validators;

import java.util.ArrayList;
import java.util.List;

import com.elfec.lecturas.gd.model.results.VoidResult;
import com.elfec.lecturas.gd.model.validations.IValidationRule;
import com.elfec.lecturas.gd.model.validations.NotNullOrEmptyValidationRule;

/**
 * Se encarga de las validaciones de los campos de la vista de login
 * 
 * @author drodriguez
 *
 */
public class LoginFieldsValidator {
	private List<IValidationRule<String>> validationRules;

	public LoginFieldsValidator() {
		validationRules = new ArrayList<>();
		validationRules.add(new NotNullOrEmptyValidationRule());
	}

	/**
	 * Valida el campo del nombre de usuario y retorna la lista de errores
	 */
	public VoidResult validateUsername(String username) {
		List<Exception> validationErrors = FieldValidator.validate("usuario",
				true, username, validationRules);
		VoidResult result = new VoidResult();
		result.addErrors(validationErrors);
		return result;
	}

	/**
	 * Valida el campo del nombre de la contraseña y retorna la lista de errores
	 */
	public VoidResult validatePassword(String password) {
		List<Exception> validationErrors = FieldValidator.validate(
				"contraseña", false, password, validationRules);
		VoidResult result = new VoidResult();
		result.addErrors(validationErrors);
		return result;
	}
}
