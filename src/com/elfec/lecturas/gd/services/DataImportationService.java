package com.elfec.lecturas.gd.services;

import java.util.List;

import com.elfec.lecturas.gd.R;
import com.elfec.lecturas.gd.business_logic.OrdenativeManager;
import com.elfec.lecturas.gd.business_logic.RouteAssignmentManager;
import com.elfec.lecturas.gd.business_logic.SessionManager;
import com.elfec.lecturas.gd.model.RouteAssignment;
import com.elfec.lecturas.gd.model.User;
import com.elfec.lecturas.gd.model.events.DataImportListener;
import com.elfec.lecturas.gd.model.results.TypedResult;
import com.elfec.lecturas.gd.model.results.VoidResult;
import com.elfec.lecturas.gd.model.security.AES;
import com.elfec.lecturas.gd.presenter.receivers.DataImportationReceiverPresenter;
import com.elfec.lecturas.gd.remote_data_access.connection.OracleDatabaseConnector;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Servicio android que corre en segundo plano para realizar la importación de
 * datos del servidor, notifica eventos de UI a
 * {@link DataImportationReceiverPresenter}
 * 
 * @author drodriguez
 *
 */
public class DataImportationService extends Service {

	/**
	 * Acción del broadcast
	 */
	public static final String BROADCAST_ACTION = "com.elfec.lecturas.gd.importEvent";
	/**
	 * Mensaje de inicio de importación
	 */
	public static final int IMPORTATION_STARTING = 1;
	/**
	 * Mensaje de actualización de un paso de importación
	 */
	public static final int UPDATE_WAITING = 2;
	/**
	 * Mensajde de fin de la importación
	 */
	public static final int IMPORTATION_FINISHED = 3;

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
				sendImportationAction(IMPORTATION_STARTING);
				OracleDatabaseConnector
						.initializeContext(DataImportationService.this);
				DataImportListener dataImportListener = new DataImportListener() {
					@Override
					public void onImportInitialized() {
						sendImportationAction(UPDATE_WAITING, strMsgId);
					}

					@Override
					public void onImportFinished(VoidResult result) {
					}
				};

				strMsgId = R.string.msg_importing_ordenatives;
				VoidResult result = new OrdenativeManager().importOrdenatives(
						username, password, dataImportListener);

				if (!result.hasErrors()) {
					strMsgId = R.string.msg_importing_route_assignments;
					TypedResult<List<RouteAssignment>> resultRouteAssignment = new RouteAssignmentManager()
							.importUserRouteAssignments(username, password,
									dataImportListener);
					result = resultRouteAssignment;
				}
				OracleDatabaseConnector.dispose();
				sendImportationFinished(result);
				DataImportationService.this.stopSelf();
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
	private void sendImportationAction(int action) {
		sendImportationAction(action, -1);
	}

	/**
	 * Envia un mensaje al broadcaster
	 * 
	 * @param action
	 *            acción
	 * @param strMsgId
	 *            stringId del mensaje
	 */
	private void sendImportationAction(int action, int strMsgId) {
		intent.putExtra("action", action);
		if (strMsgId != -1)
			intent.putExtra("message", strMsgId);
		sendBroadcast(intent);
	}

	/**
	 * Envia un mensaje al broadcaster de que se finalizó de realizar la
	 * importación o esta se interrumpió
	 * 
	 * @param result
	 */
	private void sendImportationFinished(VoidResult result) {
		intent.putExtra("action", IMPORTATION_FINISHED);
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
