package com.elfec.lecturas.gd;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

import com.activeandroid.app.Application;
import com.elfec.lecturas.gd.settings.AppPreferences;

public class ElfecApp extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		AppPreferences.initialize(getApplicationContext());
		CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
				.setDefaultFontPath("fonts/roboto_regular.ttf")
				.setFontAttrId(R.attr.fontPath).build());
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
		AppPreferences.dispose();
	}
}
