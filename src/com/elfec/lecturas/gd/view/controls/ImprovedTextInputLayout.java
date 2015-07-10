package com.elfec.lecturas.gd.view.controls;

import android.content.Context;
import android.graphics.Canvas;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class ImprovedTextInputLayout extends TextInputLayout {

	private boolean mIsHintSet;
	private CharSequence mHint;

	public ImprovedTextInputLayout(Context context) {
		super(context);
	}

	public ImprovedTextInputLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void addView(View child, int index, ViewGroup.LayoutParams params) {
		if (child instanceof EditText) {
			// Since hint will be nullify on EditText once on parent addView,
			// store hint value locally
			mHint = ((EditText) child).getHint();
		}
		super.addView(child, index, params);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		if (!mIsHintSet && ViewCompat.isLaidOut(this)) {
			// We have to reset the previous hint so that equals check pass
			setHint(null);

			// In case that hint is changed programatically
			CharSequence currentEditTextHint = getEditText().getHint();
			if (currentEditTextHint != null && currentEditTextHint.length() > 0) {
				mHint = currentEditTextHint;
				getEditText().setHint("");
			}
			setHint(mHint);
			mIsHintSet = true;
		}
	}

	/**
	 * Asigna el focus change listener al texto sin afectar al actual
	 * 
	 * @param listener
	 */
	public void setEditTextOnFocusChangeListener(
			final OnFocusChangeListener listener) {
		final OnFocusChangeListener currentListener = getEditText()
				.getOnFocusChangeListener();
		getEditText().setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				currentListener.onFocusChange(v, hasFocus);
				listener.onFocusChange(v, hasFocus);
			}
		});
	}

}