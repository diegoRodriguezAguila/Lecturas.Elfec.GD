package com.elfec.lecturas.gd.model;

import java.util.List;
import java.util.Locale;

import org.joda.time.DateTime;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.elfec.lecturas.gd.model.enums.ReadingStatus;
import com.elfec.lecturas.gd.model.enums.RouteAssignmentStatus;

/**
 * Modelo de la asignaci�n de rutas del usuario
 * 
 * @author drodriguez
 *
 */
@Table(name = "RouteAssignments")
public class RouteAssignment extends Model {

	private static final long ACCOUNT_CONST = 100000;
	private static final String importQuery = "SELECT * FROM MOVILES.LECTURASGD WHERE ANIO = %d AND MES = %d AND DIA = %d AND IDRUTA = %d AND ( NROSUM BETWEEN %d AND %d )";
	private static final String retryImportQuery = " AND IDLECTURAGD IN (SELECT IDLECTURAGD FROM ERP_ELFEC.SGC_MOVIL_LECTURAS_GD WHERE ESTADO=%d)";
	private static final String updateQuery = "UPDATE MOVILES.USUARIO_ASIGNACION SET ESTADO=%d, CANT_LEC_REC=%d WHERE UPPER(USUARIO)=UPPER('%s') AND DIA=%d AND MES=%d AND ANIO=%d AND RUTA=%d AND ORDEN_INICIO=%d AND ORDEN_FIN=%d";

	@Column(name = "AssignedUser", notNull = true)
	private String assignedUser;

	@Column(name = "Route", notNull = true)
	private int route;

	@Column(name = "AssignmentDay", notNull = true)
	private short assignmentDay;

	@Column(name = "Day", notNull = true)
	private short day;

	@Column(name = "Month", notNull = true)
	private short month;

	@Column(name = "Year", notNull = true)
	private int year;

	@Column(name = "RouteStart")
	private int routeStart;

	@Column(name = "RouteEnd")
	private int routeEnd;

	@Column(name = "Status")
	private short status;

	@Column(name = "ReceivedReadings")
	private int receivedReadings;

	@Column(name = "SentReadings")
	private int sentReadings;

	public RouteAssignment() {
		super();
	}

	public RouteAssignment(String assignedUser, int route, short assignmentDay,
			short day, short month, int year, int routeStart, int routeEnd,
			RouteAssignmentStatus status, int receivedReadings) {
		super();
		this.assignedUser = assignedUser;
		this.route = route;
		this.day = day;
		this.month = month;
		this.year = year;
		this.routeStart = routeStart;
		this.routeEnd = routeEnd;
		this.status = status.toShort();
		this.receivedReadings = receivedReadings;
		this.sentReadings = 0;
	}

	/**
	 * Obtiene la fecha de la asignaci�n en el rol
	 * 
	 * @return fecha de la asiganci�n de la ruta en el rol
	 */
	public DateTime getScheduleDate() {
		return new DateTime(year, month, day, 0, 0);
	}

	/**
	 * Obtiene la cuenta de inicio de la ruta
	 * 
	 * @return NROSUM o Cuenta de inicio de ruta
	 */
	public long getFirstSupplyNumber() {
		long startSupplyNumber = route * ACCOUNT_CONST;
		return startSupplyNumber += routeStart;
	}

	/**
	 * Obtiene la cuenta de fin de la ruta
	 * 
	 * @return NROSUM o Cuenta de inicio de ruta
	 */
	public long getLastSupplyNumber() {
		long startSupplyNumber = route * ACCOUNT_CONST;
		return startSupplyNumber += routeEnd;
	}

	/**
	 * Obtiene la consulta update para una asignaci�n de ruta
	 * 
	 * @return consulta SQL lista para ser ejecutada
	 */
	public String toUpdateSQL() {
		return String.format(Locale.getDefault(), updateQuery, status,
				sentReadings, assignedUser, day, month, year, route,
				routeStart, routeEnd);
	}

	/**
	 * Obtiene la consulta de importaci�n para una asignaci�n de ruta
	 * 
	 * @return consulta SQL lista para ser ejecutada
	 */
	public String toImportationSQL() {
		return String.format(importQuery, year, month, day, route,
				getFirstSupplyNumber(), getLastSupplyNumber())
				+ (getStatus() == RouteAssignmentStatus.RE_READING_ASSIGNED ? (String
						.format(retryImportQuery, ReadingStatus.RETRY.toShort()))
						: "");
	}

	/**
	 * Elimina todas las rutas asignadas a un usuario que no esten en estado
	 * cargada
	 * 
	 * @param assignedUser
	 */
	public static void deleteAllNonImportedUserRouteAssignments(
			String assignedUser) {
		new Delete().from(RouteAssignment.class)
				.where("AssignedUser = ?", assignedUser)
				.where("Status IN (1, 6)").execute();
	}

	/**
	 * Obtiene todas las rutas cargadas asignadas al usuario
	 * 
	 * @param assignedUser
	 * @return lista de rutas asignadas
	 */
	public static List<RouteAssignment> getAllImportedUserRouteAssignments(
			String assignedUser) {
		return new Select().from(RouteAssignment.class)
				.where("AssignedUser = ?", assignedUser)
				.where("Status IN (2, 7)").execute();
	}

	/**
	 * Obtiene todas las rutas cargadas
	 * 
	 * @return lista de rutas asignadas
	 */
	public static List<RouteAssignment> getAllImportedRouteAssignments() {
		return new Select().from(RouteAssignment.class)
				.where("Status IN (2, 7)").execute();
	}

	/**
	 * Indica si la ruta tiene alg�n estado de asignada. Su status es igual a
	 * {@link RouteAssignmentStatus#ASSIGNED} o
	 * {@link RouteAssignmentStatus#RE_READING}
	 * 
	 * @return true si es que la ruta fu� asignada
	 */
	public boolean isAssigned() {
		RouteAssignmentStatus status = getStatus();
		return status == RouteAssignmentStatus.ASSIGNED
				|| status == RouteAssignmentStatus.RE_READING_ASSIGNED;
	}

	/**
	 * Indica si la ruta tiene alg�n estado de importada. Su status es igual a
	 * {@link RouteAssignmentStatus#IMPORTED} o
	 * {@link RouteAssignmentStatus#RE_READING_IMPORTED}
	 * 
	 * @return true si es que la ruta fu� importada
	 */
	public boolean isImported() {
		RouteAssignmentStatus status = getStatus();
		return status == RouteAssignmentStatus.IMPORTED
				|| status == RouteAssignmentStatus.RE_READING_IMPORTED;
	}

	/**
	 * Verifica si es que la ruta tiene alguna lectura puesta para reintentar
	 * 
	 * @return true si es que tiene lecturas para reintentar
	 */
	public boolean hasRetryReadings() {
		return ReadingGeneralInfo.getAllReadingsSorted(ReadingStatus.RETRY,
				this).size() > 0;
	}

	// #region Getters y Setters
	public String getAssignedUser() {
		return assignedUser;
	}

	public void setAssignedUser(String assignedUser) {
		this.assignedUser = assignedUser;
	}

	public int getRoute() {
		return route;
	}

	public void setRoute(int route) {
		this.route = route;
	}

	public short getAssignmentDay() {
		return assignmentDay;
	}

	public void setAssignmentDay(short assignmentDay) {
		this.assignmentDay = assignmentDay;
	}

	public short getDay() {
		return day;
	}

	public void setDay(short day) {
		this.day = day;
	}

	public short getMonth() {
		return month;
	}

	public void setMonth(short month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getRouteStart() {
		return routeStart;
	}

	public void setRouteStart(int routeStart) {
		this.routeStart = routeStart;
	}

	public int getRouteEnd() {
		return routeEnd;
	}

	public void setRouteEnd(int routeEnd) {
		this.routeEnd = routeEnd;
	}

	public RouteAssignmentStatus getStatus() {
		return RouteAssignmentStatus.get(status);
	}

	public void setStatus(RouteAssignmentStatus status) {
		this.status = status.toShort();
	}

	public int getReceivedReadings() {
		return receivedReadings;
	}

	public void setReceivedReadings(int receivedReadings) {
		this.receivedReadings = receivedReadings;
	}

	public int getSentReadings() {
		return sentReadings;
	}

	public void setSentReadings(int sentReadings) {
		this.sentReadings = sentReadings;
	}
	// #endregion
}
