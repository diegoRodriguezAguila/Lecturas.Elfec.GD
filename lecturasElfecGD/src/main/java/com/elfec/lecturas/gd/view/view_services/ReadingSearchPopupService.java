package com.elfec.lecturas.gd.view.view_services;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alertdialogpro.material.ProgressBarCompat;
import com.elfec.lecturas.gd.R;
import com.elfec.lecturas.gd.helpers.ui.ButtonClicksHelper;
import com.elfec.lecturas.gd.helpers.util.text.AccountFormatter;
import com.elfec.lecturas.gd.helpers.util.text.MessageListFormatter;
import com.elfec.lecturas.gd.model.ReadingGeneralInfo;
import com.elfec.lecturas.gd.presenter.ReadingSearchPresenter;
import com.elfec.lecturas.gd.presenter.views.IReadingSearchView;

import org.apache.commons.lang3.math.NumberUtils;

import java.util.List;

/**
 * Servicio de popup que muestra la opci�n de b�squeda de lecturas
 * 
 * @author drodriguez
 *
 */
public class ReadingSearchPopupService implements IReadingSearchView {

	private ReadingSearchPresenter presenter;

	private Handler mHandler;
	private OnDismissListener mOnDismissListener;
	private OnReadingFoundListener mOnReadingFoundListener;

	private Context mContext;
	private Dialog mDialog;
	private Snackbar snackbar;
	private CoordinatorLayout snackBarPosition;

	// Fields
	private EditText txtAccountNumber;
	private EditText txtMeter;
	private EditText txtNUS;
	private ProgressBarCompat progressSearchingReading;
	private TextView txtSearchingReading;
	private TextView txtSearchingError;

	public ReadingSearchPopupService(Context context, View anchorView) {
		this(context, anchorView, null);
	}

	/**
	 * Crea un nuevo popup de b�squeda de lecturas
	 * 
	 * @param context
	 * @param anchorView
	 *            la vista a partir de la cual se desplegar� el popup
	 */
	@SuppressLint("InflateParams")
	public ReadingSearchPopupService(Context context, View anchorView,
			OnReadingFoundListener onReadingFoundListener) {
		this.mContext = context;
		this.mOnReadingFoundListener = onReadingFoundListener;
		this.presenter = new ReadingSearchPresenter(this);
		this.mHandler = new Handler();
		final View dialogView = LayoutInflater.from(mContext).inflate(
				R.layout.popup_reading_search, null, false);
		mDialog = new Dialog(context, R.style.DropDownDialog);
		mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		mDialog.setContentView(dialogView);
		mDialog.setCancelable(true);
		mDialog.setCanceledOnTouchOutside(true);
		float offset = context.getResources().getDimension(
				R.dimen.search_dropdown_offset);
		WindowManager.LayoutParams wmlp = mDialog.getWindow().getAttributes();
		wmlp.gravity = Gravity.TOP | Gravity.END;
		wmlp.x = (int) (anchorView.getX()); // x position
		wmlp.y = (int) (anchorView.getY() + offset);
		wmlp.width = (int) (mContext.getResources()
				.getDimension(R.dimen.search_dropdown_width));
		wmlp.height = (int) (mContext.getResources()
				.getDimension(R.dimen.search_dropdown_height));
		mDialog.getWindow().setAttributes(wmlp);
		new Thread(new Runnable() {
			@Override
			public void run() {
				mDialog.setOnDismissListener(new OnDismissListener() {
					@Override
					public void onDismiss(DialogInterface dialog) {
						if (mOnDismissListener != null)
							mOnDismissListener.onDismiss(dialog);
					}
				});
				txtAccountNumber = (EditText) dialogView
						.findViewById(R.id.txt_account_number);
				txtMeter = (EditText) dialogView.findViewById(R.id.txt_meter);
				txtNUS = (EditText) dialogView.findViewById(R.id.txt_nus);
				setTextListeners();
				progressSearchingReading = (ProgressBarCompat) dialogView
						.findViewById(R.id.progress_searching_reading);
				txtSearchingReading = (TextView) dialogView
						.findViewById(R.id.lbl_searching_reading);
				txtSearchingError = (TextView) dialogView
						.findViewById(R.id.lbl_searching_error);
				snackBarPosition = (CoordinatorLayout) dialogView
						.findViewById(R.id.snackbar_position);
				((Button) dialogView.findViewById(R.id.btn_search))
						.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								if (ButtonClicksHelper.canClickButton())
									presenter.searchReading();
							}
						});
			}
		}).start();
	}

	/**
	 * Asigna el listener que se llamar� ante el evento de encontrar una lectura
	 * 
	 * @param onReadingFoundListener
	 * @return la instancia actual de este servicio
	 */
	public ReadingSearchPopupService setOnReadingFoundListener(
			OnReadingFoundListener onReadingFoundListener) {
		mOnReadingFoundListener = onReadingFoundListener;
		return this;
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
	 * Muestra el di�logo
	 */
	public void show() {
		mDialog.show();
	}

	/**
	 * Asigna los listeners para los textos, de esta forma se evita que el
	 * usuario ponga m�s de una condici�n de b�squeda al mismo tiempo
	 */
	private void setTextListeners() {
		txtAccountNumber.addTextChangedListener(new ExclusiveTextWatcher(
				txtMeter, txtNUS));
		txtMeter.addTextChangedListener(new ExclusiveTextWatcher(
				txtAccountNumber, txtNUS));
		txtNUS.addTextChangedListener(new ExclusiveTextWatcher(
				txtAccountNumber, txtMeter));
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
		mHandler.post(new Runnable() {
			@Override
			public void run() {
				txtSearchingError.setVisibility(View.GONE);
				snackbar = Snackbar.make(snackBarPosition,
						R.string.msg_atleast_one_filed, Snackbar.LENGTH_SHORT);
				((TextView) snackbar.getView().findViewById(
						android.support.design.R.id.snackbar_text))
						.setMaxLines(3);
				snackbar.show();
			}
		});
	}

	@Override
	public void notifySearchStarted() {
		mHandler.post(new Runnable() {
			@Override
			public void run() {
				if (snackbar != null)
					snackbar.dismiss();
				txtSearchingError.setVisibility(View.GONE);
				progressSearchingReading.setVisibility(View.VISIBLE);
				txtSearchingReading.setVisibility(View.VISIBLE);
			}
		});
	}

	@Override
	public void showSearchErrors(final List<Exception> errors) {
		mHandler.post(new Runnable() {
			@Override
			public void run() {
				progressSearchingReading.setVisibility(View.GONE);
				txtSearchingReading.setVisibility(View.GONE);
				txtSearchingError.setText(MessageListFormatter
						.formatHTMLFromErrors(errors));
				txtSearchingError.setVisibility(View.VISIBLE);
			}
		});
	}

	@Override
	public void showReadingFound(final ReadingGeneralInfo reading) {
		mHandler.post(new Runnable() {
			@Override
			public void run() {
				if (mOnReadingFoundListener != null)
					mOnReadingFoundListener.onReadingFound(reading);
				mDialog.dismiss();
				Toast.makeText(mContext, R.string.msg_reading_found,
						Toast.LENGTH_LONG).show();
			}
		});
	}

	// #endregion

	/**
	 * Listener para el evento de que se encontr� una lectura, resultado de una
	 * b�squeda
	 * 
	 * @author drodriguez
	 *
	 */
	public interface OnReadingFoundListener {
		/**
		 * Se ejecuta cuando se encontr� una lectura
		 * 
		 * @param reading
		 */
		void onReadingFound(ReadingGeneralInfo reading);
	}

	/**
	 * Watcher de texto que realiza un cambio de texto exclusivo, es decir que
	 * solo permite que uno de los {@link EditText} tengan texto al mismo tiempo
	 * 
	 * @author drodriguez
	 *
	 */
	private static class ExclusiveTextWatcher implements TextWatcher {

		private EditText[] mOtherTexts;

		public ExclusiveTextWatcher(EditText... otherTexts) {
			this.mOtherTexts = otherTexts;
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
		}

		@Override
		public void afterTextChanged(Editable s) {
			if (s != null && !s.toString().isEmpty()) {
				for (int i = 0; i < mOtherTexts.length; i++) {
					mOtherTexts[i].setText(null);
				}
			}
		}

	}

}
