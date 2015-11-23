package com.elfec.lecturas.gd.view.notifiers;

import java.util.List;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.elfec.lecturas.gd.R;
import com.elfec.lecturas.gd.helpers.util.text.MessageListFormatter;
import com.elfec.lecturas.gd.presenter.views.observers.IDataExportationObserver;

public class DataExportationNotifier implements IDataExportationObserver {
	private Context mContext;
	private Intent activityIntent;

	public static final int NOTIF_ID = 888;
	private NotificationManager notificationManager;
	private NotificationCompat.Builder notifBuilder;

	public DataExportationNotifier(Context context, Intent activityIntent) {
		this.mContext = context;
		this.activityIntent = activityIntent;
		this.notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
	}

	@Override
	public void showExportationWaiting() {
		notificationManager.cancel(NOTIF_ID);
		activityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_SINGLE_TOP);
		notifBuilder = new NotificationCompat.Builder(mContext);
		notifBuilder
				.setContentTitle(mContext.getText(R.string.title_export_data))
				.setContentText(
						mContext.getText(R.string.msg_export_data_initialize))
				.setColor(
						ContextCompat.getColor(mContext, R.color.color_primary))
				.setSmallIcon(R.drawable.export_to_server_sn)
				.setOngoing(true)
				.setPriority(Notification.PRIORITY_MAX)
				.setContentIntent(
						PendingIntent.getActivity(mContext, 0, activityIntent,
								0)).setProgress(100, 0, true);
		notificationManager.notify(NOTIF_ID, notifBuilder.build());
	}

	@Override
	public void updateExportationWaiting(int strId, int totalData) {
		notifBuilder.setContentText(mContext.getText(strId));
		notifBuilder.setProgress(totalData, 0, false);
		notificationManager.notify(NOTIF_ID, notifBuilder.build());
	}

	@Override
	public void updateExportationWaiting(int strId) {
		notifBuilder.setContentText(mContext.getText(strId));
		notifBuilder.setProgress(0, 0, true);
		notificationManager.notify(NOTIF_ID, notifBuilder.build());
	}

	@Override
	public void updateExportationProgress(int dataCount, int totalData) {
		notifBuilder.setProgress(totalData, dataCount, false);
		notificationManager.notify(NOTIF_ID, notifBuilder.build());
	}

	@Override
	public void hideWaiting() {
		notifBuilder.setOngoing(false).setAutoCancel(true)
				.setProgress(0, 0, false);
	}

	@Override
	public void showErrors(int titleStrId, int iconDrawableId,
			List<Exception> errors) {
		if (errors.size() > 0) {
			notifBuilder
					.setSmallIcon(R.drawable.error_export_to_server_n)
					.setContentText(
							MessageListFormatter.fotmatHTMLFromErrors(errors))
					.setContentTitle(
							mContext.getText(R.string.title_export_data_error));
			notificationManager.notify(NOTIF_ID, notifBuilder.build());
		}
	}

	@Override
	public void notifySuccessfulExportation() {
		notifBuilder.setContentIntent(null).setContentText(
				mContext.getText(R.string.msg_data_exported_successfully));
		notificationManager.notify(NOTIF_ID, notifBuilder.build());
	}

}
