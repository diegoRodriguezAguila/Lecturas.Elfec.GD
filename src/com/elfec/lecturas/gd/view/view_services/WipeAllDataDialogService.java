package com.elfec.lecturas.gd.view.view_services;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.support.v7.app.AlertDialog;

import com.elfec.lecturas.gd.R;
import com.elfec.lecturas.gd.presenter.WipeAllDataPresenter;
import com.elfec.lecturas.gd.presenter.views.IWipeAllDataView;
import com.elfec.lecturas.gd.view.listeners.WipeConfirmationListener;

/**
 * Un servicio de dialogo para eliminar toda la información local de la
 * aplicación en el dispositivo
 * 
 * @author drodriguez
 *
 */
public class WipeAllDataDialogService implements IWipeAllDataView {

	private AlertDialog.Builder builder;
	private WipeAllDataPresenter presenter;
	private WipeConfirmationListener wipeConfirmationListener;

	public WipeAllDataDialogService(Context context,
			WipeConfirmationListener wipeConfirmationListener) {
		presenter = new WipeAllDataPresenter(this);
		this.wipeConfirmationListener = wipeConfirmationListener;
		builder = new AlertDialog.Builder(context).setIcon(R.drawable.wipe_all_data_d)
				.setTitle(R.string.title_wipe_all_data);
		presenter.defineDialogType();
	}

	@Override
	public void initializeWipeConfirmDialog() {
		builder.setMessage(R.string.msg_wipe_all_data_confirm)
				.setPositiveButton(R.string.btn_confirm, new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (wipeConfirmationListener != null)
							wipeConfirmationListener.onWipeConfirmed();
					}
				}).setNegativeButton(R.string.btn_cancel, null);
	}

	@Override
	public void initializeCannotWipeDialog() {
		builder.setMessage(R.string.msg_cannot_wipe_all_data)
				.setPositiveButton(R.string.btn_ok, null);
	}

	/**
	 * Muestra el dialogo
	 */
	public void show() {
		builder.show();
	}
}
