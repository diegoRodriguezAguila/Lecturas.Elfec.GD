package com.elfec.lecturas.gd.model.exceptions;

import org.joda.time.DateTime;

/**
 * Excepci�n que se lanza cuando no existe ninguna ruta asignada para el d�a
 * indicado
 * 
 * @author drodriguez
 *
 */
public class NoRoutesAsignedException extends Exception {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = -5652686360710549296L;
	private String username;

	public NoRoutesAsignedException(String username) {
		this.username = username;
	}

	@Override
	public String getMessage() {
		return "El usuario <b>"
				+ username
				+ "</b> no tiene ninguna ruta asignada para la fecha de hoy <b>"
				+ DateTime.now().toString("dd/MM/yyyy")
				+ "</b> o ya carg� las rutas que se le asignaron.";
	}
}
