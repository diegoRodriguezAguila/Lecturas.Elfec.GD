package com.elfec.lecturas.gd.model.interfaces;

import com.elfec.lecturas.gd.model.enums.ExportStatus;

/**
 * Interfaz que identifica a las clases que son exportables
 * 
 * @author drodriguez
 *
 */
public interface IExportable {
	/**
	 * Asigna el estado de exportaci�n
	 * 
	 * @param exportStatus
	 */
	public void setExportStatus(ExportStatus exportStatus);

	/**
	 * Obtiene una cadena describiendo el registro que se exporta, se utiliza
	 * para los errores
	 * 
	 * @return
	 */
	public String getRegistryResume();
}
