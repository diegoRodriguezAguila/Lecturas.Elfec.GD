package com.elfec.lecturas.gd.business_logic.data_exchange;

import java.net.ConnectException;
import java.sql.SQLException;
import java.util.List;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Model;
import com.elfec.lecturas.gd.model.data_exchange.ImportSource;
import com.elfec.lecturas.gd.model.results.TypedResult;
import com.elfec.lecturas.gd.model.results.VoidResult;

/**
 * Se encarga de importar cualquier tipo de información
 * 
 * @author drodriguez
 *
 */
public class DataImporter {

	/**
	 * Importa cualquier tipo de información que debe ser importada una sola vez
	 * 
	 * @param importSource
	 * @return {@link VoidResult}
	 */
	public <T extends Model> VoidResult importDataWithNoReturn(
			ImportSource<T> importSource) {
		VoidResult result = new VoidResult();
		try {
			List<T> dataList = importSource.requestData();
			ActiveAndroid.beginTransaction();
			for (T data : dataList) {
				data.save();
			}
			ActiveAndroid.setTransactionSuccessful();
		} catch (ConnectException e) {
			result.addError(e);
		} catch (SQLException e) {
			e.printStackTrace();
			result.addError(e);
		} catch (Exception e) {
			e.printStackTrace();
			result.addError(e);
		} finally {
			ActiveAndroid.endTransaction();
		}
		return result;
	}

	/**
	 * Importa cualquier tipo de información
	 * 
	 * @param importSpecs
	 * @return {@link TypedResult}
	 */
	public <T extends Model> TypedResult<List<T>> importData(
			ImportSource<T> importSource) {
		TypedResult<List<T>> result = new TypedResult<List<T>>();
		try {
			List<T> dataList = importSource.requestData();
			ActiveAndroid.beginTransaction();
			for (T data : dataList) {
				data.save();
			}
			ActiveAndroid.setTransactionSuccessful();
			result.setResult(dataList);
		} catch (ConnectException e) {
			result.addError(e);
		} catch (SQLException e) {
			e.printStackTrace();
			result.addError(e);
		} catch (Exception e) {
			e.printStackTrace();
			result.addError(e);
		} finally {
			if (ActiveAndroid.inTransaction())
				ActiveAndroid.endTransaction();
		}
		return result;
	}
}
