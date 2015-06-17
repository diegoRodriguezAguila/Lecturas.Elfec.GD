package com.elfec.lecturas.gd.business_logic.validators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.elfec.lecturas.gd.model.validations.IValidationRule;
import com.elfec.lecturas.gd.model.validations.ValidationRulesFactory;
import com.elfec.lecturas.gd.model.validations.ValidationsAndParams;

/**
 * Se encarga de validar un campo con las reglas y parametros de validaci�n
 * definidos
 * 
 * @author Diego
 *
 */
public class FieldValidator {
	/**
	 * Realiza la validaci�n de un campo y recopila los mensajes de error
	 * arrojados por cada una de las validaciones
	 * 
	 * @param fieldName
	 * @param isMaleGender
	 * @param fieldValue
	 * @param validationRules
	 * @param validationParams
	 *            , la lista de reglas de validaci�n
	 * @return
	 */
	public static <T> List<Exception> validate(String fieldName,
			boolean isMaleGender, T fieldValue,
			IValidationRule<T>[] validationRules, String[] validationParams) {
		return validate(fieldName, isMaleGender, fieldValue,
				Arrays.asList(validationRules), validationParams);
	}

	/**
	 * Realiza la validaci�n de un campo y recopila los mensajes de error
	 * arrojados por cada una de las validaciones
	 * 
	 * @param fieldName
	 * @param isMaleGender
	 * @param fieldValue
	 * @param validationRules
	 * @param validationParams
	 *            , la lista de reglas de validaci�n
	 * @return
	 */
	public static <T> List<Exception> validate(String fieldName,
			boolean isMaleGender, T fieldValue,
			List<IValidationRule<T>> validationRules, String[] validationParams) {
		List<Exception> validationErrors = new ArrayList<Exception>();
		int size = validationRules.size();
		for (int i = 0; i < size; i++) {
			if (!validationRules.get(i).isValid(
					fieldValue,
					validationParams[i] != null ? validationParams[i]
							.split(",") : null)) {
				validationErrors.add(validationRules.get(i).getError(fieldName,
						isMaleGender));
			}
		}
		return validationErrors;
	}

	/**
	 * Realiza la validaci�n de un campo y recopila los mensajes de error
	 * arrojados por cada una de las validaciones
	 * 
	 * @param fieldName
	 * @param fieldIsMaleGender
	 * @param fieldValue
	 * @param validationRules
	 *            , las reglas de validaci�n pero como cadena
	 * @return
	 */
	public static List<Exception> validate(String fieldName,
			boolean fieldIsMaleGender, String fieldValue, String validationRules) {
		ValidationsAndParams<String> validationRulesAndParams = ValidationRulesFactory
				.createValidationRulesWithParams(validationRules);
		return FieldValidator.validate(fieldName, fieldIsMaleGender,
				fieldValue, validationRulesAndParams.getValidationRules(),
				validationRulesAndParams.getParams());
	}
}
