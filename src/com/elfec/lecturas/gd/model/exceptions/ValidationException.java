package com.elfec.lecturas.gd.model.exceptions;

/**
 * Excepción que se produce al fallar una validación
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

	public ValidationException(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}

}
