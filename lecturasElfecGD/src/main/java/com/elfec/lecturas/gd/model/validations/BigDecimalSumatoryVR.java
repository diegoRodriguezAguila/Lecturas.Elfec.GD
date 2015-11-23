package com.elfec.lecturas.gd.model.validations;

import java.math.BigDecimal;

import com.elfec.lecturas.gd.model.exceptions.ValidationException;

/**
 * Regla de validaci�n que verifica si un conjunto de BigDecimal suman un total
 * proporcionado
 * 
 * @author drodriguez
 *
 */
public class BigDecimalSumatoryVR implements IValidationRule<BigDecimal> {
	private BigDecimal summatory;

	/**
	 * Inicializa el validador con los nombres de los campos que deber�an
	 * cumplir con la sumatoria
	 * 
	 * @param summatoryFields
	 *            por ejemplo: <i>"n�mero 1, n�mero 2 y n�mero3"</i> lo que
	 *            dar�a lugar a un mensaje de error: <i>"La <b>sumatoria de
	 *            n�meros</b> (el campo fieldName del m�todo getError) debe ser
	 *            igual a la suma de los campos: n�mero 1, n�mero 2 y
	 *            n�mero3</i>
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
				+ fieldName + "</b> deber�a sumar: <b>"
				+ summatory.toPlainString(), false);
	}
}
