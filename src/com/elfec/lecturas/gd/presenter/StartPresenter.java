package com.elfec.lecturas.gd.presenter;

import com.elfec.lecturas.gd.business_logic.SessionManager;
import com.elfec.lecturas.gd.presenter.views.IStartView;

/**
 * Presenter para la vista de Start
 * 
 * @author drodriguez
 *
 */
public class StartPresenter {
	
	private IStartView view;

	public StartPresenter(IStartView view) {
		this.view = view;
	}
	
	/**
	 * Asigna los valores respectivos a los campos de la vista
	 */
	public void setFields()
	{
		this.view.setCurrentUser(SessionManager.getLoggedInUsername());
	}
	
}
