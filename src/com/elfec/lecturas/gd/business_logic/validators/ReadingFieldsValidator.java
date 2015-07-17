package com.elfec.lecturas.gd.business_logic.validators;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.elfec.lecturas.gd.model.exceptions.ValidationException;
import com.elfec.lecturas.gd.model.results.VoidResult;
import com.elfec.lecturas.gd.model.validations.BigDecimalSumatoryVR;
import com.elfec.lecturas.gd.model.validations.IValidationRule;
import com.elfec.lecturas.gd.model.validations.NotNullObject;

/**
 * Se encarga de las validaciones de los campos de la vista de Lecturas
 * 
 * @author drodriguez
 *
 */
public class ReadingFieldsValidator {

	private static List<IValidationRule<DateTime>> dateTimeValidationRules;
	private static List<IValidationRule<BigDecimal>> bigDecimalValidationRules;

	static {
		dateTimeValidationRules = new ArrayList<>();
		dateTimeValidationRules.add(new NotNullObject<DateTime>());
		bigDecimalValidationRules = new ArrayList<>();
		bigDecimalValidationRules.add(new NotNullObject<BigDecimal>());
	}

	/**
	 * Valida cualquier campo de fecha u hora de la lectura
	 * 
	 * @param dateOrTime
	 * @param isDate
	 *            true si es una fecha, false si es una hora
	 * @return {@link VoidResult} resultado de la validación
	 */
	public static VoidResult validateDateOrTime(DateTime dateOrTime,
			boolean isDate) {
		VoidResult result = new VoidResult();
		result.addErrors(FieldValidator.validate(isDate ? "fecha de lectura"
				: "hora de lectura", false, dateOrTime,
				dateTimeValidationRules, FieldValidator.NO_VALIDATION_PARAMS));
		return result;
	}

	/**
	 * Valida que el campo de reset no sea nulo (-1)
	 * 
	 * @param resetCount
	 * @return {@link VoidResult} resultado de la validación
	 */
	public static VoidResult validateResetCount(int resetCount) {
		VoidResult result = new VoidResult();
		if (resetCount == -1)
			result.addError(new ValidationException(
					"El número de reset no puede estar vacío"));
		return result;
	}

	/**
	 * Valida la energía activa a distribuir
	 * 
	 * @param activeDistributing
	 * @param activePeak
	 * @param activeRest
	 * @param activeValley
	 * @return {@link VoidResult} resultado de la validación
	 */
	public static VoidResult validateActiveDistributing(
			BigDecimal activeDistributing, BigDecimal activePeak,
			BigDecimal activeRest, BigDecimal activeValley) {
		List<IValidationRule<BigDecimal>> validationRules = new ArrayList<>(
				bigDecimalValidationRules);
		validationRules.add(new BigDecimalSumatoryVR(
				"energía activa rate A, rate B y rate C"));
		Object[][] params = new Object[1][];
		params[0] = new Object[] { activePeak, activeRest, activeValley };
		VoidResult result = new VoidResult();
		result.addErrors(FieldValidator.validate("energía activa total", false,
				activeDistributing, bigDecimalValidationRules, params));
		return result;
	}

	/**
	 * Valida cualquier campo de la energía activa
	 * 
	 * @param value
	 * @param fieldName
	 * @return {@link VoidResult} resultado de la validación
	 */
	public static VoidResult validateActiveEnergyField(BigDecimal value,
			String fieldName) {
		VoidResult result = new VoidResult();
		result.addErrors(FieldValidator.validate(fieldName, false, value,
				bigDecimalValidationRules, FieldValidator.NO_VALIDATION_PARAMS));
		return result;
	}
}
