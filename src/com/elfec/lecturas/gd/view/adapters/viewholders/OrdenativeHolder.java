package com.elfec.lecturas.gd.view.adapters.viewholders;

import android.view.View;
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
	protected TextView txtCode;
	protected TextView txtDescription;

	public OrdenativeHolder(View convertView) {
		txtCode = (TextView) convertView.findViewById(R.id.lbl_ordenative_code);
		txtDescription = (TextView) convertView
				.findViewById(R.id.lbl_ordenative_description);
	}

	/**
	 * Bindea la información del ordenativo al viewholder
	 * 
	 * @param ordenative
	 */
	public void bindOrdenative(Ordenative ordenative) {
		txtCode.setText("" + ordenative.getCode());
		txtDescription.setText(ordenative.getDescription());
	}
}
