package com.elfec.lecturas.gd;



import android.app.Application;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class ElfecApp extends Application {
	@Override
	public void onCreate() {
	    super.onCreate();
	    CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
	                            .setDefaultFontPath("fonts/roboto_regular.ttf")
	                            .setFontAttrId(R.attr.fontPath)
	                            .build()
	            );
	}
}
