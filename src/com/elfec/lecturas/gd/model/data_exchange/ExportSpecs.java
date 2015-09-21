package com.elfec.lecturas.gd.model.data_exchange;

import java.net.ConnectException;
import java.sql.SQLException;
import java.util.List;

import com.activeandroid.Model;
import com.elfec.lecturas.gd.model.interfaces.IExportable;

/**
 * Interfaz que utiliza DataExporter para determinar la fuente de los datos que
 * se quieren exportar y el m�todo de exportaci�n
 * 
 * @author drodriguez
 *
 * @param <T>
 */
public interface ExportSpecs<T extends Model & IExportable> {
	/**
	 * Obtiene la informaci�n que se exportar�
	 * 
	 * @return Lista
	 */
	public List<T> requestDataToExport();

	/**
	 * M�todo que se llama para exportar la informaci�n
	 * 
	 * @return el numero de filas exportadas exitosamente, deber�a ser 1
	 */
	public int exportData(T data) throws ConnectException, SQLException;
}
