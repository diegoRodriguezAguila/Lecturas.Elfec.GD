package com.elfec.lecturas.gd.presenter;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.WordUtils;
import org.joda.time.DateTime;

import com.elfec.lecturas.gd.business_logic.ReadingOrdenativeManager;
import com.elfec.lecturas.gd.business_logic.ReadingTakenManager;
import com.elfec.lecturas.gd.business_logic.SessionManager;
import com.elfec.lecturas.gd.business_logic.validators.ReadingFieldsValidator;
import com.elfec.lecturas.gd.helpers.util.text.AccountFormatter;
import com.elfec.lecturas.gd.model.ReadingGeneralInfo;
import com.elfec.lecturas.gd.model.ReadingMeter;
import com.elfec.lecturas.gd.model.ReadingTaken;
import com.elfec.lecturas.gd.model.enums.ReadingStatus;
import com.elfec.lecturas.gd.model.exceptions.ValidationException;
import com.elfec.lecturas.gd.model.results.VoidResult;
import com.elfec.lecturas.gd.presenter.views.IReadingView;
import com.elfec.lecturas.gd.presenter.views.callbacks.ReadingSaveCallback;

/**
 * Presenter para las vistas de lecturas
 * 
 * @author drodriguez
 *
 */
public class ReadingPresenter {

	private ReadingGeneralInfo reading;
	private IReadingView view;

	private ReadingSaveCallback readingCallback;
	private boolean validateActiveDistribution;
	private boolean validateReactiveEnergy;
	private boolean validateReactiveDistribution;
	private boolean validateEnergyPower;
	private volatile boolean isInEditionMode;

	public ReadingPresenter(IReadingView view) {
		this.view = view;
	}

	/**
	 * Asigna la lectura actual y su información a la vista
	 * 
	 * @param reading
	 */
	public void setCurrentReading(ReadingGeneralInfo reading) {
		setCurrentReading(reading, true);
	}

	/**
	 * Asigna la lectura actual y su información a la vista en caso de
	 * requerirse así
	 * 
	 * @param reading
	 * @param bind
	 *            si es verdadero se actualizará la información de la vista con
	 *            la información de la lectura
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
	 * Asigna a la vista la información del cliente de la lectura actual con el
	 * formato de presentación correcto
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
		ReadingMeter readingMeter = reading.getReadingMeter();
		view.setActiveMult(readingMeter.getActiveMultiplicator());
		view.setReactiveMult(readingMeter.getReactiveMultiplicator());
	}

	/**
	 * Asigna a la vista la visibilidad de campos requerida según el tipo de
	 * medidor
	 */
	public void bindFieldsVisibility() {
		ReadingMeter readingMeter = reading.getReadingMeter();
		validateActiveDistribution = !readingMeter.getTagActivePeak().equals(
				BigDecimal.ZERO);
		validateReactiveDistribution = !readingMeter.getTagReactivePeak()
				.equals(BigDecimal.ZERO);
		validateReactiveEnergy = !readingMeter.getTagReactiveDistributing()
				.equals(BigDecimal.ZERO) || validateReactiveDistribution;
		validateEnergyPower = !readingMeter.getTagPowerPeak().equals(
				BigDecimal.ZERO);
		view.setActiveDistributionVisible(validateActiveDistribution);
		view.setReactiveEnergyVisible(validateReactiveEnergy);
		view.setReactiveDistributionVisible(validateReactiveDistribution);
		view.setEnergyPowerVisible(validateEnergyPower);
	}

	/**
	 * Asigna a la vista la información de la lectura tomada
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
	 * Inicia el proceso de validación y guardado de la lectura
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
									SessionManager.getLoggedInUsername(),
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
					boolean wasInEditionMode = isInEditionMode;
					if (!result.hasErrors() && isInEditionMode) {
						isInEditionMode = false;
						result = new ReadingOrdenativeManager()
								.deleteReadingAssignedOrdenatives(reading);
					}
					if (!result.hasErrors()) {
						view.notifyReadingSavedSuccessfully();
						view.setReadingStatus(reading.getStatus());
						view.setReadOnly(true);
						if (readingCallback != null)
							readingCallback.onReadingSavedSuccesfully(reading,
									wasInEditionMode);
					} else {
						view.showReadingSaveErrors(result.getErrors());
						if (readingCallback != null)
							readingCallback.onReadingSaveErrors(result
									.getErrors());
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
	 * @return true si es válido
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
	 * @return true si es válido
	 */
	public boolean validateReadingTime() {
		VoidResult result = ReadingFieldsValidator.validateDateTime(
				view.getReadingTime(), "hora de lectura");
		view.setReadingTimeErrors(result.getErrors());
		return !result.hasErrors();
	}

	/**
	 * Valida el campo de número de reset
	 * 
	 * @return true si es válido
	 */
	public boolean validateResetCount() {
		VoidResult result = ReadingFieldsValidator.validateResetCount(view
				.getResetCount());
		view.setResetCountErrors(result.getErrors());
		return !result.hasErrors();
	}

	// #endregion

	// #region validaciones energía activa

	/**
	 * Valida los campos de enrgía activa
	 * 
	 * @param allAreValid
	 * @return true si todos los campos son validos
	 */
	private boolean validateActiveEnergyFields() {
		boolean allAreValid = true;
		allAreValid = validateActiveDistributing() && allAreValid;
		if (validateActiveDistribution) {
			allAreValid = validateActivePeak() && allAreValid;
			allAreValid = validateActiveRest() && allAreValid;
			allAreValid = validateActiveValley() && allAreValid;
		}
		return allAreValid;
	}

	/**
	 * Valida el campo de energía activa a distirbuir/total
	 * 
	 * @return true si es válido
	 */
	public boolean validateActiveDistributing() {
		VoidResult result = ReadingFieldsValidator.validateActiveDistributing(
				view.getActiveDistributing(), view.getActivePeak(),
				view.getActiveRest(), view.getActiveValley(),
				validateActiveDistribution);
		view.setActiveDistributingErrors(result.getErrors());
		List<Exception> errors = result.getErrors();
		int count = 0;
		for (Exception error : errors) {
			if (error instanceof ValidationException
					&& ((ValidationException) error).isMandatory()) {
				count++;
			}
		}
		return count == 0;
	}

	/**
	 * Valida el campo de energía activa pico (Rate A)
	 * 
	 * @return true si es válido
	 */
	public boolean validateActivePeak() {
		VoidResult result = ReadingFieldsValidator.validateActiveEnergyField(
				view.getActivePeak(), "energía activa Rate A");
		view.setActivePeakErrors(result.getErrors());
		validateActiveDistributing();
		return !result.hasErrors();
	}

	/**
	 * Valida el campo de energía activa resto (Rate B)
	 * 
	 * @return true si es válido
	 */
	public boolean validateActiveRest() {
		VoidResult result = ReadingFieldsValidator.validateActiveEnergyField(
				view.getActiveRest(), "energía activa Rate B");
		view.setActiveRestErrors(result.getErrors());
		validateActiveDistributing();
		return !result.hasErrors();
	}

	/**
	 * Valida el campo de energía activa valle (Rate C)
	 * 
	 * @return true si es válido
	 */
	public boolean validateActiveValley() {
		VoidResult result = ReadingFieldsValidator.validateActiveEnergyField(
				view.getActiveValley(), "energía activa Rate C");
		view.setActiveValleyErrors(result.getErrors());
		validateActiveDistributing();
		return !result.hasErrors();
	}

	// #endregion

	// #region validaciones energía reactiva

	/**
	 * Valida los campos de energía reactiva
	 * 
	 * @param allAreValid
	 * @return true si todos los campos son validos
	 */
	private boolean validateReactiveEnergyFields() {
		boolean allAreValid = true;
		if (validateReactiveEnergy) {
			allAreValid = validateReactiveDistributing() && allAreValid;
			if (validateReactiveDistribution) {
				allAreValid = validateReactivePeak() && allAreValid;
				allAreValid = validateReactiveRest() && allAreValid;
				allAreValid = validateReactiveValley() && allAreValid;
			}
		}
		return allAreValid;
	}

	/**
	 * Valida el campo de energía reactiva a distirbuir/total
	 * 
	 * @return true si es válido
	 */
	public boolean validateReactiveDistributing() {
		VoidResult result = ReadingFieldsValidator
				.validateReactiveDistributing(view.getReactiveDistributing(),
						view.getReactivePeak(), view.getReactiveRest(),
						view.getReactiveValley(), validateReactiveDistribution);
		view.setReactiveDistributingErrors(result.getErrors());
		List<Exception> errors = result.getErrors();
		int count = 0;
		for (Exception error : errors) {
			if (error instanceof ValidationException
					&& ((ValidationException) error).isMandatory()) {
				count++;
			}
		}
		return count == 0;
	}

	/**
	 * Valida el campo de energía reactiva pico (Rate A)
	 * 
	 * @return true si es válido
	 */
	public boolean validateReactivePeak() {
		VoidResult result = ReadingFieldsValidator.validateReactiveEnergyField(
				view.getReactivePeak(), "energía reactiva Rate A");
		view.setReactivePeakErrors(result.getErrors());
		validateReactiveDistributing();
		return !result.hasErrors();
	}

	/**
	 * Valida el campo de energía reactiva resto (Rate B)
	 * 
	 * @return true si es válido
	 */
	public boolean validateReactiveRest() {
		VoidResult result = ReadingFieldsValidator.validateReactiveEnergyField(
				view.getReactiveRest(), "energía reactiva Rate B");
		view.setReactiveRestErrors(result.getErrors());
		validateReactiveDistributing();
		return !result.hasErrors();
	}

	/**
	 * Valida el campo de energía reactiva valle (Rate C)
	 * 
	 * @return true si es válido
	 */
	public boolean validateReactiveValley() {
		VoidResult result = ReadingFieldsValidator.validateReactiveEnergyField(
				view.getReactiveValley(), "energía reactiva Rate C");
		view.setReactiveValleyErrors(result.getErrors());
		validateReactiveDistributing();
		return !result.hasErrors();
	}

	// #endregion

	// #region validaciones demanda máxima

	/**
	 * Valida los campos de la demanda máxima
	 * 
	 * @param allAreValid
	 * @return true si todos los campos son validos
	 */
	private boolean validateEnergyPowerFields() {
		boolean allAreValid = true;
		if (validateEnergyPower) {
			allAreValid = validatePowerPeak() && allAreValid;
			allAreValid = validatePowerPeakDate() && allAreValid;
			allAreValid = validatePowerPeakTime() && allAreValid;

			allAreValid = validatePowerRestOffpeak() && allAreValid;
			allAreValid = validatePowerRestOffpeakDate() && allAreValid;
			allAreValid = validatePowerRestOffpeakTime() && allAreValid;

			allAreValid = validatePowerValleyOffpeak() && allAreValid;
			allAreValid = validatePowerValleyOffpeakDate() && allAreValid;
			allAreValid = validatePowerValleyOffpeakTime() && allAreValid;
		}
		return allAreValid;
	}

	/**
	 * Valida el campo de demanda máxima punta (Rate A)
	 * 
	 * @return true si es válido
	 */
	public boolean validatePowerPeak() {
		VoidResult result = ReadingFieldsValidator.validateEnergyPowerField(
				view.getPowerPeak(), "demanda máxima Rate A");
		view.setPowerPeakErrors(result.getErrors());
		return !result.hasErrors();
	}

	/**
	 * Valida el campo de la fecha de la demanda máxima punta (Rate A)
	 * 
	 * @return true si es válido
	 */
	public boolean validatePowerPeakDate() {
		VoidResult result = ReadingFieldsValidator.validateDateTime(
				view.getPowerPeakDate(), "fecha demanda máx. Rate A");
		view.setPowerPeakDateErrors(result.getErrors());
		return !result.hasErrors();
	}

	/**
	 * Valida el campo de la hora de la demanda máxima punta (Rate A)
	 * 
	 * @return true si es válido
	 */
	public boolean validatePowerPeakTime() {
		VoidResult result = ReadingFieldsValidator.validateDateTime(
				view.getPowerPeakTime(), "hora demanda máx. Rate A");
		view.setPowerPeakTimeErrors(result.getErrors());
		return !result.hasErrors();
	}

	/**
	 * Valida el campo de demanda máxima fuera de punta resto (Rate B)
	 * 
	 * @return true si es válido
	 */
	public boolean validatePowerRestOffpeak() {
		VoidResult result = ReadingFieldsValidator.validateEnergyPowerField(
				view.getPowerRestOffpeak(), "demanda máxima Rate B");
		view.setPowerRestOffpeakErrors(result.getErrors());
		return !result.hasErrors();
	}

	/**
	 * Valida el campo de la fecha de la demanda máxima fuera de punta resto
	 * (Rate B)
	 * 
	 * @return true si es válido
	 */
	public boolean validatePowerRestOffpeakDate() {
		VoidResult result = ReadingFieldsValidator.validateDateTime(
				view.getPowerRestOffpeakDate(), "fecha demanda máx. Rate B");
		view.setPowerRestOffpeakDateErrors(result.getErrors());
		return !result.hasErrors();
	}

	/**
	 * Valida el campo de la hora de la demanda máxima fuera de punta resto
	 * (Rate B)
	 * 
	 * @return true si es válido
	 */
	public boolean validatePowerRestOffpeakTime() {
		VoidResult result = ReadingFieldsValidator.validateDateTime(
				view.getPowerRestOffpeakTime(), "hora demanda máx. Rate B");
		view.setPowerRestOffpeakTimeErrors(result.getErrors());
		return !result.hasErrors();
	}

	/**
	 * Valida el campo de demanda máxima fuera de punta valle (Rate C)
	 * 
	 * @return true si es válido
	 */
	public boolean validatePowerValleyOffpeak() {
		VoidResult result = ReadingFieldsValidator.validateEnergyPowerField(
				view.getPowerValleyOffpeak(), "demanda máxima Rate C");
		view.setPowerValleyOffpeakErrors(result.getErrors());
		return !result.hasErrors();
	}

	/**
	 * Valida el campo de la fecha de la demanda máxima fuera de punta valle
	 * (Rate C)
	 * 
	 * @return true si es válido
	 */
	public boolean validatePowerValleyOffpeakDate() {
		VoidResult result = ReadingFieldsValidator.validateDateTime(
				view.getPowerValleyOffpeakDate(), "fecha demanda máx. Rate C");
		view.setPowerValleyOffpeakDateErrors(result.getErrors());
		return !result.hasErrors();
	}

	/**
	 * Valida el campo de la hora de la demanda máxima fuera de punta valle
	 * (Rate C)
	 * 
	 * @return true si es válido
	 */
	public boolean validatePowerValleyOffpeakTime() {
		VoidResult result = ReadingFieldsValidator.validateDateTime(
				view.getPowerValleyOffpeakTime(), "hora demanda máx. Rate C");
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

	/**
	 * Asigna el callback para el guardado de lecturas
	 * 
	 * @param readingCallback
	 */
	public void setReadingCallback(ReadingSaveCallback readingCallback) {
		this.readingCallback = readingCallback;
	}

	/**
	 * Pone a la lectura en estado de edición
	 */
	public void enableReadingEdition() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				isInEditionMode = true;
				view.setReadOnly(false);
				view.setReadingStatus(ReadingStatus.EDITING);
				view.notifyEditionModeEnabled();
			}
		}).start();
	}

	/**
	 * Pone a la lectura en su estado anterior quitando el modo de edición si es
	 * que estaba en ese estado
	 */
	public void disableReadingEdition() {
		if (isInEditionMode) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					isInEditionMode = false;
					view.setReadOnly(true);
					view.setReadingStatus(reading.getStatus());
				}
			}).start();
		}
	}

	/**
	 * Pone a la lectura actual en estado de reintentar
	 */
	public void setReadingToRetry() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				VoidResult result = ReadingTakenManager.registerReadingTaken(
						reading, new ReadingTaken(reading.getReadingRemoteId(),
								reading.getSupplyId(), DateTime.now(),
								SessionManager.getLoggedInUsername()),
						ReadingStatus.RETRY);
				if (!result.hasErrors()) {
					view.notifyReadingSavedSuccessfully();
					view.setReadingStatus(reading.getStatus());
					view.setReadOnly(true);
					if (readingCallback != null)
						readingCallback.onRetryReadingSavedSuccesfully(reading);
				} else {
					view.showReadingSaveErrors(result.getErrors());
					if (readingCallback != null)
						readingCallback.onReadingSaveErrors(result.getErrors());
				}
			}
		}).start();
	}

}
