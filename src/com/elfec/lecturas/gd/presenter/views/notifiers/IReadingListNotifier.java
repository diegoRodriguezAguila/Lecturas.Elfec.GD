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
	 * Evento que se ejecuta cuando en algún observador se generó un evento de
	 * selección de lectura
	 * 
	 * @param position
	 *            posición de la lectura en la lista
	 * @param sender
	 *            el observador que originó el evento
	 */
	public void notifyReadingSelected(int position, IReadingListObserver sender);
}
