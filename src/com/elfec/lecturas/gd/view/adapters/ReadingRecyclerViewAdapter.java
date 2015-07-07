package com.elfec.lecturas.gd.view.adapters;

import java.util.List;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.android.recyclerviewchoicemode.SingleSelector;
import com.elfec.lecturas.gd.R;
import com.elfec.lecturas.gd.model.ReadingGeneralInfo;
import com.elfec.lecturas.gd.view.adapters.viewholders.ReadingHolder;

/**
 * Adapter para las lecturas que se muestran en un recyclerview
 * 
 * @author drodriguez
 *
 */
public class ReadingRecyclerViewAdapter extends
		RecyclerView.Adapter<ReadingHolder> {

	private List<ReadingGeneralInfo> readings;
	private SingleSelector singleSelector;

	/**
	 * Provide a suitable constructor (depends on the kind of dataset)
	 * 
	 * @param readings
	 */
	public ReadingRecyclerViewAdapter(List<ReadingGeneralInfo> readings) {
		this.readings = readings;
		this.singleSelector = new SingleSelector();
	}

	/**
	 * Create new views (invoked by the layout manager)
	 */
	@Override
	public ReadingHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
		// Create a new view
		View v = LayoutInflater.from(viewGroup.getContext()).inflate(
				R.layout.reading_list_item, viewGroup, false);

		ReadingHolder readingHolder = new ReadingHolder(v, singleSelector);
		return readingHolder;
	}

	/**
	 * Replace the contents of a view (invoked by the layout manager)
	 */
	@Override
	public void onBindViewHolder(ReadingHolder viewHolder, int position) {
		viewHolder.bindReading(this.readings.get(position));
	}

	@Override
	public int getItemCount() {
		return this.readings.size();
	}
}
