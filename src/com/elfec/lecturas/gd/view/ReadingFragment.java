package com.elfec.lecturas.gd.view;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.math.NumberUtils;
import org.joda.time.DateTime;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
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
import com.elfec.lecturas.gd.helpers.util.text.MessageListFormatter;
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

	/**
	 * la Key para obtener la lectura en este fragmento
	 */
	public static final String ARG_READING = "ReadingGeneralInfo";

	private ReadingPresenter presenter;

	private Handler mHandler;
	private Runnable snackBarRunnable;

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

	private CoordinatorLayout snackBarPosition;

	private boolean mClientInfoCollapsed;
	private int mClientInfoHeight;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public ReadingFragment() {
		presenter = new ReadingPresenter(this);
		mHandler = new Handler();
		snackBarRunnable = new Runnable() {
			@Override
			public void run() {
				Snackbar snackbar = Snackbar.make(snackBarPosition,
						R.string.error_in_reading_fields, Snackbar.LENGTH_LONG)
						.setAction(R.string.btn_ok, new OnClickListener() {
							@Override
							public void onClick(View v) {
							}
						});
				((TextView) snackbar.getView().findViewById(
						android.support.design.R.id.snackbar_text))
						.setMaxLines(3);
				snackbar.show();
			}
		};

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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.fragment_reading,
				container, false);
		new Thread(new Runnable() {
			@Override
			public void run() {
				if (getArguments().containsKey(ARG_READING)) {
					presenter.setCurrentReading(
							(ReadingGeneralInfo) getArguments()
									.getSerializable(ARG_READING), false);
				}
				snackBarPosition = (CoordinatorLayout) getActivity()
						.findViewById(R.id.snackbar_position);
				initializeClientInfoFields(rootView);
				initializeReadingFields(rootView);
				mHandler.post(new Runnable() {

					@Override
					public void run() {
						presenter.bindClientInfo();
						presenter.bindReadingTaken();
					}
				});
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

	/**
	 * Obtiene un campo de un edittext como BigDecimal
	 * 
	 * @param textField
	 * @return BigDecimal del campo
	 */
	private BigDecimal getBigDecimalFromField(EditText textField) {
		String fieldString = textField.getText().toString();
		if (fieldString == null || fieldString.isEmpty())
			return null;
		return new BigDecimal(fieldString);
	}

	/**
	 * Asigna una fecha a un campo de texto
	 * 
	 * @param textField
	 * @param date
	 */
	private void setDateToField(EditText textField, DateTime date) {
		textField.setText(date != null ? date.toString("dd/MM/yyy") : null);
		textField.setTag(date);
	}

	/**
	 * Asigna una hora a un campo de texto
	 * 
	 * @param textField
	 * @param time
	 */
	private void setTimeToField(EditText textField, DateTime time) {
		textField.setText(time != null ? time.toString("HH:mm") : null);
		textField.setTag(time);
	}

	/**
	 * Asigna un BigDecimal a un campo de texto
	 * 
	 * @param textField
	 * @param value
	 */
	private void setBigDecimalToField(EditText textField, BigDecimal value) {
		textField.setText(value != null ? value.toPlainString() : null);
	}

	/**
	 * Muestra errores en un campo de texto o los elimina si la lista de errores
	 * está vacía
	 * 
	 * @param txtInputField
	 * @param errors
	 */
	private void setErrorsOnField(final TextInputLayout txtInputField,
			final List<Exception> errors) {
		mHandler.post(new Runnable() {
			@Override
			public void run() {
				if (errors.size() > 0)
					txtInputField.setError(MessageListFormatter
							.fotmatHTMLFromErrors(errors));
				else
					txtInputField.setErrorEnabled(false);
			}
		});
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
	public DateTime getReadingTime() {
		return (DateTime) txtInputReadingTime.getEditText().getTag();
	}

	@Override
	public int getResetCount() {
		return NumberUtils.toInt(txtInputResetCount.getEditText().getText()
				.toString(), -1);
	}

	@Override
	public BigDecimal getActiveDistributing() {
		return getBigDecimalFromField(txtInputActivePeak.getEditText());
	}

	@Override
	public BigDecimal getActivePeak() {
		return getBigDecimalFromField(txtInputActivePeak.getEditText());
	}

	@Override
	public BigDecimal getActiveRest() {
		return getBigDecimalFromField(txtInputActiveRest.getEditText());
	}

	@Override
	public BigDecimal getActiveValley() {
		return getBigDecimalFromField(txtInputActiveValley.getEditText());
	}

	@Override
	public BigDecimal getReactiveDistributing() {
		return getBigDecimalFromField(txtInputReactivePeak.getEditText());
	}

	@Override
	public BigDecimal getReactivePeak() {
		return getBigDecimalFromField(txtInputReactivePeak.getEditText());
	}

	@Override
	public BigDecimal getReactiveRest() {
		return getBigDecimalFromField(txtInputReactiveRest.getEditText());
	}

	@Override
	public BigDecimal getReactiveValley() {
		return getBigDecimalFromField(txtInputReactiveValley.getEditText());
	}

	@Override
	public BigDecimal getPowerPeak() {
		return getBigDecimalFromField(txtInputPowerPeak.getEditText());
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
		return getBigDecimalFromField(txtInputPowerRestOffpeak.getEditText());
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
		return getBigDecimalFromField(txtInputPowerValleyOffpeak.getEditText());
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
	public void setReadingDate(DateTime date) {
		setDateToField(txtInputReadingDate.getEditText(), date);
	}

	@Override
	public void setReadingTime(DateTime time) {
		setTimeToField(txtInputReadingTime.getEditText(), time);
	}

	@Override
	public void setResetCount(int resetCount) {
		txtInputResetCount.getEditText().setText(
				resetCount != -1 ? ("" + resetCount) : null);
	}

	@Override
	public void setActiveDistributing(BigDecimal activeDistributing) {
		setBigDecimalToField(txtInputActivePeak.getEditText(),
				activeDistributing);
	}

	@Override
	public void setActivePeak(BigDecimal activePeak) {
		setBigDecimalToField(txtInputActivePeak.getEditText(), activePeak);
	}

	@Override
	public void setActiveRest(BigDecimal activeRest) {
		setBigDecimalToField(txtInputActiveRest.getEditText(), activeRest);
	}

	@Override
	public void setActiveValley(BigDecimal activeValley) {
		setBigDecimalToField(txtInputActiveValley.getEditText(), activeValley);
	}

	@Override
	public void setReactiveDistributing(BigDecimal reactiveDistributing) {
		setBigDecimalToField(txtInputReactivePeak.getEditText(),
				reactiveDistributing);
	}

	@Override
	public void setReactivePeak(BigDecimal reactivePeak) {
		setBigDecimalToField(txtInputReactivePeak.getEditText(), reactivePeak);
	}

	@Override
	public void setReactiveRest(BigDecimal reactiveRest) {
		setBigDecimalToField(txtInputReactiveRest.getEditText(), reactiveRest);
	}

	@Override
	public void setReactiveValley(BigDecimal reactiveValley) {
		setBigDecimalToField(txtInputReactiveValley.getEditText(),
				reactiveValley);
	}

	@Override
	public void setPowerPeak(BigDecimal powerPeak) {
		setBigDecimalToField(txtInputPowerPeak.getEditText(), powerPeak);
	}

	@Override
	public void setPowerPeakDate(DateTime powerPeakDate) {
		setDateToField(txtInputPowerPeakDate.getEditText(), powerPeakDate);
	}

	@Override
	public void setPowerPeakTime(DateTime powerPeakTime) {
		setTimeToField(txtInputPowerPeakTime.getEditText(), powerPeakTime);
	}

	@Override
	public void setPowerRestOffpeak(BigDecimal powerRestOffpeak) {
		setBigDecimalToField(txtInputPowerRestOffpeak.getEditText(),
				powerRestOffpeak);
	}

	@Override
	public void setPowerRestOffpeakDate(DateTime powerRestOffpeakDate) {
		setDateToField(txtInputPowerRestOffpeakDate.getEditText(),
				powerRestOffpeakDate);
	}

	@Override
	public void setPowerRestOffpeakTime(DateTime powerRestOffpeakTime) {
		setTimeToField(txtInputPowerRestOffpeakTime.getEditText(),
				powerRestOffpeakTime);
	}

	@Override
	public void setPowerValleyOffpeak(BigDecimal powerValleyOffpeak) {
		setBigDecimalToField(txtInputPowerValleyOffpeak.getEditText(),
				powerValleyOffpeak);
	}

	@Override
	public void setPowerValleyOffpeakDate(DateTime powerValleyOffpeakDate) {
		setDateToField(txtInputPowerValleyOffpeakDate.getEditText(),
				powerValleyOffpeakDate);
	}

	@Override
	public void setPowerValleyOffpeakTime(DateTime powerValleyOffpeakTime) {
		setTimeToField(txtInputPowerValleyOffpeakTime.getEditText(),
				powerValleyOffpeakTime);
	}

	@Override
	public void setReadingDateErrors(List<Exception> errors) {
		setErrorsOnField(txtInputReadingDate, errors);
	}

	@Override
	public void setReadingTimeErrors(List<Exception> errors) {
		setErrorsOnField(txtInputReadingTime, errors);
	}

	@Override
	public void setResetCountErrors(List<Exception> errors) {
		setErrorsOnField(txtInputResetCount, errors);
	}

	@Override
	public void setActiveDistributingErrors(List<Exception> errors) {
		setErrorsOnField(txtInputActivePeak, errors);
	}

	@Override
	public void setActivePeakErrors(List<Exception> errors) {
		setErrorsOnField(txtInputActivePeak, errors);
	}

	@Override
	public void setActiveRestErrors(List<Exception> errors) {
		setErrorsOnField(txtInputActiveRest, errors);
	}

	@Override
	public void setActiveValleyErrors(List<Exception> errors) {
		setErrorsOnField(txtInputActiveValley, errors);
	}

	@Override
	public void setReactiveDistributingErrors(List<Exception> errors) {
		setErrorsOnField(txtInputReactivePeak, errors);
	}

	@Override
	public void setReactivePeakErrors(List<Exception> errors) {
		setErrorsOnField(txtInputReactivePeak, errors);
	}

	@Override
	public void setReactiveRestErrors(List<Exception> errors) {
		setErrorsOnField(txtInputReactiveRest, errors);
	}

	@Override
	public void setReactiveValleyErrors(List<Exception> errors) {
		setErrorsOnField(txtInputReactiveValley, errors);
	}

	@Override
	public void setPowerPeakErrors(List<Exception> errors) {
		setErrorsOnField(txtInputPowerPeak, errors);
	}

	@Override
	public void setPowerPeakDateErrors(List<Exception> errors) {
		setErrorsOnField(txtInputPowerPeakDate, errors);
	}

	@Override
	public void setPowerPeakTimeErrors(List<Exception> errors) {
		setErrorsOnField(txtInputPowerPeakTime, errors);
	}

	@Override
	public void setPowerRestOffpeakErrors(List<Exception> errors) {
		setErrorsOnField(txtInputPowerRestOffpeak, errors);
	}

	@Override
	public void setPowerRestOffpeakDateErrors(List<Exception> errors) {
		setErrorsOnField(txtInputPowerRestOffpeakDate, errors);
	}

	@Override
	public void setPowerRestOffpeakTimeErrors(List<Exception> errors) {
		setErrorsOnField(txtInputPowerRestOffpeakTime, errors);
	}

	@Override
	public void setPowerValleyOffpeakErrors(List<Exception> errors) {
		setErrorsOnField(txtInputPowerValleyOffpeak, errors);
	}

	@Override
	public void setPowerValleyOffpeakDateErrors(List<Exception> errors) {
		setErrorsOnField(txtInputPowerValleyOffpeakDate, errors);
	}

	@Override
	public void setPowerValleyOffpeakTimeErrors(List<Exception> errors) {
		setErrorsOnField(txtInputPowerValleyOffpeakTime, errors);
	}

	@Override
	public void notifyErrorsInFields() {
		mHandler.post(snackBarRunnable);

	}

	// #endregion
}
