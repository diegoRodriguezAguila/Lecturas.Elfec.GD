package com.elfec.lecturas.gd;

import com.activeandroid.app.Application;
import com.elfec.lecturas.gd.business_logic.Log;
import com.elfec.lecturas.gd.settings.AppPreferences;

import net.danlew.android.joda.JodaTimeAndroid;

import java.lang.Thread.UncaughtExceptionHandler;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class ElfecApp extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		AppPreferences.initialize(getApplicationContext());
		CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
				.setDefaultFontPath("fonts/helvetica_neue_roman.otf")
				.setFontAttrId(R.attr.fontPath).build());
		JodaTimeAndroid.init(this);
		setExceptionLogger();
	}

	/**
	 * Asigna el capturador de excepciones para el hilo para que se guarden en
	 * el log de errores
	 */
	private void setExceptionLogger() {
		final Thread.UncaughtExceptionHandler oldHandler = Thread
				.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {

			@Override
			public void uncaughtException(Thread thread, Throwable ex) {
				Log.error(ElfecApp.class, ex);
				// Delegates to Android's error handling
				if (oldHandler != null)
					oldHandler.uncaughtException(thread, ex);
				else
					System.exit(2);
			}
		});
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
		AppPreferences.dispose();
	}
}
