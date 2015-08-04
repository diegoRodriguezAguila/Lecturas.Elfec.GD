package com.elfec.lecturas.gd.view.view_services;

import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.elfec.lecturas.gd.R;
import com.elfec.lecturas.gd.model.ReadingGeneralInfo;
import com.elfec.lecturas.gd.presenter.views.callbacks.OrdenativesSaveCallback;
import com.elfec.lecturas.gd.view.adapters.OrdenativeFragmentAdapter;

/**
 * Servicio de dialogo que muestra la opción de agregado de ordenativos a
 * lecturas
 * 
 * @author drodriguez
 *
 */
public class OrdenativeDialogService extends DialogFragment implements
		OrdenativesSaveCallback {

	private ReadingGeneralInfo mReading;

	private ViewPager mViewPager;
	private TabLayout mTabLayout;
	private Button mBtnCancel;
	private OnDismissListener mDismissListener;

	/**
	 * Crea un nuevo servicio de adición de ordenativos
	 * 
	 * @param context
	 * @param reading
	 *            La lectura a la que se quieren agregar ordenativos
	 */
	public OrdenativeDialogService(ReadingGeneralInfo reading) {
		super();
		mReading = reading;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(DialogFragment.STYLE_NORMAL,
				R.style.Theme_AppCompat_Light_Dialog_Alert);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View dialogView = inflater.inflate(R.layout.dialog_ordenatives,
				container);

		mViewPager = (ViewPager) dialogView
				.findViewById(R.id.ordenatives_view_pager);
		mViewPager.setAdapter(new OrdenativeFragmentAdapter(this
				.getChildFragmentManager(), mReading));
		mTabLayout = (TabLayout) dialogView.findViewById(R.id.sliding_tabs);
		mTabLayout.setupWithViewPager(mViewPager);
		mBtnCancel = (Button) dialogView.findViewById(R.id.btn_cancel);
		mBtnCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getDialog().cancel();
			}
		});
		return dialogView;
	}

	@Override
	public void onOrdenativesSavedSuccesfully() {
		dismiss();
	}

	/**
	 * Agrega un DismissListener
	 * 
	 * @param listener
	 * @return la instancia actual del dialogo
	 */
	public OrdenativeDialogService setOnDismissListener(
			OnDismissListener listener) {
		mDismissListener = listener;
		return this;
	}

	@Override
	public void onDismiss(DialogInterface dialog) {
		super.onDismiss(dialog);
		if (mDismissListener != null)
			mDismissListener.onDismiss(dialog);
	}
}
