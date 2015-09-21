package com.elfec.lecturas.gd.view.view_services.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.elfec.lecturas.gd.R;
import com.elfec.lecturas.gd.model.Ordenative;
import com.elfec.lecturas.gd.model.ReadingGeneralInfo;
import com.elfec.lecturas.gd.presenter.OrdenativeAdditionPresenter;
import com.elfec.lecturas.gd.presenter.views.IOrdenativeAdditionView;
import com.elfec.lecturas.gd.presenter.views.callbacks.OrdenativesSaveCallback;
import com.elfec.lecturas.gd.view.adapters.SelectableOrdenativeAdapter;

/**
 * Servicio de dialogo que muestra la opción de agregado de ordenativos a
 * lecturas
 * 
 * @author drodriguez
 *
 */
public class OrdenativeAdditionFragment extends Fragment implements
		IOrdenativeAdditionView {

	/**
	 * la Key para obtener la lectura en este fragmento
	 */
	public static final String ARG_READING = "ReadingGeneralInfo";

	private OrdenativeAdditionPresenter presenter;

	private Handler mHandler;
	private CoordinatorLayout mRootLayout;
	private ListView listViewOrdenatives;
	private FloatingActionButton btnAddOrdenatives;

	public OrdenativeAdditionFragment() {
		super();
		mHandler = new Handler();
	}

	/**
	 * Factory method for this fragment class. Constructs a new fragment for the
	 * given reading.
	 */
	public static OrdenativeAdditionFragment create(ReadingGeneralInfo reading) {
		OrdenativeAdditionFragment fragment = new OrdenativeAdditionFragment();
		Bundle args = new Bundle();
		args.putSerializable(ARG_READING, reading);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View rootView = inflater.inflate(
				R.layout.fragment_ordenative_addition, container, false);
		ReadingGeneralInfo reading = null;
		if (getArguments().containsKey(ARG_READING)) {
			reading = (ReadingGeneralInfo) getArguments().getSerializable(
					ARG_READING);
		}
		mRootLayout = (CoordinatorLayout) rootView;
		listViewOrdenatives = (ListView) rootView
				.findViewById(R.id.list_ordenatives);
		listViewOrdenatives.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				((CheckBox) view.findViewById(R.id.chk_selected_ordenative))
						.setChecked(view.isActivated());
			}
		});
		btnAddOrdenatives = ((FloatingActionButton) rootView
				.findViewById(R.id.btn_add_ordenatives));
		btnAddOrdenatives.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				presenter.addOrdenatives();
			}
		});
		presenter = new OrdenativeAdditionPresenter(this, reading);
		presenter.loadOrdenatives();
		return rootView;
	}

	// #region Interface Methods

	@Override
	public void setOrdenatives(final List<Ordenative> ordenatives) {
		mHandler.post(new Runnable() {
			@Override
			public void run() {
				listViewOrdenatives.setAdapter(new SelectableOrdenativeAdapter(
						getActivity(),
						R.layout.selectable_ordenative_list_item, ordenatives));
			}
		});
	}

	@Override
	public List<Ordenative> getSelectedOrdenatives() {
		List<Ordenative> selectedOrdenatives = new ArrayList<>();
		SparseBooleanArray sparseBooleanArray = listViewOrdenatives
				.getCheckedItemPositions();
		int size = listViewOrdenatives.getAdapter().getCount();
		for (int i = 0; i < size; i++) {
			if (sparseBooleanArray.get(i)) {
				selectedOrdenatives.add((Ordenative) listViewOrdenatives
						.getItemAtPosition(i));
			}
		}
		return selectedOrdenatives;
	}

	@Override
	public void notifyAtLeastSelectOne() {
		Snackbar snackbar = Snackbar.make(mRootLayout,
				R.string.msg_at_least_one_ordenative, Snackbar.LENGTH_LONG)
				.setAction(R.string.btn_ok, new OnClickListener() {
					@Override
					public void onClick(View v) {
					}
				});
		((TextView) snackbar.getView().findViewById(
				android.support.design.R.id.snackbar_text)).setMaxLines(3);
		snackbar.show();
	}

	@Override
	public void notifyOrdenativesAddedSuccessfully() {
		mHandler.post(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(getActivity(),
						R.string.msg_ordenatives_added_successfully,
						Toast.LENGTH_LONG).show();
				if (getParentFragment() instanceof OrdenativesSaveCallback)
					((OrdenativesSaveCallback) getParentFragment())
							.onOrdenativesSavedSuccesfully();
			}
		});
	}

	// #endregion
}
