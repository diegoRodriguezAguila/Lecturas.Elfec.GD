package com.elfec.lecturas.gd.business_logic.validators;

import java.util.List;

import com.elfec.lecturas.gd.model.results.VoidResult;

/**
 * Se encarga de las validaciones de los campos de la vista de login
 * 
 * @author drodriguez
 *
 */
public class LoginFieldsValidator {
	/**
	 * Valida el campo del nombre de usuario y retorna la lista de errores
	 */
	public static VoidResult validateUsername(String username) {
		List<Exception> validationErrors = FieldValidator.validate("usuario",
				true, username, "NotNullOrEmpty");
		VoidResult result = new VoidResult();
		result.addErrors(validationErrors);
		return result;
	}

	/**
	 * Valida el campo del nombre de la contraseña y retorna la lista de errores
	 */
	public static VoidResult validatePassword(String password) {
		List<Exception> validationErrors = FieldValidator.validate(
				"contraseña", false, password, "NotNullOrEmpty");
		VoidResult result = new VoidResult();
		result.addErrors(validationErrors);
		return result;
	}
}
