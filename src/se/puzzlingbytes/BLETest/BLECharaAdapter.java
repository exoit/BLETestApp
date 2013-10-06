package se.puzzlingbytes.BLETest;

import android.bluetooth.BluetoothGattCharacteristic;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;

import se.puzzlingbytes.BLETest.profiles.ProfileUtils;

/**
 * Created by Mikael Olsson on 2013-09-20.
 */
public class BLECharaAdapter extends ArrayAdapter<BluetoothGattCharacteristic> {

	private Context mContext;

	public BLECharaAdapter(Context context) {
		super(context, android.R.layout.simple_list_item_2);
		mContext = context;
	}

	@Override
	public void add(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
		if (getPosition(bluetoothGattCharacteristic) == -1) {
			super.add(bluetoothGattCharacteristic);
		}
	}

	@Override
	public void addAll(
			Collection<? extends BluetoothGattCharacteristic> collection) {
		for (BluetoothGattCharacteristic bluetoothGattService : collection) {
			add(bluetoothGattService);
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

		BluetoothGattCharacteristic bluetoothGattCharacteristic = getItem(position);
		viewHolder.primaryText.setText(ProfileUtils.lookupUUIDName(
				bluetoothGattCharacteristic.getUuid(), "Unknown"));
		viewHolder.secondaryText
				.setText(translateProperties(bluetoothGattCharacteristic
						.getProperties())
						+ (bluetoothGattCharacteristic.getPermissions() > 0 ? ","
								+ translatePermission(bluetoothGattCharacteristic
										.getPermissions())
								: ""));
		return convertView;
	}

	@Override
	public boolean isEnabled(int position) {
		BluetoothGattCharacteristic bluetoothGattCharacteristic = getItem(position);
		BigInteger biProperties = BigInteger
				.valueOf(bluetoothGattCharacteristic.getProperties());
		return biProperties.testBit(1);
	}

	private String translatePermission(int permissionMask) {
		ArrayList<String> permissionList = new ArrayList<String>();
		BigInteger biPermission = BigInteger.valueOf(permissionMask);
		if (biPermission.testBit(0)) {
			permissionList.add("PERMISSION READ");
		}
		if (biPermission.testBit(1)) {
			permissionList.add("PERMISSION READ ENCRYPTED");
		}
		if (biPermission.testBit(2)) {
			permissionList.add("PERMISSION READ ENCRYPTED MITM");
		}
		if (biPermission.testBit(4)) {
			permissionList.add("PERMISSION WRITE");
		}
		if (biPermission.testBit(5)) {
			permissionList.add("PERMISSION WRITE ENCRYPTED");
		}
		if (biPermission.testBit(6)) {
			permissionList.add("PERMISSION WRITE ENCRYPTED MITM");
		}
		if (biPermission.testBit(7)) {
			permissionList.add("PERMISSION WRITE SIGNED");
		}
		if (biPermission.testBit(8)) {
			permissionList.add("PERMISSION WRITE SIGNED MITM");
		}
		StringBuilder stringBuilder = new StringBuilder();
		for (String permission : permissionList) {
			if (stringBuilder.length() > 0) {
				stringBuilder.append(", ");
			}
			stringBuilder.append(permission);
		}
		return stringBuilder.toString();
	}

	private String translateProperties(int propertyMask) {
		ArrayList<String> propertyList = new ArrayList<String>();
		BigInteger biProperties = BigInteger.valueOf(propertyMask);
		if (biProperties.testBit(0)) {
			propertyList.add("BROADCAST");
		}
		if (biProperties.testBit(1)) {
			propertyList.add("READ");
		}
		if (biProperties.testBit(2)) {
			propertyList.add("WRITE NO RESPONSE");
		}
		if (biProperties.testBit(3)) {
			propertyList.add("WRITE");
		}
		if (biProperties.testBit(4)) {
			propertyList.add("NOTIFY");
		}
		if (biProperties.testBit(5)) {
			propertyList.add("INDICATE");
		}
		if (biProperties.testBit(6)) {
			propertyList.add("SIGNED WRITE");
		}
		if (biProperties.testBit(7)) {
			propertyList.add("EXTENDED");
		}
		StringBuilder stringBuilder = new StringBuilder();
		for (String property : propertyList) {
			if (stringBuilder.length() > 0) {
				stringBuilder.append(", ");
			}
			stringBuilder.append(property);
		}
		return stringBuilder.toString();
	}

	private static class ViewHolder {
		public TextView primaryText;
		public TextView secondaryText;
	}

}
