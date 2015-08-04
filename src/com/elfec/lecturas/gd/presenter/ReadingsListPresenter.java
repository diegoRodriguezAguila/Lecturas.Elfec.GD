package com.elfec.lecturas.gd.presenter;

import java.util.List;

import com.elfec.lecturas.gd.business_logic.ReadingGeneralInfoManager;
import com.elfec.lecturas.gd.model.ReadingGeneralInfo;
import com.elfec.lecturas.gd.model.RouteAssignment;
import com.elfec.lecturas.gd.model.enums.ReadingStatus;
import com.elfec.lecturas.gd.model.results.TypedResult;
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
	 * Procesa los filtros seleccionados para obtener una nueva lista de
	 * lecturas
	 */
	public void applyFilters() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				ReadingStatus statusFilter = view.getReadingStatusFilter();
				RouteAssignment routeFilter = view.getRouteFilter();
				TypedResult<List<ReadingGeneralInfo>> result = ReadingGeneralInfoManager
						.getFilteredReadings(statusFilter, routeFilter);
				view.getReadingListNotifier().notifyFiltersApplied(
						statusFilter, routeFilter, view);
				view.getReadingListNotifier().notifyReadingListChanged(
						result.getResult(), null);
			}
		}).start();
	}
}
