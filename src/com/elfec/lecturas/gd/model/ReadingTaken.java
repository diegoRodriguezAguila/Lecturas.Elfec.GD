package com.elfec.lecturas.gd.model;

import java.math.BigDecimal;

import org.joda.time.DateTime;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Modelo donde se almacenan las lecturas tomadas
 * 
 * @author drodriguez
 *
 */
@Table(name = "ReadingsTaken")
public class ReadingTaken extends Model {
	/**
	 * IDLECTURAGD en Oracle
	 */
	@Column(name = "ReadingRemoteId", notNull = true, index = true)
	private long readingRemoteId;
	/**
	 * IDSUMINISTRO Identificador del suministros, tabla de referencia
	 * suministros
	 */
	@Column(name = "SupplyId", notNull = true)
	private int supplyId;
	/**
	 * FECHA_GUARDADA en Oracle
	 */
	@Column(name = "SaveDate")
	private DateTime saveDate;
	/**
	 * LEC_FECHA en Oracle
	 */
	@Column(name = "ReadingDate")
	private DateTime readingDate;
	/**
	 * NUMERO_RESET en Oracle
	 */
	@Column(name = "ResetCount")
	private int resetCount;
	/**
	 * LEC_ACTIVO_DISTRIBUIR en Oracle
	 */
	@Column(name = "ActiveDistributing")
	private BigDecimal activeDistributing;
	/**
	 * LEC_ACTIVO_PICO en Oracle
	 */
	@Column(name = "ActivePeak")
	private BigDecimal activePeak;
	/**
	 * LEC_ACTIVO_RESTO en Oracle
	 */
	@Column(name = "ActiveRest")
	private BigDecimal activeRest;
	/**
	 * LEC_ACTIVO_VALLE en Oracle
	 */
	@Column(name = "ActiveValley")
	private BigDecimal activeValley;
	/**
	 * LEC_REACTIVO_DISTRIBUIR en Oracle
	 */
	@Column(name = "ReactiveDistributing")
	private BigDecimal reactiveDistributing;
	/**
	 * LEC_REACTIVO_PICO en Oracle
	 */
	@Column(name = "ReactivePeak")
	private BigDecimal reactivePeak;
	/**
	 * LEC_REACTIVO_RESTO en Oracle
	 */
	@Column(name = "ReactiveRest")
	private BigDecimal reactiveRest;
	/**
	 * LEC_REACTIVO_VALLE en Oracle
	 */
	@Column(name = "ReactiveValley")
	private BigDecimal reactiveValley;
	/**
	 * POT_PUNTA en Oracle
	 */
	@Column(name = "PowerPeak")
	private BigDecimal powerPeak;
	/**
	 * FECHA_POT_PUNTA en Oracle
	 */
	@Column(name = "PowerPeakDate")
	private DateTime powerPeakDate;
	/**
	 * POT_FPUNTA_RESTO en Oracle
	 */
	@Column(name = "PowerRestOffpeak")
	private BigDecimal powerRestOffpeak;
	/**
	 * FECHA_POT_FPUNTA_RESTO en Oracle
	 */
	@Column(name = "PowerRestOffpeakDate")
	private DateTime powerRestOffpeakDate;
	/**
	 * POT_FPUNTA_VALLE en Oracle
	 */
	@Column(name = "PowerValleyOffpeak")
	private BigDecimal powerValleyOffpeak;
	/**
	 * FECHA_POT_FPUNTA_VALLE en Oracle
	 */
	@Column(name = "PowerValleyOffpeakDate")
	private DateTime powerValleyOffpeakDate;

	public ReadingTaken() {
		super();
	}

	public ReadingTaken(long readingRemoteId, int supplyId, DateTime saveDate,
			DateTime readingDate, int resetCount,
			BigDecimal activeDistributing, BigDecimal activePeak,
			BigDecimal activeRest, BigDecimal activeValley,
			BigDecimal reactiveDistributing, BigDecimal reactivePeak,
			BigDecimal reactiveRest, BigDecimal reactiveValley,
			BigDecimal powerPeak, DateTime powerPeakDate,
			BigDecimal powerRestOffpeak, DateTime powerRestOffpeakDate,
			BigDecimal powerValleyOffpeak, DateTime powerValleyOffpeakDate) {
		super();
		this.readingRemoteId = readingRemoteId;
		this.supplyId = supplyId;
		this.saveDate = saveDate;
		this.readingDate = readingDate;
		this.resetCount = resetCount;
		this.activeDistributing = activeDistributing;
		this.activePeak = activePeak;
		this.activeRest = activeRest;
		this.activeValley = activeValley;
		this.reactiveDistributing = reactiveDistributing;
		this.reactivePeak = reactivePeak;
		this.reactiveRest = reactiveRest;
		this.reactiveValley = reactiveValley;
		this.powerPeak = powerPeak;
		this.powerPeakDate = powerPeakDate;
		this.powerRestOffpeak = powerRestOffpeak;
		this.powerRestOffpeakDate = powerRestOffpeakDate;
		this.powerValleyOffpeak = powerValleyOffpeak;
		this.powerValleyOffpeakDate = powerValleyOffpeakDate;
	}

	/**
	 * Copia el Id de una lectura tomada a la instancia actual. Este método es
	 * útil para reemplazar en la base de datos una lectura tomada por otra
	 * 
	 * @param readingToCopyId
	 */
	public void assignId(ReadingTaken readingToCopyId) {
		setId(readingToCopyId.getId());
	}

	// #region Getters y Setters

	public long getReadingRemoteId() {
		return readingRemoteId;
	}

	public void setReadingRemoteId(long readingRemoteId) {
		this.readingRemoteId = readingRemoteId;
	}

	public int getSupplyId() {
		return supplyId;
	}

	public void setSupplyId(int supplyId) {
		this.supplyId = supplyId;
	}

	public DateTime getSaveDate() {
		return saveDate;
	}

	public void setSaveDate(DateTime saveDate) {
		this.saveDate = saveDate;
	}

	public DateTime getReadingDate() {
		return readingDate;
	}

	public void setReadingDate(DateTime readingDate) {
		this.readingDate = readingDate;
	}

	public int getResetCount() {
		return resetCount;
	}

	public void setResetCount(int resetCount) {
		this.resetCount = resetCount;
	}

	public BigDecimal getActiveDistributing() {
		return activeDistributing;
	}

	public void setActiveDistributing(BigDecimal activeDistributing) {
		this.activeDistributing = activeDistributing;
	}

	public BigDecimal getActivePeak() {
		return activePeak;
	}

	public void setActivePeak(BigDecimal activePeak) {
		this.activePeak = activePeak;
	}

	public BigDecimal getActiveRest() {
		return activeRest;
	}

	public void setActiveRest(BigDecimal activeRest) {
		this.activeRest = activeRest;
	}

	public BigDecimal getActiveValley() {
		return activeValley;
	}

	public void setActiveValley(BigDecimal activeValley) {
		this.activeValley = activeValley;
	}

	public BigDecimal getReactiveDistributing() {
		return reactiveDistributing;
	}

	public void setReactiveDistributing(BigDecimal reactiveDistributing) {
		this.reactiveDistributing = reactiveDistributing;
	}

	public BigDecimal getReactivePeak() {
		return reactivePeak;
	}

	public void setReactivePeak(BigDecimal reactivePeak) {
		this.reactivePeak = reactivePeak;
	}

	public BigDecimal getReactiveRest() {
		return reactiveRest;
	}

	public void setReactiveRest(BigDecimal reactiveRest) {
		this.reactiveRest = reactiveRest;
	}

	public BigDecimal getReactiveValley() {
		return reactiveValley;
	}

	public void setReactiveValley(BigDecimal reactiveValley) {
		this.reactiveValley = reactiveValley;
	}

	public BigDecimal getPowerPeak() {
		return powerPeak;
	}

	public void setPowerPeak(BigDecimal powerPeak) {
		this.powerPeak = powerPeak;
	}

	public DateTime getPowerPeakDate() {
		return powerPeakDate;
	}

	public void setPowerPeakDate(DateTime powerPeakDate) {
		this.powerPeakDate = powerPeakDate;
	}

	public BigDecimal getPowerRestOffpeak() {
		return powerRestOffpeak;
	}

	public void setPowerRestOffpeak(BigDecimal powerRestOffpeak) {
		this.powerRestOffpeak = powerRestOffpeak;
	}

	public DateTime getPowerRestOffpeakDate() {
		return powerRestOffpeakDate;
	}

	public void setPowerRestOffpeakDate(DateTime powerRestOffpeakDate) {
		this.powerRestOffpeakDate = powerRestOffpeakDate;
	}

	public BigDecimal getPowerValleyOffpeak() {
		return powerValleyOffpeak;
	}

	public void setPowerValleyOffpeak(BigDecimal powerValleyOffpeak) {
		this.powerValleyOffpeak = powerValleyOffpeak;
	}

	public DateTime getPowerValleyOffpeakDate() {
		return powerValleyOffpeakDate;
	}

	public void setPowerValleyOffpeakDate(DateTime powerValleyOffpeakDate) {
		this.powerValleyOffpeakDate = powerValleyOffpeakDate;
	}

	// #endregion

}
