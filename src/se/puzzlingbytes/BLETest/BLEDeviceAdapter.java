package se.puzzlingbytes.BLETest;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Mikael Olsson on 2013-09-20.
 */
public class BLEDeviceAdapter extends ArrayAdapter<BluetoothDevice> {

	private Context mContext;

	public BLEDeviceAdapter(Context context) {
		super(context, android.R.layout.simple_list_item_2);
		mContext = context;
	}

	@Override
	public void add(BluetoothDevice bluetoothDevice) {
		if (getPosition(bluetoothDevice) == -1) {
			super.add(bluetoothDevice);
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;

		if (convertView == null) {
			convertView = View.inflate(mContext,
					android.R.layout.simple_list_item_2, null);
			viewHolder = new ViewHolder();
			viewHolder.primaryText = (TextView) convertView
					.findViewById(android.R.id.text1);
			viewHolder.secondaryText = (TextView) convertView
					.findViewById(android.R.id.text2);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		BluetoothDevice bluetoothDevice = getItem(position);
		viewHolder.primaryText.setText(bluetoothDevice.getName());
		viewHolder.secondaryText.setText(bluetoothDevice.getAddress());
		return convertView;
	}

	private static class ViewHolder {
		public TextView primaryText;
		public TextView secondaryText;
	}

}
