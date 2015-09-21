package com.elfec.lecturas.gd.model.enums;

import java.util.ArrayList;
import java.util.List;

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
	PENDING("Pendiente", "Pendientes"),
	/**
	 * Estado de lectura leída
	 */
	READ("Leída", "Leídas"),
	/**
	 * Estado de lectura impedida
	 */
	IMPEDED("Impedida", "Impedidas"),
	/**
	 * Estado de lectura postergada
	 */
	POSTPONED("Postergada", "Postergadas"),
	/**
	 * Estado de lectura puesta para reintentar
	 */
	RETRY("Reintentar", "Reintentar"),
	/**
	 * Estado de lectura en edición
	 */
	EDITING("Editando", null);

	private String string;
	private String pluralString;

	private ReadingStatus(String string, String pluralString) {
		this.string = string;
		this.pluralString = pluralString;
	}

	@Override
	public String toString() {
		return string;
	}

	public String toPluralString() {
		return pluralString;
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

	/**
	 * Obtiene una lista de estados de lecturas como cadenas en plurales. El
	 * primer elemento de la lista es "Todas"
	 * 
	 * @return lsita de cadenas
	 */
	public static List<String> getReadingStatusPlurals() {
		ReadingStatus[] readingStatuses = ReadingStatus.values();
		List<String> readingStatusesStr = new ArrayList<String>(
				readingStatuses.length + 1);
		readingStatusesStr.add("Todas");
		for (ReadingStatus status : readingStatuses) {
			if (status.pluralString != null)
				readingStatusesStr.add(status.pluralString);
		}
		return readingStatusesStr;
	}
}
