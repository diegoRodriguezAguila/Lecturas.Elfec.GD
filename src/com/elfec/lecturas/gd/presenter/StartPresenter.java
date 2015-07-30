package com.elfec.lecturas.gd.presenter;

import android.content.Context;
import android.content.Intent;

import com.elfec.lecturas.gd.business_logic.SessionManager;
import com.elfec.lecturas.gd.model.interfaces.IDisposable;
import com.elfec.lecturas.gd.presenter.receivers.DataExportationReceiverPresenter;
import com.elfec.lecturas.gd.presenter.receivers.DataImportationReceiverPresenter;
import com.elfec.lecturas.gd.presenter.views.IStartView;
import com.elfec.lecturas.gd.services.DataExportationService;
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
	private DataExportationReceiverPresenter dataExportationReceiver;

	public StartPresenter(IStartView view) {
		this.view = view;
		this.dataImportationReceiver = new DataImportationReceiverPresenter(
				view.getImportationObserverViews(), view.getContext());
		this.dataExportationReceiver = new DataExportationReceiverPresenter(
				view.getExportationObserverViews(), view.getContext());
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
		Context contex = view.getContext();
		contex.startService(new Intent(contex, DataImportationService.class));
		dataImportationReceiver.startReceiving();
	}

	/**
	 * Inicia la descarga de datos al servidor
	 */
	public void startDataExportation() {
		Context contex = view.getContext();
		contex.startService(new Intent(contex, DataExportationService.class));
		dataExportationReceiver.startReceiving();
	}

	/**
	 * Cierra la sesión actual del usuario logeado
	 */
	public void closeCurrentSession() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				String username = SessionManager.getLoggedInUsername();
				SessionManager.finishSession();
				view.notifySessionClosed(username);
			}
		}).start();
	}

	@Override
	public void dispose() {
		view = null;
		dataImportationReceiver.dispose();
		dataExportationReceiver.dispose();
		dataImportationReceiver = null;
		dataExportationReceiver = null;
	}

}
