package com.elfec.lecturas.gd.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Modelo de los Ordenativos de las lecturas
 * 
 * @author drodriguez
 *
 */
@Table(name = "ReadingOrdenatives")
public class ReadingOrdenative extends Model {

	/**
	 * IDLECTURAGD en Oracle
	 */
	@Column(name = "ReadingRemoteId", notNull = true, index = true)
	private long readingRemoteId;

	@Column(name = "Code", index = true, notNull = true)
	private int code;

	public long getReadingRemoteId() {
		return readingRemoteId;
	}

	public ReadingOrdenative() {
		super();
	}

	public ReadingOrdenative(long readingRemoteId, int code) {
		super();
		this.readingRemoteId = readingRemoteId;
		this.code = code;
	}

	// #region Getters y Setters

	public void setReadingRemoteId(long readingRemoteId) {
		this.readingRemoteId = readingRemoteId;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	// #endregion
}
