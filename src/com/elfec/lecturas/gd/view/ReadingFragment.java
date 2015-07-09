package com.elfec.lecturas.gd.view;

import org.apache.commons.lang.WordUtils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elfec.lecturas.gd.R;
import com.elfec.lecturas.gd.helpers.util.text.AccountFormatter;
import com.elfec.lecturas.gd.model.ReadingGeneralInfo;
import com.elfec.lecturas.gd.view.animations.HeightAnimation;

public class ReadingFragment extends Fragment {

	/**
	 * la Key para obtener la lectura en este fragmento
	 */
	public static final String ARG_READING = "ReadingGeneralInfo";

	private LinearLayout layoutClientInfo;

	private ReadingGeneralInfo reading;
	private boolean mClientInfoCollapsed;
	private int mClientInfoHeight;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public ReadingFragment() {
	}

	/**
	 * Factory method for this fragment class. Constructs a new fragment for the
	 * given reading.
	 */
	public static ReadingFragment create(ReadingGeneralInfo reading) {
		ReadingFragment fragment = new ReadingFragment();
		Bundle args = new Bundle();
		args.putSerializable(ARG_READING, reading);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		reading = (ReadingGeneralInfo) getArguments().getSerializable(
				ARG_READING);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_reading, container,
				false);
		if (savedInstanceState != null
				&& savedInstanceState.containsKey(ARG_READING)) {
			reading = (ReadingGeneralInfo) savedInstanceState
					.getSerializable(ARG_READING);
		}
		((TextView) rootView.findViewById(R.id.txt_account_number))
				.setText(AccountFormatter.formatAccountNumber(reading
						.getSupplyNumber()));
		((TextView) rootView.findViewById(R.id.txt_nus)).setText(""
				+ reading.getSupplyId());
		((TextView) rootView.findViewById(R.id.txt_meter)).setText(reading
				.getReadingMeter().getSerialNumber());
		((TextView) rootView.findViewById(R.id.txt_client_name))
				.setText(WordUtils.capitalizeFully(reading.getName(),
						new char[] { '.', ' ' }));
		((TextView) rootView.findViewById(R.id.txt_address))
				.setText(WordUtils.capitalizeFully(reading.getAddress(),
						new char[] { '.', ' ' }));
		((TextView) rootView.findViewById(R.id.txt_category)).setText(reading
				.getCategoryDescription());
		layoutClientInfo = (LinearLayout) rootView
				.findViewById(R.id.layout_client_info);
		setLblClientInfoClickListener(rootView);
		return rootView;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (reading != null) {
			// Serialize and persist the reading.
			outState.putSerializable(ARG_READING, reading);
		}
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		reading = null;
	}

	/**
	 * Asigna el listener de click al label de la información del cliente para
	 * colapsarlo
	 * 
	 * @param rootView
	 */
	private void setLblClientInfoClickListener(View rootView) {
		rootView.findViewById(R.id.lbl_client_info).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (mClientInfoCollapsed)
							expandClientInfo();
						else
							collapseClientInfo();
					}
				});

	}

	/**
	 * Collapsa la información del cliente
	 */
	private void collapseClientInfo() {
		mClientInfoCollapsed = true;
		if (mClientInfoHeight == 0)
			mClientInfoHeight = layoutClientInfo.getHeight();
		Animation anim = new HeightAnimation(layoutClientInfo,
				mClientInfoHeight, 0);
		anim.setDuration(400);
		layoutClientInfo.startAnimation(anim);
	}

	/**
	 * muestra la información del cliente
	 */
	private void expandClientInfo() {
		mClientInfoCollapsed = false;
		Animation anim = new HeightAnimation(layoutClientInfo, 0,
				mClientInfoHeight);
		anim.setDuration(400);
		layoutClientInfo.startAnimation(anim);
	}
}
