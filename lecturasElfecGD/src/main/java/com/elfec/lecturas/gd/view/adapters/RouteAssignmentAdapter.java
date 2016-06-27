package com.elfec.lecturas.gd.view.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.elfec.lecturas.gd.R;
import com.elfec.lecturas.gd.model.RouteAssignment;

import java.util.List;

public class RouteAssignmentAdapter extends ArrayAdapter<RouteAssignment> {

    private static final RouteAssignment sDummyRoute = new RouteAssignment();

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
        return position == 0 ? sDummyRoute : super.getItem(position - 1);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        convertView = super.getDropDownView(position, convertView, parent);
        TextView txtView = ((TextView) convertView);
        if (position == 0)
            txtView.setText(R.string.lbl_all_routes);
        else txtView.setText(String.valueOf(getItem(position).getRoute()));
        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = super.getView(position, convertView, parent);
        TextView txtView = ((TextView) convertView);
        txtView.setTextColor(ContextCompat.getColor(
                getContext(), android.R.color.white));

        if (position == 0)
            txtView.setText(R.string.lbl_all_routes);
        else txtView.setText(String.valueOf(getItem(position).getRoute()));
        return convertView;
    }

}
