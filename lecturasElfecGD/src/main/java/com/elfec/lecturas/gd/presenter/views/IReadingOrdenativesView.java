package com.elfec.lecturas.gd.presenter.views;

import java.util.List;

import com.elfec.lecturas.gd.model.Ordenative;

/**
 * Abstracción de la vista de los ordenativos ya agregados a una lectura
 * 
 * @author drodriguez
 *
 */
public interface IReadingOrdenativesView {
	/**
	 * Asigna los ordenativos que se muestran
	 * 
	 * @param ordenatives
	 */
	public void setOrdenatives(List<Ordenative> ordenatives);
}
