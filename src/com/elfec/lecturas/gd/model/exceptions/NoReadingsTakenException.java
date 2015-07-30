package com.elfec.lecturas.gd.model.exceptions;

/**
 * Excepci�n que se lanza cuando no existen lecturas tomadas en el dispositivo
 * 
 * @author drodriguez
 *
 */
public class NoReadingsTakenException extends Exception {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 1232503305239940397L;

	@Override
	public String getMessage() {
		return "No se tom� ninguna lectura. Asegurese de haber cargado datos al tel�fono y realizado lecturas antes de intentar descargar al servidor!";
	}
}
