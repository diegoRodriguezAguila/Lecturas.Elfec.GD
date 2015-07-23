package com.elfec.lecturas.gd.view;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.WordUtils;
import org.joda.time.DateTime;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alertdialogpro.ProgressDialogPro;
import com.elfec.lecturas.gd.R;
import com.elfec.lecturas.gd.helpers.ui.ButtonClicksHelper;
import com.elfec.lecturas.gd.helpers.util.text.MessageListFormatter;
import com.elfec.lecturas.gd.presenter.StartPresenter;
import com.elfec.lecturas.gd.presenter.views.IStartView;
import com.elfec.lecturas.gd.presenter.views.observers.IDataImportationObserver;
import com.elfec.lecturas.gd.view.notifiers.DataImportationNotifier;

public class Start extends AppCompatActivity implements IStartView {

	public static final String IMEI = "IMEI";

	private StartPresenter presenter;

	private ProgressDialogPro waitingDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		((TextView) findViewById(R.id.txt_device_imei)).setText(getIntent()
				.getExtras().getString(IMEI));

		presenter = new StartPresenter(this);
		presenter.setFields();
	}

	@Override
	protected void attachBaseContext(Context newBase) {
		super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
	}

	@Override
	public void onResume() {
		super.onResume();
		((TextView) findViewById(R.id.txt_date)).setText(WordUtils
				.capitalize(DateTime.now().toString("dd MMMM yyyy")));
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		presenter.dispose();
		presenter = null;
	}

	/**
	 * Evento de click del boton btn_reading_take
	 * 
	 * @param v
	 */
	public void btnReadingTakeClick(View v) {
		if (ButtonClicksHelper.canClickButton()) {
			Intent i = new Intent(Start.this, ReadingTake.class);
			startActivity(i);
			overridePendingTransition(R.anim.slide_left_in,
					R.anim.slide_left_out);
		}
	}

	public void btnImportDataClick(View v) {
		if (ButtonClicksHelper.canClickButton()) {
			new AlertDialog.Builder(this).setTitle(R.string.title_import_data)
					.setIcon(R.drawable.import_from_server_d)
					.setMessage(R.string.msg_confirm_import_data)
					.setNegativeButton(R.string.btn_cancel, null)
					.setPositiveButton(R.string.btn_ok, new OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							presenter.startDataImportation();
						}
					}).show();
		}
	}

	// #region Interface Methods

	@Override
	public void setCurrentUser(String username) {
		((TextView) findViewById(R.id.txt_username_welcome)).setText(Html
				.fromHtml("Bienvenido <b>" + username + "</b>!"));
	}

	@Override
	public void showWaiting(final int titleStrId, final int strId,
			final int iconDrawableId) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				waitingDialog = new ProgressDialogPro(Start.this,
						R.style.AppStyle_Dialog_FlavoredMaterialLight);
				waitingDialog.setMessage(getResources().getString(strId));
				waitingDialog.setCancelable(false);
				waitingDialog.setCanceledOnTouchOutside(false);
				waitingDialog.setIcon(iconDrawableId);
				waitingDialog.setTitle(titleStrId);
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
				waitingDialog = null;
			}
		});
	}

	@Override
	public void showErrors(final int titleStrId, final int iconDrawableId,
			final List<Exception> errors) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (errors.size() > 0) {
					new AlertDialog.Builder(Start.this)
							.setTitle(titleStrId)
							.setIcon(iconDrawableId)
							.setMessage(
									MessageListFormatter
											.fotmatHTMLFromErrors(errors))
							.setPositiveButton(R.string.btn_ok, null).show();
				}
			}
		});
	}

	@Override
	public void notifySuccessfullyImportation() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(Start.this,
						R.string.msg_data_imported_successfully,
						Toast.LENGTH_LONG).show();
			}
		});
	}

	@Override
	public Context getContext() {
		return this;
	}

	@Override
	public List<IDataImportationObserver> getImportationObserverViews() {
		return Arrays.asList(this, new DataImportationNotifier(this, getIntent()));
	}

	// #endregion

}
