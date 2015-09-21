package com.elfec.lecturas.gd.helpers.util.text;

/**
 * Se encarga de interpretar los enteros de tensi�n y medici�n
 * 
 * @author drodriguez
 *
 */
public class TensionMeasurementFormatter {
	/**
	 * Formatea la tensi�n y medici�n en una cadena "tension/medici�n"
	 * 
	 * @param tension
	 *            si tension <=4 B, caso contrario M
	 * @param measurement
	 *            si medici�n <=4 B, caso contrario M
	 * @return cadena "tension/medici�n"
	 */
	public static String formatTensionMeasurement(int tension, int measurement) {
		return (tension <= 4 ? "B" : "M") + " / "
				+ (measurement <= 4 ? "B" : "M");
	}
}
