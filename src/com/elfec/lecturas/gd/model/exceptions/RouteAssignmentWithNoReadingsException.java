package com.elfec.lecturas.gd.model.exceptions;

import org.joda.time.DateTime;

import com.elfec.lecturas.gd.model.RouteAssignment;

/**
 * Excepción que se lanza cuando no se encontraron lecturas para la asignación
 * de ruta
 * 
 * @author drodriguez
 *
 */
public class RouteAssignmentWithNoReadingsException extends Exception {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 6837574751992918201L;
	private int route;
	private DateTime scheduleDate;

	public RouteAssignmentWithNoReadingsException(RouteAssignment assignedRoute) {
		this.route = assignedRoute.getRoute();
		this.scheduleDate = assignedRoute.getScheduleDate();
	}

	@Override
	public String getMessage() {
		return "No existen lecturas de la ruta <b>"
				+ route
				+ "</b>  en la fecha del rol: <b>"
				+ scheduleDate.toString("dd/MM/yyyy")
				+ "</b>. Si tiene problemas al realizar la carga solicite la desasignación de dicha ruta al administrador.";
	}
}
