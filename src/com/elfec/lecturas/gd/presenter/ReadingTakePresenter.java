package com.elfec.lecturas.gd.presenter;

import java.util.List;

import com.elfec.lecturas.gd.model.ReadingGeneralInfo;
import com.elfec.lecturas.gd.presenter.views.IReadingTakeView;
import com.elfec.lecturas.gd.presenter.views.notifiers.IReadingListNotifier;
import com.elfec.lecturas.gd.presenter.views.observers.IReadingListObserver;

public class ReadingTakePresenter implements IReadingListNotifier {
	private int currentPosition;
	@SuppressWarnings("unused")
	private IReadingTakeView view;
	private List<IReadingListObserver> observers;
	private List<ReadingGeneralInfo> shownReadings;

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
					processFoundReading(reading);
				}
			}
		}).start();
	}

}
