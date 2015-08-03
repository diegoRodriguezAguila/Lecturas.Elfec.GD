package com.elfec.lecturas.gd.view.view_services.fragments;

import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.elfec.lecturas.gd.R;
import com.elfec.lecturas.gd.model.Ordenative;
import com.elfec.lecturas.gd.model.ReadingGeneralInfo;
import com.elfec.lecturas.gd.presenter.ReadingOrdenativesPresenter;
import com.elfec.lecturas.gd.presenter.views.IReadingOrdenativesView;
import com.elfec.lecturas.gd.view.adapters.OrdenativeAdapter;

/**
 * Fragment de la vista de ordenativos de la lectura
 * 
 * @author drodriguez
 *
 */
public class ReadingOrdenativesFragment extends Fragment implements
		IReadingOrdenativesView {
	/**
	 * la Key para obtener la lectura en este fragmento
	 */
	public static final String ARG_READING = "ReadingGeneralInfo";

	private Handler mHandler;
	private ReadingOrdenativesPresenter presenter;
	private ListView listViewOrdenatives;

	public ReadingOrdenativesFragment() {
		super();
	}

	/**
	 * Factory method for this fragment class. Constructs a new fragment for the
	 * given reading.
	 */
	public static ReadingOrdenativesFragment create(ReadingGeneralInfo reading) {
		ReadingOrdenativesFragment fragment = new ReadingOrdenativesFragment();
		Bundle args = new Bundle();
		args.putSerializable(ARG_READING, reading);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View rootView = inflater.inflate(
				R.layout.fragment_reading_ordenatives, container, false);
		ReadingGeneralInfo reading = null;
		if (getArguments().containsKey(ARG_READING)) {
			reading = (ReadingGeneralInfo) getArguments().getSerializable(
					ARG_READING);
		}
		mHandler = new Handler();
		listViewOrdenatives = (ListView) rootView
				.findViewById(R.id.list_ordenatives);
		presenter = new ReadingOrdenativesPresenter(this, reading);
		presenter.loadOrdenatives();
		return rootView;
	}

	// #region interface methods
	@Override
	public void setOrdenatives(final List<Ordenative> ordenatives) {
		mHandler.post(new Runnable() {
			@Override
			public void run() {
				listViewOrdenatives.setAdapter(new OrdenativeAdapter(
						getActivity(), R.layout.ordenative_list_item,
						ordenatives));
			}
		});
	}

	// #endregion

}
