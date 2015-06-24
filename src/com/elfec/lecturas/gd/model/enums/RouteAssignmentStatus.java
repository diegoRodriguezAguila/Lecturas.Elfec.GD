package com.elfec.lecturas.gd.model.enums;

/**
 * Representa los estados de las asignaciones de rutas
 * 
 * @author drodriguez
 *
 */
public enum RouteAssignmentStatus {
	/**
	 * Estado de ruta asignada, pendiente de carga
	 */
	ASSIGNED,
	/**
	 * Estado de ruta importada al móvil
	 */
	IMPORTED,
	/**
	 * Estado de ruta exporta del móvil al servidor
	 */
	EXPORTED,
	/**
	 * Estado de ruta exportada del móvil al servidor para relectura
	 */
	RE_READING,
	/**
	 * Estado de ruta exportada del servidor al billing
	 */
	EXPORTED_BILLING,
	/**
	 * Estado de ruta en servidor asignada para su relectura
	 */
	RE_READING_ASSIGNED,
	/**
	 * Estado de ruta de relectura importada al móvil
	 */
	RE_READING_IMPORTED,
	/**
	 * Estado de ruta de relectura exportada del móvil al servidor
	 */
	RE_READING_EXPORTED;

	/**
	 * Obtiene el estado del usuario equivalente al short provisto
	 * 
	 * @param status
	 * @return
	 */
	public static RouteAssignmentStatus get(short status) {
		return RouteAssignmentStatus.values()[status - 1];
	}

	/**
	 * Convierte el estado de asignación de ruta actual a su short equivalente
	 * 
	 * @return Short equivalente al estado
	 */
	public short toShort() {
		return (short) (this.ordinal() + 1);
	}
}
