package com.elfec.lecturas.gd.model.exceptions;

import org.joda.time.DateTime;

/**
 * Excepción que se lanza cuando la fecha del sistema no coincide con la del
 * servidor
 * 
 * @author drodriguez
 *
 */
public class SysdateUnsyncFromServerException extends Exception {
	/**
	 * Serial
	 */
	private static final long serialVersionUID = 4423337587140272844L;

	private DateTime serverDate;

	/**
	 * Construye una excepción con la fecha del servidor provista que será la
	 * que se muestre en el mensaje
	 * 
	 * @param serverDate
	 */
	public SysdateUnsyncFromServerException(DateTime serverDate) {
		this.serverDate = serverDate;
	}

	@Override
	public String getMessage() {
		return "La fecha y hora del dispositivo es diferente a la del servidor, para poder proceder es obligatorio que las "
				+ "fechas del dispositivo y el servidor coincidan.<br/>"
				+ "La fecha del servidor es: <b>"
				+ serverDate.toString("dd/MM/yyyy HH:mm") + "</b>";
	}

}
