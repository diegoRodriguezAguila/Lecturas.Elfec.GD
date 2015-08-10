package com.elfec.lecturas.gd.model.exceptions;

/**
 * Excepci�n que se produce al fallar una validaci�n
 * 
 * @author drodriguez
 *
 */
public class ValidationException extends Exception {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 7477087352247090921L;
	private String message;
	/**
	 * Indica si la validaci�n es obligatoria o solo es una advertencia por
	 * defecto se toma como que toda validaci�n es obligatoria
	 */
	private boolean isMandatory;

	public ValidationException(String message) {
		this(message, true);
	}

	public ValidationException(String message, boolean isMandatory) {
		this.message = message;
		this.isMandatory = isMandatory;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public boolean isMandatory() {
		return isMandatory;
	}

	public void setMandatory(boolean isMandatory) {
		this.isMandatory = isMandatory;
	}

}
