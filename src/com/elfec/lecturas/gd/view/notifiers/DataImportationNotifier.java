package com.elfec.lecturas.gd.view.notifiers;

import java.util.List;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.RemoteViews;

import com.elfec.lecturas.gd.R;
import com.elfec.lecturas.gd.helpers.util.text.MessageListFormatter;
import com.elfec.lecturas.gd.presenter.views.observers.IDataImportationObserver;

/**
 * Se encarga de mostrar información de importación en el área de notificaciones
 * 
 * @author drodriguez
 *
 */
public class DataImportationNotifier implements IDataImportationObserver {

	private Context context;
	private Intent activityIntent;

	public static final int NOTIF_ID = 777;
	private NotificationManager notificationManager;
	private Notification notification;

	public DataImportationNotifier(Context context, Intent activityIntent) {
		this.context = context;
		this.activityIntent = activityIntent;
		this.notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
	}

	@Override
	public void showImportationWaiting() {
		if (notification != null)
			notificationManager.cancel(NOTIF_ID);
		activityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_SINGLE_TOP);
		NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(
				context);
		RemoteViews notifView = new RemoteViews(context.getPackageName(),
				R.layout.notification_layout);
		notifView.setTextViewText(R.id.lbl_notification_title,
				context.getString(R.string.title_import_data));
		notifView.setTextViewText(R.id.lbl_notification_message,
				context.getString(R.string.msg_import_data_initialize));
		notifBuilder
				.setColor(
						context.getResources().getColor(R.color.color_primary))
				.setSmallIcon(R.drawable.import_from_server_sn)
				.setOngoing(true)
				.setPriority(Notification.PRIORITY_MAX)
				.setContentIntent(
						PendingIntent
								.getActivity(context, 0, activityIntent, 0));
		notification = notifBuilder.build();
		notification.contentView = notifView;
		notification.bigContentView = notifView;
		notificationManager.notify(NOTIF_ID, notification);
	}

	@Override
	public void updateImportationWaiting(int msgStrId) {
		notification.contentView.setTextViewText(R.id.lbl_notification_message,
				context.getString(msgStrId));
		notificationManager.notify(NOTIF_ID, notification);
	}

	@Override
	public void hideImportationWaiting() {
		notification.contentView.setViewVisibility(
				R.id.notification_progress_bar, View.GONE);
		notification.flags = Notification.FLAG_AUTO_CANCEL;
	}

	@Override
	public void showErrors(int titleStrId, int iconDrawableId,
			List<Exception> errors) {
		if (errors.size() > 0) {
			notification.contentView.setImageViewResource(
					R.id.notification_img,
					R.drawable.error_import_from_server_n);
			notification.contentView.setTextViewText(
					R.id.lbl_notification_title, context.getString(titleStrId));
			notification.contentView.setTextViewText(
					R.id.lbl_notification_message,
					MessageListFormatter.fotmatHTMLFromErrors(errors));
			notificationManager.notify(NOTIF_ID, notification);
		}
	}

	@Override
	public void notifySuccessfulImportation() {
		notification.contentView.setTextViewText(R.id.lbl_notification_message,
				context.getString(R.string.msg_data_imported_successfully));
		notificationManager.notify(NOTIF_ID, notification);
	}

}
