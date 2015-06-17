package com.elfec.lecturas.gd.model.exceptions;

/**
 * Se lanza cuando una clase que requeria inicializaci�n se utiliza en su estado
 * actual
 * 
 * @author drodriguez
 *
 */
public class InitializationException extends RuntimeException {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = -5084877272342523516L;

	@Override
	public String getMessage() {
		return "La clase no se inicializ� correctamente antes de utilizarse";
	}

}
