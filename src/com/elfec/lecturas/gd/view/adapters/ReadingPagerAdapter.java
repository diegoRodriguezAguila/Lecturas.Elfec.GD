package com.elfec.lecturas.gd.view.adapters;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.elfec.lecturas.gd.model.ReadingGeneralInfo;
import com.elfec.lecturas.gd.view.ReadingFragment;

public class ReadingPagerAdapter extends FragmentStatePagerAdapter {

	private List<ReadingGeneralInfo> readings;

	public ReadingPagerAdapter(FragmentManager fm,
			List<ReadingGeneralInfo> readings) {
		super(fm);
		this.readings = readings;
	}

	@Override
	public Fragment getItem(int pos) {
		return ReadingFragment.create(readings.get(pos));
	}

	@Override
	public int getCount() {
		return readings.size();
	}

}
