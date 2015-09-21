package com.elfec.lecturas.gd.model.exceptions;

import com.elfec.lecturas.gd.model.RouteAssignment;

/**
 * Excepci�n que se lanza cuando no se encontraron lecturas para la asignaci�n
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
	private RouteAssignment assignedRoute;

	public RouteAssignmentWithNoReadingsException(RouteAssignment assignedRoute) {
		this.assignedRoute = assignedRoute;
	}

	@Override
	public String getMessage() {
		return "No existen lecturas de la ruta <b>"
				+ assignedRoute.getRoute()
				+ "</b>  en la fecha del rol: <b>"
				+ assignedRoute.getScheduleDate().toString("dd/MM/yyyy")
				+ "</b>. Si tiene problemas al realizar la carga solicite la desasignaci�n de dicha ruta al administrador.";
	}
}
