package com.elfec.lecturas.gd.presenter.views.observers;

import java.util.List;

import com.elfec.lecturas.gd.model.ReadingGeneralInfo;
import com.elfec.lecturas.gd.presenter.views.notifiers.IReadingListNotifier;

/**
 * Representa una vista o cualquier observador que quiera recibir
 * actualizaciones de eventos sobre una lista de lecturas. Los observadores
 * deberían utilizar el {@link IReadingListNotifier} en sus eventos sobre la
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
	 * Devuelve el listener para monitorear las acciones del observer asignado
	 * 
	 * @return {@link IReadingListNotifier} notifier asignado o null en caso de
	 *         no haberse asignado ninguno
	 */
	public IReadingListNotifier getReadingListNotifier();

	/**
	 * Asigna la lista de lecturas para mostrar. Esta asignación no debería
	 * propagar nuevamente el evento de notificación de cambio de lista de
	 * lecturas
	 * 
	 * @param readings
	 */
	public void setReadings(List<ReadingGeneralInfo> readings);

	/**
	 * Rebindea los datos de la lectura actual a las vistas que la esten
	 * observando, esto sirve para notificar sobre cambios que se realizaron en
	 * la lectura actual
	 * 
	 * @param position
	 */
	public void rebindReading(int position);

	/**
	 * Elimina la lectura de la posición especificada
	 * 
	 * @param position
	 */
	public void removeReading(int position);

	/**
	 * Asigna la posición de la lectura seleccionada actual. Esta asignación no
	 * debería propagar nuevamente el evento de notificación
	 * 
	 * @param position
	 */
	public void setSelectedReading(int position);

	/**
	 * Hace que se resetee cualquier filtro que haya sido aplicado sobre la
	 * lista de lecturas
	 */
	public void resetFilters();
}
