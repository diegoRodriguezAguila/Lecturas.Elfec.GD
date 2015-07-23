package com.elfec.lecturas.gd.view.view_services;

import java.util.List;

import org.apache.commons.lang.math.NumberUtils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;

import com.elfec.lecturas.gd.R;
import com.elfec.lecturas.gd.helpers.ui.ButtonClicksHelper;
import com.elfec.lecturas.gd.helpers.util.text.AccountFormatter;
import com.elfec.lecturas.gd.model.ReadingGeneralInfo;
import com.elfec.lecturas.gd.presenter.ReadingSearchPresenter;
import com.elfec.lecturas.gd.presenter.views.IReadingSearchView;

/**
 * Servicio de popup que muestra la opción de búsqueda de lecturas
 * 
 * @author drodriguez
 *
 */
public class ReadingSearchPopupService implements IReadingSearchView {

	private ReadingSearchPresenter presenter;

	private Handler mHandler;
	private OnDismissListener mOnDismissListener;

	private PopupWindow mPopupWindow;
	private Context mContext;
	private View mAnchorView;

	// Fields
	private EditText txtAccountNumber;
	private EditText txtMeter;
	private EditText txtNUS;

	/**
	 * Crea un nuevo popup de búsqueda de lecturas
	 * 
	 * @param context
	 * @param anchorView
	 *            la vista a partir de la cual se desplegará el popup
	 */
	@SuppressLint("InflateParams")
	public ReadingSearchPopupService(Context context, View anchorView) {
		this.mContext = context;
		this.mAnchorView = anchorView;
		this.presenter = new ReadingSearchPresenter(this);
		this.mHandler = new Handler();
		View popupView = LayoutInflater.from(mContext).inflate(
				R.layout.popup_reading_search, null, false);
		mPopupWindow = new PopupWindow(popupView,
				(int) (mContext.getResources()
						.getDimension(R.dimen.search_dropdown_width)),
				(int) (mContext.getResources()
						.getDimension(R.dimen.search_dropdown_height)));
		mPopupWindow.setBackgroundDrawable(ContextCompat.getDrawable(mContext,
				R.drawable.abc_popup_background_mtrl_mult));
		mPopupWindow.setOutsideTouchable(true);
		mPopupWindow.setTouchable(true);
		mPopupWindow.setFocusable(true);
		mPopupWindow.setAnimationStyle(R.style.PopupAnimation);

		txtAccountNumber = (EditText) popupView
				.findViewById(R.id.txt_account_number);
		txtMeter = (EditText) popupView.findViewById(R.id.txt_meter);
		txtNUS = (EditText) popupView.findViewById(R.id.txt_nus);
		((Button) popupView.findViewById(R.id.btn_search))
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (ButtonClicksHelper.canClickButton())
							presenter.searchReading();
					}
				});
	}

	/**
	 * Sets the listener to be called when the window is dismissed.
	 * 
	 * @param onDismissListener
	 * @return la instancia actual de este servicio
	 */
	public ReadingSearchPopupService setOnDismissListener(
			OnDismissListener onDismissListener) {
		mOnDismissListener = onDismissListener;
		return this;
	}

	/**
	 * Muestra el diálogo
	 */
	public void show() {
		if (!mPopupWindow.isShowing()) {
			dimBackground();
			mPopupWindow.showAsDropDown(mAnchorView, 0, (int) mContext
					.getResources()
					.getDimension(R.dimen.search_dropdown_offset));
		}
	}

	/**
	 * Pone el background para el popup
	 * 
	 * @return
	 */
	private PopupWindow dimBackground() {
		@SuppressLint("InflateParams")
		final View layout = (LayoutInflater.from(mContext)).inflate(
				R.layout.fadepopup, null);
		final PopupWindow fadePopup = new PopupWindow(layout, mAnchorView
				.getRootView().getWidth(),
				mAnchorView.getRootView().getHeight()
						- (int) (mContext.getResources()
								.getDimension(R.dimen.search_bg_top_offset)),
				false);
		fadePopup.setAnimationStyle(R.style.PopupBackgroundAnimation);
		mPopupWindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				fadePopup.dismiss();
				if (mOnDismissListener != null)
					mOnDismissListener.onDismiss();
			}
		});
		fadePopup.showAtLocation(layout, Gravity.NO_GRAVITY, 0, (int) (mContext
				.getResources().getDimension(R.dimen.search_bg_top_offset)));
		return fadePopup;
	}

	// #region Interface Methods

	@Override
	public String getAccountNumber() {
		return AccountFormatter.unformatAccountNumber(txtAccountNumber
				.getText().toString());
	}

	@Override
	public String getMeter() {
		return txtMeter.getText().toString().trim();
	}

	@Override
	public int getNUS() {
		return NumberUtils.toInt(txtNUS.getText().toString().toString(), -1);
	}

	@Override
	public void notifyAtleastOneField() {
		// TODO Auto-generated method stub

	}

	@Override
	public void notifySearchStarted() {
		// TODO Auto-generated method stub

	}

	@Override
	public void showSearchErrors(List<Exception> errors) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showReadingFound(ReadingGeneralInfo reading) {
		mPopupWindow.dismiss();
	}

	// #endregion
}
