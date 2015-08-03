package com.elfec.lecturas.gd.view.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.elfec.lecturas.gd.model.ReadingGeneralInfo;
import com.elfec.lecturas.gd.view.view_services.fragments.OrdenativeAdditionFragment;
import com.elfec.lecturas.gd.view.view_services.fragments.ReadingOrdenativesFragment;

/**
 * Clase que se encarga de adaptador para la vista de ordenativos con las
 * pantallas de agregar y ver ordenativos
 * 
 * @author drodriguez
 *
 */
public class OrdenativeFragmentAdapter extends FragmentPagerAdapter {
	final int PAGE_COUNT = 2;
	private String mTabTitles[] = new String[] { "Agregar Ordenativos",
			"Ver Ordenativos" };
	private ReadingGeneralInfo reading;

	public OrdenativeFragmentAdapter(FragmentManager fm,
			ReadingGeneralInfo reading) {
		super(fm);
		this.reading = reading;
	}

	@Override
	public int getCount() {
		return PAGE_COUNT;
	}

	@Override
	public Fragment getItem(int position) {
		return position == 0 ? OrdenativeAdditionFragment.create(reading)
				: ReadingOrdenativesFragment.create(reading);
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return mTabTitles[position];
	}
}
