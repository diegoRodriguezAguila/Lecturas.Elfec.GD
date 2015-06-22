package com.elfec.lecturas.gd.view;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
 import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.elfec.lecturas.gd.R;

public class ReadingTake extends AppCompatActivity {

	private DrawerLayout drawerLayout;
	private ActionBarDrawerToggle drawerToggle;
	private Toolbar toolbar;
	private ActionBar actionBar;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reading_take);
		initializeActionBar();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.reading_take, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.
		if (drawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action buttons
        switch(item.getItemId()) {
        case R.id.menu_search:
           
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
	}

	@Override
	protected void attachBaseContext(Context newBase) {
		super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		drawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		drawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
	}
	
	/**
	 * Inicializa los valores de la barra de herramientas principal
	 */
	private void initializeActionBar() {
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		actionBar = getSupportActionBar();
		
		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);
		actionBar.setIcon(ResourcesCompat.getDrawable(getResources(),
				R.drawable.ic_launcher, getTheme()));
		actionBar.setDisplayUseLogoEnabled(true);

		drawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		drawerLayout, /* DrawerLayout object */
		toolbar, /* nav drawer image to replace 'Up' caret */
		R.string.drawer_open, /* "open drawer" description for accessibility */
		R.string.drawer_close /* "close drawer" description for accessibility */
		) {
			public void onDrawerClosed(View view) {
				// invalidateOptionsMenu(); // creates call to
				// onPrepareOptionsMenu()
			}

			public void onDrawerOpened(View drawerView) {
				// invalidateOptionsMenu(); // creates call to
				// onPrepareOptionsMenu()
			}
		};
		drawerLayout.setDrawerListener(drawerToggle);
	}
}
