package com.elfec.lecturas.gd.model.data_exchange;

import java.net.ConnectException;
import java.sql.SQLException;
import java.util.List;

import com.activeandroid.Model;

/**
 * Interfaz que utiliza DataImporter para determinar la fuente de los datos que
 * se quieren importar
 * 
 * @author drodriguez
 *
 * @param <T>
 */
public interface ImportSource<T extends Model> {
	/**
	 * Obtiene la informaci�n
	 * 
	 * @return Lista de tipo T
	 * @throws ConnectException
	 * @throws SQLException
	 */
	public List<T> requestData() throws ConnectException, SQLException;
}