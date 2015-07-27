package com.elfec.lecturas.gd.business_logic.validators;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.elfec.lecturas.gd.model.exceptions.ValidationException;
import com.elfec.lecturas.gd.model.results.VoidResult;
import com.elfec.lecturas.gd.model.validations.BigDecimalSumatoryVR;
import com.elfec.lecturas.gd.model.validations.IValidationRule;
import com.elfec.lecturas.gd.model.validations.NotNullObjectVR;

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
		dateTimeValidationRules.add(new NotNullObjectVR<DateTime>());
		bigDecimalValidationRules = new ArrayList<>();
		bigDecimalValidationRules.add(new NotNullObjectVR<BigDecimal>());
	}

	/**
	 * Valida cualquier campo de fecha u hora de la lectura
	 * 
	 * @param dateTime
	 * @param fieldName
	 * @return {@link VoidResult} resultado de la validaci�n
	 */
	public static VoidResult validateDateTime(DateTime dateTime,
			String fieldName) {
		VoidResult result = new VoidResult();
		result.addErrors(FieldValidator.validate(fieldName, false, dateTime,
				dateTimeValidationRules));
		return result;
	}

	/**
	 * Valida que el campo de reset no sea nulo (-1)
	 * 
	 * @param resetCount
	 * @return {@link VoidResult} resultado de la validaci�n
	 */
	public static VoidResult validateResetCount(int resetCount) {
		VoidResult result = new VoidResult();
		if (resetCount == -1)
			result.addError(new ValidationException(
					"El <b>n�mero de reset</b> no puede estar vac�o"));
		return result;
	}

	/**
	 * Valida la energ�a activa a distribuir
	 * 
	 * @param activeDistributing
	 * @param activePeak
	 * @param activeRest
	 * @param activeValley
	 * @return {@link VoidResult} resultado de la validaci�n
	 */
	/**
	 * Valida la energ�a activa a distribuir
	 * 
	 * @param activeDistributing
	 * @param activePeak
	 * @param activeRest
	 * @param activeValley
	 * @param validateEnergyDistribution
	 *            true si se debe validar que la suma de los 3 componentes de la
	 *            energ�a sean igual al total de energ�a activa
	 * @return {@link VoidResult} resultado de la validaci�n
	 */
	public static VoidResult validateActiveDistributing(
			BigDecimal activeDistributing, BigDecimal activePeak,
			BigDecimal activeRest, BigDecimal activeValley,
			boolean validateEnergyDistribution) {
		List<IValidationRule<BigDecimal>> validationRules = new ArrayList<>(
				bigDecimalValidationRules);
		Object[][] params = new Object[2][];
		if (validateEnergyDistribution) {
			validationRules.add(new BigDecimalSumatoryVR(
					"energ�a activa Rate A, Rate B y Rate C"));
			params[1] = new Object[] { activePeak, activeRest, activeValley };
		}
		VoidResult result = new VoidResult();
		result.addErrors(FieldValidator.validate("energ�a activa total", false,
				activeDistributing, validationRules, params));
		return result;
	}

	/**
	 * Valida cualquier campo de la energ�a activa
	 * 
	 * @param value
	 * @param fieldName
	 * @return {@link VoidResult} resultado de la validaci�n
	 */
	public static VoidResult validateActiveEnergyField(BigDecimal value,
			String fieldName) {
		VoidResult result = new VoidResult();
		result.addErrors(FieldValidator.validate(fieldName, false, value,
				bigDecimalValidationRules));
		return result;
	}

	/**
	 * Valida la energ�a reactiva a distribuir
	 * 
	 * @param reactiveDistributing
	 * @param reactivePeak
	 * @param reactiveRest
	 * @param reactiveValley
	 * @return {@link VoidResult} resultado de la validaci�n
	 */
	public static VoidResult validateReactiveDistributing(
			BigDecimal reactiveDistributing, BigDecimal reactivePeak,
			BigDecimal reactiveRest, BigDecimal reactiveValley,
			boolean validateReactiveDistribution) {
		List<IValidationRule<BigDecimal>> validationRules = new ArrayList<>(
				bigDecimalValidationRules);
		Object[][] params = new Object[2][];
		if (validateReactiveDistribution) {
			validationRules.add(new BigDecimalSumatoryVR(
					"energ�a reactiva Rate A, Rate B y Rate C"));
			params[1] = new Object[] { reactivePeak, reactiveRest,
					reactiveValley };
		}
		VoidResult result = new VoidResult();
		result.addErrors(FieldValidator.validate("energ�a reactiva total",
				false, reactiveDistributing, validationRules, params));
		return result;
	}

	/**
	 * Valida cualquier campo de la energ�a reactiva
	 * 
	 * @param value
	 * @param fieldName
	 * @return {@link VoidResult} resultado de la validaci�n
	 */
	public static VoidResult validateReactiveEnergyField(BigDecimal value,
			String fieldName) {
		VoidResult result = new VoidResult();
		result.addErrors(FieldValidator.validate(fieldName, false, value,
				bigDecimalValidationRules));
		return result;
	}

	/**
	 * Valida cualquier campo de la demanda de energ�a
	 * 
	 * @param value
	 * @param fieldName
	 * @return {@link VoidResult} resultado de la validaci�n
	 */
	public static VoidResult validateEnergyPowerField(BigDecimal value,
			String fieldName) {
		VoidResult result = new VoidResult();
		result.addErrors(FieldValidator.validate(fieldName, false, value,
				bigDecimalValidationRules));
		return result;
	}

}
