package com.elfec.lecturas.gd.presenter.views;

import java.util.List;

import com.elfec.lecturas.gd.presenter.views.observers.IDataImportationObserver;

import android.content.Context;

/**
 * Abstracción de la vista de Start
 * 
 * @author drodriguez
 *
 */
public interface IStartView extends IDataImportationObserver{

	/**
	 * Asigna el campo del usuario logeado actual
	 * 
	 * @param username
	 */
	public void setCurrentUser(String username);
	/**
	 * Obtiene la lista de vistas que desean ser informadas de los eventos de importación
	 * @return Lista de abstracción de vistas
	 */
	public List<IDataImportationObserver> getImportationObserverViews();	
	/**
	 * Obtiene el contexto actual de la vista
	 * 
	 * @return Context
	 */
	public Context getContext();
}
