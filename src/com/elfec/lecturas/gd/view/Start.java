package com.elfec.lecturas.gd.view;

import org.apache.commons.lang.WordUtils;
import org.joda.time.DateTime;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.elfec.lecturas.gd.R;
import com.elfec.lecturas.gd.presenter.StartPresenter;
import com.elfec.lecturas.gd.presenter.views.IStartView;

public class Start extends AppCompatActivity implements IStartView {

	public static final String IMEI = "IMEI";
	
	private StartPresenter presenter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		((TextView) findViewById(R.id.txt_device_imei)).setText(getIntent()
				.getExtras().getString(IMEI));
		
		presenter = new StartPresenter(this);
		presenter.setFields();
	}

	@Override
	protected void attachBaseContext(Context newBase) {
		super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		((TextView) findViewById(R.id.txt_date)).setText(WordUtils.capitalize(DateTime.now().toString("dd MMMM yyyy")));
	}

	@Override
	public void onBackPressed() {
		finish();// go back to the previous Activity
		overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
	}

	/**
	 * Evento de click del boton btn_reading_take
	 * 
	 * @param v
	 */
	public void btnReadingTakeClick(View v) {
		Intent i = new Intent(Start.this, ReadingTake.class);
		startActivity(i);
		overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
	}
	
	
	//#region Interface Methods

	@Override
	public void setCurrentUser(String username) {
		((TextView) findViewById(R.id.txt_username_welcome)).setText(Html.fromHtml("Bienvenido <b>"+username+"</b>!"));
	}
	
	//#endregion

}
