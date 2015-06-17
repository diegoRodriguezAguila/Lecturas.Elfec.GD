package com.elfec.lecturas.gd;

import com.activeandroid.app.Application;
import com.elfec.lecturas.gd.settings.AppPreferences;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class ElfecApp extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		AppPreferences.initialize(getApplicationContext());
		CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
				.setDefaultFontPath("fonts/roboto_regular.ttf")
				.setFontAttrId(R.attr.fontPath).build());
	}
}
