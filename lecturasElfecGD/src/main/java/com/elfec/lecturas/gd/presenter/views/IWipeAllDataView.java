package com.elfec.lecturas.gd.presenter.views;

public interface IWipeAllDataView {
	/**
	 * Inicializa el dialogo de confirmaci�n de eliminaci�n de datos
	 */
	public void  initializeWipeConfirmDialog();
	/**
	 * Inicializa el dialogo de que no se puede realizar eliminaci�n por cobros
	 */
	public void initializeCannotWipeDialog();
}
