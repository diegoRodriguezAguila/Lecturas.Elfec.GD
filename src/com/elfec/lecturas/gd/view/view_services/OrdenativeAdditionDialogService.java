package com.elfec.lecturas.gd.view.view_services;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.ListView;

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
		return (List<Ordenative>) listViewOrdenatives.getCheckedItemPositions();
	}

	// #endregion
}
