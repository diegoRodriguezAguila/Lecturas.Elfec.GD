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
				List<ReadingGeneralInfo> readings = ReadingGeneralInfo
						.getAllReadingsSorted();
				for (IReadingListObserver obs : observers) {
					obs.setReadings(readings);
				}
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
}
