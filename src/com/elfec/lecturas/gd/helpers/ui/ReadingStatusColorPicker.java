package com.elfec.lecturas.gd.helpers.ui;

import com.elfec.lecturas.gd.R;
import com.elfec.lecturas.gd.model.enums.ReadingStatus;

/***
 * Clase que se encarga de proveer un color para cada estado de las lecturas
 * 
 * @author drodriguez
 *
 */
public class ReadingStatusColorPicker {
	/**
	 * Obtiene el Id del color correspondiente al estado de la lectura
	 * 
	 * @param status
	 * @return id del color resource
	 */
	public static int getResourceColorId(ReadingStatus status) {
		switch (status) {
		case PENDING:
			return android.R.color.holo_red_light;
		case READ:
			return android.R.color.holo_green_dark;
		default:
			return R.color.color_primary;
		}
	}
}
