package com.elfec.lecturas.gd.presenter.views;

import java.util.List;

import com.elfec.lecturas.gd.model.ReadingGeneralInfo;
import com.elfec.lecturas.gd.model.RouteAssignment;

/**
 * Abstracción de la vista de lista de lecturas
 * 
 * @author drodriguez
 *
 */
public interface IReadingsListView {
	/**
	 * Asigna la lista de rutas para mostrar
	 * 
	 * @param routes
	 */
	public void setRoutes(List<RouteAssignment> routes);

	/**
	 * Asigna la lista de lecturas para mostrar
	 * 
	 * @param readings
	 */
	public void setReadings(List<ReadingGeneralInfo> readings);
}
