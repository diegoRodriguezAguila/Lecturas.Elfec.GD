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

	public ValidationException(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}

}
