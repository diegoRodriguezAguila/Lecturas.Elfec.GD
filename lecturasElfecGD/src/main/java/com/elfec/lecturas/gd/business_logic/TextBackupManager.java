package com.elfec.lecturas.gd.business_logic;

import java.io.File;
import java.io.OutputStream;
import java.util.Locale;

import org.joda.time.DateTime;

import com.elfec.lecturas.gd.helpers.util.files.FileManager;
import com.elfec.lecturas.gd.model.interfaces.IBackupable;
import com.elfec.lecturas.gd.settings.AppPreferences;

/**
 * Clase de logica de negocio que se encarga de realizar un backup de cualquier
 * cobro que se realize y las transacciones, COBROS y COB_WS
 * 
 * @author drodriguez
 *
 */
public class TextBackupManager {

	public static final String BACKUP_FILE_NAME = "%d-%d %s backup.txt";
	private static final String SQL_ENDL = ";\r\n";

	/**
	 * Obtiene el nombre del archivo de backup para el periodo dado
	 * 
	 * @param year
	 * @param month
	 * @param backupFile
	 * @return el nombre del archivo de backup dado el periodo
	 */
	public static String getPeriodFileName(int year, int month,
			String backupFile) {
		return String.format(Locale.getDefault(), BACKUP_FILE_NAME, year,
				month, backupFile);
	}

	/**
	 * Obtiene el nombre del archivo de backup del periodo actual
	 * 
	 * @param backupFile
	 * @return el nombre del archivo de backup del periodo actual
	 */
	public static String getCurrentPeriodFileName(String backupFile) {
		DateTime date = DateTime.now();
		return getPeriodFileName(date.getYear(), date.getMonthOfYear(),
				backupFile);
	}

	/**
	 * Guarda un modelo backupable en el archivo de backup de todas las memorias
	 * externas. <br/>
	 * <i><b>Nota.-</b> Este proceso accede multiples veces a distintos
	 * almacenamientos de disco duro, por lo que siempre se ejecuta en un hilo
	 * aparte</i>
	 * 
	 * @param backupable
	 */
	public static void saveBackup(final IBackupable backupable) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				String fileName = getCurrentPeriodFileName(backupable
						.getBackupFilename());
				File[] extAppDirs = FileManager
						.getAllExternalAppDirectories(AppPreferences
								.getApplicationContext());
				OutputStream os;
				byte[] backupText = (backupable.toInsertSQL() + SQL_ENDL)
						.getBytes();
				for (File extAppDir : extAppDirs) {
					try {
						os = FileManager.getFileOutputStream(FileManager
								.getExternalFile(extAppDir, fileName, true),
								true);
						os.write(backupText);
						os.close();
					} catch (Exception e) {
						Log.error(TextBackupManager.class, e);
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	/**
	 * Elimina un modelo backupable del archivo de backup de todas las memorias
	 * externas. <br/>
	 * <i><b>Nota.-</b> Este proceso accede multiples veces a distintos
	 * almacenamientos de disco duro, por lo que siempre se ejecuta en un hilo
	 * aparte</i>
	 * 
	 * @param backupable
	 */
	public static void deleteBackup(final IBackupable backupable) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				String fileName = getCurrentPeriodFileName(backupable
						.getBackupFilename());
				File[] extAppDirs = FileManager
						.getAllExternalAppDirectories(AppPreferences
								.getApplicationContext());

				OutputStream os;
				byte[] backupText = (backupable.toDeleteSQL() + SQL_ENDL)
						.getBytes();
				for (File extAppDir : extAppDirs) {
					try {
						os = FileManager.getFileOutputStream(FileManager
								.getExternalFile(extAppDir, fileName, true),
								true);
						os.write(backupText);
						os.close();
					} catch (Exception e) {
						Log.error(TextBackupManager.class, e);
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

}
