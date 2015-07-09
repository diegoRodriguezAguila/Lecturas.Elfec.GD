package com.elfec.lecturas.gd.presenter;

import com.elfec.lecturas.gd.model.ReadingGeneralInfo;
import com.elfec.lecturas.gd.model.RouteAssignment;
import com.elfec.lecturas.gd.presenter.views.IReadingsListView;

/**
 * Presenter para las vistas de lista de lecturas
 * 
 * @author drodriguez
 *
 */
public class ReadingsListPresenter {

	private IReadingsListView view;

	public ReadingsListPresenter(IReadingsListView view) {
		this.view = view;
	}

	/**
	 * Inicia el proceso de carga de las rutas
	 */
	public void loadRoutes() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				view.setRoutes(RouteAssignment.getAllImportedRouteAssignments());
			}
		}).start();
	}

	/**
	 * Inicia el proceso de carga de las lecturas
	 */
	public void loadReadingsGeneralInfo() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				view.setReadings(ReadingGeneralInfo.getAllReadingsSorted());
			}
		}).start();
	}
}