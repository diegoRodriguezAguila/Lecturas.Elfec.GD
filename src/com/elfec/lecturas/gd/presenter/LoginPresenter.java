package com.elfec.lecturas.gd.presenter;

import android.os.Looper;

import com.elfec.lecturas.gd.business_logic.validators.LoginFieldsValidator;
import com.elfec.lecturas.gd.model.results.VoidResult;
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
		String username = view.getUsername();
		String password = view.getPassword();
		VoidResult usernameValidationResult = LoginFieldsValidator.validateUsername(username);
		view.setUsernameFieldErrors(usernameValidationResult.getErrors());
		VoidResult passwordValidationResult = LoginFieldsValidator.validatePassword(password);
		view.setPasswordFieldErrors(passwordValidationResult.getErrors());
		if(!usernameValidationResult.hasErrors() && 
				!passwordValidationResult.hasErrors()){
			startThreadedUserValidation(username, password);
		}
		else view.notifyErrorsInFields();
	}

	/**
	 * Inicializa las validaciones respectivas para poder logear al usuario, todo en un hilo
	 * @param username
	 * @param password
	 */
	private void startThreadedUserValidation(final String username, final String password) {
		new Thread(new Runnable() {				
			@Override
			public void run() {
				Looper.prepare();
				view.showWaiting();
				/*DataAccessResult<User> result = ElfecUserManager.validateUser(view.getUsername(), password, view.getIMEI());
				result = importUserZones(password, result);
				result = importParameterSettings(password, result);
				view.hideWaiting();
				view.clearPassword();
				if(!result.hasErrors())
				{
					view.goToLoadData();
					SessionManager.startSession(result.getResult());
					OracleDatabaseConnector.disposeInstance();
				}
				view.showLoginErrors(result.getErrors());*/
				Looper.loop();
			}
		}).start();
	}
}
