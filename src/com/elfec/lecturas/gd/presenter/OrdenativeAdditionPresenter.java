package com.elfec.lecturas.gd.presenter;

import com.elfec.lecturas.gd.model.Ordenative;
import com.elfec.lecturas.gd.model.ReadingGeneralInfo;
import com.elfec.lecturas.gd.presenter.views.IOrdenativeAdditionView;

/**
 * Presenter para la vista de agregar ordenativos
 * 
 * @author drodriguez
 *
 */
public class OrdenativeAdditionPresenter {

	private IOrdenativeAdditionView view;
	private ReadingGeneralInfo reading;

	public OrdenativeAdditionPresenter(IOrdenativeAdditionView view,
			ReadingGeneralInfo reading) {
		this.view = view;
		this.reading = reading;
	}

	/**
	 * Carga los ordenativos que están disponibles para la lectura actual
	 */
	public void loadOrdenatives() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				view.setOrdenatives(Ordenative
						.getTypeOrdenatives(Ordenative.MANUAL));
			}
		}).start();
	}

	/**
	 * Agrega los ordenativos seleccionados
	 */
	public void addOrdenatives() {

	}

}
