package com.elfec.lecturas.gd.business_logic;

import java.io.FileOutputStream;
import java.io.PrintStream;

import org.joda.time.DateTime;

import com.elfec.lecturas.gd.helpers.util.files.FileManager;

/**
 * Es un log que guarda errores o eventos que ocurran en la capa de lógica de
 * negocio
 * 
 * @author drodriguez
 *
 */
public class Log {
	private static final String LOG_FILENAME = "error_log.txt";
	private static final String TIMESTAMP_SEPARATOR = " >>>>>>>>>>> ";
	private static final String SEPARATOR = "__________________________________________________________";

	/**
	 * Constructor privado por que no se debe instanciar para usar
	 */
	private Log() {
	}

	/**
	 * Registra un error en el log
	 * 
	 * @param origin
	 * @param e
	 */
	public static synchronized void error(final Class<?> origin,
			final Throwable e) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				String orig = origin.getSimpleName();
				String timestamp = DateTime.now().toString(
						"yyyy/MM/dd HH:mm:ss");
				FileOutputStream logFileStream = FileManager.getFileOutputStream(
						FileManager.getExternalFile(LOG_FILENAME, true), true);
				PrintStream ps = new PrintStream(logFileStream);
				ps.println((orig + TIMESTAMP_SEPARATOR + timestamp));
				ps.println(SEPARATOR);
				e.printStackTrace(ps);
				ps.println(SEPARATOR);
			}
		}).start();
	}
}
