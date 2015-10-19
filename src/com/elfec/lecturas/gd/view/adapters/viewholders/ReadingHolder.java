package com.elfec.lecturas.gd.view.adapters.viewholders;

import org.apache.commons.lang.WordUtils;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.bignerdranch.android.recyclerviewchoicemode.MultiSelector;
import com.bignerdranch.android.recyclerviewchoicemode.SwappingHolder;
import com.elfec.lecturas.gd.R;
import com.elfec.lecturas.gd.helpers.ui.ReadingStatusColorPicker;
import com.elfec.lecturas.gd.helpers.util.text.AccountFormatter;
import com.elfec.lecturas.gd.model.ReadingGeneralInfo;

/**
 * Provide a reference to the views for each data item Complex data items may
 * need more than one view per item, and you provide access to all the vieews
 * for a data item in a view holder
 */
public class ReadingHolder extends SwappingHolder {

	private TextView txtAccountNumber;
	private TextView txtReadingStatus;
	private TextView txtNUS;
	private TextView txtMeterNumber;
	private TextView txtClientName;

	// private ReadingGeneralInfo reading;

	public ReadingHolder(View itemView, MultiSelector selector) {
		super(itemView, selector);
		txtAccountNumber = (TextView) itemView
				.findViewById(R.id.txt_account_number);
		txtReadingStatus = (TextView) itemView
				.findViewById(R.id.txt_reading_status);
		txtNUS = (TextView) itemView.findViewById(R.id.txt_nus);
		txtMeterNumber = (TextView) itemView.findViewById(R.id.txt_meter);
		txtClientName = (TextView) itemView.findViewById(R.id.txt_client_name);
	}

	public void bindReading(ReadingGeneralInfo reading) {
		// this.reading = reading;
		txtAccountNumber.setText(AccountFormatter.formatAccountNumber(reading
				.getSupplyNumber()));
		txtReadingStatus.setText(reading.getStatus().toString());
		txtReadingStatus.setBackgroundColor(ContextCompat.getColor(
				txtReadingStatus.getContext(), ReadingStatusColorPicker
						.getResourceColorId(reading.getStatus())));
		txtNUS.setText("" + reading.getSupplyId());
		txtMeterNumber.setText(reading.getReadingMeter().getSerialNumber());
		txtClientName.setText(WordUtils.capitalizeFully(reading.getName(),
				new char[] { '.', ' ' }));
	}

}