package com.elfec.lecturas.gd.helpers.util.datetime;

import org.joda.time.DateTime;

/**
 * Helper para operaciones con dateTimes
 * 
 * @author drodriguez
 *
 */
public class DateTimeHelper {
	/**
	 * Junta un campo de fecha y de hora en un solo objeto
	 * 
	 * @param date
	 * @param time
	 * @return fecha y hora juntadas
	 */
	public static DateTime joinDateAndTime(DateTime date, DateTime time) {
		if (date == null)
			return null;
		if (time == null)
			return date.withTime(0, 0, 0, 0);
		return new DateTime(date.getYear(), date.getMonthOfYear(),
				date.getDayOfMonth(), time.getHourOfDay(),
				time.getMinuteOfHour());
	}
}
