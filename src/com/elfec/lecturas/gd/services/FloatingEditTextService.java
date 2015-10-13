package com.elfec.lecturas.gd.services;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Binder;
import android.os.IBinder;
import android.support.v7.internal.view.ContextThemeWrapper;
import android.support.v7.widget.AppCompatEditText;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.TextView;

import com.elfec.lecturas.gd.R;

public class FloatingEditTextService extends Service {

	// Binder given to clients
	private final IBinder mBinder = new LocalBinder();
	private boolean mIsWindowShown;
	private WindowManager windowManager;
	private View mFloatingEditTextView;
	private TextView mLblFieldName;
	private AppCompatEditText mTxtFieldValue;

	WindowManager.LayoutParams params;

	@SuppressLint("InflateParams")
	@Override
	public void onCreate() {
		super.onCreate();
		windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
		mFloatingEditTextView = LayoutInflater.from(
				CalligraphyContextWrapper.wrap(new ContextThemeWrapper(this,
						R.style.AppCustomTheme))).inflate(
				R.layout.floating_edittext_view, null, false);
		mLblFieldName = (TextView) mFloatingEditTextView
				.findViewById(R.id.lbl_field_name);
		mTxtFieldValue = (AppCompatEditText) mFloatingEditTextView
				.findViewById(R.id.txt_field_value);

		setTouchListener();
	}

	/**
	 * Pone el touch listener a los campos necesarios
	 */
	private void setTouchListener() {
		params = new WindowManager.LayoutParams(
				WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.TYPE_PHONE,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
				PixelFormat.TRANSLUCENT);

		params.gravity = Gravity.TOP | Gravity.START;
		params.x = 90;
		params.y = 100;
		OnTouchListener touchListener = new View.OnTouchListener() {
			private int initialX;
			private int initialY;
			private float initialTouchX;
			private float initialTouchY;

			@SuppressLint("ClickableViewAccessibility")
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					initialX = params.x;
					initialY = params.y;
					initialTouchX = event.getRawX();
					initialTouchY = event.getRawY();
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				case MotionEvent.ACTION_MOVE:
					params.x = initialX
							+ (int) (event.getRawX() - initialTouchX);
					params.y = initialY
							+ (int) (event.getRawY() - initialTouchY);
					windowManager.updateViewLayout(mFloatingEditTextView,
							params);
					return true;
				}
				return false;
			}
		};

		// this code is for dragging the chat head
		mFloatingEditTextView.setOnTouchListener(touchListener);
		mTxtFieldValue.setOnTouchListener(touchListener);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	/**
	 * Muestra el campo en ventana flotante
	 * 
	 * @param name
	 * @param value
	 */
	public void showFloatingEditText(String name, String value) {
		if (!mIsWindowShown)
			windowManager.addView(mFloatingEditTextView, params);
		setFieldName(name);
		setFieldValue(value);
		mIsWindowShown = true;
	}

	/**
	 * Esconde el campo en ventana flotante
	 */
	public void hideFloatingEditText() {
		if (mFloatingEditTextView != null && mIsWindowShown) {
			windowManager.removeView(mFloatingEditTextView);
			mIsWindowShown = false;
		}
	}

	/**
	 * Devuelve true si la vista ya está mostrada
	 * 
	 * @return
	 */
	public boolean isWindowShown() {
		return mIsWindowShown;
	}

	/**
	 * Asigna el nombre del campo
	 * 
	 * @param name
	 */
	public void setFieldName(String name) {
		mLblFieldName.setText(name);
	}

	/**
	 * Asigna el valor del campo
	 * 
	 * @param value
	 */
	public void setFieldValue(String value) {
		mTxtFieldValue.setText(value);
	}

	/**
	 * Binder para la clase
	 * 
	 * @author drodriguez
	 *
	 */
	public class LocalBinder extends Binder {
		public FloatingEditTextService getService() {
			return FloatingEditTextService.this;
		}
	}

}
