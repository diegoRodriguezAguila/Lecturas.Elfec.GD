package com.elfec.lecturas.gd.presenter;

import com.elfec.lecturas.gd.business_logic.ReadingGeneralInfoManager;
import com.elfec.lecturas.gd.model.ReadingGeneralInfo;
import com.elfec.lecturas.gd.model.results.TypedResult;
import com.elfec.lecturas.gd.presenter.views.IReadingSearchView;

/**
 * Presenter para la vista de búsqueda de lecturas
 * 
 * @author drodriguez
 *
 */
public class ReadingSearchPresenter {

	private IReadingSearchView view;

	public ReadingSearchPresenter(IReadingSearchView view) {
		this.view = view;
	}

	/**
	 * Inicia la búsqueda de lecturas
	 */
	public void searchReading() {
		final String accountNumber = view.getAccountNumber();
		final String meter = view.getMeter();
		final int nus = view.getNUS();
		if (!accountNumber.isEmpty() || !meter.isEmpty() || nus != -1) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					view.notifySearchStarted();
					TypedResult<ReadingGeneralInfo> result = new ReadingGeneralInfoManager()
							.searchReading(accountNumber, meter, nus);
					view.showSearchErrors(result.getErrors());
					if (!result.hasErrors())
						view.showReadingFound(result.getResult());
				}
			}).start();
		}
	}

}
