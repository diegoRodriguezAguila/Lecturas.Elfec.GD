package com.elfec.lecturas.gd.model.utils;

import java.math.BigDecimal;

import org.joda.time.DateTime;

/**
 * Clase con utilidades de conversión a SQL
 * 
 * @author drodriguez
 *
 */
public class SQLUtils {
	/**
	 * Convierte un bigdecimal a una cadena para ser usada en sql si el
	 * parametro es null devuelve la cadena "NULL"
	 * 
	 * @param bigDecimal
	 * @return valor SQL
	 */
	public static String bigDecimalToSQLString(BigDecimal bigDecimal) {
		return bigDecimal == null ? "NULL" : bigDecimal.toPlainString();
	}

	/**
	 * Convierte un bigdecimal a una cadena para ser usada en sql si el
	 * parametro es null devuelve la cadena "NULL"
	 * 
	 * @param bigDecimal
	 * @return valor SQL
	 */
	public static String dateTimeToSQLString(DateTime dateTime) {
		return dateTime == null ? "NULL" : ("'"
				+ dateTime.toString("dd/MM/yyyy HH:mm:ss") + "'");
	}

	/**
	 * Convierte un entero a una cadena para ser usada en sql si el parametro es
	 * -1 devuelve la cadena "NULL"
	 * 
	 * @param number
	 * @return valor SQL
	 */
	public static String intToSQLString(int number) {
		return (number == -1 ? "NULL" : ("" + number));
	}
}
