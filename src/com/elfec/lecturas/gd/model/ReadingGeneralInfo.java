package com.elfec.lecturas.gd.model;

import java.math.BigDecimal;

import org.joda.time.DateTime;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Modelo de la información general de las lecturas
 * 
 * @author drodriguez
 *
 */
@Table(name = "ReadingGeneralInfos")
public class ReadingGeneralInfo extends Model {

	/**
	 * IDLECTURAGD en Oracle
	 */
	@Column(name = "ReadingRemoteId", notNull = true)
	private long readingRemoteId;
	/**
	 * ANIO en Oracle
	 */
	@Column(name = "Year", notNull = true)
	private short year;
	/**
	 * MES en Oracle
	 */
	@Column(name = "Month", notNull = true)
	private short month;
	/**
	 * DIA en Oraculo
	 */
	@Column(name = "Day", notNull = true)
	private int day;
	/**
	 * IDRUTA en Oracle
	 */
	@Column(name = "RouteId", notNull = true)
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
	@Column(name = "SupplyId", notNull = true)
	private int supplyId;
	/**
	 * NROSUM en Oracle
	 */
	@Column(name = "SupplyNumber")
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
	 * PORCENTAJE_AP (aseo público) en Oracle
	 */
	@Column(name = "PublicCleaningPercentage")
	private BigDecimal publicCleaningPercentage;
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

	// #region Getters y Setters

	public long getReadingRemoteId() {
		return readingRemoteId;
	}

	public void setReadingRemoteId(long readingRemoteId) {
		this.readingRemoteId = readingRemoteId;
	}

	public short getYear() {
		return year;
	}

	public void setYear(short year) {
		this.year = year;
	}

	public short getMonth() {
		return month;
	}

	public void setMonth(short month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
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

	public BigDecimal getPublicCleaningPercentage() {
		return publicCleaningPercentage;
	}

	public void setPublicCleaningPercentage(BigDecimal publicCleaningPercentage) {
		this.publicCleaningPercentage = publicCleaningPercentage;
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

	// #endregion

}
