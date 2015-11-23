package com.elfec.lecturas.gd.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alertdialogpro.ProgressDialogPro;
import com.elfec.lecturas.gd.R;
import com.elfec.lecturas.gd.helpers.ui.ButtonClicksHelper;
import com.elfec.lecturas.gd.helpers.util.text.MessageListFormatter;
import com.elfec.lecturas.gd.presenter.StartPresenter;
import com.elfec.lecturas.gd.presenter.views.IStartView;
import com.elfec.lecturas.gd.presenter.views.observers.IDataExportationObserver;
import com.elfec.lecturas.gd.presenter.views.observers.IDataImportationObserver;
import com.elfec.lecturas.gd.view.listeners.WipeConfirmationListener;
import com.elfec.lecturas.gd.view.notifiers.DataExportationNotifier;
import com.elfec.lecturas.gd.view.notifiers.DataImportationNotifier;
import com.elfec.lecturas.gd.view.view_services.WipeAllDataDialogService;

import org.apache.commons.lang3.text.WordUtils;
import org.joda.time.DateTime;

import java.util.Arrays;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

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
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int idItem = item.getItemId();
		switch (idItem) {
		case (R.id.menu_wipe_all_data): {
			new WipeAllDataDialogService(this, new WipeConfirmationListener() {
				@Override
				public void onWipeConfirmed() {
					presenter.processDataWipe();
				}
			}).show();
			return true;
		}
		default: {
			return true;
		}
		}
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
		presenter.closeCurrentSession();
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

	public void btnExportDataClick(View v) {
		if (ButtonClicksHelper.canClickButton()) {
			new AlertDialog.Builder(this).setTitle(R.string.title_export_data)
					.setIcon(R.drawable.export_to_server_d)
					.setMessage(R.string.msg_confirm_export_data)
					.setNegativeButton(R.string.btn_cancel, null)
					.setPositiveButton(R.string.btn_ok, new OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							presenter.startDataExportation();
						}
					}).show();
		}
	}

	/**
	 * Notifica al usuario un mensaje
	 * 
	 * @param msgId
	 */
	public void notifyUser(final int msgId) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(Start.this, msgId, Toast.LENGTH_LONG).show();
			}
		});
	}

	// #region Interface Methods

	@Override
	public void setCurrentUser(String username) {
		((TextView) findViewById(R.id.txt_username_welcome)).setText(Html
				.fromHtml("Bienvenido <b>" + username + "</b>!"));
	}

	@Override
	public void showImportationWaiting() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				waitingDialog = new ProgressDialogPro(Start.this,
						R.style.AppStyle_Dialog_FlavoredMaterialLight);
				waitingDialog.setMessage(getResources().getString(
						R.string.msg_import_data_initialize));
				waitingDialog.setCancelable(false);
				waitingDialog.setCanceledOnTouchOutside(false);
				waitingDialog.setIcon(R.drawable.import_from_server_d);
				waitingDialog.setTitle(R.string.title_import_data);
				waitingDialog.setCanceledOnTouchOutside(false);
				waitingDialog.show();
			}
		});
	}

	@Override
	public void updateImportationWaiting(final int strId) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (waitingDialog != null)
					waitingDialog.setMessage(getResources().getString(strId));
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
											.formatHTMLFromErrors(errors))
							.setPositiveButton(R.string.btn_ok, null).show();
				}
			}
		});
	}

	@Override
	public void notifySuccessfulImportation() {
		notifyUser(R.string.msg_data_imported_successfully);
	}

	@Override
	public Context getContext() {
		return this;
	}

	@Override
	public List<IDataImportationObserver> getImportationObserverViews() {
		return Arrays.asList(this, new DataImportationNotifier(this,
				getIntent()));
	}

	@Override
	public void notifySessionClosed(final String username) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(
						Start.this,
						Html.fromHtml(String.format(
								getString(R.string.msg_session_closed),
								username)), Toast.LENGTH_LONG).show();
				finish();// go back to the previous Activity
				overridePendingTransition(R.anim.slide_right_in,
						R.anim.slide_right_out);
			}
		});
	}

	@Override
	public List<IDataExportationObserver> getExportationObserverViews() {
		return Arrays.asList(this, new DataExportationNotifier(this,
				getIntent()));
	}

	@Override
	public void showExportationWaiting() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				waitingDialog = new ProgressDialogPro(Start.this,
						R.style.AppStyle_Dialog_FlavoredMaterialLight);
				waitingDialog.setMessage(getResources().getText(
						R.string.msg_export_data_initialize));
				waitingDialog.setCancelable(false);
				waitingDialog.setIndeterminate(true);
				waitingDialog.setIcon(R.drawable.export_to_server_d);
				waitingDialog.setTitle(R.string.title_export_data);
				waitingDialog
						.setProgressStyle(ProgressDialogPro.STYLE_HORIZONTAL);
				waitingDialog.setCanceledOnTouchOutside(false);
				waitingDialog.show();
			}
		});
	}

	@Override
	public void updateExportationWaiting(final int strId, final int totalData) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (waitingDialog != null) {
					waitingDialog.setIndeterminate(false);
					waitingDialog.setMax(totalData);
					waitingDialog.setMessage(getResources().getString(strId));
				}
			}
		});
	}

	@Override
	public void updateExportationWaiting(final int strId) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (waitingDialog != null) {
					waitingDialog.setMax(0);
					waitingDialog.setIndeterminate(true);
					waitingDialog.setMessage(getResources().getString(strId));
				}
			}
		});
	}

	@Override
	public void updateExportationProgress(final int dataCount, int totalData) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (waitingDialog != null)
					waitingDialog.setProgress(dataCount);
			}
		});
	}

	@Override
	public void notifySuccessfulExportation() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(Start.this,
						R.string.msg_data_exported_successfully,
						Toast.LENGTH_LONG).show();
				presenter.closeCurrentSession();
			}
		});
	}

	/**
	 * Esconde el mensaje de espera
	 */
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
	public void showWipingDataWait() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				waitingDialog = new ProgressDialogPro(Start.this,
						R.style.AppStyle_Dialog_FlavoredMaterialLight);
				waitingDialog.setMessage(getResources().getString(
						R.string.msg_restoring_route_assignments));
				waitingDialog.setCancelable(false);
				waitingDialog.setIcon(R.drawable.wipe_all_data_d);
				waitingDialog.setTitle(R.string.title_wipe_all_data);
				waitingDialog.setCanceledOnTouchOutside(false);
				waitingDialog.show();
			}
		});
	}

	@Override
	public void notifySuccessfulDataWipe() {
		notifyUser(R.string.msg_all_data_wiped_successfully);
	}

	// #endregion

}
