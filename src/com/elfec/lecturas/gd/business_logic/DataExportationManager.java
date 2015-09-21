package com.elfec.lecturas.gd.business_logic;

import com.elfec.lecturas.gd.model.ReadingOrdenative;
import com.elfec.lecturas.gd.model.ReadingTaken;
import com.elfec.lecturas.gd.model.RouteAssignment;
import com.elfec.lecturas.gd.model.exceptions.NoExportPendingDataException;
import com.elfec.lecturas.gd.model.exceptions.NoReadingsTakenException;
import com.elfec.lecturas.gd.model.results.VoidResult;

/**
 * Capa de lógica de negocio para la exportación de datos
 * 
 * @author drodriguez
 *
 */
public class DataExportationManager {
	/**
	 * Valida las condiciones de negocio para la exportación de los cobros
	 * 
	 * @return resultado de la validación
	 */
	public VoidResult validateExportation() {
		VoidResult result = new VoidResult();
		try {
			if (ReadingTaken.getAll(ReadingTaken.class).size() == 0)
				throw new NoReadingsTakenException();
			if (ReadingTaken.getExportPendingReadingsTaken().size() == 0
					&& ReadingOrdenative.getExportPendingReadingOrdenatives()
							.size() == 0
					&& RouteAssignment.getAllImportedRouteAssignments().size() == 0)
				throw new NoExportPendingDataException();
		} catch (NoReadingsTakenException e) {
			result.addError(e);
		} catch (NoExportPendingDataException e) {
			result.addError(e);
		}

		return result;
	}
}
