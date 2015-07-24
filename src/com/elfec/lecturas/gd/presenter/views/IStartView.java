package com.elfec.lecturas.gd.presenter.views;

import java.util.List;

import android.content.Context;

import com.elfec.lecturas.gd.presenter.views.observers.IDataImportationObserver;

/**
 * Abstracci�n de la vista de Start
 * 
 * @author drodriguez
 *
 */
public interface IStartView extends IDataImportationObserver {

	/**
	 * Asigna el campo del usuario logeado actual
	 * 
	 * @param username
	 */
	public void setCurrentUser(String username);

	/**
	 * Obtiene la lista de vistas que desean ser informadas de los eventos de
	 * importaci�n
	 * 
	 * @return Lista de abstracci�n de vistas
	 */
	public List<IDataImportationObserver> getImportationObserverViews();

	/**
	 * Obtiene el contexto actual de la vista
	 * 
	 * @return Context
	 */
	public Context getContext();

	/**
	 * Notifica al usuario que se cerr� la sesi�n del usuario
	 * 
	 * @param username
	 */
	public void notifySessionClosed(String username);
}
