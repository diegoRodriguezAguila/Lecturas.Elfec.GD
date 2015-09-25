package com.elfec.lecturas.gd.view;

import java.util.Arrays;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.elfec.lecturas.gd.R;
import com.elfec.lecturas.gd.helpers.ui.ButtonClicksHelper;
import com.elfec.lecturas.gd.helpers.ui.FloatingActionButtonAnimator;
import com.elfec.lecturas.gd.helpers.ui.KeyboardHelper;
import com.elfec.lecturas.gd.model.ReadingGeneralInfo;
import com.elfec.lecturas.gd.model.ReadingTaken;
import com.elfec.lecturas.gd.model.enums.ReadingStatus;
import com.elfec.lecturas.gd.presenter.ReadingTakePresenter;
import com.elfec.lecturas.gd.presenter.views.IReadingTakeView;
import com.elfec.lecturas.gd.presenter.views.IReadingView;
import com.elfec.lecturas.gd.presenter.views.IReadingsListView;
import com.elfec.lecturas.gd.presenter.views.callbacks.ReadingSaveCallback;
import com.elfec.lecturas.gd.presenter.views.notifiers.IReadingListNotifier;
import com.elfec.lecturas.gd.view.adapters.ReadingPagerAdapter;
import com.elfec.lecturas.gd.view.listeners.OnReadingEditClickListener;
import com.elfec.lecturas.gd.view.listeners.OnReadingRetryClickListener;
import com.elfec.lecturas.gd.view.listeners.OnReadingSaveClickListener;
import com.elfec.lecturas.gd.view.view_services.OrdenativeDialogService;
import com.elfec.lecturas.gd.view.view_services.ReadingSearchPopupService;
import com.elfec.lecturas.gd.view.view_services.ReadingSearchPopupService.OnReadingFoundListener;

public class ReadingTake extends AppCompatActivity implements IReadingTakeView,
		ReadingSaveCallback {

	private ReadingTakePresenter presenter;
	private IReadingListNotifier readingListNotifier;

	private DrawerLayout drawerLayout;
	private ActionBarDrawerToggle drawerToggle;
	private Toolbar toolbar;
	private ActionBar actionBar;
	private ViewPager readingsViewPager;
	private ReadingPagerAdapter readingPagerAdapter;
	private FloatingActionButton btnSaveReading;
	private FloatingActionButton btnEditReading;

	public MenuItem menuAddOrdenatives;
	public MenuItem menuRetryReading;
	public View menuViewSearchReading;

	private int position;
	private int lastReadingPos;

	@SuppressLint("NewApi")
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
						KeyboardHelper.hideKeyboard(drawerLayout);
						position = pos;
						setFloatingButtonsVisibility(pos);
						setMenuItemsVisibility(pos);
						if (readingListNotifier != null)
							readingListNotifier.notifyReadingSelected(position,
									ReadingTake.this);
					}
				});
		presenter = new ReadingTakePresenter(this, Arrays.asList(this,
				(IReadingsListView) getSupportFragmentManager()
						.findFragmentById(R.id.fragment_nav_reading_list)));
		presenter.loadReadingsGeneralInfo();
		btnSaveReading = (FloatingActionButton) findViewById(R.id.btn_save_reading);
		btnEditReading = (FloatingActionButton) findViewById(R.id.btn_edit_reading);
		btnEditReading.setBackgroundTintList(getResources().getColorStateList(
				R.color.blue_btn_color));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.reading_take, menu);
		menuAddOrdenatives = menu.findItem(R.id.menu_add_ordenatives);
		menuRetryReading = menu.findItem(R.id.menu_retry_reading);
		setMenuItemsVisibility(position);
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
		case R.id.menu_retry_reading: {
			if (ButtonClicksHelper.canClickButton()) {
				new AlertDialog.Builder(this)
						.setTitle(R.string.title_retry_reading)
						.setIcon(R.drawable.retry_reading_d)
						.setMessage(
								getResources().getText(
										R.string.msg_confirm_retry_reading))
						.setNegativeButton(R.string.btn_cancel, null)
						.setPositiveButton(R.string.btn_confirm,
								new OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										((OnReadingRetryClickListener) readingPagerAdapter
												.getCurrentItem())
												.readingRetryClicked();
									}
								}).show();
			}
			return true;
		}
		case R.id.menu_add_ordenatives: {
			if (ButtonClicksHelper.canClickButton()) {
				showOrdenativesDialog(
						readingPagerAdapter.getReadingAt(readingsViewPager
								.getCurrentItem()), null);
			}
			return true;
		}
		case R.id.menu_search: {
			if (ButtonClicksHelper.canClickButton()) {
				KeyboardHelper.hideKeyboard(drawerLayout);
				new ReadingSearchPopupService(
						this,
						findViewById(menuAddOrdenatives.isVisible() ? R.id.menu_add_ordenatives
								: (menuRetryReading.isVisible() ? R.id.menu_retry_reading
										: R.id.menu_search)),
						new OnReadingFoundListener() {
							@Override
							public void onReadingFound(
									ReadingGeneralInfo reading) {
								presenter.processFoundReading(reading);
							}
						}).show();
			}
			return true;
		}
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
			if (!((IReadingView) readingPagerAdapter.getCurrentItem())
					.hasPendingChanges()) {
				exitReadingTake();
			} else {
				new AlertDialog.Builder(this)
						.setIcon(R.drawable.warning)
						.setTitle(R.string.title_exit_reading)
						.setMessage(R.string.msg_exit_reading)
						.setPositiveButton(R.string.btn_ok,
								new OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										exitReadingTake();
									}
								}).setNegativeButton(R.string.btn_cancel, null)
						.show();
			}
		}
	}

	private void exitReadingTake() {
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
	 * Muestra el dialogo de los ordenativos
	 * 
	 * @param reading
	 */
	private void showOrdenativesDialog(ReadingGeneralInfo reading,
			OnDismissListener listener) {
		KeyboardHelper.hideKeyboard(drawerLayout);
		new OrdenativeDialogService(reading).setOnDismissListener(listener)
				.show(getSupportFragmentManager(), "Ordenative Dialog");
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
		if (position < (lastReadingPos - 1)) {
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

	/**
	 * Identifica si es que es necesario cambiar de boton según los estados de
	 * la lectura anterior y de la lectura actual
	 * 
	 * @param pos
	 *            posición de la lectura actual
	 */
	private void setFloatingButtonsVisibility(int pos) {
		if ((readingPagerAdapter != null && readingPagerAdapter.getCount() > 0)
				&& (readingPagerAdapter.getReadingAt(pos).getStatus() != ReadingStatus.RETRY)) {
			ReadingTaken currentReading = readingPagerAdapter.getReadingAt(pos)
					.getReadingTaken();
			if (btnEditReading.getVisibility() == View.GONE
					&& currentReading != null)
				FloatingActionButtonAnimator.hideAndShow(btnSaveReading,
						btnEditReading);
			if (btnSaveReading.getVisibility() == View.GONE
					&& currentReading == null)
				FloatingActionButtonAnimator.hideAndShow(btnEditReading,
						btnSaveReading);
		} else { // no adapter, no readings
			FloatingActionButtonAnimator.hide(btnSaveReading);
			FloatingActionButtonAnimator.hide(btnEditReading);
		}
	}

	/**
	 * Asigna la visibilidad a los componentes del menú según el estado de la
	 * lectura en la posición indicada
	 * 
	 * @param posición
	 *            de la lectura actual
	 */
	private void setMenuItemsVisibility(final int pos) {
		if (readingPagerAdapter != null && readingPagerAdapter.getCount() > 0) {
			ReadingGeneralInfo reading = readingPagerAdapter.getReadingAt(pos);
			boolean isRead = reading.getStatus() == ReadingStatus.READ
					|| reading.getStatus() == ReadingStatus.IMPEDED;
			boolean isRetry = reading.getStatus() == ReadingStatus.RETRY;
			if (menuAddOrdenatives != null && menuRetryReading != null) {
				menuAddOrdenatives.setVisible(isRead && !isRetry);
				menuRetryReading.setVisible(!isRead && !isRetry);
			}
		} else if (menuAddOrdenatives != null && menuRetryReading != null) {
			menuAddOrdenatives.setVisible(false);
			menuRetryReading.setVisible(false);
		}
	}

	/**
	 * Manejador de click del boton de guardar lectura
	 * 
	 * @param v
	 */
	public void btnSaveReading(View v) {
		if (ButtonClicksHelper.canClickButton())
			((OnReadingSaveClickListener) readingPagerAdapter.getCurrentItem())
					.readingSaveClicked(v);
	}

	/**
	 * Manejador de click del boton de editar la lectura
	 * 
	 * @param v
	 */
	public void btnEditReading(final View v) {
		if (ButtonClicksHelper.canClickButton()) {
			new AlertDialog.Builder(this)
					.setTitle(R.string.title_edit_reding)
					.setIcon(R.drawable.confirm_edit_reading)
					.setMessage(
							getResources().getText(
									R.string.msg_confirm_edit_reading))
					.setNegativeButton(R.string.btn_cancel, null)
					.setPositiveButton(R.string.btn_confirm,
							new OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									FloatingActionButtonAnimator.hideAndShow(
											btnEditReading, btnSaveReading);
									menuAddOrdenatives.setVisible(false);
									((OnReadingEditClickListener) readingPagerAdapter
											.getCurrentItem())
											.readingEditClicked(v);
								}
							}).show();
		}
		;
	}

	// #region Interface Methods

	@Override
	public void setReadings(final List<ReadingGeneralInfo> readings) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				lastReadingPos = readings.size();
				position = 0;
				readingPagerAdapter = new ReadingPagerAdapter(
						getSupportFragmentManager(), readings);
				readingsViewPager.setAdapter(readingPagerAdapter);
			}
		});
	}

	@Override
	public void setReadingListNotifier(IReadingListNotifier notifier) {
		this.readingListNotifier = notifier;
	}

	@Override
	public void setSelectedReading(final int position) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				setFloatingButtonsVisibility(position);
				setMenuItemsVisibility(position);
				readingsViewPager.setCurrentItem(position,
						Math.abs(ReadingTake.this.position - position) == 1);
			}
		});
	}

	@Override
	public IReadingListNotifier getReadingListNotifier() {
		return readingListNotifier;
	}

	@Override
	public void onReadingSavedSuccesfully(final ReadingGeneralInfo reading,
			final boolean wasInEditionMode) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				showOrdenativesDialog(reading, wasInEditionMode ? null
						: new OnDismissListener() {
							@Override
							public void onDismiss(DialogInterface dialog) {
								int lastPosition = position;
								if (!presenter.hasReadingStatusFilter())
									btnNextReading(null);
								else {
									presenter
											.verifyFiltersValidity(lastPosition);
									setFloatingButtonsVisibility(lastPosition);
									setMenuItemsVisibility(lastPosition);
								}
							}
						});
				FloatingActionButtonAnimator.hideAndShow(btnSaveReading,
						btnEditReading);
				menuAddOrdenatives.setVisible(true);
				menuRetryReading.setVisible(false);
				readingListNotifier.notifyRebindReading(position,
						ReadingTake.this);
			}
		});
	}

	@Override
	public void onReadingSaveErrors(List<Exception> errors) {
	}

	@Override
	public void resetFilters() {// nothing should happen here
	}

	@Override
	public void rebindReading(int position) {
		readingPagerAdapter.instantiateItem(readingsViewPager, position);
	}

	@Override
	public void removeReading(int position) {
		if (lastReadingPos > 0)
			lastReadingPos--;
		readingPagerAdapter.removeItem(position);
	}

	@Override
	public void onRetryReadingSavedSuccesfully(ReadingGeneralInfo savedReading) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				int lastPosition = position;
				menuRetryReading.setVisible(false);
				readingListNotifier.notifyRebindReading(lastPosition,
						ReadingTake.this);
				if (!presenter.hasReadingStatusFilter())
					btnNextReading(null);
				else {
					presenter.verifyFiltersValidity(lastPosition);
					setFloatingButtonsVisibility(lastPosition);
					setMenuItemsVisibility(lastPosition);
				}
			}
		});
	}

	// #endregion
}
