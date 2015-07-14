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

	@Override
	public int getCount() {
		return readings.size();
	}

	@Override
	public ReadingFragment getNewFragment() {
		return ReadingFragment.create();
	}

	@Override
	public void bindFragmentInfo(ReadingFragment fragment, int position) {
		fragment.bindReadingInfo(readings.get(position));
	}

}
