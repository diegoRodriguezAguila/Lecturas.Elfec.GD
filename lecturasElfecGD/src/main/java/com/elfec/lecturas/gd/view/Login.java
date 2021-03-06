package com.elfec.lecturas.gd.view;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.alertdialogpro.ProgressDialogPro;
import com.elfec.lecturas.gd.R;
import com.elfec.lecturas.gd.helpers.ui.ButtonClicksHelper;
import com.elfec.lecturas.gd.helpers.ui.KeyboardHelper;
import com.elfec.lecturas.gd.helpers.util.text.MessageListFormatter;
import com.elfec.lecturas.gd.presenter.LoginPresenter;
import com.elfec.lecturas.gd.presenter.views.ILoginView;
import com.elfec.lecturas.gd.view.controls.ImprovedTextInputLayout;

import java.util.List;
import java.util.Locale;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Login extends AppCompatActivity implements ILoginView {

	private LoginPresenter presenter;

	private View rootLayout;
	private ImprovedTextInputLayout txtInputUsername;
	private ImprovedTextInputLayout txtInputPassword;
	private ProgressDialogPro waitingDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		getSupportActionBar().setTitle(R.string.title_login);
		rootLayout = findViewById(R.id.root_layout);
		txtInputUsername = ((ImprovedTextInputLayout) findViewById(R.id.username_text_input_layout));
		txtInputPassword = ((ImprovedTextInputLayout) findViewById(R.id.password_text_input_layout));
		setVersionTitle();
		presenter = new LoginPresenter(this);
		// TEST PRUPOUSES
		// txtInputUsername.getEditText().setText("rcuenca");
		// txtInputPassword.getEditText().setText("E1206");
	}

	@Override
	protected void attachBaseContext(Context newBase) {
		super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		presenter.dispose();
		presenter = null;
	}

	/**
	 * Asigna la versión al titulo
	 */
	private void setVersionTitle() {
		try {
			PackageInfo pinfo = getPackageManager().getPackageInfo(
					getPackageName(), 0);
			((TextView) findViewById(R.id.title_app_full_name)).setText(String
					.format(getString(R.string.app_full_name),
							pinfo.versionName));
		} catch (NameNotFoundException e) {
			String.format(getString(R.string.app_full_name), "desconocida");
		}
	}

	/**
	 * Evento de click del boton btn_login
	 * 
	 * @param v
	 */
	public void btnLoginClick(View v) {
		if (ButtonClicksHelper.canClickButton()) {
			KeyboardHelper.hideKeyboard(rootLayout);
			presenter.login();
		}
	}

	// #region Interface Methods

	@Override
	public String getUsername() {
		return txtInputUsername.getEditText().getText().toString().trim()
				.toUpperCase(Locale.getDefault());
	}

	@Override
	public String getPassword() {
		return txtInputPassword.getEditText().getText().toString().trim();
	}

	@Override
	public String getIMEI() {
		return ((TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE))
				.getDeviceId();
	}

	@Override
	public void setUsernameFieldErrors(List<Exception> errors) {
		if (errors.size() > 0)
			txtInputUsername.setError(MessageListFormatter
					.formatHTMLFromErrors(errors));
		else
			txtInputUsername.setErrorEnabled(false);
	}

	@Override
	public void setPasswordFieldErrors(List<Exception> errors) {
		if (errors.size() > 0)
			txtInputPassword.setError(MessageListFormatter
					.formatHTMLFromErrors(errors));
		else
			txtInputPassword.setErrorEnabled(false);
	}

	@Override
	public void clearPassword() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				txtInputPassword.getEditText().setText(null);
			}
		});
	}

	@Override
	public void notifyErrorsInFields() {
		Snackbar.make(findViewById(R.id.snackbar_position),
				R.string.errors_in_fields, Snackbar.LENGTH_LONG)
				.setAction(R.string.btn_ok, new OnClickListener() {
					@Override
					public void onClick(View v) {
					}
				}).show();
	}

	@Override
	public void showWaiting() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				waitingDialog = new ProgressDialogPro(Login.this,
						R.style.AppStyle_Dialog_FlavoredMaterialLight);
				waitingDialog.setMessage(getResources().getString(
						R.string.msg_login_waiting));
				waitingDialog.setCancelable(false);
				waitingDialog.setCanceledOnTouchOutside(false);
				waitingDialog.show();
			}
		});
	}

	@Override
	public void updateWaiting(final int strId) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (waitingDialog != null)
					waitingDialog.setMessage(getResources().getString(strId));
			}
		});
	}

	@Override
	public void hideWaiting() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (waitingDialog != null)
					waitingDialog.dismiss();
			}
		});
	}

	@Override
	public void showLoginErrors(final List<Exception> validationErrors) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (validationErrors.size() > 0) {
					new AlertDialog.Builder(Login.this)
							.setTitle(R.string.title_login_errors)
							.setMessage(
									MessageListFormatter
											.formatHTMLFromErrors(validationErrors))
							.setPositiveButton(R.string.btn_ok, null).show();
				}
			}
		});
	}

	@Override
	public void goToStart() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Intent i = new Intent(Login.this, Start.class);
				i.putExtra(Start.IMEI, getIMEI());
				startActivity(i);
				overridePendingTransition(R.anim.slide_left_in,
						R.anim.slide_left_out);
			}
		});
	}

	@Override
	public Context getContext() {
		return this;
	}

	// #endregion
}
