package com.elfec.lecturas.gd.view;

import java.math.BigDecimal;

import org.apache.commons.lang.math.NumberUtils;
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
import com.elfec.lecturas.gd.helpers.ui.ButtonClicksHelper;
import com.elfec.lecturas.gd.model.ReadingGeneralInfo;
import com.elfec.lecturas.gd.presenter.ReadingPresenter;
import com.elfec.lecturas.gd.presenter.views.IReadingView;
import com.elfec.lecturas.gd.view.animations.HeightAnimation;
import com.elfec.lecturas.gd.view.controls.ImprovedTextInputLayout;
import com.elfec.lecturas.gd.view.listeners.OnReadingSaveClickListener;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog.OnTimeSetListener;

public class ReadingFragment extends Fragment implements IReadingView,
		OnReadingSaveClickListener {

	private ReadingPresenter presenter;

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

	private boolean mClientInfoCollapsed;
	private int mClientInfoHeight;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public ReadingFragment() {
		presenter = new ReadingPresenter(this);
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
		initializeClientInfoFields(rootView);
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

	/**
	 * Inicializa los campos de texto de información del cliente
	 * 
	 * @param rootView
	 */
	private void initializeClientInfoFields(final View rootView) {
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
	 * Asigna la información de la lectura al fragment
	 * 
	 * @param reading
	 */
	public void bindReadingInfo(ReadingGeneralInfo reading) {
		presenter.setCurrentReading(reading);
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
						if (hasFocus && ButtonClicksHelper.canClickButton())
							showDatePicker(txtInputLayoutDate.getEditText());
					}

				});
		txtInputLayoutDate.getEditText().setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (ButtonClicksHelper.canClickButton())
							showDatePicker(txtInputLayoutDate.getEditText());
					}
				});
	}

	/**
	 * Muestra un DatePicker y llena la información en el texto provisto y su
	 * tag con el objeto de {@link DateTime}
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
						DateTime date = new DateTime(year, month, day, 0, 0);
						txtToBindInfo.setText(date.toString("dd/MM/yyy"));
						txtToBindInfo.setTag(date);
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
						if (hasFocus && ButtonClicksHelper.canClickButton())
							showTimePicker(txtInputLayoutTime.getEditText());
					}

				});
		txtInputLayoutTime.getEditText().setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (ButtonClicksHelper.canClickButton())
							showTimePicker(txtInputLayoutTime.getEditText());
					}
				});
	}

	/**
	 * Muestra un TimePicker que llenará la información en el respectivo campo
	 * de texto y su tag con el objeto de {@link DateTime}
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
						DateTime time = new DateTime(dateNow.getYear(), dateNow
								.getMonthOfYear(), dateNow.getDayOfMonth(),
								hourOfDay, minute);
						txtToBindInfo.setText(time.toString("HH:mm"));
						txtToBindInfo.setTag(time);
					}
				}, dateNow.getHourOfDay(), dateNow.getMinuteOfHour(), true);
		tpd.show(getActivity().getFragmentManager(), "TimePickerDialog");
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

	// #region Interface Methods

	@Override
	public void setAccountNumber(String accountNumber) {
		txtAccountNumber.setText(accountNumber);
	}

	@Override
	public void setNUS(int NUS) {
		txtNUS.setText(NUS != -1 ? ("" + NUS) : "");
	}

	@Override
	public void setMeterSerialNumber(String serialNumber) {
		txtMeter.setText(serialNumber);
	}

	@Override
	public void setClientName(String name) {
		txtClientName.setText(name);
	}

	@Override
	public void setAddress(String address) {
		txtAddress.setText(address);
	}

	@Override
	public void setCategory(String category) {
		txtCategory.setText(category);
	}

	@Override
	public DateTime getReadingDate() {
		return (DateTime) txtInputReadingDate.getEditText().getTag();
	}

	@Override
	public void setReadingDate(DateTime date) {
		txtInputReadingDate.getEditText().setText(
				date != null ? date.toString("dd/MM/yyy") : null);
		txtInputReadingDate.getEditText().setTag(date);
	}

	@Override
	public DateTime getReadingTime() {
		return (DateTime) txtInputReadingTime.getEditText().getTag();
	}

	@Override
	public void setReadingTime(DateTime time) {
		txtInputReadingTime.getEditText().setText(
				time != null ? time.toString("HH:mm") : null);
		txtInputReadingTime.getEditText().setTag(time);
	}

	@Override
	public int getResetCount() {
		return NumberUtils.toInt(txtInputResetCount.getEditText().getText()
				.toString(), -1);
	}

	@Override
	public void setResetCount(int resetCount) {
		txtInputResetCount.getEditText().setText(
				resetCount != -1 ? ("" + resetCount) : null);
	}

	@Override
	public BigDecimal getActiveDistributing() {
		String activeDistributing = txtInputActivePeak.getEditText().getText()
				.toString();
		if (activeDistributing == null || activeDistributing.isEmpty())
			return null;
		return new BigDecimal(activeDistributing);
	}

	@Override
	public void setActiveDistributing(BigDecimal activeDistributing) {
		txtInputActivePeak.getEditText().setText(
				activeDistributing != null ? activeDistributing.toPlainString()
						: null);
		;
	}

	@Override
	public BigDecimal getActivePeak() {
		String activePeak = txtInputActivePeak.getEditText().getText()
				.toString();
		if (activePeak == null || activePeak.isEmpty())
			return null;
		return new BigDecimal(activePeak);
	}

	@Override
	public BigDecimal getActiveRest() {
		String activeRest = txtInputActiveRest.getEditText().getText()
				.toString();
		if (activeRest == null || activeRest.isEmpty())
			return null;
		return new BigDecimal(activeRest);
	}

	@Override
	public BigDecimal getActiveValley() {
		String activeValley = txtInputActiveValley.getEditText().getText()
				.toString();
		if (activeValley == null || activeValley.isEmpty())
			return null;
		return new BigDecimal(activeValley);
	}

	@Override
	public BigDecimal getReactiveDistributing() {
		String reactivePeak = txtInputReactivePeak.getEditText().getText()
				.toString();
		if (reactivePeak == null || reactivePeak.isEmpty())
			return null;
		return new BigDecimal(reactivePeak);
	}

	@Override
	public BigDecimal getReactivePeak() {
		String reactivePeak = txtInputReactivePeak.getEditText().getText()
				.toString();
		if (reactivePeak == null || reactivePeak.isEmpty())
			return null;
		return new BigDecimal(reactivePeak);
	}

	@Override
	public BigDecimal getReactiveRest() {
		String reactiveRest = txtInputReactiveRest.getEditText().getText()
				.toString();
		if (reactiveRest == null || reactiveRest.isEmpty())
			return null;
		return new BigDecimal(reactiveRest);
	}

	@Override
	public BigDecimal getReactiveValley() {
		String reactiveValley = txtInputReactiveValley.getEditText().getText()
				.toString();
		if (reactiveValley == null || reactiveValley.isEmpty())
			return null;
		return new BigDecimal(reactiveValley);
	}

	@Override
	public BigDecimal getPowerPeak() {
		String powerPeak = txtInputPowerPeak.getEditText().getText().toString();
		if (powerPeak == null || powerPeak.isEmpty())
			return null;
		return new BigDecimal(powerPeak);
	}

	@Override
	public DateTime getPowerPeakDate() {
		return (DateTime) txtInputPowerPeakDate.getEditText().getTag();
	}

	@Override
	public DateTime getPowerPeakTime() {
		return (DateTime) txtInputPowerPeakTime.getEditText().getTag();
	}

	@Override
	public BigDecimal getPowerRestOffpeak() {
		String powerRestOffpeak = txtInputPowerRestOffpeak.getEditText()
				.getText().toString();
		if (powerRestOffpeak == null || powerRestOffpeak.isEmpty())
			return null;
		return new BigDecimal(powerRestOffpeak);
	}

	@Override
	public DateTime getPowerRestOffpeakDate() {
		return (DateTime) txtInputPowerRestOffpeakDate.getEditText().getTag();
	}

	@Override
	public DateTime getPowerRestOffpeakTime() {
		return (DateTime) txtInputPowerRestOffpeakTime.getEditText().getTag();
	}

	@Override
	public BigDecimal getPowerValleyOffpeak() {
		String powerValleyOffpeak = txtInputPowerValleyOffpeak.getEditText()
				.getText().toString();
		if (powerValleyOffpeak == null || powerValleyOffpeak.isEmpty())
			return null;
		return new BigDecimal(powerValleyOffpeak);
	}

	@Override
	public DateTime getPowerValleyOffpeakDate() {
		return (DateTime) txtInputPowerValleyOffpeakDate.getEditText().getTag();
	}

	@Override
	public DateTime getPowerValleyOffpeakTime() {
		return (DateTime) txtInputPowerValleyOffpeakTime.getEditText().getTag();
	}

	@Override
	public void readingSaveClicked(View v) {
		presenter.saveReading();
	}

	@Override
	public void setActivePeak(BigDecimal activePeak) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setActiveRest(BigDecimal activeRest) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setActiveValley(BigDecimal activeValley) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setReactiveDistributing(BigDecimal reactiveDistributing) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setReactivePeak(BigDecimal reactivePeak) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setReactiveRest(BigDecimal reactiveRest) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setReactiveValley(BigDecimal reactiveValley) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPowerPeak(BigDecimal powerPeak) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPowerPeakDate(DateTime powerPeakDate) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPowerPeakTime(DateTime powerPeakTime) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPowerRestOffpeak(BigDecimal powerRestOffpeak) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPowerRestOffpeakDate(DateTime powerRestOffpeakDate) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPowerRestOffpeakTime(DateTime powerRestOffpeakTime) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPowerValleyOffpeak(BigDecimal powerValleyOffpeak) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPowerValleyOffpeakDate(DateTime powerValleyOffpeakDate) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPowerValleyOffpeakTime(DateTime powerValleyOffpeakTime) {
		// TODO Auto-generated method stub

	}

	// #endregion
}
