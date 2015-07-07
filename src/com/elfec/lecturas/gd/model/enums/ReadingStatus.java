package com.elfec.lecturas.gd.model.enums;

/**
 * Describe los estados de las lecturas
 * 
 * @author drodriguez
 *
 */
public enum ReadingStatus {
	/**
	 * Estado de lectura pendiente
	 */
	PENDING("Pendiente"),
	/**
	 * Estado de lectura leída
	 */
	READ("Leída"),
	/**
	 * Estado de lectura impedida
	 */
	IMPEDED("Impedida"),
	/**
	 * Estado de lectura postergada
	 */
	POSTPONED("Postergada"),
	/**
	 * Estado de lectura puesta para reintentar
	 */
	RETRY("Reintentar");

	private String string;

	private ReadingStatus(String string) {
		this.string = string;
	}

	@Override
	public String toString() {
		return string;
	}

	/**
	 * Obtiene el estado de lectura equivalente al short provisto
	 * 
	 * @param status
	 * @return
	 */
	public static ReadingStatus get(short status) {
		return ReadingStatus.values()[status];
	}

	/**
	 * Convierte el estado de lectura actual a su short equivalente
	 * 
	 * @return short equivalente al estado
	 */
	public short toShort() {
		return (short) this.ordinal();
	}
}
