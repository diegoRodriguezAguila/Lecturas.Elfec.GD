package com.elfec.lecturas.gd.model;

import java.util.List;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

/**
 * Modelo de los Ordenativos
 * 
 * @author drodriguez
 *
 */
@Table(name = "Ordenatives")
public class Ordenative extends Model {

	/**
	 * Ordenativos de tipo automático
	 */
	public static final String AUTOMATIC = "AUTOMATICA";
	/**
	 * Ordenativos de tipo manual
	 */
	public static final String MANUAL = "MANUAL";
	/**
	 * Ordenativos de tipo impedimento
	 */
	public static final String IMPEDIMENT = "IMPEDIMENTO";
	/**
	 * IDNOVEDAD en Oracle
	 */
	@Column(name = "Code", index = true, notNull = true)
	private int code;

	/**
	 * DESCRIPCION en Oracle
	 */
	@Column(name = "Description")
	private String description;

	/**
	 * TIPO_NOV_MOB
	 */
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

	/**
	 * Obtiene todos los ordenativos del tipo indicado
	 * 
	 * @param type
	 *            {@link #AUTOMATIC}, {@link #MANUAL} o {@link #IMPEDIMENT}
	 * @return Lista de ordenativos
	 */
	public static List<Ordenative> getTypeOrdenatives(String type) {
		return new Select().from(Ordenative.class).where("Type = ?", type)
				.orderBy("Code").execute();
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
