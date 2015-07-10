package com.elfec.lecturas.gd.presenter.views.notifiers;

import com.elfec.lecturas.gd.presenter.views.observers.IReadingListObserver;

/**
 * Provee de un notificador para los observadores de los eventos realizados
 * sobre una lista de lecturas
 * 
 * @author drodriguez
 *
 */
public interface IReadingListNotifier {
	/**
	 * Evento que se ejecuta cuando en alg�n observador se gener� un evento de
	 * selecci�n de lectura
	 * 
	 * @param position
	 *            posici�n de la lectura en la lista
	 * @param sender
	 *            el observador que origin� el evento
	 */
	public void notifyReadingSelected(int position, IReadingListObserver sender);
}
