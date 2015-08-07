package com.elfec.lecturas.gd.presenter;

import java.util.List;

import com.elfec.lecturas.gd.model.ReadingGeneralInfo;
import com.elfec.lecturas.gd.model.RouteAssignment;
import com.elfec.lecturas.gd.model.enums.ReadingStatus;
import com.elfec.lecturas.gd.presenter.views.IReadingTakeView;
import com.elfec.lecturas.gd.presenter.views.notifiers.IReadingListNotifier;
import com.elfec.lecturas.gd.presenter.views.observers.IReadingListObserver;

public class ReadingTakePresenter implements IReadingListNotifier {
	private int currentPosition;
	private IReadingTakeView view;
	private List<IReadingListObserver> observers;
	private List<ReadingGeneralInfo> shownReadings;
	private ReadingStatus statusFilter;
	private RouteAssignment routeFilter;

	public ReadingTakePresenter(IReadingTakeView view,
			List<IReadingListObserver> observers) {
		this.view = view;
		this.observers = observers;
		currentPosition = 0;
		startlistenToObservers();
	}

	/**
	 * Inicia la escucha de eventos propagados por los observers
	 */
	private void startlistenToObservers() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (IReadingListObserver obs : observers) {
					obs.setReadingListNotifier(ReadingTakePresenter.this);
				}
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
				notifyReadingListChanged(
						ReadingGeneralInfo.getAllReadingsSorted(), null);
			}
		}).start();
	}

	@Override
	public void notifyReadingSelected(int position, IReadingListObserver sender) {
		if (currentPosition != position) {
			for (IReadingListObserver obs : observers) {
				if (obs != sender)
					obs.setSelectedReading(position);
			}
			currentPosition = position;
		}
	}

	@Override
	public void notifyReadingListChanged(List<ReadingGeneralInfo> readings,
			IReadingListObserver sender) {
		ReadingGeneralInfo currentReading = (shownReadings != null && shownReadings
				.size() > currentPosition) ? shownReadings.get(currentPosition)
				: null;
		shownReadings = readings;
		for (IReadingListObserver obs : observers) {
			if (obs != sender)
				obs.setReadings(shownReadings);
		}
		int position = shownReadings.indexOf(currentReading);
		position = position == -1 ? 0 : position;
		if (position == currentPosition)
			currentPosition = -1;
		notifyReadingSelected(position, sender);
	}

	@Override
	public void notifyResetFilters(IReadingListObserver sender) {
		statusFilter = null;
		routeFilter = null;
		for (IReadingListObserver obs : observers) {
			if (obs != sender)
				obs.resetFilters();
		}
	}

	@Override
	public void notifyRebindReading(int position, IReadingListObserver sender) {
		for (IReadingListObserver obs : observers) {
			if (obs != sender)
				obs.rebindReading(position);
		}
	}

	@Override
	public void notifyFiltersApplied(ReadingStatus status,
			RouteAssignment route, IReadingListObserver sender) {
		statusFilter = status;
		routeFilter = route;
	}

	@Override
	public void notifyRemoveReading(int position, IReadingListObserver sender) {
		for (IReadingListObserver obs : observers) {
			if (obs != sender)
				obs.removeReading(position);
		}
	}

	/**
	 * Procesa la lectura encontrada, para notificar a todos los observadores
	 * que deben mostrarla. En caso de no existir en la lista actual, se vuelve
	 * a obtener absoultamente todas las lecturas de la base de datos y se la
	 * selecciona.
	 * 
	 * @param reading
	 */
	public void processFoundReading(final ReadingGeneralInfo reading) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				int position = shownReadings.indexOf(reading);
				if (position != -1) {// Lectura se encuentra en lista actual
					notifyReadingSelected(position, null);
				} else {
					shownReadings = ReadingGeneralInfo.getAllReadingsSorted();
					currentPosition = shownReadings.indexOf(reading);
					notifyReadingListChanged(shownReadings, null);
					notifyResetFilters(view);
				}
			}
		}).start();
	}

	/**
	 * Verifica que los cambios hechos en el estado de la lectura corresponda a
	 * los filtros actuales, si es que no se actualizará las listas
	 * 
	 * @param position
	 */
	public void verifyFiltersValidity(int position) {
		if (!readingMatchFilters(shownReadings.get(position))) {
			// only called once since its the same list instance on all views
			shownReadings.remove(position);
			if (shownReadings.size() == 0)
				notifyReadingListChanged(shownReadings, null);
			else
				notifyRemoveReading(position, null);
		}
	}

	/**
	 * Verifica si una lectura coincide con los filtros actuales
	 * 
	 * @param reading
	 * @return true si es que si coincide
	 */
	private boolean readingMatchFilters(ReadingGeneralInfo reading) {
		boolean matches = true;
		if (statusFilter != null)
			matches = reading.getStatus().equals(statusFilter) && matches;
		if (routeFilter != null)
			matches = reading.getRouteId() == routeFilter.getRoute() && matches;
		return matches;
	}

	/**
	 * True si es que hay filtros de estado de la lectura aplicados
	 * 
	 * @return
	 */
	public boolean hasReadingStatusFilter() {
		return (statusFilter != null);
	}

}
