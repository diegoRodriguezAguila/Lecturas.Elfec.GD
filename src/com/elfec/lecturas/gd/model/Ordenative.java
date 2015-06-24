package com.elfec.lecturas.gd.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;

/**
 * Modelo de los Ordenativos
 * 
 * @author drodriguez
 *
 */
@Table(name = "Ordenatives")
public class Ordenative extends Model {

	@Column(name = "Code", index = true, notNull = true)
	private int code;

	@Column(name = "Description")
	private String description;

	@Column(name = "Type")
	private String type;

	public Ordenative() {
		super();
	}

	public Ordenative(int code, String description, String type) {
		super();
		this.code = code;
		this.description = description;
		this.type = type;
	}

	/**
	 * ELimina todos los ordenativos guardados
	 */
	public static void deleteAllOrdenatives() {
		new Delete().from(Ordenative.class).execute();
	}

	// #region Getters y Setters

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	// #endregion
}
