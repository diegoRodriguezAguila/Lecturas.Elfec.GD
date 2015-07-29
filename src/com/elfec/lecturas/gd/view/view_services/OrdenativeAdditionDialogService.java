package com.elfec.lecturas.gd.view.view_services;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
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
import com.elfec.lecturas.gd.view.adapters.OrdenativeAdapter;

/**
 * Servicio de dialogo que muestra la opción de agregado de ordenativos a
 * lecturas
 * 
 * @author drodriguez
 *
 */
public class OrdenativeAdditionDialogService implements IOrdenativeAdditionView {

	private OrdenativeAdditionPresenter presenter;

	private Handler mHandler;
	private AlertDialog mDialog;
	private CoordinatorLayout mRootLayout;
	private ListView listViewOrdenatives;

	/**
	 * Crea un nuevo servicio de adición de ordenativos
	 * 
	 * @param context
	 * @param reading
	 *            La lectura a la que se quieren agregar ordenativos
	 */
	@SuppressLint("InflateParams")
	public OrdenativeAdditionDialogService(Context context,
			ReadingGeneralInfo reading) {
		mHandler = new Handler();
		View dialogView = LayoutInflater.from(context).inflate(
				R.layout.dialog_ordenative_addition, null, false);
		mRootLayout = (CoordinatorLayout) dialogView;
		listViewOrdenatives = (ListView) dialogView
				.findViewById(R.id.list_ordenatives);
		listViewOrdenatives.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				((CheckBox) view.findViewById(R.id.chk_selected_ordenative))
						.setChecked(view.isActivated());
			}
		});
		mDialog = new AlertDialog.Builder(context)
				.setTitle(R.string.title_ordenative_addition)
				.setView(dialogView).setPositiveButton(R.string.btn_add, null)
				.setNegativeButton(R.string.btn_cancel, null).create();
		presenter = new OrdenativeAdditionPresenter(this, reading);
		presenter.loadOrdenatives();
	}

	/**
	 * Muestra el diálogo de selección de ordenativos
	 */
	public void show() {
		mDialog.show();
		mDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						if (presenter.addOrdenatives())
							mDialog.dismiss();
					}
				});
	}

	// #region Interface Methods

	@Override
	public void setOrdenatives(final List<Ordenative> ordenatives) {
		mHandler.post(new Runnable() {
			@Override
			public void run() {
				listViewOrdenatives.setAdapter(new OrdenativeAdapter(mDialog
						.getContext(), R.layout.ordenative_list_item,
						ordenatives));
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
				Toast.makeText(mDialog.getContext(),
						R.string.msg_ordenatives_added_successfully,
						Toast.LENGTH_LONG).show();
			}
		});
	}

	// #endregion
}
