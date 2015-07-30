package com.elfec.lecturas.gd.presenter.views;

public interface IWipeAllDataView {
	/**
	 * Inicializa el dialogo de confirmación de eliminación de datos
	 */
	public void  initializeWipeConfirmDialog();
	/**
	 * Inicializa el dialogo de que no se puede realizar eliminación por cobros
	 */
	public void initializeCannotWipeDialog();
}
