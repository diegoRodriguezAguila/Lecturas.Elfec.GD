package com.elfec.lecturas.gd.model;

import java.util.Locale;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.elfec.lecturas.gd.model.enums.RouteAssignmentStatus;

/**
 * Modelo de la asignación de rutas del usuario
 * 
 * @author drodriguez
 *
 */
@Table(name = "RouteAssignments")
public class RouteAssignment extends Model {

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
	 * Obtiene la consulta update para una asignación de ruta
	 * 
	 * @return consulta SQL lista para ser ejecutada
	 */
	public String toUpdateSQL() {
		return String.format(Locale.getDefault(), updateQuery, status,
				sentReadings, assignedUser, day, month, year, route,
				routeStart, routeEnd);
	}

	/**
	 * Elimina todas las rutas asignadas a un usuario
	 * 
	 * @param username
	 */
	public static void deleteAllUserRouteAssignments(String assignedUser) {
		new Delete().from(RouteAssignment.class)
				.where("AssignedUser = ?", assignedUser).execute();
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
