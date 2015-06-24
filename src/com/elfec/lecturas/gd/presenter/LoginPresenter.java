package com.elfec.lecturas.gd.presenter;

import android.os.Looper;

import com.elfec.lecturas.gd.business_logic.ElfecUserManager;
import com.elfec.lecturas.gd.business_logic.SessionManager;
import com.elfec.lecturas.gd.business_logic.validators.LoginFieldsValidator;
import com.elfec.lecturas.gd.model.User;
import com.elfec.lecturas.gd.model.interfaces.IDisposable;
import com.elfec.lecturas.gd.model.results.DataAccessResult;
import com.elfec.lecturas.gd.model.results.VoidResult;
import com.elfec.lecturas.gd.presenter.views.ILoginView;
import com.elfec.lecturas.gd.remote_data_access.connection.OracleDatabaseConnector;

/**
 * Presenter para la vista de Login
 * 
 * @author drodriguez
 *
 */
public class LoginPresenter implements IDisposable {

	private ILoginView view;

	public LoginPresenter(ILoginView view) {
		this.view = view;
		OracleDatabaseConnector.initializeContext(view.getContext());
	}

	/**
	 * Inicia el proceso de ingreso al sistema
	 */
	public void login() {
		String username = view.getUsername();
		String password = view.getPassword();
		VoidResult usernameValidationResult = LoginFieldsValidator
				.validateUsername(username);
		view.setUsernameFieldErrors(usernameValidationResult.getErrors());
		VoidResult passwordValidationResult = LoginFieldsValidator
				.validatePassword(password);
		view.setPasswordFieldErrors(passwordValidationResult.getErrors());
		if (!usernameValidationResult.hasErrors()
				&& !passwordValidationResult.hasErrors()) {
			startThreadedUserValidation(username, password);
		} else
			view.notifyErrorsInFields();
	}

	/**
	 * Inicializa las validaciones respectivas para poder logear al usuario,
	 * todo en un hilo
	 * 
	 * @param username
	 * @param password
	 */
	private void startThreadedUserValidation(final String username,
			final String password) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				Looper.prepare();
				view.showWaiting();
				DataAccessResult<User> result = new ElfecUserManager().validateUser(
						username, password, view.getIMEI());
				view.clearPassword();
				view.hideWaiting();
				if (!result.hasErrors()) {
					view.goToStart();
					SessionManager.startSession(result.getResult());
					OracleDatabaseConnector.dispose();
				}
				view.showLoginErrors(result.getErrors());
				Looper.loop();
			}
		}).start();
	}

	@Override
	public void dispose() {
		view = null;
		OracleDatabaseConnector.dispose();
	}
}
