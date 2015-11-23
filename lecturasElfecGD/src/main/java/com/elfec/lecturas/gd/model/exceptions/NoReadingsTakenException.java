package com.elfec.lecturas.gd.model.exceptions;

/**
 * Excepción que se lanza cuando no existen lecturas tomadas en el dispositivo
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
		return "No se tomó ninguna lectura. Asegurese de haber cargado datos al teléfono y realizado lecturas antes de intentar descargar al servidor!";
	}
}
