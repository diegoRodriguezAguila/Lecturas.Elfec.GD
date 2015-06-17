package com.elfec.lecturas.gd.model.results;

import java.util.ArrayList;

/**
 * Es una clase que contiene el resultado de un acceso a datos adicionalmente de
 * una lista de errores en caso de haber ocurrido alguno
 * 
 * @author drodriguez
 * @param <TResult>
 */
public class DataAccessResult<TResult> extends TypedResult<TResult> {
	private boolean isRemoteDataAccess;

	public DataAccessResult() {
		listOfErrors = new ArrayList<Exception>();
	}

	public DataAccessResult(boolean isRemoteDataAccess) {
		this.isRemoteDataAccess = isRemoteDataAccess;
		listOfErrors = new ArrayList<Exception>();
	}

	public DataAccessResult(TResult result) {
		this.result = result;
		listOfErrors = new ArrayList<Exception>();
	}

	public DataAccessResult(TResult result, boolean isRemoteDataAccess) {
		this.result = result;
		this.isRemoteDataAccess = isRemoteDataAccess;
		listOfErrors = new ArrayList<Exception>();
	}

	/**
	 * Inidica si el resultado es de un acceso a datos remoto o no
	 * 
	 * @return true/false
	 */
	public boolean isRemoteDataAccess() {
		return isRemoteDataAccess;
	}

	public void setRemoteDataAccess(boolean isRemoteDataAccess) {
		this.isRemoteDataAccess = isRemoteDataAccess;
	}
}
