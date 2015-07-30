package com.elfec.lecturas.gd.presenter;

import com.elfec.lecturas.gd.model.ReadingTaken;
import com.elfec.lecturas.gd.presenter.views.IWipeAllDataView;

public class WipeAllDataPresenter {

	private IWipeAllDataView view;

	public WipeAllDataPresenter(IWipeAllDataView view) {
		this.view = view;
	}

	/**
	 * Define si se mostrará un dialogo de confirmación de eliminación o uno de
	 * que no se pueden eliminar los datos
	 */
	public void defineDialogType() {
		if (ReadingTaken.getAll(ReadingTaken.class).size() > 0)// hay lecturas
			view.initializeCannotWipeDialog();
		else
			view.initializeWipeConfirmDialog();
	}

}
