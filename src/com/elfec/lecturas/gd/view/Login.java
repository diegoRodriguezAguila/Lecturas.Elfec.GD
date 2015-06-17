package com.elfec.lecturas.gd.view;

import java.util.List;
import java.util.Locale;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.elfec.lecturas.gd.R;
import com.elfec.lecturas.gd.helpers.utils.text.MessageListFormatter;
import com.elfec.lecturas.gd.helpers.utils.ui.KeyboardHelper;
import com.elfec.lecturas.gd.presenter.LoginPresenter;
import com.elfec.lecturas.gd.presenter.views.ILoginView;


public class Login extends AppCompatActivity implements ILoginView {
	
	private LoginPresenter presenter;
	private long lastClickTime = 0;
	
	private View rootLayout;
	private TextInputLayout txtInputUsername;
	private TextInputLayout txtInputPassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		getSupportActionBar().setTitle(R.string.title_login);
		rootLayout = findViewById(R.id.root_layout);
		txtInputUsername = ((TextInputLayout)findViewById(R.id.username_text_input_layout));
		txtInputPassword = ((TextInputLayout)findViewById(R.id.password_text_input_layout));
		setVersionTitle();
		presenter = new LoginPresenter(this);
	}
	
	@Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
	
	/**
	 * Asigna la versión al titulo
	 */
	private void setVersionTitle() {
		try {
			PackageInfo pinfo = getPackageManager().getPackageInfo(getPackageName(), 0);
			((TextView)findViewById(R.id.title_app_full_name)).setText(
					String.format(getString(R.string.app_full_name),pinfo.versionName));  
		} catch (NameNotFoundException e) {
			String.format(getString(R.string.app_full_name),"desconocida");  
		}  
	}
	
	/**
	 * Evento de click del boton btn_login
	 * @param v
	 */
	public void btnLoginClick(View v){
		if (SystemClock.elapsedRealtime() - lastClickTime > 1000){
			KeyboardHelper.hideKeyboard(rootLayout);
			presenter.login();
		}
        lastClickTime = SystemClock.elapsedRealtime();
	}
	
	//#region Interface Methods

	@Override
	public String getUsername() {
		return txtInputUsername.getEditText().getText()
				.toString().trim().toUpperCase(Locale.getDefault());
	}

	@Override
	public String getPassword() {
		return txtInputPassword.getEditText().getText()
				.toString().trim();
	}
	
	@Override
	public String getIMEI() {
		return ((TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
	}
	
	@Override
	public void setUsernameFieldErrors(List<String> errors) {
		if(errors.size()>0)
			txtInputUsername.setError(MessageListFormatter.fotmatHTMLFromStringList(errors));
		else txtInputUsername.setError(null);
	}

	@Override
	public void setPasswordFieldErrors(List<String> errors) {
		if(errors.size()>0)
			txtInputPassword.setError(MessageListFormatter.fotmatHTMLFromStringList(errors));
		else txtInputPassword.setError(null);
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showWaiting() {
		Snackbar.make(findViewById(R.id.snackbar_position), R.string.title_login, Snackbar.LENGTH_LONG)
		.setAction("ACEPTAR", new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		}).show();
		/*
		new AlertDialog.Builder(this).setMessage("Hola patricio").setPositiveButton("aceptar", null).show();
		ProgressDialogPro progressDialog = new ProgressDialogPro(this);
		progressDialog.setMessage("O esperas esta vaina o gacas");
		progressDialog.show();*/
	}

	@Override
	public void updateWaiting(int strId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hideWaiting() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showLoginErrors(List<Exception> validationErrors) {
		// TODO Auto-generated method stub
		
	}
	
	//#endregion
}
