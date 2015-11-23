package com.elfec.lecturas.gd.model.results;

import java.util.ArrayList;

/**
 * Es una clase que contiene el resultado de un acceso a datos adicionalmente de
 * una lista de errores en caso de haber ocurrido alguno
 * 
 * @author Diego
 *
 * @param <TResult>
 */
public class TypedResult<TResult> extends VoidResult {
	/**
	 * Serial
	 */
	private static final long serialVersionUID = 8475461808008087175L;
	protected transient TResult result;

	public TypedResult() {
		listOfErrors = new ArrayList<Exception>();
	}

	public TypedResult(TResult result) {
		this.result = result;
		listOfErrors = new ArrayList<Exception>();
	}

	/**
	 * Asigna el resultado a un servicio web
	 * 
	 * @param result
	 * @return
	 */
	public TypedResult<TResult> setResult(TResult result) {
		this.result = result;
		return this;
	}

	/**
	 * Obtiene el resultado de un acceso a datos
	 * 
	 * @return Resultado del acceso a datos
	 */
	public TResult getResult() {
		return this.result;
	}
}
