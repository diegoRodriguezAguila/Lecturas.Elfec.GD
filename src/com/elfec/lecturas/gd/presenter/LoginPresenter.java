package com.elfec.lecturas.gd.presenter;

import com.elfec.lecturas.gd.presenter.views.ILoginView;
/**
 * Presenter para la vista de Login
 * @author drodriguez
 *
 */
public class LoginPresenter {
	
	private ILoginView view;
	
	public LoginPresenter(ILoginView view) {
		this.view = view;
	}
	
	/**
	 * Inicia el proceso de ingreso al sistema
	 */
	public void login(){
		view.showWaiting();
	}
}
