package com.elfec.lecturas.gd.presenter.views;

import java.util.List;

import com.elfec.lecturas.gd.model.RouteAssignment;
import com.elfec.lecturas.gd.presenter.views.observers.IReadingListObserver;

/**
 * Abstracción de la vista de lista de lecturas
 * 
 * @author drodriguez
 *
 */
public interface IReadingsListView extends IReadingListObserver {
	/**
	 * Asigna la lista de rutas para mostrar
	 * 
	 * @param routes
	 */
	public void setRoutes(List<RouteAssignment> routes);
}
