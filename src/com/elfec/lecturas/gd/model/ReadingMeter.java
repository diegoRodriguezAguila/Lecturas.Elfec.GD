package com.elfec.lecturas.gd.model;

import java.math.BigDecimal;

import org.joda.time.DateTime;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;

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
	@Column(name = "ReadingRemoteId", notNull = true, index = true)
	private long readingRemoteId;
	/**
	 * IDMEDIDOR en Oracle
	 */
	@Column(name = "MeterId", notNull = true)
	private int meterId;
	/**
	 * NROSERIE en Oracle
	 */
	@Column(name = "SerialNumber", notNull = true, index = true)
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
	@Column(name = "TagActiveDistributing", notNull = true)
	private BigDecimal tagActiveDistributing;
	/**
	 * TAG_ACTIVO_PICO en Oracle
	 */
	@Column(name = "TagActivePeak", notNull = true)
	private BigDecimal tagActivePeak;
	/**
	 * TAG_ACTIVO_RESTO en Oracle
	 */
	@Column(name = "TagActiveRest", notNull = true)
	private BigDecimal tagActiveRest;
	/**
	 * TAG_ACTIVO_VALLE en Oracle
	 */
	@Column(name = "TagActiveValley", notNull = true)
	private BigDecimal tagActiveValley;
	/**
	 * TAG_REACTIVO_DISTRIBUIR en Oracle
	 */
	@Column(name = "TagReactiveDistributing", notNull = true)
	private BigDecimal tagReactiveDistributing;
	/**
	 * TAG_REACTIVO_PICO en Oracle
	 */
	@Column(name = "TagReactivePeak", notNull = true)
	private BigDecimal tagReactivePeak;
	/**
	 * TAG_REACTIVO_RESTO en Oracle
	 */
	@Column(name = "TagReactiveRest", notNull = true)
	private BigDecimal tagReactiveRest;
	/**
	 * TAG_REACTIVO_VALLE en Oracle
	 */
	@Column(name = "TagReactiveValley", notNull = true)
	private BigDecimal tagReactiveValley;
	/**
	 * TAG_POT_PUNTA en Oracle
	 */
	@Column(name = "TagPowerPeak", notNull = true)
	private BigDecimal tagPowerPeak;
	/**
	 * TAG_POT_FPUNTA en Oracle
	 */
	@Column(name = "TagPowerOffpeak", notNull = true)
	private BigDecimal tagPowerOffpeak;
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
	@Column(name = "LastActiveValley")
	private BigDecimal lastActiveValley;
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
	@Column(name = "LastReactiveValley")
	private BigDecimal lastReactiveValley;
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
	@Column(name = "DraggedPowerPeak")
	private BigDecimal draggedPowerPeak;
	/**
	 * POT_FPUNTA_ARRASTRE en Oracle
	 */
	@Column(name = "DraggedPowerOffpeak")
	private BigDecimal draggedPowerOffpeak;

	public ReadingMeter() {
		super();
	}

	public ReadingMeter(long readingRemoteId, int meterId, String serialNumber,
			short meterTypeId, short digits, int supplyId, DateTime lastDate,
			DateTime currentDate, BigDecimal tagActiveDistributing,
			BigDecimal tagActivePeak, BigDecimal tagActiveRest,
			BigDecimal tagActiveValley, BigDecimal tagReactiveDistributing,
			BigDecimal tagReactivePeak, BigDecimal tagReactiveRest,
			BigDecimal tagReactiveValley, BigDecimal tagPowerPeak,
			BigDecimal tagPowerOffpeak, BigDecimal lastActiveDistributing,
			BigDecimal lastActivePeak, BigDecimal lastActiveRest,
			BigDecimal lastActiveValley, BigDecimal lastReactiveDistributing,
			BigDecimal lastReactivePeak, BigDecimal lastReactiveRest,
			BigDecimal lastReactiveValley, int averageActiveConsumption,
			int averageReactiveConsumption, BigDecimal draggedPowerPeak,
			BigDecimal draggedPowerOffpeak) {
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
		this.tagActiveValley = tagActiveValley;
		this.tagReactiveDistributing = tagReactiveDistributing;
		this.tagReactivePeak = tagReactivePeak;
		this.tagReactiveRest = tagReactiveRest;
		this.tagReactiveValley = tagReactiveValley;
		this.tagPowerPeak = tagPowerPeak;
		this.tagPowerOffpeak = tagPowerOffpeak;
		this.lastActiveDistributing = lastActiveDistributing;
		this.lastActivePeak = lastActivePeak;
		this.lastActiveRest = lastActiveRest;
		this.lastActiveValley = lastActiveValley;
		this.lastReactiveDistributing = lastReactiveDistributing;
		this.lastReactivePeak = lastReactivePeak;
		this.lastReactiveRest = lastReactiveRest;
		this.lastReactiveValley = lastReactiveValley;
		this.averageActiveConsumption = averageActiveConsumption;
		this.averageReactiveConsumption = averageReactiveConsumption;
		this.draggedPowerPeak = draggedPowerPeak;
		this.draggedPowerOffpeak = draggedPowerOffpeak;
	}

	/**
	 * Elimina toda la información de Medidores de lecturas que tengan los Ids
	 * en la clausula pasada
	 * 
	 * @param readingRemoteIds
	 *            clausula In en formato (123,4213,...)
	 */
	public static void deleteReadingMeters(String readingRemoteIds) {
		new Delete().from(ReadingMeter.class)
				.where("ReadingRemoteId IN " + readingRemoteIds).execute();
	}

	/**
	 * Obtiene el multiplicador de la energía activa. Ya sea el de la energía a
	 * distribuir o la de pico elige el que sea diferente de cero. Si ambos son
	 * cero retorna cero.
	 * 
	 * @return el multiplicador de la energía activa
	 */
	public BigDecimal getActiveMultiplicator() {
		return tagActiveDistributing.equals(BigDecimal.ZERO) ? tagActivePeak
				: tagActiveDistributing;
	}

	/**
	 * Obtiene el multiplicador de la energía reactiva. Ya sea el de la energía
	 * a distribuir o la de pico elige el que sea diferente de cero. Si ambos
	 * son cero retorna cero.
	 * 
	 * @return el multiplicador de la energía reactiva
	 */
	public BigDecimal getReactiveMultiplicator() {
		return tagReactiveDistributing.equals(BigDecimal.ZERO) ? tagReactivePeak
				: tagReactiveDistributing;
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

	public BigDecimal getTagActiveValley() {
		return tagActiveValley;
	}

	public void setTagActiveValley(BigDecimal tagActiveValley) {
		this.tagActiveValley = tagActiveValley;
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

	public BigDecimal getTagReactiveValley() {
		return tagReactiveValley;
	}

	public void setTagReactiveValley(BigDecimal tagReactiveValley) {
		this.tagReactiveValley = tagReactiveValley;
	}

	public BigDecimal getTagPowerPeak() {
		return tagPowerPeak;
	}

	public void setTagPowerPeak(BigDecimal tagPowerPeak) {
		this.tagPowerPeak = tagPowerPeak;
	}

	public BigDecimal getTagPowerOffpeak() {
		return tagPowerOffpeak;
	}

	public void setTagPowerOffpeak(BigDecimal tagPowerOffpeak) {
		this.tagPowerOffpeak = tagPowerOffpeak;
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

	public BigDecimal getLastActiveValley() {
		return lastActiveValley;
	}

	public void setLastActiveValley(BigDecimal lastActiveValley) {
		this.lastActiveValley = lastActiveValley;
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

	public BigDecimal getLastReactiveValley() {
		return lastReactiveValley;
	}

	public void setLastReactiveValley(BigDecimal lastReactiveValley) {
		this.lastReactiveValley = lastReactiveValley;
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

	public BigDecimal getDraggedPowerPeak() {
		return draggedPowerPeak;
	}

	public void setDraggedPowerPeak(BigDecimal draggedPowerPeak) {
		this.draggedPowerPeak = draggedPowerPeak;
	}

	public BigDecimal getDraggedPowerOffpeak() {
		return draggedPowerOffpeak;
	}

	public void setDraggedPowerOffpeak(BigDecimal draggedPowerOffpeak) {
		this.draggedPowerOffpeak = draggedPowerOffpeak;
	}

	// #endregion
}
