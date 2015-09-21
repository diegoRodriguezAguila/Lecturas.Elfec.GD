package com.elfec.lecturas.gd.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.From;
import com.activeandroid.query.Select;
import com.elfec.lecturas.gd.model.enums.ReadingStatus;

/**
 * Modelo de la información general de las lecturas
 * 
 * @author drodriguez
 *
 */
@Table(name = "ReadingsGeneralInfo")
public class ReadingGeneralInfo extends Model implements Serializable {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = -7318814193344831496L;
	/**
	 * IDLECTURAGD en Oracle
	 */
	@Column(name = "ReadingRemoteId", notNull = true, index = true)
	private long readingRemoteId;
	/**
	 * ANIO en Oracle
	 */
	@Column(name = "Year", notNull = true)
	private int year;
	/**
	 * MES en Oracle
	 */
	@Column(name = "Month", notNull = true)
	private short month;
	/**
	 * DIA en Oraculo
	 */
	@Column(name = "Day", notNull = true)
	private short day;
	/**
	 * IDRUTA en Oracle
	 */
	@Column(name = "RouteId", notNull = true, index = true)
	private int routeId;
	/**
	 * IDCLIENTE en Oracle
	 */
	@Column(name = "ClientId", notNull = true)
	private int clientId;
	/**
	 * IDSUMINISTRO Identificador del suministros, tabla de referencia
	 * suministros
	 */
	@Column(name = "SupplyId", notNull = true, index = true)
	private int supplyId;
	/**
	 * NROSUM en Oracle
	 */
	@Column(name = "SupplyNumber", index = true)
	private String supplyNumber;
	/**
	 * NOMBRE en Oracle
	 */
	@Column(name = "Name")
	private String name;
	/**
	 * DIRECCION en Oracle
	 */
	@Column(name = "Address")
	private String address;
	/**
	 * NIT en Oracle
	 */
	@Column(name = "NIT")
	private String NIT;
	/**
	 * IDCATEGORIA en Oracle
	 */
	@Column(name = "CategoryId", notNull = true)
	private String categoryId;
	/**
	 * DESCRIP_CATEG en Oracle
	 */
	@Column(name = "CategoryDescription")
	private String categoryDescription;
	/**
	 * IDMEDIDOR en Oracle
	 */
	@Column(name = "MeterId", notNull = true)
	private int meterId;
	/**
	 * IMPRIMIR_AVISO en Oracle
	 */
	@Column(name = "PrintPrompt")
	private short printPrompt;
	/**
	 * IMPORTE_DEUDA en Oracle
	 */
	@Column(name = "DebtAmount")
	private BigDecimal debtAmount;
	/**
	 * MESES_DEUDA en Oracle
	 */
	@Column(name = "OwedMonths")
	private String owedMonths;
	/**
	 * PERDIDAS_FE en Oracle
	 */
	@Column(name = "FELosses")
	private BigDecimal FELosses;
	/**
	 * PERDIDAS_CU en Oracle
	 */
	@Column(name = "CULosses")
	private BigDecimal CULosses;
	/**
	 * FACOR_CARGA en Oracle
	 */
	@Column(name = "DemandFactor")
	private BigDecimal demandFactor;
	/**
	 * HORAS_MES en Oracle
	 */
	@Column(name = "MonthHours")
	private int monthHours;
	/**
	 * TIPO_ASEO en Oracle
	 */
	@Column(name = "CleaningType")
	private short cleaningType;
	/**
	 * PORCENTAJE_AP (alumbrado público) en Oracle
	 */
	@Column(name = "PublicLightingPercentage")
	private BigDecimal publicLightingPercentage;
	/**
	 * TIPO_AP (alumbrado público) en Oracle
	 */
	@Column(name = "PublicLightingType")
	private short publicLightingType;
	/**
	 * POT_PUNTA_CONTRATADA en Oracle
	 */
	@Column(name = "ContractedPeakPower")
	private int contractedPeakPower;
	/**
	 * POT_FPUNTA_CONTRATADA en Oracle
	 */
	@Column(name = "ContractedOffpeakPower")
	private int contractedOffpeakPower;
	/**
	 * FECHA_PASIBLE_CORTE en Oracle
	 */
	@Column(name = "OutagePassibleDate")
	private DateTime outagePassibleDate;
	/**
	 * FECHA_VENCIMIENTO en Oracle
	 */
	@Column(name = "ExpirationDate")
	private DateTime expirationDate;
	/**
	 * TENSION en Oracle
	 */
	@Column(name = "Tension")
	private int tension;
	/**
	 * MEDICION en Oracle
	 */
	@Column(name = "Measurement")
	private int measurement;
	/**
	 * TRAFO_DESC en Oracle
	 */
	@Column(name = "TransformerDesc")
	private String transformerDesc;

	// Extra Vars
	/**
	 * El estado actual de la lectura
	 */
	@Column(name = "Status", index = true)
	private short status;

	/**
	 * Guarda la información del medidor relacionado a esta lectura
	 */
	private transient ReadingMeter readingMeter;
	/**
	 * Guarda la información de la lectura tomada por el lector
	 */
	private transient ReadingTaken readingTaken;

	public ReadingGeneralInfo() {
		super();
	}

	public ReadingGeneralInfo(long readingRemoteId, int year, short month,
			short day, int routeId, int clientId, int supplyId,
			String supplyNumber, String name, String address, String NIT,
			String categoryId, String categoryDescription, int meterId,
			short printPrompt, BigDecimal debtAmount, String owedMonths,
			BigDecimal fELosses, BigDecimal cULosses, BigDecimal demandFactor,
			int monthHours, short cleaningType,
			BigDecimal publicLightingPercentage, short publicLightingType,
			int contractedPeakPower, int contractedOffpeakPower,
			DateTime outagePassibleDate, DateTime expirationDate, int tension,
			int measurement, String transformerDesc, ReadingStatus status) {
		super();
		initialize(readingRemoteId, year, month, day, routeId, clientId,
				supplyId, supplyNumber, name, address, NIT, categoryId,
				categoryDescription, meterId, printPrompt, debtAmount,
				owedMonths, fELosses, cULosses, demandFactor, monthHours,
				cleaningType, publicLightingPercentage, publicLightingType,
				contractedPeakPower, contractedOffpeakPower,
				outagePassibleDate, expirationDate, tension, measurement,
				transformerDesc, status.toShort());
	}

	// #region Initializer
	/**
	 * Inicializa los campos de la lectura
	 * 
	 * @param readingRemoteId
	 * @param year
	 * @param month
	 * @param day
	 * @param routeId
	 * @param clientId
	 * @param supplyId
	 * @param supplyNumber
	 * @param name
	 * @param address
	 * @param NIT
	 * @param categoryId
	 * @param categoryDescription
	 * @param meterId
	 * @param printPrompt
	 * @param debtAmount
	 * @param owedMonths
	 * @param fELosses
	 * @param cULosses
	 * @param demandFactor
	 * @param monthHours
	 * @param cleaningType
	 * @param publicCleaningPercentage
	 * @param publicLightingType
	 * @param contractedPeakPower
	 * @param contractedOffpeakPower
	 * @param outagePassibleDate
	 * @param expirationDate
	 * @param s
	 */
	public void initialize(long readingRemoteId, int year, short month,
			short day, int routeId, int clientId, int supplyId,
			String supplyNumber, String name, String address, String NIT,
			String categoryId, String categoryDescription, int meterId,
			short printPrompt, BigDecimal debtAmount, String owedMonths,
			BigDecimal fELosses, BigDecimal cULosses, BigDecimal demandFactor,
			int monthHours, short cleaningType,
			BigDecimal publicLightingPercentage, short publicLightingType,
			int contractedPeakPower, int contractedOffpeakPower,
			DateTime outagePassibleDate, DateTime expirationDate, int tension,
			int measurement, String transformerDesc, short s) {
		this.readingRemoteId = readingRemoteId;
		this.year = year;
		this.month = month;
		this.day = day;
		this.routeId = routeId;
		this.clientId = clientId;
		this.supplyId = supplyId;
		this.supplyNumber = supplyNumber;
		this.name = name;
		this.address = address;
		this.NIT = NIT;
		this.categoryId = categoryId;
		this.categoryDescription = categoryDescription;
		this.meterId = meterId;
		this.printPrompt = printPrompt;
		this.debtAmount = debtAmount;
		this.owedMonths = owedMonths;
		this.FELosses = fELosses;
		this.CULosses = cULosses;
		this.demandFactor = demandFactor;
		this.monthHours = monthHours;
		this.cleaningType = cleaningType;
		this.publicLightingPercentage = publicLightingPercentage;
		this.publicLightingType = publicLightingType;
		this.contractedPeakPower = contractedPeakPower;
		this.contractedOffpeakPower = contractedOffpeakPower;
		this.outagePassibleDate = outagePassibleDate;
		this.expirationDate = expirationDate;
		this.tension = tension;
		this.measurement = measurement;
		this.transformerDesc = transformerDesc;
		this.status = s;
	}

	// #endregion

	/**
	 * Obtiene la información del medidor de la lectura. Cachéa esta consulta en
	 * una variable
	 * 
	 * @return {@link ReadingMeter}
	 */
	public ReadingMeter getReadingMeter() {
		if (readingMeter == null)
			readingMeter = new Select().from(ReadingMeter.class)
					.where("ReadingRemoteId = ?", readingRemoteId)
					.executeSingle();
		return readingMeter;
	}

	/**
	 * Asigna a la lectura la lectura tomada y el estado indicado.
	 * Posteriormente se guarda esta lectura con el nuevo estado. Es necesario
	 * llamar a {@link #save()} para que los cambios surtan efecto
	 * 
	 * @param readingTaken
	 *            La lectura tomada, es necesario haber llamado a
	 *            {@link #save()}, es decir que esté guardada en la base de
	 *            datos antes de asignarla caso contrario lanza excepción
	 *            {@link IllegalArgumentException}
	 * @param status
	 *            {@link ReadingStatus#READ}, {@link ReadingStatus#IMPEDED} o
	 *            {@link ReadingStatus#RETRY} caso contrario lanza excepción
	 *            {@link IllegalArgumentException}
	 */
	public void assignReadingTaken(ReadingTaken readingTaken,
			ReadingStatus status) {
		if (status != ReadingStatus.READ && status != ReadingStatus.IMPEDED
				&& status != ReadingStatus.RETRY)
			throw new IllegalArgumentException(
					"La lectura solo puede asignar como tomada con los estados: LEIDA, IMPEDIDA o REINTENTAR!");
		if (readingTaken != null && readingTaken.getId() != null
				&& readingTaken.getId() != -1) {
			this.readingTaken = readingTaken;
			setStatus(status);
		} else
			throw new IllegalArgumentException(
					"La lectura tomada tiene que haberse guardado en la base de datos antes de asignarla a la lectura");
	}

	/**
	 * Obtiene la información de la lectura tomada por el lector. Cachéa esta
	 * consulta en una variable. Esta consulta depende del estado de la lectura
	 * si está en {@link ReadingStatus#READ}, {@link ReadingStatus#IMPEDED} o
	 * {@link ReadingStatus#RETRY} hace la consulta a la base de datos, caso
	 * contrario devuelve null.
	 * 
	 * @return {@link ReadingTaken} la lectura tomada, o null en caso de no
	 *         haberse tomado la lectura aún
	 */
	public ReadingTaken getReadingTaken() {
		ReadingStatus status = getStatus();
		if (status != ReadingStatus.READ && status != ReadingStatus.IMPEDED
				&& status != ReadingStatus.RETRY)
			return null;
		if (readingTaken == null)
			readingTaken = new Select().from(ReadingTaken.class)
					.where("ReadingRemoteId = ?", readingRemoteId)
					.executeSingle();
		return readingTaken;
	}

	/**
	 * Obtiene todos los ordenativos asignados a esta lectura. Esta consulta
	 * depende del estado de la lectura si está en {@link ReadingStatus#READ} o
	 * {@link ReadingStatus#IMPEDED}, en cuyo caso devuelve el resultado de la
	 * consulta. Si no, devuelve una lista vacía
	 * 
	 * @return Lista de ordenativos de la lectura
	 */
	public List<Ordenative> getAssignedOrdenatives() {
		if (getStatus() != ReadingStatus.READ
				&& getStatus() != ReadingStatus.IMPEDED)
			return new ArrayList<>();
		return new Select().from(Ordenative.class).as("o")
				.join(ReadingOrdenative.class).as("ro").on("o.Code=ro.Code")
				.join(ReadingGeneralInfo.class).as("r")
				.on("r.ReadingRemoteId=ro.ReadingRemoteId")
				.where("r.ReadingRemoteId = ?", readingRemoteId)
				.orderBy("o.Code").execute();
	}

	/**
	 * Obtiene todas las lecturas ordenadas por numero de cuenta
	 * 
	 * @return Lista de lecturas
	 */
	public static List<ReadingGeneralInfo> getAllReadingsSorted() {
		return new Select().from(ReadingGeneralInfo.class)
				.orderBy("SupplyNumber").execute();
	}

	/**
	 * Obtiene todas las lecturas ordenadas por numero de cuenta. Si se
	 * proporcionan parámetros se filtra la lista según ellos
	 * 
	 * @param status
	 *            {@link ReadingStatus} estado de la lectura
	 * @param route
	 *            {@link RouteAssignment} assignación de ruta
	 * @return Lista de lecturas
	 */
	public static List<ReadingGeneralInfo> getAllReadingsSorted(
			ReadingStatus status, RouteAssignment route) {
		From query = new Select().from(ReadingGeneralInfo.class);
		if (status != null)
			query.where("Status=?", status.toShort());
		if (route != null)
			query.where("RouteId=?", route.getRoute());
		return query.orderBy("SupplyNumber").execute();
	}

	/**
	 * Elimina toda la información general de lecturas que pertenezcan a la
	 * asignación de ruta dada
	 * 
	 * @param routeAssignment
	 */
	public static void deleteAssignedRouteReadingsInfo(
			RouteAssignment routeAssignment) {
		new Delete()
				.from(ReadingGeneralInfo.class)
				.where("RouteId = ? AND Day = ? AND Month = ? AND Year = ?",
						routeAssignment.getRoute(), routeAssignment.getDay(),
						routeAssignment.getMonth(), routeAssignment.getYear())
				.where("SupplyNumber BETWEEN "
						+ routeAssignment.getFirstSupplyNumber() + " AND "
						+ routeAssignment.getLastSupplyNumber()).execute();
	}

	/**
	 * Busca una lectura que corresponda con alguno de los parámetros pasados.
	 * Si se pasan múltiples parámetros se busca con todas las condiciones
	 * simultaneamente
	 * 
	 * @param accountNumber
	 * @param meter
	 * @param nus
	 * @return {@link ReadingGeneralInfo} lectura encontrada o null en caso de
	 *         no haberse encontrado ninguna.
	 */
	public static ReadingGeneralInfo findReading(String accountNumber,
			String meter, int nus) {
		From query = new Select().from(ReadingGeneralInfo.class).as("r");
		if (accountNumber != null && !accountNumber.isEmpty())
			query.where("r.SupplyNumber=?", accountNumber);
		if (meter != null && !meter.isEmpty()) {
			query.join(ReadingMeter.class).as("m")
					.on("r.ReadingRemoteId=m.ReadingRemoteId");
			query.where("m.SerialNumber =?", meter);
		}
		if (nus != -1)
			query.where("r.SupplyId=?", nus);
		return query.executeSingle();
	}

	/**
	 * Busca una lectura según su Id de lectura
	 * 
	 * @param readingRemoteId
	 * @return Lectura encontrada, null en caso de no haberse encontrado
	 */
	public static ReadingGeneralInfo findByReadingRemoteId(long readingRemoteId) {
		return new Select().from(ReadingGeneralInfo.class)
				.where("ReadingRemoteId = ?", readingRemoteId).executeSingle();
	}

	// #region Serialization
	/**
	 * Metodo de serialización manual
	 * 
	 * @param out
	 * @throws IOException
	 */
	private void writeObject(ObjectOutputStream out) throws IOException {
		out.writeLong(getId());
		out.writeLong(readingRemoteId);
		out.writeInt(year);
		out.writeShort(month);
		out.writeShort(day);
		out.writeInt(routeId);
		out.writeInt(clientId);
		out.writeInt(supplyId);
		out.writeUTF(supplyNumber);
		out.writeUTF(name);
		out.writeUTF(address);
		out.writeUTF(NIT);
		out.writeUTF(categoryId);
		out.writeUTF(categoryDescription);
		out.writeInt(meterId);
		out.writeShort(printPrompt);
		out.writeObject(debtAmount);
		out.writeUTF(owedMonths);
		out.writeObject(FELosses);
		out.writeObject(CULosses);
		out.writeObject(demandFactor);
		out.writeInt(monthHours);
		out.writeShort(cleaningType);
		out.writeObject(publicLightingPercentage);
		out.writeShort(publicLightingType);
		out.writeInt(contractedPeakPower);
		out.writeInt(contractedOffpeakPower);
		out.writeObject(outagePassibleDate);
		out.writeObject(expirationDate);
		out.writeInt(tension);
		out.writeInt(measurement);
		out.writeUTF(transformerDesc);
		out.writeShort(status);
	}

	/**
	 * Metodo de serialización manual
	 * 
	 * @param in
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void readObject(ObjectInputStream in) throws IOException,
			ClassNotFoundException {
		setId(in.readLong());
		initialize(in.readLong(), in.readInt(), in.readShort(), in.readShort(),
				in.readInt(), in.readInt(), in.readInt(), in.readUTF(),
				in.readUTF(), in.readUTF(), in.readUTF(), in.readUTF(),
				in.readUTF(), in.readInt(), in.readShort(),
				(BigDecimal) in.readObject(), in.readUTF(),
				(BigDecimal) in.readObject(), (BigDecimal) in.readObject(),
				(BigDecimal) in.readObject(), in.readInt(), in.readShort(),
				(BigDecimal) in.readObject(), in.readShort(), in.readInt(),
				in.readInt(), (DateTime) in.readObject(),
				(DateTime) in.readObject(), in.readInt(), in.readInt(),
				in.readUTF(), in.readShort());
	}

	// #endregion

	// #region Getters y Setters

	public long getReadingRemoteId() {
		return readingRemoteId;
	}

	public void setReadingRemoteId(long readingRemoteId) {
		this.readingRemoteId = readingRemoteId;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public short getMonth() {
		return month;
	}

	public void setMonth(short month) {
		this.month = month;
	}

	public short getDay() {
		return day;
	}

	public void setDay(short day) {
		this.day = day;
	}

	public int getRouteId() {
		return routeId;
	}

	public void setRouteId(int routeId) {
		this.routeId = routeId;
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public int getSupplyId() {
		return supplyId;
	}

	public void setSupplyId(int supplyId) {
		this.supplyId = supplyId;
	}

	public String getSupplyNumber() {
		return supplyNumber;
	}

	public void setSupplyNumber(String supplyNumber) {
		this.supplyNumber = supplyNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNIT() {
		return NIT;
	}

	public void setNIT(String nIT) {
		NIT = nIT;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryDescription() {
		return categoryDescription;
	}

	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}

	public int getMeterId() {
		return meterId;
	}

	public void setMeterId(int meterId) {
		this.meterId = meterId;
	}

	public short getPrintPrompt() {
		return printPrompt;
	}

	public void setPrintPrompt(short printPrompt) {
		this.printPrompt = printPrompt;
	}

	public BigDecimal getDebtAmount() {
		return debtAmount;
	}

	public void setDebtAmount(BigDecimal debtAmount) {
		this.debtAmount = debtAmount;
	}

	public String getOwedMonths() {
		return owedMonths;
	}

	public void setOwedMonths(String owedMonths) {
		this.owedMonths = owedMonths;
	}

	public BigDecimal getFELosses() {
		return FELosses;
	}

	public void setFELosses(BigDecimal fELosses) {
		FELosses = fELosses;
	}

	public BigDecimal getCULosses() {
		return CULosses;
	}

	public void setCULosses(BigDecimal cULosses) {
		CULosses = cULosses;
	}

	public BigDecimal getDemandFactor() {
		return demandFactor;
	}

	public void setDemandFactor(BigDecimal demandFactor) {
		this.demandFactor = demandFactor;
	}

	public int getMonthHours() {
		return monthHours;
	}

	public void setMonthHours(int monthHours) {
		this.monthHours = monthHours;
	}

	public short getCleaningType() {
		return cleaningType;
	}

	public void setCleaningType(short cleaningType) {
		this.cleaningType = cleaningType;
	}

	public BigDecimal getPublicLightingPercentage() {
		return publicLightingPercentage;
	}

	public void setPublicLightingPercentage(BigDecimal publicCleaningPercentage) {
		this.publicLightingPercentage = publicCleaningPercentage;
	}

	public short getPublicLightingType() {
		return publicLightingType;
	}

	public void setPublicLightingType(short publicLightingType) {
		this.publicLightingType = publicLightingType;
	}

	public int getContractedPeakPower() {
		return contractedPeakPower;
	}

	public void setContractedPeakPower(int contractedPeakPower) {
		this.contractedPeakPower = contractedPeakPower;
	}

	public int getContractedOffpeakPower() {
		return contractedOffpeakPower;
	}

	public void setContractedOffpeakPower(int contractedOffpeakPower) {
		this.contractedOffpeakPower = contractedOffpeakPower;
	}

	public DateTime getOutagePassibleDate() {
		return outagePassibleDate;
	}

	public void setOutagePassibleDate(DateTime outagePassibleDate) {
		this.outagePassibleDate = outagePassibleDate;
	}

	public DateTime getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(DateTime expirationDate) {
		this.expirationDate = expirationDate;
	}

	public ReadingStatus getStatus() {
		return ReadingStatus.get(status);
	}

	public void setStatus(ReadingStatus status) {
		this.status = status.toShort();
	}

	public int getTension() {
		return tension;
	}

	public void setTension(int tension) {
		this.tension = tension;
	}

	public int getMeasurement() {
		return measurement;
	}

	public void setMeasurement(int measurement) {
		this.measurement = measurement;
	}

	public String getTransformerDesc() {
		return transformerDesc;
	}

	public void setTransformerDesc(String transformerDesc) {
		this.transformerDesc = transformerDesc;
	}

	// #endregion

}
