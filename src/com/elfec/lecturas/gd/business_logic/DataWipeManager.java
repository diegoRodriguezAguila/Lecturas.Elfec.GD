package com.elfec.lecturas.gd.business_logic;

import java.io.File;

import android.database.sqlite.SQLiteDatabase;

import com.activeandroid.ActiveAndroid;
import com.elfec.lecturas.gd.model.results.VoidResult;
import com.elfec.lecturas.gd.settings.AppPreferences;

/**
 * Capa de l�gica de negocio que realiza operaciones sobre el eliminado de
 * informaci�n local
 * 
 * @author drodriguez
 *
 */
public class DataWipeManager {
	/**
	 * Elimina toda la informaci�n de la aplicaci�n que se debe eliminar del
	 * dispositivo
	 * 
	 * @return
	 */
	public VoidResult wipeAllData() {
		VoidResult result = new VoidResult();
		try {
			ActiveAndroid.getDatabase().close();
			SQLiteDatabase.deleteDatabase(new File(ActiveAndroid.getDatabase()
					.getPath()));
			ActiveAndroid.dispose();
			ActiveAndroid.initialize(AppPreferences.getApplicationContext());
			AppPreferences.instance().wipeOnceRequiredDataPreferences();
		} catch (Exception e) {
			Log.error(DataWipeManager.class, e);
			e.printStackTrace();
			result.addError(new RuntimeException(
					"Ocurri� un error al eliminar la informaci�n local! "
							+ "Es probable que la informaci�n se haya corrompido, porfavor elimine los datos desde el "
							+ "administrador de aplicaciones de Android! Info. adicional: "
							+ e.getMessage()));
		}

		return result;
	}
}
