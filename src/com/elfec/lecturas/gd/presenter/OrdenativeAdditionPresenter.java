package com.elfec.lecturas.gd.presenter;

import java.util.List;

import com.elfec.lecturas.gd.business_logic.OrdenativeManager;
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
				view.setOrdenatives(OrdenativeManager
						.getReadingUnassignedOrdenatives(reading).getResult());
			}
		}).start();
	}

	/**
	 * Agrega los ordenativos seleccionados
	 * 
	 * @return true si es que se pudieron agregar los ordenativos, false en caso
	 *         de que haya ocurrido algún error
	 */
	public boolean addOrdenatives() {
		final List<Ordenative> selectedOrdenatives = view
				.getSelectedOrdenatives();
		if (selectedOrdenatives.size() > 0) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					if (!OrdenativeManager.addOrdenativesToReading(reading,
							selectedOrdenatives).hasErrors()) {
						view.notifyOrdenativesAddedSuccessfully();
					}
				}
			}).start();
			return true;
		} else
			view.notifyAtLeastSelectOne();
		return false;
	}

}
