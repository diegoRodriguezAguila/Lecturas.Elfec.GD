package com.elfec.lecturas.gd.presenter;

import android.content.Context;
import android.content.Intent;

import com.elfec.lecturas.gd.R;
import com.elfec.lecturas.gd.business_logic.SessionManager;
import com.elfec.lecturas.gd.model.interfaces.IDisposable;
import com.elfec.lecturas.gd.presenter.receivers.DataImportationReceiverPresenter;
import com.elfec.lecturas.gd.presenter.views.IStartView;
import com.elfec.lecturas.gd.services.DataImportationService;

/**
 * Presenter para la vista de Start
 * 
 * @author drodriguez
 *
 */
public class StartPresenter implements IDisposable {

	private IStartView view;

	private DataImportationReceiverPresenter dataImportationReceiver;

	public StartPresenter(IStartView view) {
		this.view = view;
		this.dataImportationReceiver = new DataImportationReceiverPresenter(
				view, view.getContext());
	}

	/**
	 * Asigna los valores respectivos a los campos de la vista
	 */
	public void setFields() {
		this.view.setCurrentUser(SessionManager.getLoggedInUsername());
	}

	/**
	 * Inicia la carga de datos del servidor
	 */
	public void startDataImportation() {
		view.showWaiting(R.string.title_import_data,
				R.string.msg_import_data_initialize,
				R.drawable.import_from_server_d);
		Context contex = view.getContext();
		contex.startService(new Intent(contex, DataImportationService.class));
		dataImportationReceiver.startReceiving();
	}

	@Override
	public void dispose() {
		view = null;
		dataImportationReceiver.dispose();
		dataImportationReceiver = null;
	}

}
