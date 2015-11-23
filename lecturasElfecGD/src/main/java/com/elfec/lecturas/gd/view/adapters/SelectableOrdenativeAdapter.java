package com.elfec.lecturas.gd.view.adapters;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.elfec.lecturas.gd.model.Ordenative;
import com.elfec.lecturas.gd.view.adapters.viewholders.SelectableOrdenativeHolder;

public class SelectableOrdenativeAdapter extends OrdenativeAdapter {

	private boolean[] checkBoxState;

	public SelectableOrdenativeAdapter(Context context, int resource,
			List<Ordenative> ordenatives) {
		super(context, resource, ordenatives);
		this.checkBoxState = new boolean[ordenatives.size()];
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		SelectableOrdenativeHolder viewHolder;
		if (convertView == null) {
			convertView = inflater.inflate(mResource, parent, false);
			viewHolder = new SelectableOrdenativeHolder(convertView);
			convertView.setTag(viewHolder);
		} else
			viewHolder = (SelectableOrdenativeHolder) convertView.getTag();
		viewHolder.bindOrdenative(getItem(position), checkBoxState[position],
				new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						checkBoxState[position] = isChecked;
					}
				});

		return convertView;
	}
}
