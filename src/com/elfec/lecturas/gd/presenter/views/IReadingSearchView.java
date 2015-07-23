package com.elfec.lecturas.gd.presenter.views;

import java.util.List;

import com.elfec.lecturas.gd.model.ReadingGeneralInfo;

/**
 * Abstracción de la vista de búsqueda de lecturas
 * 
 * @author drodriguez
 *
 */
public interface IReadingSearchView {
	/**
	 * Obtiene el numero de cuenta sin guiones
	 * 
	 * @return el numero de cuenta sin formato
	 */
	public String getAccountNumber();

	/**
	 * Obtiene el número de serie del medidor
	 * 
	 * @return número de serie del medidor
	 */
	public String getMeter();

	/**
	 * Obtiene el nus
	 * 
	 * @return
	 */
	public int getNUS();

	/**
	 * Notifica al usuario que debe al menos poner un término de búsqueda
	 */
	public void notifyAtleastOneField();

	/**
	 * Notifica al usuario que la búsqueda comenzó
	 */
	public void notifySearchStarted();

	/**
	 * Muestra al usuario la lectura encontrada
	 * 
	 * @param reading
	 */
	public void showReadingFound(ReadingGeneralInfo reading);

	/**
	 * Muestra los errores que podrían haber ocurrido durante la búsqueda
	 * 
	 * @param errors
	 */
	public void showSearchErrors(List<Exception> errors);

}
