package com.elfec.lecturas.gd.view;

import java.lang.reflect.Field;
import java.util.List;

import uk.co.chrisjenx.calligraphy.TypefaceUtils;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import android.widget.TextView;

import com.elfec.lecturas.gd.R;
import com.elfec.lecturas.gd.model.ReadingGeneralInfo;
import com.elfec.lecturas.gd.model.RouteAssignment;
import com.elfec.lecturas.gd.presenter.ReadingsListPresenter;
import com.elfec.lecturas.gd.presenter.views.IReadingsListView;
import com.elfec.lecturas.gd.view.adapters.ReadingRecyclerViewAdapter;
import com.elfec.lecturas.gd.view.adapters.ReadingStatusAdapter;
import com.elfec.lecturas.gd.view.adapters.RouteAssignmentAdapter;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

public class ReadingsListFragment extends Fragment implements IReadingsListView {

	private ReadingsListPresenter presenter;
	private Handler mHandler;

	private Spinner spinnerReadingStatus;
	private Spinner spinnerRoutes;
	private SuperRecyclerView readingsList;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public ReadingsListFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		presenter = new ReadingsListPresenter(this);
		mHandler = new Handler();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_list_of_readings,
				container, false);
		spinnerReadingStatus = (Spinner) view
				.findViewById(R.id.spinner_reading_status);
		spinnerRoutes = (Spinner) view.findViewById(R.id.spinner_routes);
		setCollapsableToolBarTitle((CollapsingToolbarLayout) view
				.findViewById(R.id.collapsing_toolbar));
		readingsList = (SuperRecyclerView) view
				.findViewById(R.id.list_readings);
		readingsList.setHasFixedSize(true);
		readingsList.setLayoutManager(new LinearLayoutManager(getActivity()));

		setOnItemSelectedListeners();
		setReadingStatusAdapter();
		presenter.loadRoutes();
		presenter.loadReadingsGeneralInfo();
		return view;
	}

	/**
	 * Asigna el adapter del spinner de estados de las rutas
	 */
	public void setReadingStatusAdapter() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				final ReadingStatusAdapter adapter = new ReadingStatusAdapter(
						getActivity(),
						R.layout.support_simple_spinner_dropdown_item);
				mHandler.post(new Runnable() {
					@Override
					public void run() {
						spinnerReadingStatus.setAdapter(adapter);
					}
				});
			}
		}).start();
	}

	/**
	 * Asigna el titulo con estilo para la la toolbar colapsable
	 * 
	 * @param collapsingToolbarLayout
	 */
	private void setCollapsableToolBarTitle(
			CollapsingToolbarLayout collapsingToolbarLayout) {
		collapsingToolbarLayout
				.setTitle(getString(R.string.title_reading_list));
		final Typeface tf = TypefaceUtils.load(getActivity().getAssets(),
				"fonts/helvetica_neue_light.otf");
		try {
			final Field cthf = collapsingToolbarLayout.getClass()
					.getDeclaredField("mCollapsingTextHelper");
			cthf.setAccessible(true);
			final Object cth = cthf.get(collapsingToolbarLayout);
			final Field tpf = cth.getClass().getDeclaredField("mTextPaint");
			tpf.setAccessible(true);
			((TextPaint) tpf.get(cth)).setTypeface(tf);
		} catch (Exception ignored) {
			// Nothing to do
		}
	}

	/**
	 * Asigna los item selected listeners a los spinners
	 */
	public void setOnItemSelectedListeners() {
		spinnerReadingStatus
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> adapter, View v,
							int pos, long id) {
						if (v != null) {
							((TextView) v).setTextColor(getResources()
									.getColor(android.R.color.white));
							readingsList.setAdapter(null);
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> adapter) {
					}
				});
		spinnerRoutes.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> adapter, View v, int pos,
					long id) {
				if (v != null)
					presenter.loadReadingsGeneralInfo();
			}

			@Override
			public void onNothingSelected(AdapterView<?> adapter) {
			}
		});
	}

	// #region Interface Methods

	@Override
	public void setRoutes(final List<RouteAssignment> routes) {
		mHandler.post(new Runnable() {
			@Override
			public void run() {
				spinnerRoutes.setAdapter(new RouteAssignmentAdapter(
						getActivity(),
						R.layout.support_simple_spinner_dropdown_item, routes));
			}
		});
	}

	@Override
	public void setReadings(final List<ReadingGeneralInfo> readings) {
		mHandler.post(new Runnable() {
			@Override
			public void run() {
				readingsList
						.setAdapter(new ReadingRecyclerViewAdapter(readings));
			}
		});
	}

	// #endregion
}
