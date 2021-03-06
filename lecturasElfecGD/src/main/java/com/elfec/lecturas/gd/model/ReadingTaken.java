package com.elfec.lecturas.gd.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

import org.joda.time.DateTime;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.elfec.lecturas.gd.model.enums.ExportStatus;
import com.elfec.lecturas.gd.model.enums.ReadingStatus;
import com.elfec.lecturas.gd.model.interfaces.IBackupable;
import com.elfec.lecturas.gd.model.interfaces.IExportable;
import com.elfec.lecturas.gd.model.utils.SQLUtils;

/**
 * Modelo donde se almacenan las lecturas tomadas
 * 
 * @author drodriguez
 *
 */
@Table(name = "ReadingsTaken")
public class ReadingTaken extends Model implements IExportable, IBackupable {
	private static final String BACKUP_FILENAME = "Lecturas";
	public static final String INSERT_QUERY = "INSERT INTO ERP_ELFEC.SGC_MOVIL_LECTURAS_GD "
			+ "VALUES (%d, %d, TO_DATE(%s, 'dd/mm/yyyy hh24:mi:ss'), %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, "
			+ "TO_DATE(%s, 'dd/mm/yyyy hh24:mi:ss'), %s, TO_DATE(%s, 'dd/mm/yyyy hh24:mi:ss'), %s, TO_DATE(%s, 'dd/mm/yyyy hh24:mi:ss'), "
			+ "TO_DATE(%s, 'dd/mm/yyyy hh24:mi:ss'), '%s', SYSDATE, USER, %d)";

	private static final String DELETE_QUERY = "DELETE FROM ERP_ELFEC.SGC_MOVIL_LECTURAS_GD WHERE IDLECTURAGD=%d";

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
	 * AUD_FECHA en Oracle
	 */
	@Column(name = "SaveDate")
	private DateTime saveDate;
	/**
	 * AUD_USUARIO en Oracle
	 */
	@Column(name = "ReaderUser")
	private String readerUser;
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

	// ATRIBUTO EXTRA
	@Column(name = "ExportStatus", index = true, notNull = true)
	private short exportStatus;

	public ReadingTaken() {
		super();
	}

	public ReadingTaken(long readingRemoteId, int supplyId, DateTime saveDate,
			String readerUser, DateTime readingDate, int resetCount,
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
		this.readerUser = readerUser;
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

	public ReadingTaken(long readingRemoteId, int supplyId, DateTime saveDate,
			String readerUser) {
		super();
		this.readingRemoteId = readingRemoteId;
		this.supplyId = supplyId;
		this.saveDate = saveDate;
		this.readerUser = readerUser;
		this.resetCount = -1;
	}

	/**
	 * Copia el Id de una lectura tomada a la instancia actual. Este m�todo es
	 * �til para reemplazar en la base de datos una lectura tomada por otra
	 * 
	 * @param readingToCopyId
	 */
	public void assignId(ReadingTaken readingToCopyId) {
		setId(readingToCopyId.getId());
		setExportStatus(readingToCopyId.getExportStatus());
	}

	/**
	 * Obtiene todas las lecturas tomadas pendientes de exportaci�n
	 * 
	 * @return lista de lecturas tomadas pendeintes de exportaci�n
	 */
	public static List<ReadingTaken> getExportPendingReadingsTaken() {
		return new Select().from(ReadingTaken.class)
				.where("ExportStatus = ?", ExportStatus.NOT_EXPORTED.toShort())
				.execute();
	}

	/**
	 * Obtiene todas las lecturas tomadas exportadas de forma exitosa que no
	 * tengan estado {@link ReadingStatus#RETRY}
	 * 
	 * @return lista de lecturas tomadas exportadas de forma exitosa
	 */
	public static List<ReadingTaken> getExportedRouteReadingsTaken(int route) {
		return new Select().from(ReadingTaken.class).as("rt")
				.join(ReadingGeneralInfo.class).as("rgi")
				.on("rt.ReadingRemoteId = rgi.ReadingRemoteId")
				.where("rgi.RouteId = ?", route)
				.where("rt.ExportStatus = ?", ExportStatus.EXPORTED.toShort())
				.where("rgi.Status <> ?", ReadingStatus.RETRY.toShort())
				.execute();
	}

	/**
	 * Obtiene la consulta de inserci�n remota SQL de esta lectura tomada
	 * 
	 * @return consulta INSERT SQL de esta lectura tomada
	 */
	public String toRemoteInsertSQL() {
		return String.format(Locale.getDefault(), INSERT_QUERY,
				readingRemoteId, supplyId,
				SQLUtils.dateTimeToSQLString(readingDate),
				SQLUtils.intToSQLString(resetCount),
				SQLUtils.bigDecimalToSQLString(activeDistributing),
				SQLUtils.bigDecimalToSQLString(activePeak),
				SQLUtils.bigDecimalToSQLString(activeRest),
				SQLUtils.bigDecimalToSQLString(activeValley),
				SQLUtils.bigDecimalToSQLString(reactiveDistributing),
				SQLUtils.bigDecimalToSQLString(reactivePeak),
				SQLUtils.bigDecimalToSQLString(reactiveRest),
				SQLUtils.bigDecimalToSQLString(reactiveValley),
				SQLUtils.bigDecimalToSQLString(powerPeak),
				SQLUtils.dateTimeToSQLString(powerPeakDate),
				SQLUtils.bigDecimalToSQLString(powerRestOffpeak),
				SQLUtils.dateTimeToSQLString(powerRestOffpeakDate),
				SQLUtils.bigDecimalToSQLString(powerValleyOffpeak),
				SQLUtils.dateTimeToSQLString(powerValleyOffpeakDate),
				SQLUtils.dateTimeToSQLString(saveDate), readerUser,
				getReadingGeneralInfo().getStatus().toShort());
	}

	/**
	 * Obtiene la informaci�n general de lectura a la que est� relacionada esta
	 * lectura tomada
	 * 
	 * @return {@link ReadingGeneralInfo} informaci�n general de lectura
	 */
	public ReadingGeneralInfo getReadingGeneralInfo() {
		return ReadingGeneralInfo.findByReadingRemoteId(readingRemoteId);
	}

	@Override
	public String getBackupFilename() {
		return BACKUP_FILENAME;
	}

	@Override
	public String toInsertSQL() {
		return toRemoteInsertSQL();
	}

	@Override
	public String toDeleteSQL() {
		return String
				.format(Locale.getDefault(), DELETE_QUERY, readingRemoteId);
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

	public String getReaderUser() {
		return readerUser;
	}

	public void setReaderUser(String readerUser) {
		this.readerUser = readerUser;
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

	public ExportStatus getExportStatus() {
		return ExportStatus.get(exportStatus);
	}

	@Override
	public void setExportStatus(ExportStatus exportStatus) {
		this.exportStatus = exportStatus.toShort();
	}

	@Override
	public String getRegistryResume() {
		return "Lectura con Id: <b>" + readingRemoteId + "</b>"
				+ " del suministro: <b>" + supplyId + "</b>";
	}

	// #endregion

}
