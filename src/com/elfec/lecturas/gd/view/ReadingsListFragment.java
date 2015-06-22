package com.elfec.lecturas.gd.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

import com.elfec.lecturas.gd.R;

public class ReadingsListFragment extends Fragment {

	private Spinner spinnerReadingStatus;
	private Spinner spinnerRoutes;
	
	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public ReadingsListFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_list_of_readings,
				container, false);
		spinnerReadingStatus = (Spinner) view.findViewById(R.id.spinner_reading_status);
		spinnerRoutes = (Spinner) view.findViewById(R.id.spinner_routes);
		spinnerReadingStatus.setAdapter(new ArrayAdapter<String>(getActivity(),
				R.layout.support_simple_spinner_dropdown_item, getResources()
						.getStringArray(R.array.reading_status_array)));
		spinnerRoutes.setAdapter(new ArrayAdapter<String>(getActivity(),
				R.layout.support_simple_spinner_dropdown_item, getResources()
				.getStringArray(R.array.reading_status_array)));
		setOnItemSelectedListeners();
		return view;
	}
	
	/**
	 * Asigna los item selected listeners a los spinners
	 */
	public void setOnItemSelectedListeners() {
		spinnerReadingStatus.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> adapter, View v,
					int pos, long id) {
				if(v!=null)
					((TextView) v).setTextColor(getResources().getColor(android.R.color.white));
			}

			@Override
			public void onNothingSelected(AdapterView<?> adapter) {
				// TODO Auto-generated method stub
				
			}
		});
		spinnerRoutes.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> adapter, View v,
					int pos, long id) {
				if(v!=null)
					((TextView) v).setTextColor(getResources().getColor(android.R.color.white));
			}

			@Override
			public void onNothingSelected(AdapterView<?> adapter) {
				// TODO Auto-generated method stub
				
			}
		});
	}
}
