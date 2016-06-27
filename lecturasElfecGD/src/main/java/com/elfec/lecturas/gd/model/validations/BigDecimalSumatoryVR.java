package com.elfec.lecturas.gd.model.validations;

import com.elfec.lecturas.gd.model.exceptions.ValidationException;

import java.math.BigDecimal;

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
