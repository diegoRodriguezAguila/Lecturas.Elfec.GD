package com.elfec.lecturas.gd.model.exceptions;

/**
 * Excepción que se lanza cuando la fecha actual del sistema no se encuentra
 * entre la fecha de sincronización y el rango de dias
 * 
 * @author drodriguez
 *
 */
public class SyncDateOutOfRangeException extends Exception {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 3114477526708236800L;

	@Override
	public String getMessage() {
		return "La fecha del dispositivo no se encuentra en el rango de días de uso permitido. "
				+ "Compruebe que la fecha del dispositivo es correcta!";
	}
}
