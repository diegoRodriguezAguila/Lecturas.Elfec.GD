package com.elfec.lecturas.gd.services;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
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

		params = new WindowManager.LayoutParams(
				WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.TYPE_PHONE,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
				PixelFormat.TRANSLUCENT);

		params.gravity = Gravity.TOP | Gravity.START;
		params.x = 0;
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
		windowManager.addView(mFloatingEditTextView, params);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mFloatingEditTextView != null)
			windowManager.removeView(mFloatingEditTextView);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}
