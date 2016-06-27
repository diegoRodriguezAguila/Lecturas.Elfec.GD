package com.elfec.lecturas.gd.business_logic.validators;

import com.elfec.lecturas.gd.model.validations.IValidationRule;
import com.elfec.lecturas.gd.model.validations.ValidationRulesFactory;
import com.elfec.lecturas.gd.model.validations.ValidationsAndParams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Se encarga de validar un campo con las reglas y parametros de validaci+on
 * definidos
 * 
 * @author Diego
 *
 */
public class FieldValidator {
	/**
	 * Constante que indica que no se requieren parametros para una validación
	 */
	public static final Object[][] NO_VALIDATION_PARAMS = new Object[][] {};

	/**
	 * Realiza la validación de un campo y recopila los mensajes de error
	 * arrojados por cada una de las validaciones. Esta validación asume que no
	 * hay parámetros para ninguna de las validaciones
	 * 
	 * @param fieldName
	 * @param isMaleGender
	 * @param fieldValue
	 * @param validationRules
	 *            , la lista de reglas de validación
	 * @return
	 */
	public static <T> List<Exception> validate(String fieldName,
			boolean isMaleGender, T fieldValue,
			List<IValidationRule<T>> validationRules) {
		return validate(fieldName, isMaleGender, fieldValue, validationRules,
				NO_VALIDATION_PARAMS);
	}

	/**
	 * Realiza la validación de un campo y recopila los mensajes de error
	 * arrojados por cada una de las validaciones
	 * 
	 * @param fieldName
	 * @param isMaleGender
	 * @param fieldValue
	 * @param validationRules
	 * @param validationParams
	 *            , la lista de reglas de validación
	 * @return
	 */
	public static <T> List<Exception> validate(String fieldName,
			boolean isMaleGender, T fieldValue,
			IValidationRule<T>[] validationRules, Object[][] validationParams) {
		return validate(fieldName, isMaleGender, fieldValue,
				Arrays.asList(validationRules), validationParams);
	}

	/**
	 * Realiza la validación de un campo y recopila los mensajes de error
	 * arrojados por cada una de las validaciones
	 * 
	 * @param fieldName
	 * @param isMaleGender
	 * @param fieldValue
	 * @param validationRules
	 * @param validationParams
	 *            , la lista de reglas de validación
	 * @return
	 */
	public static <T> List<Exception> validate(String fieldName,
			boolean isMaleGender, T fieldValue,
			List<IValidationRule<T>> validationRules,
			Object[][] validationParams) {
		List<Exception> validationErrors = new ArrayList<Exception>();
		int size = validationRules.size();
		for (int i = 0; i < size; i++) {
			if (!validationRules
					.get(i)
					.isValid(
							fieldValue,
							(i < validationParams.length && validationParams[i] != null) ? validationParams[i]
									: null)) {
				validationErrors.add(validationRules.get(i).getError(fieldName,
						isMaleGender));
			}
		}
		return validationErrors;
	}

	/**
	 * Realiza la validación de un campo y recopila los mensajes de error
	 * arrojados por cada una de las validaciones
	 * 
	 * @param fieldName
	 * @param fieldIsMaleGender
	 * @param fieldValue
	 * @param validationRules
	 *            , las reglas de validación pero como cadena
	 * @return
	 */
	public static <T> List<Exception> validate(String fieldName,
			boolean fieldIsMaleGender, T fieldValue, String validationRules) {
		ValidationsAndParams<T> validationRulesAndParams = ValidationRulesFactory
				.createValidationRulesWithParams(validationRules);
		return FieldValidator.validate(fieldName, fieldIsMaleGender,
				fieldValue, validationRulesAndParams.getValidationRules(),
				validationRulesAndParams.getParams());
	}
}
