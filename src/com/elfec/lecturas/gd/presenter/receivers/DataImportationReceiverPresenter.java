package com.elfec.lecturas.gd.presenter.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.HandlerThread;

import com.elfec.lecturas.gd.R;
import com.elfec.lecturas.gd.model.interfaces.IDisposable;
import com.elfec.lecturas.gd.model.results.VoidResult;
import com.elfec.lecturas.gd.presenter.views.IStartView;
import com.elfec.lecturas.gd.services.DataImportationService;

/**
 * Servicio de presenter que se encarga de escuchar los mensajes del servicio de
 * importación de datos su interacción con la vista
 * 
 * @author drodriguez
 *
 */
public class DataImportationReceiverPresenter extends BroadcastReceiver
		implements IDisposable {

	private IStartView view;
	private Context callerContext;

	public DataImportationReceiverPresenter(IStartView view,
			Context callerContext) {
		this.view = view;
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
		this.view = null;
	}

	@Override
	public void onReceive(Context context, final Intent intent) {
		if (view != null) {// is not disposed
			int action = intent.getIntExtra("action", -1);
			switch (action) {
			case DataImportationService.UPDATE_WAITING: {
				view.updateWaiting(intent.getIntExtra("message", 0));
				break;
			}
			case DataImportationService.IMPORTATION_FINISHED: {
				VoidResult result = (VoidResult) intent
						.getSerializableExtra("result");
				stopReceiving();
				view.hideWaiting();
				view.showErrors(R.string.title_import_data_error,
						R.drawable.error_import_from_server, result.getErrors());
				if (!result.hasErrors())
					view.notifySuccessfullyImportation();
				break;
			}
			default:
				break;
			}
		}

	}
}
