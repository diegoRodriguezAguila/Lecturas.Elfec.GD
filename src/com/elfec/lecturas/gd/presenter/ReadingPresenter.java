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
			readingTaken.setReadingRemoteId(reading.getReadingRemoteId());
			readingTaken.setSupplyId(reading.getSupplyId());
			view.setReadingDate(readingTaken.getReadingDate());
			view.setReadingTime(readingTaken.getReadingDate());
			view.setResetCount(readingTaken.getResetCount());
			view.setActiveDistributing(readingTaken.getReactiveDistributing());
		}
	}

	/**
	 * Inicia el proceso de validación y guardado de la lectura
	 */
	public void saveReading() {
		VoidResult result = ReadingFieldsValidator.validateActiveDistributing(
				view.getActivePeak(), view.getActivePeak(),
				view.getActiveRest(), view.getActiveValley());
	}
}
