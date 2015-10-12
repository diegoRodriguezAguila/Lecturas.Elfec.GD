package com.elfec.lecturas.gd.model;

import java.util.List;
import java.util.Locale;

import org.joda.time.DateTime;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.elfec.lecturas.gd.model.enums.ExportStatus;
import com.elfec.lecturas.gd.model.interfaces.IBackupable;
import com.elfec.lecturas.gd.model.interfaces.IExportable;

/**
 * Modelo de los Ordenativos de las lecturas
 * 
 * @author drodriguez
 *
 */
@Table(name = "ReadingOrdenatives")
public class ReadingOrdenative extends Model implements IExportable,
		IBackupable {
	private static final String BACKUP_FILENAME = "Ordenativos";
	public static final String INSERT_QUERY = "INSERT INTO ERP_ELFEC.SGC_MOVIL_ORDENATIVOS_GD "
			+ "VALUES (%d, %d, %d, TO_DATE('%s', 'dd/mm/yyyy hh24:mi:ss'), '%s', SYSDATE, USER)";
	private static final String DELETE_QUERY = "DELETE FROM ERP_ELFEC.SGC_MOVIL_ORDENATIVOS_GD WHERE IDLECTURAGD=%d AND COD_ORDENATIVO=%d";
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
	 * COD_ORDENATIVO
	 */
	@Column(name = "Code", index = true, notNull = true)
	private int code;

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

	// ATRIBUTO EXTRA
	@Column(name = "ExportStatus", index = true, notNull = true)
	private short exportStatus;

	public ReadingOrdenative() {
		super();
	}

	public ReadingOrdenative(long readingRemoteId, int supplyId, int code,
			DateTime saveDate, String readerUser) {
		super();
		this.readingRemoteId = readingRemoteId;
		this.supplyId = supplyId;
		this.code = code;
		this.saveDate = saveDate;
		this.readerUser = readerUser;
	}

	/**
	 * Obtiene todos los ordenativos pendientes de exportación de las lecturas
	 * tomadas
	 * 
	 * @return lista de ordenativos de lecturas pendientes de exportación
	 */
	public static List<ReadingOrdenative> getExportPendingReadingOrdenatives() {
		return new Select().from(ReadingOrdenative.class)
				.where("ExportStatus = ?", ExportStatus.NOT_EXPORTED.toShort())
				.execute();
	}

	/**
	 * Obtiene los ordenativos asignados a una lectura según su Id de lectura
	 * 
	 * @param readingRemoteId
	 * @return Lista de ordenativos asignados
	 */
	public static List<ReadingOrdenative> findByReadingRemoteId(
			long readingRemoteId) {
		return new Select().from(ReadingOrdenative.class)
				.where("ReadingRemoteId = ?", readingRemoteId).execute();
	}

	/**
	 * Obtiene los ordenativos asignados a una lectura según su Id de lectura
	 * 
	 * @param readingRemoteId
	 * @return Lista de ordenativos asignados
	 */
	public static void deleteByReadingRemoteId(long readingRemoteId) {
		new Delete().from(ReadingOrdenative.class)
				.where("ReadingRemoteId = ?", readingRemoteId).execute();
	}

	/**
	 * Obtiene la consulta INSERT del ordenativo
	 * 
	 * @return consulta INSERT del ordenativo
	 */
	public String toRemoteInsertSQL() {
		return String.format(Locale.getDefault(), INSERT_QUERY,
				readingRemoteId, supplyId, code,
				saveDate.toString("dd/MM/yyyy HH:mm:ss"), readerUser);
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
		return String.format(Locale.getDefault(), DELETE_QUERY,
				readingRemoteId, code);
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

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
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

	public ExportStatus getExportStatus() {
		return ExportStatus.get(exportStatus);
	}

	@Override
	public void setExportStatus(ExportStatus exportStatus) {
		this.exportStatus = exportStatus.toShort();
	}

	@Override
	public String getRegistryResume() {
		return "Ordenativo: <b>" + code
				+ "</b> de la lectura del suministro: <b>" + supplyId + "</b>";
	}

	// #endregion
}
