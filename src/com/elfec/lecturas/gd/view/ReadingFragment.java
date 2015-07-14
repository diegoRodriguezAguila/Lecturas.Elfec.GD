package com.elfec.lecturas.gd.view;

import org.apache.commons.lang.WordUtils;
import org.joda.time.DateTime;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elfec.lecturas.gd.R;
import com.elfec.lecturas.gd.helpers.util.text.AccountFormatter;
import com.elfec.lecturas.gd.model.ReadingGeneralInfo;
import com.elfec.lecturas.gd.view.animations.HeightAnimation;
import com.elfec.lecturas.gd.view.controls.ImprovedTextInputLayout;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog.OnTimeSetListener;

public class ReadingFragment extends Fragment {

	/**
	 * la Key para obtener la lectura en este fragmento
	 */
	public static final String ARG_READING = "ReadingGeneralInfo";

	private LinearLayout layoutClientInfo;
	private ImprovedTextInputLayout txtInputReadingDate;
	private ImprovedTextInputLayout txtInputReadingTime;
	private TextView txtAccountNumber;
	private TextView txtNUS;
	private TextView txtMeter;
	private TextView txtClientName;
	private TextView txtAddress;
	private TextView txtCategory;

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
	public static ReadingFragment create() {
		ReadingFragment fragment = new ReadingFragment();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null && getArguments().containsKey(ARG_READING))
			reading = (ReadingGeneralInfo) getArguments().getSerializable(
					ARG_READING);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.fragment_reading,
				container, false);
		if (savedInstanceState != null
				&& savedInstanceState.containsKey(ARG_READING)) {
			reading = (ReadingGeneralInfo) savedInstanceState
					.getSerializable(ARG_READING);
		}
		initializeClientInfo(rootView);
		layoutClientInfo = (LinearLayout) rootView
				.findViewById(R.id.layout_client_info);
		txtInputReadingDate = ((ImprovedTextInputLayout) rootView
				.findViewById(R.id.txt_input_layout_reading_date));
		txtInputReadingTime = ((ImprovedTextInputLayout) rootView
				.findViewById(R.id.txt_input_layout_reading_time));
		new Thread(new Runnable() {
			@Override
			public void run() {
				setReadingDateListeners();
				setReadingTimeListeners();
				setLblClientInfoClickListener(rootView);
			}
		}).start();
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
	 * Inicializa los valores de la lectura actual, utilizando la información en
	 * la variable <b>reading</b>
	 * 
	 * @param rootView
	 */
	private void initializeClientInfo(final View rootView) {
		txtAccountNumber = (TextView) rootView
				.findViewById(R.id.txt_account_number);
		txtNUS = (TextView) rootView.findViewById(R.id.txt_nus);
		txtMeter = (TextView) rootView.findViewById(R.id.txt_meter);
		txtClientName = (TextView) rootView.findViewById(R.id.txt_client_name);
		txtAddress = (TextView) rootView.findViewById(R.id.txt_address);
		txtCategory = (TextView) rootView.findViewById(R.id.txt_category);
	}

	/**
	 * Asigna la información de la lectura al fragment
	 * 
	 * @param reading
	 */
	public void bindReadingInfo(ReadingGeneralInfo reading) {
		this.reading = reading;
		txtAccountNumber.setText(AccountFormatter.formatAccountNumber(reading
				.getSupplyNumber()));
		txtNUS.setText("" + reading.getSupplyId());
		txtMeter.setText(reading.getReadingMeter().getSerialNumber());
		txtClientName.setText(WordUtils.capitalizeFully(reading.getName(),
				new char[] { '.', ' ' }));
		txtAddress.setText(WordUtils.capitalizeFully(reading.getAddress(),
				new char[] { '.', ' ' }));
		txtCategory.setText(reading.getCategoryDescription());
	}

	/**
	 * Asigna los listeners de el textinput de la fecha actual de lectura
	 */
	private void setReadingDateListeners() {
		txtInputReadingDate
				.setEditTextOnFocusChangeListener(new OnFocusChangeListener() {
					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						if (hasFocus) {
							showDatePicker();
						}
					}

				});
		txtInputReadingDate.getEditText().setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						showDatePicker();
					}
				});
	}

	/**
	 * Muestra un datePicker
	 */
	private void showDatePicker() {
		DateTime dateNow = DateTime.now();
		DatePickerDialog dpd = DatePickerDialog.newInstance(
				new OnDateSetListener() {
					@Override
					public void onDateSet(DatePickerDialog dpd, int year,
							int month, int day) {
						txtInputReadingDate.getEditText().setText(
								new DateTime(year, month, day, 0, 0)
										.toString("dd/MM/yyy"));
					}
				}, dateNow.getYear(), dateNow.getMonthOfYear(), dateNow
						.getDayOfMonth());
		dpd.show(getActivity().getFragmentManager(), "DatePickerdialog");
	}

	/**
	 * Asigna los listeners de el textinput de la hora actual de lectura
	 */
	private void setReadingTimeListeners() {
		txtInputReadingTime
				.setEditTextOnFocusChangeListener(new OnFocusChangeListener() {
					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						if (hasFocus) {
							showTimePicker();
						}
					}

				});
		txtInputReadingTime.getEditText().setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						showTimePicker();
					}
				});
	}

	/**
	 * Muestra un timePicker
	 */
	private void showTimePicker() {
		final DateTime dateNow = DateTime.now();
		TimePickerDialog tpd = TimePickerDialog.newInstance(
				new OnTimeSetListener() {
					@Override
					public void onTimeSet(RadialPickerLayout view,
							int hourOfDay, int minute) {
						txtInputReadingTime.getEditText().setText(
								new DateTime(dateNow.getYear(), dateNow
										.getMonthOfYear(), dateNow
										.getDayOfMonth(), hourOfDay, minute)
										.toString("HH:mm"));
					}
				}, dateNow.getHourOfDay(), dateNow.getMinuteOfHour(), true);
		tpd.show(getActivity().getFragmentManager(), "TimePickerdialog");
	}

	/**
	 * Asigna el listener de click al label de la información del cliente para
	 * colapsarlo
	 * 
	 * @param rootView
	 */
	private void setLblClientInfoClickListener(final View rootView) {
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
	 * Muestra la información del cliente
	 */
	private void expandClientInfo() {
		mClientInfoCollapsed = false;
		Animation anim = new HeightAnimation(layoutClientInfo, 0,
				mClientInfoHeight);
		anim.setDuration(400);
		layoutClientInfo.startAnimation(anim);
	}
}
