package com.elfec.lecturas.gd.view.controls;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elfec.lecturas.gd.R;

/**
 * {@link TextInputLayout} mejorado, arregla el bug de que los hints no se
 * muestran en edittexts que estan dentro de fragmentos. Provee también de un
 * metodo {@link #setEditTextOnFocusChangeListener(OnFocusChangeListener)} para
 * agregar un {@link OnFocusChangeListener} al EditText sin sobre escribir la
 * funcionalidad de la animación del hint
 * 
 * @author drodriguez
 *
 */
public class ImprovedTextInputLayout extends TextInputLayout {

	private static final int ANIMATION_DURATION = 200;
	private static final FastOutSlowInInterpolator FAST_OUT_SLOW_IN_INTERPOLATOR = new FastOutSlowInInterpolator();

	private boolean mIsHintSet;
	private CharSequence mHint;
	private int mErrorTextAppearance;
	private TextView mErrorView;
	private boolean mErrorEnabled;

	public ImprovedTextInputLayout(Context context) {
		super(context);
	}

	public ImprovedTextInputLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		final TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.TextInputLayout, 0,
				R.style.Widget_Design_TextInputLayout);
		mErrorTextAppearance = a.getResourceId(
				R.styleable.TextInputLayout_errorTextAppearance, 0);
		a.recycle();
	}

	@Override
	public void addView(View child, int index, ViewGroup.LayoutParams params) {
		if (child instanceof EditText) {
			// Since hint will be nullify on EditText once on parent addView,
			// store hint value locally
			mHint = ((EditText) child).getHint();
		}
		super.addView(child, index, params);
		if (child instanceof EditText) {
			LinearLayout.LayoutParams layoutParams = (LayoutParams) params;
			int marginDelta = (int) (layoutParams.topMargin * 0.6f);
			layoutParams.topMargin += marginDelta;
		}
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

	@Override
	public void setError(CharSequence error) {
		if (!mErrorEnabled) {
			if (TextUtils.isEmpty(error)) {
				// If error isn't enabled, and the error is empty, just return
				return;
			}
			// Else, we'll assume that they want to enable the error
			// functionality
			setErrorEnabled(true);
		}
		if (!TextUtils.isEmpty(error)) {
			ViewCompat.setAlpha(mErrorView, 0f);
			mErrorView.setText(error);
			ViewCompat.animate(mErrorView).alpha(1f)
					.setDuration(ANIMATION_DURATION)
					.setInterpolator(FAST_OUT_SLOW_IN_INTERPOLATOR)
					.setListener(new ViewPropertyAnimatorListenerAdapter() {
						@Override
						public void onAnimationStart(View view) {
							view.setVisibility(VISIBLE);
						}
					}).start();
		} else {
			if (mErrorView.getVisibility() == VISIBLE) {
				ViewCompat.animate(mErrorView).alpha(0f)
						.setDuration(ANIMATION_DURATION)
						.setInterpolator(FAST_OUT_SLOW_IN_INTERPOLATOR)
						.setListener(new ViewPropertyAnimatorListenerAdapter() {
							@Override
							public void onAnimationEnd(View view) {
								view.setVisibility(INVISIBLE);
							}
						}).start();
			}
		}
		if (getEditText() != null) {
			getEditText().setActivated(!TextUtils.isEmpty(error));
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void setErrorEnabled(boolean enabled) {
		if (mErrorEnabled != enabled) {
			if (mErrorView != null) {
				ViewCompat.animate(mErrorView).cancel();
			}
			if (enabled) {
				mErrorView = new TextView(getContext());
				mErrorView
						.setTextAppearance(getContext(), mErrorTextAppearance);
				mErrorView.setVisibility(INVISIBLE);
				addView(mErrorView);
				if (getEditText() != null) {
					// Add some start/end padding to the error so that it
					// matches the EditText
					ViewCompat.setPaddingRelative(mErrorView,
							ViewCompat.getPaddingStart(getEditText()), 0,
							ViewCompat.getPaddingEnd(getEditText()),
							getEditText().getPaddingBottom());
				}
			} else {
				removeView(mErrorView);
				mErrorView = null;
			}
			mErrorEnabled = enabled;
		}
		if (!enabled)
			setError(null);
		super.setErrorEnabled(enabled);
		if (getEditText() != null) {
			getEditText().setActivated(enabled);
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
				if (currentListener != null)
					currentListener.onFocusChange(v, hasFocus);
				listener.onFocusChange(v, hasFocus);
			}
		});
	}

	/**
	 * Obtiene el hint del campo
	 * 
	 * @return hint
	 */
	public CharSequence getHint() {
		return mHint;
	}

}