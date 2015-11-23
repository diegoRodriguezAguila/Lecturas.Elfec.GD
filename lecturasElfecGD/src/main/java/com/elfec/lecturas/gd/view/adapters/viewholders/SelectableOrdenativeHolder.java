package com.elfec.lecturas.gd.view.adapters.viewholders;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.elfec.lecturas.gd.R;
import com.elfec.lecturas.gd.model.Ordenative;

/**
 * ViewHolder para oredenativos
 * 
 * @author drodriguez
 *
 */
public class SelectableOrdenativeHolder extends OrdenativeHolder {
	protected CheckBox chkSelected;

	public SelectableOrdenativeHolder(View convertView) {
		super(convertView);
		chkSelected = (CheckBox) convertView
				.findViewById(R.id.chk_selected_ordenative);
	}

	/**
	 * Bindea la información del ordenativo al viewholder
	 * 
	 * @param ordenative
	 * @param isChecked
	 * @param listener
	 */
	public void bindOrdenative(Ordenative ordenative, boolean isChecked,
			OnCheckedChangeListener listener) {
		super.bindOrdenative(ordenative);
		chkSelected.setOnCheckedChangeListener(listener);
		chkSelected.setChecked(isChecked);
	}
}
