package com.elfec.lecturas.gd.view.adapters;

import java.util.List;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.elfec.lecturas.gd.model.RouteAssignment;

public class RouteAssignmentAdapter extends ArrayAdapter<RouteAssignment> {

	public RouteAssignmentAdapter(Context context, int resource,
			List<RouteAssignment> objects) {
		super(context, resource, objects);
	}

	@Override
	public int getCount() {
		return super.getCount() + 1;
	}

	@Override
	public RouteAssignment getItem(int position) {
		return position == 0 ? null : super.getItem(position - 1);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		if (convertView == null)
			convertView = super.getDropDownView(position, convertView, parent);
		if (position == 0) {
			((TextView) convertView).setText("Todas");
		} else {
			RouteAssignment routeAssignment = getItem(position);
			((TextView) convertView).setText("" + routeAssignment.getRoute());
		}
		return convertView;
	}

	@Override
	public int getViewTypeCount() {
		return 1;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null)
			convertView = super.getView(position, convertView, parent);
		((TextView) convertView).setTextColor(ContextCompat.getColor(
				getContext(), android.R.color.white));
		if (position == 0) {
			((TextView) convertView).setText("Todas");
		} else {
			RouteAssignment routeAssignment = getItem(position);
			((TextView) convertView).setText("" + routeAssignment.getRoute());
		}
		return convertView;
	}

}
