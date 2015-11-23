package com.elfec.lecturas.gd.model.validations;

import java.math.BigDecimal;

import com.elfec.lecturas.gd.model.exceptions.ValidationException;

/**
 * Regla de validación que verifica si un conjunto de BigDecimal suman un total
 * proporcionado
 * 
 * @author drodriguez
 *
 */
public class BigDecimalSumatoryVR implements IValidationRule<BigDecimal> {
	private BigDecimal summatory;

	/**
	 * Inicializa el validador con los nombres de los campos que deberían
	 * cumplir con la sumatoria
	 * 
	 * @param summatoryFields
	 *            por ejemplo: <i>"número 1, número 2 y número3"</i> lo que
	 *            daría lugar a un mensaje de error: <i>"La <b>sumatoria de
	 *            números</b> (el campo fieldName del método getError) debe ser
	 *            igual a la suma de los campos: número 1, número 2 y
	 *            número3</i>
	 */
	public BigDecimalSumatoryVR() {
		this.summatory = BigDecimal.ZERO;
	}

	@Override
	public boolean isValid(BigDecimal total, Object... params) {
		if (total == null)
			total = BigDecimal.ONE.negate();
		for (Object sum : params) {
			if (sum != null && sum instanceof BigDecimal)
				summatory = summatory.add((BigDecimal) sum);
		}
		return total.equals(summatory);
	}

	@Override
	public ValidationException getError(String fieldName, boolean isMaleGender) {
		return new ValidationException((isMaleGender ? "El " : "La ") + "<b>"
				+ fieldName + "</b> debería sumar: <b>"
				+ summatory.toPlainString(), false);
	}
}
