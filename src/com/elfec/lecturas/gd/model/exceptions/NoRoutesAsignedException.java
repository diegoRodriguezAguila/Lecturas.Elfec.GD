package com.elfec.lecturas.gd.model.exceptions;

import org.joda.time.DateTime;

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
		return "El usuario <b>" + username
				+ "</b> no tiene asignada ninguna ruta para la fecha de hoy <b>"+DateTime.now().toString("dd/MM/yyyy")+"</b>.";
	}
}
