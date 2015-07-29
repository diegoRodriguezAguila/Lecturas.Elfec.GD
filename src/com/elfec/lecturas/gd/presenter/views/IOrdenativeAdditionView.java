package com.elfec.lecturas.gd.presenter.views;

import java.util.List;

import com.elfec.lecturas.gd.model.Ordenative;

/**
 * Abstracción de la vista de agregado de ordenativos
 * 
 * @author drodriguez
 *
 */
public interface IOrdenativeAdditionView {

	/**
	 * Asigna los ordenativos que se muestran
	 * 
	 * @param ordenatives
	 */
	public void setOrdenatives(List<Ordenative> ordenatives);

	/**
	 * Obtiene la lista de ordenativos que fueron seleccionados por el usuario
	 * para agregar a la lectura
	 * 
	 * @return Lista de ordenativos
	 */
	public List<Ordenative> getSelectedOrdenatives();
}
