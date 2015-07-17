package com.elfec.lecturas.gd.presenter;

import org.apache.commons.lang.WordUtils;

import com.elfec.lecturas.gd.business_logic.validators.ReadingFieldsValidator;
import com.elfec.lecturas.gd.helpers.util.text.AccountFormatter;
import com.elfec.lecturas.gd.model.ReadingGeneralInfo;
import com.elfec.lecturas.gd.model.ReadingTaken;
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
	}

	/**
	 * Asigna a la vista la información de la lectura tomada
	 */
	public void bindReadingTaken() {
		ReadingTaken readingTaken = reading.getReadingTaken();
		if (readingTaken != null) {
			// #region readingTaken view assignations
			readingTaken.setReadingRemoteId(reading.getReadingRemoteId());
			readingTaken.setSupplyId(reading.getSupplyId());
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
		}
	}

	/**
	 * Inicia el proceso de validación y guardado de la lectura
	 */
	public void saveReading() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				if (validateFields()) {
					// DO STUFF
				} else
					view.notifyErrorsInFields();
			}
		}).start();
	}

	public boolean validateFields() {
		ReadingFieldsValidator validator = new ReadingFieldsValidator();
		boolean allAreValid = true;
		VoidResult result;

		result = validator.validateDateTime(view.getReadingDate(),
				"fecha de lectura");
		view.setReadingDateErrors(result.getErrors());
		allAreValid = allAreValid && !result.hasErrors();

		result = validator.validateDateTime(view.getReadingTime(),
				"hora de lectura");
		view.setReadingTimeErrors(result.getErrors());
		allAreValid = allAreValid && !result.hasErrors();

		result = validator.validateResetCount(view.getResetCount());
		view.setResetCountErrors(result.getErrors());
		allAreValid = allAreValid && !result.hasErrors();

		result = validator.validateResetCount(view.getResetCount());
		view.setResetCountErrors(result.getErrors());
		allAreValid = allAreValid && !result.hasErrors();

		result = validator.validateActiveDistributing(view.getActivePeak(),
				view.getActivePeak(), view.getActiveRest(),
				view.getActiveValley());
		view.setActiveDistributingErrors(result.getErrors());
		allAreValid = allAreValid && !result.hasErrors();

		return allAreValid;
	}
}
