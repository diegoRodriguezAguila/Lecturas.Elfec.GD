package com.elfec.lecturas.gd.view.view_services;

import java.util.Vector;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.codetroopers.betterpickers.timepicker.TimePicker;
import com.codetroopers.betterpickers.timepicker.TimePickerDialogFragment.TimePickerDialogHandler;
import com.elfec.lecturas.gd.R;

public class TimePickerDialogService {

	private TimePicker mPicker;
	private AlertDialog mDialog;
	private Vector<TimePickerDialogHandler> mTimePickerDialogHandlers = new Vector<TimePickerDialogHandler>();

	@SuppressLint("InflateParams")
	public TimePickerDialogService(Context context) {
		View rootView = LayoutInflater.from(context).inflate(
				R.layout.dialog_time_picker, null, false);
		mDialog = new AlertDialog.Builder(context).setView(rootView)
				.setNegativeButton(R.string.btn_cancel, null)
				.setPositiveButton(R.string.btn_ok, new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						for (TimePickerDialogHandler handler : mTimePickerDialogHandlers) {
							handler.onDialogTimeSet(-1, mPicker.getHours(),
									mPicker.getMinutes());
						}
					}
				}).create();
		mPicker = (TimePicker) rootView;
		mPicker.setTheme(R.style.AppStyle_TimePickerStyle);
		mPicker.setSetButton(mDialog.getButton(AlertDialog.BUTTON_POSITIVE));
	}

	/**
	 * Muestra el dialogo
	 */
	public void show() {
		mDialog.show();
	}

	/**
	 * Attach a handler to be notified in addition to the Fragment's Activity
	 * and target Fragment.
	 *
	 * @param handlers
	 *            a Vector of handlers
	 */
	public TimePickerDialogService addTimePickerDialogHandlers(
			TimePickerDialogHandler handler) {
		mTimePickerDialogHandlers.add(handler);
		return this;
	}

	/**
	 * Attach a Vector of handlers to be notified in addition to the Fragment's
	 * Activity and target Fragment.
	 *
	 * @param handlers
	 *            a Vector of handlers
	 */
	public TimePickerDialogService setTimePickerDialogHandlers(
			Vector<TimePickerDialogHandler> handlers) {
		mTimePickerDialogHandlers = handlers;
		return this;
	}
}
