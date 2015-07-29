package com.elfec.lecturas.gd.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.elfec.lecturas.gd.business_logic.SessionManager;
import com.elfec.lecturas.gd.model.User;
import com.elfec.lecturas.gd.model.security.AES;
import com.elfec.lecturas.gd.presenter.receivers.DataImportationReceiverPresenter;

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
	private String username;
	private String password;

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
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onDestroy() {
		intent = null;
		super.onDestroy();
	}
}
