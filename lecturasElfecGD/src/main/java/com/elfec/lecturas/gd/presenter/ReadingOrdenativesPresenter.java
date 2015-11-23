package com.elfec.lecturas.gd.presenter;

import com.elfec.lecturas.gd.model.ReadingGeneralInfo;
import com.elfec.lecturas.gd.presenter.views.IReadingOrdenativesView;

/**
 * Presenter para la vista de los ordenativos asignados a una lectura
 * 
 * @author drodriguez
 *
 */
public class ReadingOrdenativesPresenter {
	private IReadingOrdenativesView view;
	private ReadingGeneralInfo reading;

	public ReadingOrdenativesPresenter(IReadingOrdenativesView view,
			ReadingGeneralInfo reading) {
		this.view = view;
		this.reading = reading;
	}

	/**
	 * Carga los ordenativos que se asignaron a la lectura actual
	 */
	public void loadOrdenatives() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				view.setOrdenatives(reading.getAssignedOrdenatives());
			}
		}).start();
	}
}
