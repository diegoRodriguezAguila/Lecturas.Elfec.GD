package com.elfec.lecturas.gd.view.adapters.viewholders;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.elfec.lecturas.gd.R;
import com.elfec.lecturas.gd.model.Ordenative;

/**
 * ViewHolder para oredenativos
 * 
 * @author drodriguez
 *
 */
public class OrdenativeHolder {
	private TextView txtCode;
	private TextView txtDescription;
	private CheckBox chkSelected;

	public OrdenativeHolder(View convertView) {
		txtCode = (TextView) convertView.findViewById(R.id.lbl_ordenative_code);
		txtDescription = (TextView) convertView
				.findViewById(R.id.lbl_ordenative_description);
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
		txtCode.setText("" + ordenative.getCode());
		txtDescription.setText(ordenative.getDescription());
		chkSelected.setOnCheckedChangeListener(listener);
		chkSelected.setChecked(isChecked);
	}
}
