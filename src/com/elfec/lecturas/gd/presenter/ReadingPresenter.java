package com.elfec.lecturas.gd.presenter;

import java.math.BigDecimal;

import org.apache.commons.lang.WordUtils;
import org.joda.time.DateTime;

import com.elfec.lecturas.gd.business_logic.ReadingTakenManager;
import com.elfec.lecturas.gd.business_logic.validators.ReadingFieldsValidator;
import com.elfec.lecturas.gd.helpers.util.text.AccountFormatter;
import com.elfec.lecturas.gd.model.ReadingGeneralInfo;
import com.elfec.lecturas.gd.model.ReadingMeter;
import com.elfec.lecturas.gd.model.ReadingTaken;
import com.elfec.lecturas.gd.model.enums.ReadingStatus;
import com.elfec.lecturas.gd.model.results.VoidResult;
import com.elfec.lecturas.gd.presenter.views.IReadingView;

/**
 * Presenter para las vistas de lecturas
 * 
 * @author drodriguez
 *
 */
public class ReadingPresenter {

	private ReadingGeneralInfo reading;
	private IReadingView view;

	public ReadingPresenter(IReadingView view) {
		this.view = view;
	}

	/**
	 * Asigna la lectura actual y su informaci�n a la vista
	 * 
	 * @param reading
	 */
	public void setCurrentReading(ReadingGeneralInfo reading) {
		setCurrentReading(reading, true);
	}

	/**
	 * Asigna la lectura actual y su informaci�n a la vista en caso de
	 * requerirse as�
	 * 
	 * @param reading
	 * @param bind
	 *            si es verdadero se actualizar� la informaci�n de la vista con
	 *            la informaci�n de la lectura
	 */
	public void setCurrentReading(ReadingGeneralInfo reading, boolean bind) {
		this.reading = reading;
		if (bind) {
			bindClientInfo();
			bindFieldsVisibility();
			view.clearAllFieldsAndErrors(reading.getReadingTaken() != null);
			bindReadingTaken();
		}
	}

	/**
	 * Asigna a la vista la informaci�n del cliente de la lectura actual con el
	 * formato de presentaci�n correcto
	 */
	public void bindClientInfo() {
		view.setAccountNumber(AccountFormatter.formatAccountNumber(reading
				.getSupplyNumber()));
		view.setNUS(reading.getSupplyId());
		view.setMeterSerialNumber(reading.getReadingMeter().getSerialNumber());
		view.setClientName(WordUtils.capitalizeFully(reading.getName(),
				new char[] { '.', ' ' }));
		view.setAddress(WordUtils.capitalizeFully(reading.getAddress(),
				new char[] { '.', ' ' }));
		view.setCategory(reading.getCategoryDescription());
		view.setReadingStatus(reading.getStatus());
	}

	/**
	 * Asigna a la vista la visibilidad de campos requerida seg�n el tipo de
	 * medidor
	 */
	public void bindFieldsVisibility() {
		ReadingMeter readingMeter = reading.getReadingMeter();
		view.setActiveDistributionVisible(!readingMeter.getTagActivePeak()
				.equals(BigDecimal.ZERO));
		view.setReactiveEnergyVisible(!readingMeter
				.getTagReactiveDistributing().equals(BigDecimal.ZERO)
				|| !readingMeter.getTagReactivePeak().equals(BigDecimal.ZERO));
		view.setReactiveDistributionVisible(!readingMeter.getTagReactivePeak()
				.equals(BigDecimal.ZERO));
		view.setEnergyPowerVisible(!readingMeter.getTagPowerPeak().equals(
				BigDecimal.ZERO));
	}

	/**
	 * Asigna a la vista la informaci�n de la lectura tomada
	 */
	public void bindReadingTaken() {
		ReadingTaken readingTaken = reading.getReadingTaken();
		if (readingTaken != null) {
			view.setReadOnly(true);
			// #region readingTaken view assignations
			view.setReadingDate(readingTaken.getReadingDate());
			view.setReadingTime(readingTaken.getReadingDate());
			view.setResetCount(readingTaken.getResetCount());
			view.setActiveDistributing(readingTaken.getActiveDistributing());
			view.setActivePeak(readingTaken.getActivePeak());
			view.setActiveRest(readingTaken.getActiveRest());
			view.setActiveValley(readingTaken.getActiveValley());
			view.setReactiveDistributing(readingTaken.getReactiveDistributing());
			view.setReactivePeak(readingTaken.getReactivePeak());
			view.setReactiveRest(readingTaken.getReactiveRest());
			view.setReactiveValley(readingTaken.getReactiveValley());
			view.setPowerPeak(readingTaken.getPowerPeak());
			view.setPowerPeakDate(readingTaken.getPowerPeakDate());
			view.setPowerPeakTime(readingTaken.getPowerPeakDate());
			view.setPowerRestOffpeak(readingTaken.getPowerRestOffpeak());
			view.setPowerRestOffpeakDate(readingTaken.getPowerRestOffpeakDate());
			view.setPowerRestOffpeakTime(readingTaken.getPowerRestOffpeakDate());
			view.setPowerValleyOffpeak(readingTaken.getPowerValleyOffpeak());
			view.setPowerValleyOffpeakDate(readingTaken
					.getPowerValleyOffpeakDate());
			view.setPowerValleyOffpeakTime(readingTaken
					.getPowerValleyOffpeakDate());
			// #endregion
		} else
			view.setReadOnly(false);
	}

	/**
	 * Inicia el proceso de validaci�n y guardado de la lectura
	 */
	public void saveReading() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				if (validateFields()) {
					VoidResult result = ReadingTakenManager.registerReadingTaken(
							reading,
							new ReadingTaken(reading.getReadingRemoteId(),
									reading.getSupplyId(), DateTime.now(),
									joinDateAndTime(view.getReadingDate(),
											view.getReadingTime()), view
											.getResetCount(), view
											.getActiveDistributing(), view
											.getActivePeak(), view
											.getActiveRest(), view
											.getActiveValley(), view
											.getReactiveDistributing(), view
											.getReactivePeak(), view
											.getReactiveRest(), view
											.getReactiveValley(), view
											.getPowerPeak(), joinDateAndTime(
											view.getPowerPeakDate(),
											view.getPowerPeakTime()), view
											.getPowerRestOffpeak(),
									joinDateAndTime(
											view.getPowerRestOffpeakDate(),
											view.getPowerRestOffpeakTime()),
									view.getPowerValleyOffpeak(),
									joinDateAndTime(
											view.getPowerValleyOffpeakDate(),
											view.getPowerValleyOffpeakTime())),
							ReadingStatus.READ);
					view.showReadingSaveErrors(result.getErrors());
					if (!result.hasErrors()) {
						view.notifyReadingSavedSuccessfully();
						view.setReadOnly(true);
					}
				} else
					view.notifyErrorsInFields();
			}
		}).start();
	}

	/**
	 * Junta un campo de fecha y de hora en un solo objeto
	 * 
	 * @param date
	 * @param time
	 * @return fecha y hora juntadas
	 */
	private DateTime joinDateAndTime(DateTime date, DateTime time) {
		return new DateTime(date.getYear(), date.getMonthOfYear(),
				date.getDayOfMonth(), time.getHourOfDay(),
				time.getMinuteOfHour());
	}

	/**
	 * Valida todos los campos de la lectura
	 * 
	 * @return
	 */
	private boolean validateFields() {
		boolean allAreValid = true;
		allAreValid = validateReadingDate() && allAreValid;
		allAreValid = validateReadingTime() && allAreValid;
		allAreValid = validateResetCount() && allAreValid;

		allAreValid = validateActiveEnergyFields() && allAreValid;
		allAreValid = validateReactiveEnergyFields() && allAreValid;
		allAreValid = validateEnergyPowerFields() && allAreValid;
		return allAreValid;
	}

	// #region validaciones de lectura
	/**
	 * Valida el campo de fecha de lectura
	 * 
	 * @return true si es v�lido
	 */
	public boolean validateReadingDate() {
		VoidResult result = ReadingFieldsValidator.validateDateTime(
				view.getReadingDate(), "fecha de lectura");
		view.setReadingDateErrors(result.getErrors());
		return !result.hasErrors();
	}

	/**
	 * Valida el campo de hora de lectura
	 * 
	 * @return true si es v�lido
	 */
	public boolean validateReadingTime() {
		VoidResult result = ReadingFieldsValidator.validateDateTime(
				view.getReadingTime(), "hora de lectura");
		view.setReadingTimeErrors(result.getErrors());
		return !result.hasErrors();
	}

	/**
	 * Valida el campo de n�mero de reset
	 * 
	 * @return true si es v�lido
	 */
	public boolean validateResetCount() {
		VoidResult result = ReadingFieldsValidator.validateResetCount(view
				.getResetCount());
		view.setResetCountErrors(result.getErrors());
		return !result.hasErrors();
	}

	// #endregion

	// #region validaciones energ�a activa

	/**
	 * Valida los campos de enrg�a activa
	 * 
	 * @param allAreValid
	 * @return true si todos los campos son validos
	 */
	private boolean validateActiveEnergyFields() {
		boolean allAreValid = true;
		allAreValid = validateActiveDistributing() && allAreValid;
		allAreValid = validateActivePeak() && allAreValid;
		allAreValid = validateActiveRest() && allAreValid;
		allAreValid = validateActiveValley() && allAreValid;
		return allAreValid;
	}

	/**
	 * Valida el campo de energ�a activa a distirbuir/total
	 * 
	 * @return true si es v�lido
	 */
	public boolean validateActiveDistributing() {
		VoidResult result = ReadingFieldsValidator.validateActiveDistributing(
				view.getActiveDistributing(), view.getActivePeak(),
				view.getActiveRest(), view.getActiveValley());
		view.setActiveDistributingErrors(result.getErrors());
		return !result.hasErrors();
	}

	/**
	 * Valida el campo de energ�a activa pico (Rate A)
	 * 
	 * @return true si es v�lido
	 */
	public boolean validateActivePeak() {
		VoidResult result = ReadingFieldsValidator.validateActiveEnergyField(
				view.getActivePeak(), "energ�a activa Rate A");
		view.setActivePeakErrors(result.getErrors());
		return !result.hasErrors();
	}

	/**
	 * Valida el campo de energ�a activa resto (Rate B)
	 * 
	 * @return true si es v�lido
	 */
	public boolean validateActiveRest() {
		VoidResult result = ReadingFieldsValidator.validateActiveEnergyField(
				view.getActiveRest(), "energ�a activa Rate B");
		view.setActiveRestErrors(result.getErrors());
		return !result.hasErrors();
	}

	/**
	 * Valida el campo de energ�a activa valle (Rate C)
	 * 
	 * @return true si es v�lido
	 */
	public boolean validateActiveValley() {
		VoidResult result = ReadingFieldsValidator.validateActiveEnergyField(
				view.getActiveValley(), "energ�a activa Rate C");
		view.setActiveValleyErrors(result.getErrors());
		return !result.hasErrors();
	}

	// #endregion

	// #region validaciones energ�a reactiva

	/**
	 * Valida los campos de energ�a reactiva
	 * 
	 * @param allAreValid
	 * @return true si todos los campos son validos
	 */
	private boolean validateReactiveEnergyFields() {
		boolean allAreValid = true;
		allAreValid = validateReactiveDistributing() && allAreValid;
		allAreValid = validateReactivePeak() && allAreValid;
		allAreValid = validateReactiveRest() && allAreValid;
		allAreValid = validateReactiveValley() && allAreValid;
		return allAreValid;
	}

	/**
	 * Valida el campo de energ�a reactiva a distirbuir/total
	 * 
	 * @return true si es v�lido
	 */
	public boolean validateReactiveDistributing() {
		VoidResult result = ReadingFieldsValidator
				.validateReactiveDistributing(view.getReactiveDistributing(),
						view.getReactivePeak(), view.getReactiveRest(),
						view.getReactiveValley());
		view.setReactiveDistributingErrors(result.getErrors());
		return !result.hasErrors();
	}

	/**
	 * Valida el campo de energ�a reactiva pico (Rate A)
	 * 
	 * @return true si es v�lido
	 */
	public boolean validateReactivePeak() {
		VoidResult result = ReadingFieldsValidator.validateReactiveEnergyField(
				view.getReactivePeak(), "energ�a reactiva Rate A");
		view.setReactivePeakErrors(result.getErrors());
		return !result.hasErrors();
	}

	/**
	 * Valida el campo de energ�a reactiva resto (Rate B)
	 * 
	 * @return true si es v�lido
	 */
	public boolean validateReactiveRest() {
		VoidResult result = ReadingFieldsValidator.validateReactiveEnergyField(
				view.getReactiveRest(), "energ�a reactiva Rate B");
		view.setReactiveRestErrors(result.getErrors());
		return !result.hasErrors();
	}

	/**
	 * Valida el campo de energ�a reactiva valle (Rate C)
	 * 
	 * @return true si es v�lido
	 */
	public boolean validateReactiveValley() {
		VoidResult result = ReadingFieldsValidator.validateReactiveEnergyField(
				view.getReactiveValley(), "energ�a reactiva Rate C");
		view.setReactiveValleyErrors(result.getErrors());
		return !result.hasErrors();
	}

	// #endregion

	// #region validaciones demanda m�xima

	/**
	 * Valida los campos de la demanda m�xima
	 * 
	 * @param allAreValid
	 * @return true si todos los campos son validos
	 */
	private boolean validateEnergyPowerFields() {
		boolean allAreValid = true;
		allAreValid = validatePowerPeak() && allAreValid;
		allAreValid = validatePowerPeakDate() && allAreValid;
		allAreValid = validatePowerPeakTime() && allAreValid;

		allAreValid = validatePowerRestOffpeak() && allAreValid;
		allAreValid = validatePowerRestOffpeakDate() && allAreValid;
		allAreValid = validatePowerRestOffpeakTime() && allAreValid;

		allAreValid = validatePowerValleyOffpeak() && allAreValid;
		allAreValid = validatePowerValleyOffpeakDate() && allAreValid;
		allAreValid = validatePowerValleyOffpeakTime() && allAreValid;
		return allAreValid;
	}

	/**
	 * Valida el campo de demanda m�xima punta (Rate A)
	 * 
	 * @return true si es v�lido
	 */
	public boolean validatePowerPeak() {
		VoidResult result = ReadingFieldsValidator.validateEnergyPowerField(
				view.getPowerPeak(), "demanda m�xima Rate A");
		view.setPowerPeakErrors(result.getErrors());
		return !result.hasErrors();
	}

	/**
	 * Valida el campo de la fecha de la demanda m�xima punta (Rate A)
	 * 
	 * @return true si es v�lido
	 */
	public boolean validatePowerPeakDate() {
		VoidResult result = ReadingFieldsValidator.validateDateTime(
				view.getPowerPeakDate(), "fecha demanda m�x. Rate A");
		view.setPowerPeakDateErrors(result.getErrors());
		return !result.hasErrors();
	}

	/**
	 * Valida el campo de la hora de la demanda m�xima punta (Rate A)
	 * 
	 * @return true si es v�lido
	 */
	public boolean validatePowerPeakTime() {
		VoidResult result = ReadingFieldsValidator.validateDateTime(
				view.getPowerPeakTime(), "hora demanda m�x. Rate A");
		view.setPowerPeakTimeErrors(result.getErrors());
		return !result.hasErrors();
	}

	/**
	 * Valida el campo de demanda m�xima fuera de punta resto (Rate B)
	 * 
	 * @return true si es v�lido
	 */
	public boolean validatePowerRestOffpeak() {
		VoidResult result = ReadingFieldsValidator.validateEnergyPowerField(
				view.getPowerRestOffpeak(), "demanda m�xima Rate B");
		view.setPowerRestOffpeakErrors(result.getErrors());
		return !result.hasErrors();
	}

	/**
	 * Valida el campo de la fecha de la demanda m�xima fuera de punta resto
	 * (Rate B)
	 * 
	 * @return true si es v�lido
	 */
	public boolean validatePowerRestOffpeakDate() {
		VoidResult result = ReadingFieldsValidator.validateDateTime(
				view.getPowerRestOffpeakDate(), "fecha demanda m�x. Rate B");
		view.setPowerRestOffpeakDateErrors(result.getErrors());
		return !result.hasErrors();
	}

	/**
	 * Valida el campo de la hora de la demanda m�xima fuera de punta resto
	 * (Rate B)
	 * 
	 * @return true si es v�lido
	 */
	public boolean validatePowerRestOffpeakTime() {
		VoidResult result = ReadingFieldsValidator.validateDateTime(
				view.getPowerRestOffpeakTime(), "hora demanda m�x. Rate B");
		view.setPowerRestOffpeakTimeErrors(result.getErrors());
		return !result.hasErrors();
	}

	/**
	 * Valida el campo de demanda m�xima fuera de punta valle (Rate C)
	 * 
	 * @return true si es v�lido
	 */
	public boolean validatePowerValleyOffpeak() {
		VoidResult result = ReadingFieldsValidator.validateEnergyPowerField(
				view.getPowerValleyOffpeak(), "demanda m�xima Rate C");
		view.setPowerValleyOffpeakErrors(result.getErrors());
		return !result.hasErrors();
	}

	/**
	 * Valida el campo de la fecha de la demanda m�xima fuera de punta valle
	 * (Rate C)
	 * 
	 * @return true si es v�lido
	 */
	public boolean validatePowerValleyOffpeakDate() {
		VoidResult result = ReadingFieldsValidator.validateDateTime(
				view.getPowerValleyOffpeakDate(), "fecha demanda m�x. Rate C");
		view.setPowerValleyOffpeakDateErrors(result.getErrors());
		return !result.hasErrors();
	}

	/**
	 * Valida el campo de la hora de la demanda m�xima fuera de punta valle
	 * (Rate C)
	 * 
	 * @return true si es v�lido
	 */
	public boolean validatePowerValleyOffpeakTime() {
		VoidResult result = ReadingFieldsValidator.validateDateTime(
				view.getPowerValleyOffpeakTime(), "hora demanda m�x. Rate C");
		view.setPowerValleyOffpeakTimeErrors(result.getErrors());
		return !result.hasErrors();
	}

	// #endregion

	public boolean validateFieldByNumber(int fieldNumber) {
		switch (fieldNumber) {
		case 0:
			return validateReadingDate();
		case 1:
			return validateReadingTime();
		case 2:
			return validateResetCount();
		case 3:
			return validateActiveDistributing();
		case 4:
			return validateActivePeak();
		case 5:
			return validateActiveRest();
		case 6:
			return validateActiveValley();
		case 7:
			return validateReactiveDistributing();
		case 8:
			return validateReactivePeak();
		case 9:
			return validateReactiveRest();
		case 10:
			return validateReactiveValley();
		case 11:
			return validatePowerPeak();
		case 12:
			return validatePowerPeakDate();
		case 13:
			return validatePowerPeakTime();
		case 14:
			return validatePowerRestOffpeak();
		case 15:
			return validatePowerRestOffpeakDate();
		case 16:
			return validatePowerRestOffpeakTime();
		case 17:
			return validatePowerValleyOffpeak();
		case 18:
			return validatePowerValleyOffpeakDate();
		case 19:
			return validatePowerValleyOffpeakTime();
		default:
			return false;
		}
	}
}
