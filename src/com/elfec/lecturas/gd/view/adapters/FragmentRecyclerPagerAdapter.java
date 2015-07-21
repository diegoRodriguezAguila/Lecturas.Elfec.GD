package com.elfec.lecturas.gd.view.adapters;

import java.security.InvalidParameterException;
import java.util.ArrayList;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * Implementación de {@link android.support.v4.view.PagerAdapter} que utiliza
 * {@link Fragment} que sean del mismo tipo para instanciar y cachear los
 * {@link Fragment}, la idea es que llena un pool de instancias del tipo de
 * {@link Fragment}.
 * <p>
 * Subclasses solo necesitan implementar {@link #getNewFragment()} y
 * {@link #bindFragmentInfo(FT, int)} para tener un adapter funcional.
 * </p>
 * 
 * @author drodriguez
 *
 * @param <FT>
 */
public abstract class FragmentRecyclerPagerAdapter<FT extends Fragment> extends
		PagerAdapter {

	private final FragmentManager mFragmentManager;
	private FragmentTransaction mCurTransaction = null;
	private Fragment mCurrentPrimaryItem = null;
	private int mCurrentPrimaryPosition = -1;

	/**
	 * Tamaño de la pool de fragments
	 */
	private int mFragmentPoolSize = 3;
	private ArrayList<FT> mFragmentPool = new ArrayList<FT>();

	public FragmentRecyclerPagerAdapter(FragmentManager fm) {
		mFragmentManager = fm;
	}

	/**
	 * Obtiene una nueva instancia del fragmento, este fragmento solo
	 * inicializará la información en la posición provista una única vez al ser
	 * creado. Se llama solo el número de veces hasta que se llene el pool de
	 * fragments.
	 * 
	 * @param position
	 * @return {@link Fragment}
	 */
	public abstract FT getNewFragment(int position);

	/**
	 * Asigna la información respectiva de la posición a la instancia del
	 * fragmento. No es necesario verificar la nullicidad del fragmento nunca
	 * será nulo
	 * 
	 * @param fragment
	 * @param position
	 */
	public abstract void bindFragmentInfo(FT fragment, int position);

	@Override
	public void startUpdate(ViewGroup container) {
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		int relativePos = position % mFragmentPoolSize;
		if (mFragmentPool.size() > relativePos) {
			FT f = mFragmentPool.get(relativePos);
			if (f != null) {
				bindFragmentInfo(f, position);
				return f;
			}
		}

		if (mCurTransaction == null) {
			mCurTransaction = mFragmentManager.beginTransaction();
		}

		FT fragment = getNewFragment(position);
		while (mFragmentPool.size() <= relativePos) {
			mFragmentPool.add(null);
		}
		fragment.setMenuVisibility(false);
		fragment.setUserVisibleHint(false);
		mFragmentPool.set(relativePos, fragment);
		mCurTransaction.add(container.getId(), fragment);
		return fragment;
	}

	@Override
	public void setPrimaryItem(ViewGroup container, int position, Object object) {
		Fragment fragment = (Fragment) object;
		if (mCurrentPrimaryPosition != position) {
			if (fragment != mCurrentPrimaryItem && mCurrentPrimaryItem != null
					&& fragment != null) {
				mCurrentPrimaryItem.setMenuVisibility(false);
				mCurrentPrimaryItem.setUserVisibleHint(false);
			}
			if (fragment != null) {
				fragment.setMenuVisibility(true);
				fragment.setUserVisibleHint(true);
			}
			mCurrentPrimaryItem = fragment;
			mCurrentPrimaryPosition = position;
		}
	}

	/**
	 * Obtiene el item que actualmente esta visendosé, aquel señalado como
	 * Primary
	 * 
	 * @return
	 */
	public Fragment getCurrentItem() {
		return mCurrentPrimaryItem;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
	}

	@Override
	public void finishUpdate(ViewGroup container) {
		if (mCurTransaction != null) {
			mCurTransaction.commit();
			mCurTransaction = null;
			mFragmentManager.executePendingTransactions();
		}
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return ((Fragment) object).getView() == view;
	}

	/**
	 * Asigna el tamaño del pool de fragmentos, minimo es 3 sino lanza excepción
	 * 
	 * @param size
	 */
	public void setFragmentPoolSize(int size) {
		if (size < 3)
			throw new InvalidParameterException(
					"El tamaño de la pool no puede ser menor a 3!");
		if (mFragmentPoolSize != size) {
			mFragmentPoolSize = size;
			if (mFragmentPool.size() < size) {
				mFragmentPool = (ArrayList<FT>) mFragmentPool.subList(0, size);
			}
		}
	}

	@Override
	public Parcelable saveState() {
		return null;
	}

	@Override
	public void restoreState(Parcelable state, ClassLoader loader) {
	}
}
