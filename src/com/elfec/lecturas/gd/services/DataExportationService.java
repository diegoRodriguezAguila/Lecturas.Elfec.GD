package com.elfec.lecturas.gd.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.elfec.lecturas.gd.R;
import com.elfec.lecturas.gd.business_logic.DataExportationManager;
import com.elfec.lecturas.gd.business_logic.DataWipeManager;
import com.elfec.lecturas.gd.business_logic.ReadingOrdenativeManager;
import com.elfec.lecturas.gd.business_logic.ReadingTakenManager;
import com.elfec.lecturas.gd.business_logic.RouteAssignmentManager;
import com.elfec.lecturas.gd.business_logic.SessionManager;
import com.elfec.lecturas.gd.model.RouteAssignment;
import com.elfec.lecturas.gd.model.User;
import com.elfec.lecturas.gd.model.events.DataExportListener;
import com.elfec.lecturas.gd.model.results.VoidResult;
import com.elfec.lecturas.gd.model.security.AES;
import com.elfec.lecturas.gd.presenter.receivers.DataImportationReceiverPresenter;
import com.elfec.lecturas.gd.remote_data_access.connection.OracleDatabaseConnector;

/**
 * Servicio android que corre en segundo plano para realizar la exportación de
 * datos al servidor, notifica eventos de UI a
 * {@link DataImportationReceiverPresenter}
 * 
 * @author drodriguez
 *
 */
public class DataExportationService extends Service {
	/**
	 * Acción del broadcast
	 */
	public static final String BROADCAST_ACTION = "com.elfec.lecturas.gd.exportEvent";
	/**
	 * Mensaje de inicio de exportación
	 */
	public static final int EXPORTATION_STARTING = 1;
	/**
	 * Mensaje de actualización de un paso de exportación
	 */
	public static final int UPDATE_WAITING = 2;
	/**
	 * Mensaje de actualización del progreso de exportación
	 */
	public static final int UPDATE_PROGRESS = 3;
	/**
	 * Mensajde de fin de la exportación
	 */
	public static final int EXPORTATION_FINISHED = 4;

	private String username;
	private String password;
	private int strMsgId;

	private Intent intent;

	@Override
	public void onCreate() {
		super.onCreate();
		username = SessionManager.getLoggedInUsername();
		User user = User.findByUserName(username);
		password = new AES().decrypt(user.generateUserKey(),
				user.getEncryptedPassword());
		intent = new Intent(BROADCAST_ACTION);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				sendExportationMessage(EXPORTATION_STARTING);
				OracleDatabaseConnector
						.initializeContext(DataExportationService.this);
				DataExportListener exportListener = new DataExportListener() {
					@Override
					public void onExportInitialized(int totalElements) {
						sendExportationMessage(UPDATE_WAITING, strMsgId,
								totalElements);
					}

					@Override
					public void onExporting(int exportCount, int totalElements) {
						sendExportationProgress(exportCount, totalElements);
					}

					@Override
					public void onExportFinalized() {
					}
				};
				VoidResult result = new DataExportationManager()
						.validateExportation();
				if (!result.hasErrors()) {
					strMsgId = R.string.msg_exporting_readings_taken;
					result = ReadingTakenManager.exportAllReadingsTaken(
							username, password, exportListener);
				}
				if (!result.hasErrors()) {
					strMsgId = R.string.msg_exporting_reading_ordenatives;
					result = new ReadingOrdenativeManager()
							.exportAllReadingOrdenatives(username, password,
									exportListener);
				}
				if (!result.hasErrors()) {// FINALIZACION
					sendExportationMessage(UPDATE_WAITING,
							R.string.msg_finishing_export);
					result = new RouteAssignmentManager()
							.setRoutesSuccessfullyExported(username, password,
									RouteAssignment
											.getAllImportedRouteAssignments());
				}
				if (!result.hasErrors()) {// wipe data
					sendExportationMessage(UPDATE_WAITING,
							R.string.msg_wiping_all_data);
					result = new DataWipeManager().wipeAllData();
				}
				OracleDatabaseConnector.dispose();
				sendExportationFinished(result);
				stopSelf();
			}
		}).start();
		return Service.START_NOT_STICKY;
	}

	/**
	 * Envia un mensaje al broadcaster
	 * 
	 * @param action
	 *            acción
	 */
	private void sendExportationMessage(int action) {
		sendExportationMessage(action, -1);
	}

	/**
	 * Envia un mensaje al broadcaster
	 * 
	 * @param action
	 *            acción
	 */
	private void sendExportationMessage(int action, int strMsgId) {
		sendExportationMessage(action, strMsgId, -1);
	}

	/**
	 * Envia un mensaje al broadcaster
	 * 
	 * @param action
	 *            acción
	 * @param strMsgId
	 *            stringId del mensaje
	 * @param totalData
	 *            numero total de datos a importar -1 si solo es un mensaje
	 *            informativo
	 */
	private void sendExportationMessage(int action, int strMsgId, int totalData) {
		intent.putExtra("action", action);
		if (strMsgId != -1)
			intent.putExtra("message", strMsgId);
		if (strMsgId != -1)
			intent.putExtra("total_data", totalData);
		sendBroadcast(intent);
	}

	/**
	 * Enviaun mensaje al broadcaster de actualizar el progreso de exportación
	 * 
	 * @param count
	 * @param total
	 */
	private void sendExportationProgress(int count, int total) {
		intent.putExtra("action", UPDATE_PROGRESS);
		intent.putExtra("data_count", count);
		intent.putExtra("total_data", total);
	}

	/**
	 * Envia un mensaje al broadcaster de que se finalizó de realizar la
	 * exportación o esta se interrumpió
	 * 
	 * @param result
	 */
	private void sendExportationFinished(VoidResult result) {
		intent.putExtra("action", EXPORTATION_FINISHED);
		intent.putExtra("result", result);
		sendBroadcast(intent);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onDestroy() {
		intent = null;
		super.onDestroy();
	}
}
