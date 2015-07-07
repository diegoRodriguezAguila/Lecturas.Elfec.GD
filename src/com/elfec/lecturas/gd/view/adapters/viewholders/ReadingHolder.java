package com.elfec.lecturas.gd.view.adapters.viewholders;

import org.apache.commons.lang.WordUtils;

import android.view.View;
import android.widget.TextView;

import com.bignerdranch.android.recyclerviewchoicemode.MultiSelector;
import com.bignerdranch.android.recyclerviewchoicemode.SwappingHolder;
import com.elfec.lecturas.gd.R;
import com.elfec.lecturas.gd.helpers.util.text.AccountFormatter;
import com.elfec.lecturas.gd.model.ReadingGeneralInfo;

/**
 * Provide a reference to the views for each data item Complex data items may
 * need more than one view per item, and you provide access to all the vieews
 * for a data item in a view holder
 */
public class ReadingHolder extends SwappingHolder implements
		View.OnClickListener {

	public TextView txtAccountNumber;
	public TextView txtNUS;
	public TextView txtMeterNumber;
	public TextView txtClientName;
	private ReadingGeneralInfo reading;
	private MultiSelector selector;

	public ReadingHolder(View itemView, MultiSelector selector) {
		super(itemView, selector);
		itemView.setOnClickListener(this);
		txtAccountNumber = (TextView) itemView
				.findViewById(R.id.txt_account_number);
		txtNUS = (TextView) itemView.findViewById(R.id.txt_nus);
		txtMeterNumber = (TextView) itemView.findViewById(R.id.txt_meter);
		txtClientName = (TextView) itemView.findViewById(R.id.txt_client_name);
		this.selector = selector;
	}

	public void bindReading(ReadingGeneralInfo reading) {
		this.reading = reading;
		txtAccountNumber.setText(AccountFormatter.formatAccountNumber(reading
				.getSupplyNumber()));
		txtNUS.setText("" + reading.getSupplyId());
		txtMeterNumber.setText(reading.getReadingMeter().getSerialNumber());
		txtClientName.setText(WordUtils.capitalizeFully(reading.getName(),
				new char[] { '.', ' ' }));
	}

	@Override
	public void onClick(View itemView) {
		if (reading == null)
			return;
		selector.tapSelection(this);
		selector.setSelected(this, true);
	}
}