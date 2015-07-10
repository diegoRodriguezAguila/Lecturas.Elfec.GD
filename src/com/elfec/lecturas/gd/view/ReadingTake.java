package com.elfec.lecturas.gd.view;

import java.util.Arrays;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.elfec.lecturas.gd.R;
import com.elfec.lecturas.gd.helpers.ui.KeyboardHelper;
import com.elfec.lecturas.gd.model.ReadingGeneralInfo;
import com.elfec.lecturas.gd.presenter.ReadingTakePresenter;
import com.elfec.lecturas.gd.presenter.views.IReadingTakeView;
import com.elfec.lecturas.gd.presenter.views.IReadingsListView;
import com.elfec.lecturas.gd.presenter.views.notifiers.IReadingListNotifier;
import com.elfec.lecturas.gd.view.adapters.ReadingPagerAdapter;

public class ReadingTake extends AppCompatActivity implements IReadingTakeView {

	private ReadingTakePresenter presenter;
	private IReadingListNotifier readingListNotifier;

	private DrawerLayout drawerLayout;
	private ActionBarDrawerToggle drawerToggle;
	private Toolbar toolbar;
	private ActionBar actionBar;
	private ViewPager readingsViewPager;

	private int position;
	private int lastReadingPos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reading_take);
		initializeActionBar();
		initializeNavReadingList();
		readingsViewPager = (ViewPager) findViewById(R.id.readings_view_pager);
		readingsViewPager
				.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int pos) {
						position = pos;
						if (readingListNotifier != null)
							readingListNotifier.notifyReadingSelected(position,
									ReadingTake.this);
					}
				});
		presenter = new ReadingTakePresenter(this, Arrays.asList(this,
				(IReadingsListView) getSupportFragmentManager()
						.findFragmentById(R.id.fragment_nav_reading_list)));
		presenter.loadReadingsGeneralInfo();
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
		switch (item.getItemId()) {
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
		if (drawerLayout.isDrawerVisible(Gravity.START)) {
			drawerLayout.closeDrawer(Gravity.START);
			return;
		} else {
			super.onBackPressed();
			overridePendingTransition(R.anim.slide_right_in,
					R.anim.slide_right_out);
		}
	}

	/**
	 * Inicializa los valores de la barra de herramientas principal
	 */
	private void initializeActionBar() {
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		actionBar = getSupportActionBar();

		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);
		actionBar.setIcon(ResourcesCompat.getDrawable(getResources(),
				R.drawable.ic_launcher, getTheme()));
		actionBar.setDisplayUseLogoEnabled(true);
	}

	/**
	 * Inicializa el drawer de navegación lateral que tiene la lista de lecturas
	 */
	private void initializeNavReadingList() {
		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
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
				KeyboardHelper.hideKeyboard(drawerLayout);
			}
		};
		drawerLayout.setDrawerListener(drawerToggle);
	}

	/**
	 * Manejador de click del boton de ir a primera lectura
	 * 
	 * @param v
	 */
	public void btnFirstReading(View v) {
		setSelectedReading(0);
	}

	/**
	 * Manejador de click del boton de lectura anterior
	 * 
	 * @param v
	 */
	public void btnPreviousReading(View v) {
		if (position > 0) {
			setSelectedReading(position - 1);
		}
	}

	/**
	 * Manejador de click del boton de lectura siguiente
	 * 
	 * @param v
	 */
	public void btnNextReading(View v) {
		if (position < lastReadingPos) {
			setSelectedReading(position + 1);
		}
	}

	/**
	 * Manejador de click del boton de ir a ultima lectura
	 * 
	 * @param v
	 */
	public void btnLastReading(View v) {
		setSelectedReading(lastReadingPos - 1);
	}

	// #region Interface Methods

	@Override
	public void setReadings(final List<ReadingGeneralInfo> readings) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				lastReadingPos = readings.size();
				position = 0;
				readingsViewPager.setAdapter(new ReadingPagerAdapter(
						getSupportFragmentManager(), readings));
			}
		});
	}

	@Override
	public void setReadingListNotifier(IReadingListNotifier listener) {
		this.readingListNotifier = listener;
	}

	@Override
	public void setSelectedReading(int position) {
		readingsViewPager.setCurrentItem(position, true);
	}

	// #endregion
}
