package com.elfec.lecturas.gd.presenter.views.notifiers;

import java.util.List;

import com.elfec.lecturas.gd.model.ReadingGeneralInfo;
import com.elfec.lecturas.gd.model.RouteAssignment;
import com.elfec.lecturas.gd.model.enums.ReadingStatus;
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
	 * Evento que se ejecuta cuando en algún observable se generó un evento de
	 * selección de lectura
	 * 
	 * @param position
	 *            posición de la lectura en la lista
	 * @param sender
	 *            el observador que originó el evento
	 */
	public void notifyReadingSelected(int position, IReadingListObserver sender);

	/**
	 * Evento que se ejecuta cuando algun observable se generó un evento de
	 * cambio de lista de lecturas
	 * 
	 * @param readings
	 * @param sender
	 */
	public void notifyReadingListChanged(List<ReadingGeneralInfo> readings,
			IReadingListObserver sender);

	/**
	 * Evento que se ejecuta cuando se quiere ordenar a todos los observadores
	 * que reseteen cualquier filtro aplicado sobre la lista de lecturas
	 * 
	 * @param sender
	 */
	public void notifyResetFilters(IReadingListObserver sender);

	/**
	 * Evento que se ejecuta cuando se quiere notificar a todos los observadores
	 * que se aplicó un filtro sobre la lista de lecturas
	 * 
	 * @param sender
	 */
	public void notifyFiltersApplied(ReadingStatus status,
			RouteAssignment route, IReadingListObserver sender);

	/**
	 * Indica a todos los observers que deberían rebindear la lectura en la
	 * posición solicitada
	 * 
	 * @param position
	 *            posición de la lectura en la lista
	 * @param sender
	 *            el observador que originó el evento
	 */
	public void notifyRebindReading(int position, IReadingListObserver sender);

	/**
	 * Notifica a las vistas que debe eliminarse la lectura en la posición
	 * indicada
	 * 
	 * @param position
	 *            posición de la lectura en la lista
	 * @param sender
	 *            el observador que originó el evento
	 */
	public void notifyRemoveReading(int position, IReadingListObserver sender);
}
