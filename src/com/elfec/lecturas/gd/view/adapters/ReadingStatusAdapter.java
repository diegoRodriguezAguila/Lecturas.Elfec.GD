package com.elfec.lecturas.gd.view.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.elfec.lecturas.gd.model.enums.ReadingStatus;

public class ReadingStatusAdapter extends ArrayAdapter<String> {

	public ReadingStatusAdapter(Context context, int resource) {
		super(context, resource, ReadingStatus.getReadingStatusPlurals());
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null)
			convertView = super.getView(position, convertView, parent);
		((TextView) convertView).setTextColor(ContextCompat.getColor(
				getContext(), android.R.color.white));
		return convertView;
	}

}
