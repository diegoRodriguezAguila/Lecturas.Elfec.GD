package com.elfec.lecturas.gd.model.exceptions;

/**
 * Excepción que se lanza cuando ya no existe información pendiente para
 * exportar
 * 
 * @author drodriguez
 *
 */
public class NoExportPendingDataException extends Exception {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = -6073911036665973850L;

	@Override
	public String getMessage() {
		return "No existe información pendiente para ser descargar al servidor!";
	}
}
