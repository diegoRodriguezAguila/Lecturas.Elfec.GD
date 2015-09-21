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
import com.elfec.lecturas.gd.presenter.views.observers.IDataExportationObserver;
import com.elfec.lecturas.gd.services.DataExportationService;

/**
 * Servicio de presenter que se encarga de escuchar los mensajes del servicio de
 * importaci�n de datos su interacci�n con la vista
 * 
 * @author drodriguez
 *
 */
public class DataExportationReceiverPresenter extends BroadcastReceiver
		implements IDisposable {

	private List<IDataExportationObserver> observers;
	private Context callerContext;

	public DataExportationReceiverPresenter(
			List<IDataExportationObserver> observers, Context callerContext) {
		this.observers = observers;
		this.callerContext = callerContext;
	}

	/**
	 * Inicia a recibir mensajes
	 */
	public void startReceiving() {
		HandlerThread handlerThread = new HandlerThread(
				"DataExportationReceiverThread");
		handlerThread.start();
		callerContext.registerReceiver(this, new IntentFilter(
				DataExportationService.BROADCAST_ACTION), null, new Handler(
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
			case DataExportationService.EXPORTATION_STARTING: {
				exportationStarting();
				break;
			}
			case DataExportationService.UPDATE_WAITING: {
				updateWaiting(intent.getIntExtra("message", 0),
						intent.getIntExtra("total_data", -1));
				break;
			}
			case DataExportationService.UPDATE_PROGRESS: {
				updateProgress(intent.getIntExtra("data_count", 0),
						intent.getIntExtra("total_data", 0));
				break;
			}
			case DataExportationService.EXPORTATION_FINISHED: {
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
	 * Notifica al usuario en la UI que empez� la exportaci�n de datos
	 */
	private void exportationStarting() {
		for (IDataExportationObserver observer : observers) {
			observer.showExportationWaiting();
		}

	}

	/**
	 * Realiza la actualizaci�n del mensaje de espera a la interfaz
	 * 
	 * @param msgStrId
	 * @param totalData
	 */
	private void updateWaiting(int msgStrId, int totalData) {
		boolean messageOnly = totalData == -1;
		for (IDataExportationObserver observer : observers) {
			if (messageOnly)
				observer.updateExportationWaiting(msgStrId);
			else
				observer.updateExportationWaiting(msgStrId, totalData);
		}
	}

	/**
	 * Realiza la actualizaci�n de la barra de progreso de la interfaz
	 * 
	 * @param dataCount
	 * @param totalData
	 */
	private void updateProgress(int dataCount, int totalData) {
		for (IDataExportationObserver observer : observers) {
			observer.updateExportationProgress(dataCount, totalData);
		}
	}

	/**
	 * Notifica al usuario de que el evento de importaci�n finaliz�
	 * 
	 * @param result
	 */
	private void importationFinished(VoidResult result) {
		stopReceiving();
		for (IDataExportationObserver observer : observers) {
			observer.hideWaiting();
			observer.showErrors(R.string.title_export_data_error,
					R.drawable.error_export_to_server, result.getErrors());
			if (!result.hasErrors()) {
				observer.notifySuccessfulExportation();
			}
		}
	}
}
