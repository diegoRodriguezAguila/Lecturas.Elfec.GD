package com.elfec.lecturas.gd.presenter;

import android.content.Context;
import android.content.Intent;

import com.elfec.lecturas.gd.R;
import com.elfec.lecturas.gd.business_logic.DataWipeManager;
import com.elfec.lecturas.gd.business_logic.RouteAssignmentManager;
import com.elfec.lecturas.gd.business_logic.SessionManager;
import com.elfec.lecturas.gd.model.RouteAssignment;
import com.elfec.lecturas.gd.model.User;
import com.elfec.lecturas.gd.model.interfaces.IDisposable;
import com.elfec.lecturas.gd.model.results.VoidResult;
import com.elfec.lecturas.gd.model.security.AES;
import com.elfec.lecturas.gd.presenter.receivers.DataExportationReceiverPresenter;
import com.elfec.lecturas.gd.presenter.receivers.DataImportationReceiverPresenter;
import com.elfec.lecturas.gd.presenter.views.IStartView;
import com.elfec.lecturas.gd.remote_data_access.connection.OracleDatabaseConnector;
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

	/**
	 * Procesa la eliminación de todos los datos
	 */
	public void processDataWipe() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				view.showWipingDataWait();
				OracleDatabaseConnector.initializeContext(view.getContext());
				VoidResult result = restoreRouteAssignments(new VoidResult());
				result = wipeAllData(result);
				view.hideWaiting();
				view.showErrors(R.string.title_wipe_all_data_errors,
						R.drawable.wipe_all_data_d, result.getErrors());
				if (!result.hasErrors()) {
					view.notifySuccessfulDataWipe();
					closeCurrentSession();
				}
				OracleDatabaseConnector.disposeInstance();
			}
		}).start();
	}

	/**
	 * Llama a los emtodos necesarios para restaurar los estados de las rutas
	 * asignadas
	 * 
	 * @param result
	 * @return result
	 */
	protected VoidResult restoreRouteAssignments(VoidResult result) {
		if (!result.hasErrors()) {
			String username = SessionManager.getLoggedInUsername();
			User user = User.findByUserName(username);
			String password = new AES().decrypt(user.generateUserKey(),
					user.getEncryptedPassword());
			result = new RouteAssignmentManager().restoreRouteAssignments(
					username, password,
					RouteAssignment.getAllImportedRouteAssignments());
		}
		return result;
	}

	/**
	 * Llama a los metodos necesarios para eliminar toda la información
	 * 
	 * @param result
	 * @return result
	 */
	protected VoidResult wipeAllData(VoidResult result) {
		if (!result.hasErrors()) {
			view.updateImportationWaiting(R.string.msg_wiping_all_data);
			result = new DataWipeManager().wipeAllData();
		}
		return result;
	}
}
