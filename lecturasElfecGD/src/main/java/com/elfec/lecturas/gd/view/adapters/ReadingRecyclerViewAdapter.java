package com.elfec.lecturas.gd.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elfec.lecturas.gd.R;
import com.elfec.lecturas.gd.model.ReadingGeneralInfo;
import com.elfec.lecturas.gd.view.adapters.viewholders.ReadingHolder;
import com.elfec.widgets.recyclerviewchoicemode.RecyclerViewChoiceAdapter;
import com.elfec.widgets.recyclerviewchoicemode.SingleSelector;

import java.util.List;

/**
 * Adapter para las lecturas que se muestran en un recyclerview
 * 
 * @author drodriguez
 *
 */
public class ReadingRecyclerViewAdapter extends
		RecyclerViewChoiceAdapter<ReadingHolder> {

	private List<ReadingGeneralInfo> readings;

	/**
	 * Provide a suitable constructor (depends on the kind of dataset)
	 * 
	 * @param readings
	 */
	public ReadingRecyclerViewAdapter(List<ReadingGeneralInfo> readings) {
		super(new SingleSelector());
		this.readings = readings;
	}

	/**
	 * Create new views (invoked by the layout manager)
	 */
	@Override
	public ReadingHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
		// Create a new view
		View v = LayoutInflater.from(viewGroup.getContext()).inflate(
				R.layout.reading_list_item, viewGroup, false);
		ReadingHolder readingHolder = new ReadingHolder(v, selector);
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

	/**
	 * Elimina un objeto de la lista
	 * 
	 * @param position
	 */
	public void removeItem(int position) {
		notifyItemRemoved(position);
	}
}
