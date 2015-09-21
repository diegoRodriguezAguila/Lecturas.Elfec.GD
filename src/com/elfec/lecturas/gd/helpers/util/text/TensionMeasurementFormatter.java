package com.elfec.lecturas.gd.helpers.util.text;

/**
 * Se encarga de interpretar los enteros de tensión y medición
 * 
 * @author drodriguez
 *
 */
public class TensionMeasurementFormatter {
	/**
	 * Formatea la tensión y medición en una cadena "tension/medición"
	 * 
	 * @param tension
	 *            si tension <=4 B, caso contrario M
	 * @param measurement
	 *            si medición <=4 B, caso contrario M
	 * @return cadena "tension/medición"
	 */
	public static String formatTensionMeasurement(int tension, int measurement) {
		return (tension <= 4 ? "B" : "M") + " / "
				+ (measurement <= 4 ? "B" : "M");
	}
}
