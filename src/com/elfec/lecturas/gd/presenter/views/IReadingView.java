package com.elfec.lecturas.gd.presenter.views;

import java.math.BigDecimal;
import java.util.List;

import org.joda.time.DateTime;

/**
 * Abstracción de la vista de lectura
 * 
 * @author drodriguez
 *
 */
public interface IReadingView {

	public void setAccountNumber(String accountNumber);

	public void setNUS(int NUS);

	public void setMeterSerialNumber(String serialNumber);

	public void setClientName(String name);

	public void setAddress(String address);

	public void setCategory(String category);

	public DateTime getReadingDate();

	public void setReadingDate(DateTime date);

	public DateTime getReadingTime();

	public void setReadingTime(DateTime time);

	public int getResetCount();

	public void setResetCount(int resetCount);

	public BigDecimal getActiveDistributing();

	public void setActiveDistributing(BigDecimal activeDistributing);

	public BigDecimal getActivePeak();

	public void setActivePeak(BigDecimal activePeak);

	public BigDecimal getActiveRest();

	public void setActiveRest(BigDecimal activeRest);

	public BigDecimal getActiveValley();

	public void setActiveValley(BigDecimal activeValley);

	public BigDecimal getReactiveDistributing();

	public void setReactiveDistributing(BigDecimal reactiveDistributing);

	public BigDecimal getReactivePeak();

	public void setReactivePeak(BigDecimal reactivePeak);

	public BigDecimal getReactiveRest();

	public void setReactiveRest(BigDecimal reactiveRest);

	public BigDecimal getReactiveValley();

	public void setReactiveValley(BigDecimal reactiveValley);

	public BigDecimal getPowerPeak();

	public void setPowerPeak(BigDecimal powerPeak);

	public DateTime getPowerPeakDate();

	public void setPowerPeakDate(DateTime powerPeakDate);

	public DateTime getPowerPeakTime();

	public void setPowerPeakTime(DateTime powerPeakTime);

	public BigDecimal getPowerRestOffpeak();

	public void setPowerRestOffpeak(BigDecimal powerRestOffpeak);

	public DateTime getPowerRestOffpeakDate();

	public void setPowerRestOffpeakDate(DateTime powerRestOffpeakDate);

	public DateTime getPowerRestOffpeakTime();

	public void setPowerRestOffpeakTime(DateTime powerRestOffpeakTime);

	public BigDecimal getPowerValleyOffpeak();

	public void setPowerValleyOffpeak(BigDecimal powerValleyOffpeak);

	public DateTime getPowerValleyOffpeakDate();

	public void setPowerValleyOffpeakDate(DateTime powerValleyOffpeakDate);

	public DateTime getPowerValleyOffpeakTime();

	public void setPowerValleyOffpeakTime(DateTime powerValleyOffpeakTime);

	/**
	 * Limpia todos los campos y sus errores
	 */
	public void clearAllFieldsAndErrors();

	// #region Set Errors
	public void setReadingDateErrors(List<Exception> errors);

	public void setReadingTimeErrors(List<Exception> errors);

	public void setResetCountErrors(List<Exception> errors);

	public void setActiveDistributingErrors(List<Exception> errors);

	public void setActivePeakErrors(List<Exception> errors);

	public void setActiveRestErrors(List<Exception> errors);

	public void setActiveValleyErrors(List<Exception> errors);

	public void setReactiveDistributingErrors(List<Exception> errors);

	public void setReactivePeakErrors(List<Exception> errors);

	public void setReactiveRestErrors(List<Exception> errors);

	public void setReactiveValleyErrors(List<Exception> errors);

	public void setPowerPeakErrors(List<Exception> errors);

	public void setPowerPeakDateErrors(List<Exception> errors);

	public void setPowerPeakTimeErrors(List<Exception> errors);

	public void setPowerRestOffpeakErrors(List<Exception> errors);

	public void setPowerRestOffpeakDateErrors(List<Exception> errors);

	public void setPowerRestOffpeakTimeErrors(List<Exception> errors);

	public void setPowerValleyOffpeakErrors(List<Exception> errors);

	public void setPowerValleyOffpeakDateErrors(List<Exception> errors);

	public void setPowerValleyOffpeakTimeErrors(List<Exception> errors);

	/**
	 * Notifica al usuario que existen errores en los campos
	 */
	public void notifyErrorsInFields();
	// #endregion
}
