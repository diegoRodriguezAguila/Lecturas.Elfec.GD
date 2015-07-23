package com.elfec.lecturas.gd.view.view_services;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;

import com.elfec.lecturas.gd.R;

/**
 * Servicio de popup que muestra la opción de búsqueda de lecturas
 * 
 * @author drodriguez
 *
 */
public class ReadingSearchPopupService {

	private OnDismissListener mOnDismissListener;

	private DisplayMetrics mMetrics = Resources.getSystem().getDisplayMetrics();
	private PopupWindow mPopupWindow;
	private View mPopupView;
	private View mAnchorView;

	private static final int POPUP_WIDTH = 400;
	private static final int POPUP_HEIGHT = 500;

	/**
	 * Crea un nuevo popup de búsqueda de lecturas
	 * 
	 * @param context
	 * @param anchorView
	 *            la vista a partir de la cual se desplegará el popup
	 */
	@SuppressLint("InflateParams")
	public ReadingSearchPopupService(View anchorView) {
		this.mAnchorView = anchorView;
		mMetrics = Resources.getSystem().getDisplayMetrics();
		mPopupView = LayoutInflater.from(mAnchorView.getContext()).inflate(
				R.layout.list_loading, null, false);
		mPopupWindow = new PopupWindow(mPopupView,
				(int) (mMetrics.density * POPUP_WIDTH),
				(int) (mMetrics.density * POPUP_HEIGHT));
		mPopupWindow.setBackgroundDrawable(ContextCompat.getDrawable(
				mAnchorView.getContext(),
				R.drawable.abc_popup_background_mtrl_mult));
		mPopupWindow.setOutsideTouchable(true);
		mPopupWindow.setTouchable(true);
		mPopupWindow.setFocusable(true);
		mPopupWindow.setAnimationStyle(R.style.PopupAnimation);

	}

	/**
	 * Sets the listener to be called when the window is dismissed.
	 * 
	 * @param onDismissListener
	 */
	public void setOnDismissListener(OnDismissListener onDismissListener) {
		mOnDismissListener = onDismissListener;
	}

	/**
	 * Muestra el diálogo
	 */
	public void show() {
		if (!mPopupWindow.isShowing()) {
			dimBackground();
			mPopupWindow.showAsDropDown(mAnchorView, 0,
					(int) (mMetrics.density * (-14)));
		}
	}

	/**
	 * Pone el background para el popup
	 * 
	 * @return
	 */
	private PopupWindow dimBackground() {
		@SuppressLint("InflateParams")
		final View layout = (LayoutInflater.from(mAnchorView.getContext()))
				.inflate(R.layout.fadepopup, null);
		final PopupWindow fadePopup = new PopupWindow(layout, mAnchorView
				.getRootView().getWidth(), mAnchorView.getRootView()
				.getHeight() - (int) (mMetrics.density * 89), false);
		fadePopup.setAnimationStyle(R.style.PopupAnimation);
		mPopupWindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				fadePopup.dismiss();
				if (mOnDismissListener != null)
					mOnDismissListener.onDismiss();
			}
		});
		fadePopup.showAtLocation(layout, Gravity.NO_GRAVITY, 0,
				(int) (mMetrics.density * 89));
		return fadePopup;
	}
}
