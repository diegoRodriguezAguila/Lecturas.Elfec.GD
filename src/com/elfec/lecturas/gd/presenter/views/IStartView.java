package com.elfec.lecturas.gd.presenter.views;

import java.util.List;

import android.content.Context;

import com.elfec.lecturas.gd.presenter.views.observers.IDataExportationObserver;
import com.elfec.lecturas.gd.presenter.views.observers.IDataImportationObserver;

/**
 * Abstracción de la vista de Start
 * 
 * @author drodriguez
 *
 */
public interface IStartView extends IDataImportationObserver,
		IDataExportationObserver {

	/**
	 * Asigna el campo del usuario logeado actual
	 * 
	 * @param username
	 */
	public void setCurrentUser(String username);

	/**
	 * Obtiene la lista de vistas que desean ser informadas de los eventos de
	 * importación
	 * 
	 * @return Lista de abstracción de vistas
	 */
	public List<IDataImportationObserver> getImportationObserverViews();

	/**
	 * Obtiene la lista de vistas que desean ser informadas de los eventos de
	 * exportación
	 * 
	 * @return Lista de abstracción de vistas
	 */
	public List<IDataExportationObserver> getExportationObserverViews();

	/**
	 * Obtiene el contexto actual de la vista
	 * 
	 * @return Context
	 */
	public Context getContext();

	/**
	 * Notifica al usuario que se cerró la sesión del usuario
	 * 
	 * @param username
	 */
	public void notifySessionClosed(String username);

	/**
	 * Indica al usuario que debe esperar a que se eliminen los datos
	 */
	public void showWipingDataWait();

	/**
	 * Informa al usuario que se eliminaron los datos exitosamente
	 */
	public void notifySuccessfulDataWipe();
}
