package com.elfec.lecturas.gd.presenter.receivers;

import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.HandlerThread;

import com.elfec.lecturas.gd.R;
import com.elfec.lecturas.gd.model.interfaces.IDisposable;
import com.elfec.lecturas.gd.model.results.VoidResult;
import com.elfec.lecturas.gd.presenter.views.observers.IDataImportationObserver;
import com.elfec.lecturas.gd.services.DataImportationService;

/**
 * Servicio de presenter que se encarga de escuchar los mensajes del servicio de
 * importaci�n de datos su interacci�n con la vista
 * 
 * @author drodriguez
 *
 */
public class DataImportationReceiverPresenter extends BroadcastReceiver
		implements IDisposable {

	private List<IDataImportationObserver> observers;
	private Context callerContext;

	public DataImportationReceiverPresenter(
			List<IDataImportationObserver> observers, Context callerContext) {
		this.observers = observers;
		this.callerContext = callerContext;
	}

	/**
	 * Inicia a recibir mensajes
	 */
	public void startReceiving() {
		HandlerThread handlerThread = new HandlerThread(
				"DataImportationReceiverThread");
		handlerThread.start();
		callerContext.registerReceiver(this, new IntentFilter(
				DataImportationService.BROADCAST_ACTION), null, new Handler(
				handlerThread.getLooper()));
	}

	/**
	 * Deja de recibir mensajes
	 */
	public void stopReceiving() {
		callerContext.unregisterReceiver(this);
	}

	@Override
	public void dispose() {
		this.callerContext = null;
		this.observers = null;
	}

	@Override
	public void onReceive(Context context, final Intent intent) {
		if (observers != null) {// is not disposed
			int action = intent.getIntExtra("action", -1);
			switch (action) {
			case DataImportationService.IMPORTATION_STARTING: {
				importationStarting();
				break;
			}
			case DataImportationService.UPDATE_WAITING: {
				updateWaiting(intent.getIntExtra("message", 0));
				break;
			}
			case DataImportationService.IMPORTATION_FINISHED: {
				importationFinished((VoidResult) intent
						.getSerializableExtra("result"));
				break;
			}
			default:
				break;
			}
		}

	}

	/**
	 * Notifica al usuario en la UI que empez� la importaci�n de datos
	 */
	private void importationStarting() {
		for (IDataImportationObserver observer : observers) {
			observer.showImportationWaiting();
		}

	}

	/**
	 * Realiza la actualizaci�n del mensaje de espera a la interfaz
	 * 
	 * @param msgStrId
	 */
	private void updateWaiting(int msgStrId) {
		for (IDataImportationObserver observer : observers) {
			observer.updateImportationWaiting(msgStrId);
		}
	}

	/**
	 * Notifica al usuario de que el evento de importaci�n finaliz�
	 * 
	 * @param result
	 */
	private void importationFinished(VoidResult result) {
		stopReceiving();
		for (IDataImportationObserver observer : observers) {
			observer.hideWaiting();
			observer.showErrors(R.string.title_import_data_error,
					R.drawable.error_import_from_server, result.getErrors());
			if (!result.hasErrors()) {
				observer.notifySuccessfulImportation();
			}
		}
	}
}
