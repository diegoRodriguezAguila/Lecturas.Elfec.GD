package com.elfec.lecturas.gd.business_logic.data_exchange;

import java.net.ConnectException;
import java.sql.SQLException;
import java.util.List;

import com.activeandroid.Model;
import com.elfec.lecturas.gd.business_logic.Log;
import com.elfec.lecturas.gd.model.data_exchange.ExportSpecs;
import com.elfec.lecturas.gd.model.enums.ExportStatus;
import com.elfec.lecturas.gd.model.events.DataExportListener;
import com.elfec.lecturas.gd.model.exceptions.ExportationException;
import com.elfec.lecturas.gd.model.interfaces.IExportable;
import com.elfec.lecturas.gd.model.results.DataAccessResult;

/**
 * Se encarga de exportar cualquier tipo de información
 * 
 * @author drodriguez
 *
 */
public class DataExporter {

	/**
	 * Importa cualquier tipo de información que debe ser importada una sola vez
	 * 
	 * @param exportSpecs
	 * @param exportListener
	 * @return Resultado del acceso remoto a datos
	 */
	public <T extends Model & IExportable> DataAccessResult<Boolean> exportData(
			ExportSpecs<T> exportSpecs, DataExportListener exportListener) {
		DataAccessResult<Boolean> result = new DataAccessResult<Boolean>(true);
		if (exportListener == null)
			exportListener = new DataExportListener() {// DUMMY Listener
				@Override
				public void onExporting(int exportCount, int totalElements) {
				}

				@Override
				public void onExportInitialized(int totalElements) {
				}

				@Override
				public void onExportFinalized() {
				}
			};// DUMMY Listener

		try {
			List<T> dataList = exportSpecs.requestDataToExport();
			int size = dataList.size();
			exportListener.onExportInitialized(size);
			int rowRes, count = 0;
			for (T data : dataList) {
				rowRes = exportSpecs.exportData(data);
				if (rowRes == 1) // se insertó existosamente
				{
					data.setExportStatus(ExportStatus.EXPORTED);
					data.save();
					count++;
					exportListener.onExporting(count, size);
				} else
					throw new ExportationException(
							"No se pudo insertar el registro: "
									+ data.getRegistryResume());

			}
			result.setResult(true);
		} catch (ExportationException e) {
			result.addError(e);
		} catch (ConnectException e) {
			result.addError(e);
		} catch (SQLException e) {
			Log.error(DataExporter.class, e);
			e.printStackTrace();
			result.addError(new ExportationException(e.getMessage()));
		} catch (Exception e) {
			Log.error(DataExporter.class, e);
			e.printStackTrace();
			result.addError(new ExportationException(e.getMessage()));
		}
		exportListener.onExportFinalized();
		return result;
	}
}
