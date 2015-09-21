package com.elfec.lecturas.gd.model.exceptions;

/**
 * Excepci�n que se lanza cuando el n�mero de medidores cargados no coincide con
 * el de lecturas
 * 
 * @author drodriguez
 *
 */
public class ReadingAndMeterUnmatchException extends Exception {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 4947676531087071162L;
	private int readingsCount;
	private int metersCount;

	public ReadingAndMeterUnmatchException(int readingsCount, int metersCount) {
		super();
		this.readingsCount = readingsCount;
		this.metersCount = metersCount;
	}

	@Override
	public String getMessage() {
		return "La cantidad de informaci�n de lecturas y de medidores no coinciden (Lecturas: <b>"
				+ readingsCount
				+ "</b>, Medidores: <b>"
				+ metersCount
				+ "</b>). Esto puede deberse a una inconsistencia en la informaci�n del servidor, porfavor cont�ctese con un administrador.";
	}

}
