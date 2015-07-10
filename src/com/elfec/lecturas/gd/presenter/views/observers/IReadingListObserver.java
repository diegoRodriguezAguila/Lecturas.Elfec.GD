package com.elfec.lecturas.gd.presenter.views.observers;

import java.util.List;

import com.elfec.lecturas.gd.model.ReadingGeneralInfo;
import com.elfec.lecturas.gd.presenter.views.notifiers.IReadingListNotifier;

/**
 * Representa una vista o cualquier observador que quiera recibir
 * actualizaciones de eventos sobre una lista de lecturas. Los observadores
 * deber�an utilizar el {@link IReadingListNotifier} en sus eventos sobre la
 * lista para notificar a otros observadores y a si mismos.
 * 
 * @author drodriguez
 *
 */
public interface IReadingListObserver {
	/**
	 * Asigna un listener para monitorear las acciones del observer
	 * 
	 * @param notifier
	 */
	public void setReadingListNotifier(IReadingListNotifier notifier);

	/**
	 * Asigna la lista de lecturas para mostrar. Esta asignaci�n no deber�a
	 * propagar nuevamente el evento de notificaci�n
	 * 
	 * @param readings
	 */
	public void setReadings(List<ReadingGeneralInfo> readings);

	/**
	 * Asigna la posici�n de la lectura seleccionada actual. Esta asignaci�n no
	 * deber�a propagar nuevamente el evento de notificaci�n
	 * 
	 * @param position
	 */
	public void setSelectedReading(int position);
}
