package com.elfec.lecturas.gd.model.enums;

/**
 * Se utiliza para las llaves de los par�metros de la configuraci�n de conexi�n
 * a la base de datos
 * 
 * @author drodriguez
 *
 */
public enum ConnectionParam {
	/**
	 * Ip del host de conexi�n
	 */
	IP("ip"),
	/**
	 * Puerto de conexi�n
	 */
	PORT("port"),
	/**
	 * Servicio Oracle de conexi�n
	 */
	SERVICE("service"),
	/**
	 * Rol que debe habilitar la aplicaci�n
	 */
	ROLE("role"),
	/**
	 * La contrase�a del rol
	 */
	PASSWORD("password");
	private String value;

	private ConnectionParam(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return this.value;
	}
}