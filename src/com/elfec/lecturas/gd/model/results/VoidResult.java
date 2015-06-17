package com.elfec.lecturas.gd.model.results;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa el resultado de realizar una acci�n en un gestionador de la capa
 * de l�gica de negocio u otra que lo requiera sin tipo de retorno, solo con
 * lista de errores
 * 
 * @author drodriguez
 *
 */
public class VoidResult {
	protected List<Exception> listOfErrors;

	public VoidResult() {
		listOfErrors = new ArrayList<Exception>();
	}

	/**
	 * Obtiene la lista de errores del resultado de un servicio web
	 * 
	 * @return Lista de errores del WS
	 */
	public List<Exception> getErrors() {
		return listOfErrors;
	}

	/**
	 * Agrega un error a la lista de errores del resultado de un servicio web
	 * 
	 * @param e
	 */
	public void addError(Exception e) {
		listOfErrors.add(e);
	}

	/**
	 * Agrega multiples errores a la lita de errores
	 * 
	 * @param errors
	 */
	public void addErrors(List<Exception> errors) {
		listOfErrors.addAll(errors);
	}

	/**
	 * Si es que el resultado del acceso a datos tuvo errores
	 * 
	 * @return true si los hubo
	 */
	public boolean hasErrors() {
		return listOfErrors.size() > 0;
	}
}
