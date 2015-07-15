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
import android.widget.EditText;
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

	// Client Info
	private LinearLayout layoutClientInfo;
	private TextView txtAccountNumber;
	private TextView txtNUS;
	private TextView txtMeter;
	private TextView txtClientName;
	private TextView txtAddress;
	private TextView txtCategory;

	// Reading Fields
	private ImprovedTextInputLayout txtInputReadingDate;
	private ImprovedTextInputLayout txtInputReadingTime;
	private ImprovedTextInputLayout txtInputResetCount;
	private ImprovedTextInputLayout txtInputActivePeak;
	private ImprovedTextInputLayout txtInputActiveRest;
	private ImprovedTextInputLayout txtInputActiveValley;
	private ImprovedTextInputLayout txtInputReactivePeak;
	private ImprovedTextInputLayout txtInputReactiveRest;
	private ImprovedTextInputLayout txtInputReactiveValley;
	private ImprovedTextInputLayout txtInputPowerPeak;
	private ImprovedTextInputLayout txtInputPowerPeakDate;
	private ImprovedTextInputLayout txtInputPowerPeakTime;
	private ImprovedTextInputLayout txtInputPowerRestOffpeak;
	private ImprovedTextInputLayout txtInputPowerRestOffpeakDate;
	private ImprovedTextInputLayout txtInputPowerRestOffpeakTime;
	private ImprovedTextInputLayout txtInputPowerValleyOffpeak;
	private ImprovedTextInputLayout txtInputPowerValleyOffpeakDate;
	private ImprovedTextInputLayout txtInputPowerValleyOffpeakTime;

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
	 * Factory method for this fragment class
	 */
	public static ReadingFragment create() {
		ReadingFragment fragment = new ReadingFragment();
		return fragment;
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
		initializeReadingFields(rootView);
		new Thread(new Runnable() {
			@Override
			public void run() {
				setDateListeners();
				setTimeListeners();
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
	 * Inicializa los campos de texto de informaci�n del cliente
	 * 
	 * @param rootView
	 */
	private void initializeClientInfo(final View rootView) {
		layoutClientInfo = (LinearLayout) rootView
				.findViewById(R.id.layout_client_info);
		txtAccountNumber = (TextView) rootView
				.findViewById(R.id.txt_account_number);
		txtNUS = (TextView) rootView.findViewById(R.id.txt_nus);
		txtMeter = (TextView) rootView.findViewById(R.id.txt_meter);
		txtClientName = (TextView) rootView.findViewById(R.id.txt_client_name);
		txtAddress = (TextView) rootView.findViewById(R.id.txt_address);
		txtCategory = (TextView) rootView.findViewById(R.id.txt_category);
	}

	/**
	 * Inicializa los campos de lectura
	 * 
	 * @param rootView
	 */
	public void initializeReadingFields(final View rootView) {
		txtInputReadingDate = ((ImprovedTextInputLayout) rootView
				.findViewById(R.id.txt_input_layout_reading_date));
		txtInputReadingTime = ((ImprovedTextInputLayout) rootView
				.findViewById(R.id.txt_input_layout_reading_time));
		txtInputResetCount = ((ImprovedTextInputLayout) rootView
				.findViewById(R.id.txt_input_layout_reset_count));
		txtInputActivePeak = ((ImprovedTextInputLayout) rootView
				.findViewById(R.id.txt_input_layout_active_peak));
		txtInputActiveRest = ((ImprovedTextInputLayout) rootView
				.findViewById(R.id.txt_input_layout_active_rest));
		txtInputActiveValley = ((ImprovedTextInputLayout) rootView
				.findViewById(R.id.txt_input_layout_active_valley));
		txtInputReactivePeak = ((ImprovedTextInputLayout) rootView
				.findViewById(R.id.txt_input_layout_reactive_peak));
		txtInputReactiveRest = ((ImprovedTextInputLayout) rootView
				.findViewById(R.id.txt_input_layout_reactive_rest));
		txtInputReactiveValley = ((ImprovedTextInputLayout) rootView
				.findViewById(R.id.txt_input_layout_reactive_valley));
		txtInputPowerPeak = ((ImprovedTextInputLayout) rootView
				.findViewById(R.id.txt_input_layout_power_peak));
		txtInputPowerPeakDate = ((ImprovedTextInputLayout) rootView
				.findViewById(R.id.txt_input_layout_power_peak_date));
		txtInputPowerPeakTime = ((ImprovedTextInputLayout) rootView
				.findViewById(R.id.txt_input_layout_power_peak_time));
		txtInputPowerRestOffpeak = ((ImprovedTextInputLayout) rootView
				.findViewById(R.id.txt_input_layout_power_rest_offpeak));
		txtInputPowerRestOffpeakDate = ((ImprovedTextInputLayout) rootView
				.findViewById(R.id.txt_input_layout_power_rest_offpeak_date));
		txtInputPowerRestOffpeakTime = ((ImprovedTextInputLayout) rootView
				.findViewById(R.id.txt_input_layout_power_rest_offpeak_time));
		txtInputPowerValleyOffpeak = ((ImprovedTextInputLayout) rootView
				.findViewById(R.id.txt_input_layout_power_valley_offpeak));
		txtInputPowerValleyOffpeakDate = ((ImprovedTextInputLayout) rootView
				.findViewById(R.id.txt_input_layout_power_valley_offpeak_date));
		txtInputPowerValleyOffpeakTime = ((ImprovedTextInputLayout) rootView
				.findViewById(R.id.txt_input_layout_power_valley_offpeak_time));
	}

	/**
	 * Asigna la informaci�n de la lectura al fragment
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
	 * Asigna los listeners de el textinput de la fecha actual a los campos de
	 * fecha de la lectura
	 */
	private void setDateListeners() {
		setTextInputDateListener(txtInputReadingDate);
		setTextInputDateListener(txtInputPowerPeakDate);
		setTextInputDateListener(txtInputPowerRestOffpeakDate);
		setTextInputDateListener(txtInputPowerValleyOffpeakDate);
	}

	/**
	 * Asigna el listener a para mostrar un DatePicker al textInputLayout
	 * requerido
	 * 
	 * @param txtInputLayoutDate
	 *            {@link ImprovedTextInputLayout}
	 */
	private void setTextInputDateListener(
			final ImprovedTextInputLayout txtInputLayoutDate) {
		txtInputLayoutDate
				.setEditTextOnFocusChangeListener(new OnFocusChangeListener() {
					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						if (hasFocus) {
							showDatePicker(txtInputLayoutDate.getEditText());
						}
					}

				});
		txtInputReadingDate.getEditText().setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						showDatePicker(txtInputLayoutDate.getEditText());
					}
				});
	}

	/**
	 * Muestra un DatePicker y llena la informaci�n en el texto provisto
	 * 
	 * @param txtToBindInfo
	 *            {@link EditText}
	 */
	private void showDatePicker(final EditText txtToBindInfo) {
		DateTime dateNow = DateTime.now();
		DatePickerDialog dpd = DatePickerDialog.newInstance(
				new OnDateSetListener() {
					@Override
					public void onDateSet(DatePickerDialog dpd, int year,
							int month, int day) {
						txtToBindInfo.setText(new DateTime(year, month, day, 0,
								0).toString("dd/MM/yyy"));
					}
				}, dateNow.getYear(), dateNow.getMonthOfYear(), dateNow
						.getDayOfMonth());
		dpd.show(getActivity().getFragmentManager(), "DatePickerDialog");
	}

	/**
	 * Asigna los listeners de el textinput de la hora actual a todos los campos
	 * de hora de la lectura
	 */
	private void setTimeListeners() {
		setTextInputTimeListener(txtInputReadingTime);
		setTextInputTimeListener(txtInputPowerPeakTime);
		setTextInputTimeListener(txtInputPowerRestOffpeakTime);
		setTextInputTimeListener(txtInputPowerValleyOffpeakTime);
	}

	/**
	 * Asigna el listener a para mostrar un DatePicker al textInputLayout
	 * requerido
	 * 
	 * @param txtInputLayoutTime
	 *            {@link ImprovedTextInputLayout}
	 */
	private void setTextInputTimeListener(
			final ImprovedTextInputLayout txtInputLayoutTime) {
		txtInputLayoutTime
				.setEditTextOnFocusChangeListener(new OnFocusChangeListener() {
					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						if (hasFocus) {
							showTimePicker(txtInputLayoutTime.getEditText());
						}
					}

				});
		txtInputLayoutTime.getEditText().setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						showTimePicker(txtInputLayoutTime.getEditText());
					}
				});
	}

	/**
	 * Muestra un TimePicker que llenar� la informaci�n en el respectivo campo
	 * de texto
	 * 
	 * @param txtToBindInfo
	 *            {@link EditText}
	 */
	private void showTimePicker(final EditText txtToBindInfo) {
		final DateTime dateNow = DateTime.now();
		TimePickerDialog tpd = TimePickerDialog.newInstance(
				new OnTimeSetListener() {
					@Override
					public void onTimeSet(RadialPickerLayout view,
							int hourOfDay, int minute) {
						txtToBindInfo.setText(new DateTime(dateNow.getYear(),
								dateNow.getMonthOfYear(), dateNow
										.getDayOfMonth(), hourOfDay, minute)
								.toString("HH:mm"));
					}
				}, dateNow.getHourOfDay(), dateNow.getMinuteOfHour(), true);
		tpd.show(getActivity().getFragmentManager(), "TimePickerDialog");
	}

	/**
	 * Asigna el listener de click al label de la informaci�n del cliente para
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
	 * Collapsa la informaci�n del cliente
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
	 * Muestra la informaci�n del cliente
	 */
	private void expandClientInfo() {
		mClientInfoCollapsed = false;
		Animation anim = new HeightAnimation(layoutClientInfo, 0,
				mClientInfoHeight);
		anim.setDuration(400);
		layoutClientInfo.startAnimation(anim);
	}
}
