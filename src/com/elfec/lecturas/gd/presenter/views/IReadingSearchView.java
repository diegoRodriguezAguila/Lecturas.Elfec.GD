package com.elfec.lecturas.gd.presenter.views;

import java.util.List;

import com.elfec.lecturas.gd.model.ReadingGeneralInfo;

/**
 * Abstracci�n de la vista de b�squeda de lecturas
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
	 * Obtiene el n�mero de serie del medidor
	 * 
	 * @return n�mero de serie del medidor
	 */
	public String getMeter();

	/**
	 * Obtiene el nus
	 * 
	 * @return
	 */
	public int getNUS();

	/**
	 * Notifica al usuario que debe al menos poner un t�rmino de b�squeda
	 */
	public void notifyAtleastOneField();

	/**
	 * Notifica al usuario que la b�squeda comenz�
	 */
	public void notifySearchStarted();

	/**
	 * Muestra al usuario la lectura encontrada
	 * 
	 * @param reading
	 */
	public void showReadingFound(ReadingGeneralInfo reading);

	/**
	 * Muestra los errores que podr�an haber ocurrido durante la b�squeda
	 * 
	 * @param errors
	 */
	public void showSearchErrors(List<Exception> errors);

}
