package com.elfec.lecturas.gd.view.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.elfec.lecturas.gd.model.Ordenative;
import com.elfec.lecturas.gd.view.adapters.viewholders.OrdenativeHolder;
import com.elfec.lecturas.gd.view.adapters.viewholders.SelectableOrdenativeHolder;

public class OrdenativeAdapter extends ArrayAdapter<Ordenative> {

	/**
	 * The resource indicating what views to inflate to display the content of
	 * this array adapter.
	 */
	protected int mResource;
	protected LayoutInflater inflater;

	public OrdenativeAdapter(Context context, int resource,
			List<Ordenative> ordenatives) {
		super(context, resource, ordenatives);
		this.mResource = resource;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		OrdenativeHolder viewHolder;
		if (convertView == null) {
			convertView = inflater.inflate(mResource, parent, false);
			viewHolder = new SelectableOrdenativeHolder(convertView);
			convertView.setTag(viewHolder);
		} else
			viewHolder = (OrdenativeHolder) convertView.getTag();
		viewHolder.bindOrdenative(getItem(position));
		return convertView;
	}
}
