package com.elfec.lecturas.gd.model;

import java.math.BigDecimal;

import org.joda.time.DateTime;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Modelo de las lecturas y sus medidores
 * 
 * @author drodriguez
 *
 */
@Table(name = "ReadingMeters")
public class ReadingMeter extends Model {
	/**
	 * IDLECTURAGD en Oracle
	 */
	@Column(name = "ReadingRemoteId", notNull = true)
	private long readingRemoteId;
	/**
	 * IDMEDIDOR en Oracle
	 */
	@Column(name = "MeterId", notNull = true)
	private int meterId;
	/**
	 * NROSERIE en Oracle
	 */
	@Column(name = "SerialNumber", notNull = true)
	private String serialNumber;
	/**
	 * IDTIPO_MEDIDOR en Oracle
	 */
	@Column(name = "MeterTypeId", notNull = true)
	private short meterTypeId;
	/**
	 * DIGITOS en Oracle
	 */
	@Column(name = "Digits")
	private short digits;
	/**
	 * IDSUMINISTRO Identificador del suministros, tabla de referencia
	 * suministros
	 */
	@Column(name = "SupplyId", notNull = true)
	private int supplyId;
	/**
	 * FECHA_ANTERIOR en Oracle
	 */
	@Column(name = "LastDate")
	private DateTime lastDate;
	/**
	 * FECHA_ACTUAL en Oracle
	 */
	@Column(name = "CurrentDate")
	private DateTime currentDate;
	/**
	 * TAG_ACTIVO_DISTRIBUIR en Oracle
	 */
	@Column(name = "TagActiveDistributing")
	private BigDecimal tagActiveDistributing;
	/**
	 * TAG_ACTIVO_PICO en Oracle
	 */
	@Column(name = "TagActivePeak")
	private BigDecimal tagActivePeak;
	/**
	 * TAG_ACTIVO_RESTO en Oracle
	 */
	@Column(name = "TagActiveRest")
	private BigDecimal tagActiveRest;
	/**
	 * TAG_ACTIVO_VALLE en Oracle
	 */
	@Column(name = "TagActiveValey")
	private BigDecimal tagActiveValey;
	/**
	 * TAG_REACTIVO_DISTRIBUIR en Oracle
	 */
	@Column(name = "TagReactiveDistributing")
	private BigDecimal tagReactiveDistributing;
	/**
	 * TAG_REACTIVO_PICO en Oracle
	 */
	@Column(name = "TagReactivePeak")
	private BigDecimal tagReactivePeak;
	/**
	 * TAG_REACTIVO_RESTO en Oracle
	 */
	@Column(name = "TagReactiveRest")
	private BigDecimal tagReactiveRest;
	/**
	 * TAG_REACTIVO_VALLE en Oracle
	 */
	@Column(name = "TagReactiveValey")
	private BigDecimal tagReactiveValey;
	/**
	 * TAG_POT_PUNTA en Oracle
	 */
	@Column(name = "TagPeakPower")
	private BigDecimal tagPeakPower;
	/**
	 * TAG_POT_FPUNTA en Oracle
	 */
	@Column(name = "TagOffpeakPower")
	private BigDecimal tagOffpeakPower;
	/**
	 * LEC_ANT_ACTIVO_DISTRIBUIR en Oracle
	 */
	@Column(name = "LastActiveDistributing")
	private BigDecimal lastActiveDistributing;
	/**
	 * LEC_ANT_ACTIVO_PICO en Oracle
	 */
	@Column(name = "LastActivePeak")
	private BigDecimal lastActivePeak;
	/**
	 * LEC_ANT_ACTIVO_RESTO en Oracle
	 */
	@Column(name = "LastActiveRest")
	private BigDecimal lastActiveRest;
	/**
	 * LEC_ANT_ACTIVO_VALLE en Oracle
	 */
	@Column(name = "LastActiveValey")
	private BigDecimal lastActiveValey;
	/**
	 * LEC_ANT_REACTIVO_DISTRIBUIR en Oracle
	 */
	@Column(name = "LastReactiveDistributing")
	private BigDecimal lastReactiveDistributing;
	/**
	 * LEC_ANT_REACTIVO_PICO en Oracle
	 */
	@Column(name = "LastReactivePeak")
	private BigDecimal lastReactivePeak;
	/**
	 * LEC_ANT_REACTIVO_RESTO en Oracle
	 */
	@Column(name = "LastReactiveRest")
	private BigDecimal lastReactiveRest;
	/**
	 * LEC_ANT_REACTIVO_VALLE en Oracle
	 */
	@Column(name = "LastReactiveValey")
	private BigDecimal lastReactiveValey;
	/**
	 * CONSUMO_PROM_ACT en Oracle
	 */
	@Column(name = "AverageActiveConsumption")
	private int averageActiveConsumption;
	/**
	 * CONSUMO_PROM_REACT en Oracle
	 */
	@Column(name = "AverageReactiveConsumption")
	private int averageReactiveConsumption;
	/**
	 * POT_PUNTA_ARRASTRE en Oracle
	 */
	@Column(name = "DraggedPeakPower")
	private BigDecimal draggedPeakPower;
	/**
	 * POT_FPUNTA_ARRASTRE en Oracle
	 */
	@Column(name = "DraggedOffpeakPower")
	private BigDecimal draggedOffpeakPower;

	public ReadingMeter() {
		super();
	}

	public ReadingMeter(long readingRemoteId, int meterId, String serialNumber,
			short meterTypeId, short digits, int supplyId, DateTime lastDate,
			DateTime currentDate, BigDecimal tagActiveDistributing,
			BigDecimal tagActivePeak, BigDecimal tagActiveRest,
			BigDecimal tagActiveValey, BigDecimal tagReactiveDistributing,
			BigDecimal tagReactivePeak, BigDecimal tagReactiveRest,
			BigDecimal tagReactiveValey, BigDecimal tagPeakPower,
			BigDecimal tagOffpeakPower, BigDecimal lastActiveDistributing,
			BigDecimal lastActivePeak, BigDecimal lastActiveRest,
			BigDecimal lastActiveValey, BigDecimal lastReactiveDistributing,
			BigDecimal lastReactivePeak, BigDecimal lastReactiveRest,
			BigDecimal lastReactiveValey, int averageActiveConsumption,
			int averageReactiveConsumption, BigDecimal draggedPeakPower,
			BigDecimal draggedOffpeakPower) {
		super();
		this.readingRemoteId = readingRemoteId;
		this.meterId = meterId;
		this.serialNumber = serialNumber;
		this.meterTypeId = meterTypeId;
		this.digits = digits;
		this.supplyId = supplyId;
		this.lastDate = lastDate;
		this.currentDate = currentDate;
		this.tagActiveDistributing = tagActiveDistributing;
		this.tagActivePeak = tagActivePeak;
		this.tagActiveRest = tagActiveRest;
		this.tagActiveValey = tagActiveValey;
		this.tagReactiveDistributing = tagReactiveDistributing;
		this.tagReactivePeak = tagReactivePeak;
		this.tagReactiveRest = tagReactiveRest;
		this.tagReactiveValey = tagReactiveValey;
		this.tagPeakPower = tagPeakPower;
		this.tagOffpeakPower = tagOffpeakPower;
		this.lastActiveDistributing = lastActiveDistributing;
		this.lastActivePeak = lastActivePeak;
		this.lastActiveRest = lastActiveRest;
		this.lastActiveValey = lastActiveValey;
		this.lastReactiveDistributing = lastReactiveDistributing;
		this.lastReactivePeak = lastReactivePeak;
		this.lastReactiveRest = lastReactiveRest;
		this.lastReactiveValey = lastReactiveValey;
		this.averageActiveConsumption = averageActiveConsumption;
		this.averageReactiveConsumption = averageReactiveConsumption;
		this.draggedPeakPower = draggedPeakPower;
		this.draggedOffpeakPower = draggedOffpeakPower;
	}

	// #region Getters y Setters

	public long getReadingRemoteId() {
		return readingRemoteId;
	}

	public void setReadingRemoteId(long readingRemoteId) {
		this.readingRemoteId = readingRemoteId;
	}

	public int getMeterId() {
		return meterId;
	}

	public void setMeterId(int meterId) {
		this.meterId = meterId;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public short getMeterTypeId() {
		return meterTypeId;
	}

	public void setMeterTypeId(short meterTypeId) {
		this.meterTypeId = meterTypeId;
	}

	public short getDigits() {
		return digits;
	}

	public void setDigits(short digits) {
		this.digits = digits;
	}

	public int getSupplyId() {
		return supplyId;
	}

	public void setSupplyId(int supplyId) {
		this.supplyId = supplyId;
	}

	public DateTime getLastDate() {
		return lastDate;
	}

	public void setLastDate(DateTime lastDate) {
		this.lastDate = lastDate;
	}

	public DateTime getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(DateTime currentDate) {
		this.currentDate = currentDate;
	}

	public BigDecimal getTagActiveDistributing() {
		return tagActiveDistributing;
	}

	public void setTagActiveDistributing(BigDecimal tagActiveDistributing) {
		this.tagActiveDistributing = tagActiveDistributing;
	}

	public BigDecimal getTagActivePeak() {
		return tagActivePeak;
	}

	public void setTagActivePeak(BigDecimal tagActivePeak) {
		this.tagActivePeak = tagActivePeak;
	}

	public BigDecimal getTagActiveRest() {
		return tagActiveRest;
	}

	public void setTagActiveRest(BigDecimal tagActiveRest) {
		this.tagActiveRest = tagActiveRest;
	}

	public BigDecimal getTagActiveValey() {
		return tagActiveValey;
	}

	public void setTagActiveValey(BigDecimal tagActiveValey) {
		this.tagActiveValey = tagActiveValey;
	}

	public BigDecimal getTagReactiveDistributing() {
		return tagReactiveDistributing;
	}

	public void setTagReactiveDistributing(BigDecimal tagReactiveDistributing) {
		this.tagReactiveDistributing = tagReactiveDistributing;
	}

	public BigDecimal getTagReactivePeak() {
		return tagReactivePeak;
	}

	public void setTagReactivePeak(BigDecimal tagReactivePeak) {
		this.tagReactivePeak = tagReactivePeak;
	}

	public BigDecimal getTagReactiveRest() {
		return tagReactiveRest;
	}

	public void setTagReactiveRest(BigDecimal tagReactiveRest) {
		this.tagReactiveRest = tagReactiveRest;
	}

	public BigDecimal getTagReactiveValey() {
		return tagReactiveValey;
	}

	public void setTagReactiveValey(BigDecimal tagReactiveValey) {
		this.tagReactiveValey = tagReactiveValey;
	}

	public BigDecimal getTagPeakPower() {
		return tagPeakPower;
	}

	public void setTagPeakPower(BigDecimal tagPeakPower) {
		this.tagPeakPower = tagPeakPower;
	}

	public BigDecimal getTagOffpeakPower() {
		return tagOffpeakPower;
	}

	public void setTagOffpeakPower(BigDecimal tagOffpeakPower) {
		this.tagOffpeakPower = tagOffpeakPower;
	}

	public BigDecimal getLastActiveDistributing() {
		return lastActiveDistributing;
	}

	public void setLastActiveDistributing(BigDecimal lastActiveDistributing) {
		this.lastActiveDistributing = lastActiveDistributing;
	}

	public BigDecimal getLastActivePeak() {
		return lastActivePeak;
	}

	public void setLastActivePeak(BigDecimal lastActivePeak) {
		this.lastActivePeak = lastActivePeak;
	}

	public BigDecimal getLastActiveRest() {
		return lastActiveRest;
	}

	public void setLastActiveRest(BigDecimal lastActiveRest) {
		this.lastActiveRest = lastActiveRest;
	}

	public BigDecimal getLastActiveValey() {
		return lastActiveValey;
	}

	public void setLastActiveValey(BigDecimal lastActiveValey) {
		this.lastActiveValey = lastActiveValey;
	}

	public BigDecimal getLastReactiveDistributing() {
		return lastReactiveDistributing;
	}

	public void setLastReactiveDistributing(BigDecimal lastReactiveDistributing) {
		this.lastReactiveDistributing = lastReactiveDistributing;
	}

	public BigDecimal getLastReactivePeak() {
		return lastReactivePeak;
	}

	public void setLastReactivePeak(BigDecimal lastReactivePeak) {
		this.lastReactivePeak = lastReactivePeak;
	}

	public BigDecimal getLastReactiveRest() {
		return lastReactiveRest;
	}

	public void setLastReactiveRest(BigDecimal lastReactiveRest) {
		this.lastReactiveRest = lastReactiveRest;
	}

	public BigDecimal getLastReactiveValey() {
		return lastReactiveValey;
	}

	public void setLastReactiveValey(BigDecimal lastReactiveValey) {
		this.lastReactiveValey = lastReactiveValey;
	}

	public int getAverageActiveConsumption() {
		return averageActiveConsumption;
	}

	public void setAverageActiveConsumption(int averageActiveConsumption) {
		this.averageActiveConsumption = averageActiveConsumption;
	}

	public int getAverageReactiveConsumption() {
		return averageReactiveConsumption;
	}

	public void setAverageReactiveConsumption(int averageReactiveConsumption) {
		this.averageReactiveConsumption = averageReactiveConsumption;
	}

	public BigDecimal getDraggedPeakPower() {
		return draggedPeakPower;
	}

	public void setDraggedPeakPower(BigDecimal draggedPeakPower) {
		this.draggedPeakPower = draggedPeakPower;
	}

	public BigDecimal getDraggedOffpeakPower() {
		return draggedOffpeakPower;
	}

	public void setDraggedOffpeakPower(BigDecimal draggedOffpeakPower) {
		this.draggedOffpeakPower = draggedOffpeakPower;
	}

	// #endregion
}