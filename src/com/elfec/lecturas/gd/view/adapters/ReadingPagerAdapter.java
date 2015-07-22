package com.elfec.lecturas.gd.view.adapters;

import java.util.List;

import android.support.v4.app.FragmentManager;

import com.elfec.lecturas.gd.model.ReadingGeneralInfo;
import com.elfec.lecturas.gd.view.ReadingFragment;

public class ReadingPagerAdapter extends
		FragmentRecyclerPagerAdapter<ReadingFragment> {

	private List<ReadingGeneralInfo> readings;

	public ReadingPagerAdapter(FragmentManager fm,
			List<ReadingGeneralInfo> readings) {
		super(fm);
		this.readings = readings;
	}

	/**
	 * Obtiene la lectura en la posición especificada
	 * 
	 * @param position
	 * @return {@link ReadingGeneralInfo} lectura en la posición
	 */
	public ReadingGeneralInfo getReadingAt(int position) {
		return readings.get(position);
	}

	@Override
	public int getCount() {
		return readings.size();
	}

	@Override
	public ReadingFragment getNewFragment(int position) {
		return ReadingFragment.create(readings.get(position));
	}

	@Override
	public void bindFragmentInfo(ReadingFragment fragment, int position) {
		fragment.bindReadingInfo(readings.get(position));
	}

}
