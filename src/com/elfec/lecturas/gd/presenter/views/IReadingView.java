package com.elfec.lecturas.gd.presenter.views;

import java.math.BigDecimal;

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
}
