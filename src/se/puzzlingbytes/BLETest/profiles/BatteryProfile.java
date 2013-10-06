package se.puzzlingbytes.BLETest.profiles;

import android.bluetooth.BluetoothGattCharacteristic;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Mikael Olsson on 2013-09-20.
 */
public class BatteryProfile {

	public static final UUID BATTERY_SERVICE_UUID = ProfileUtils
			.generateStandardUUID("180F");

	public static final UUID BATTERY_LEVEL_UUID = ProfileUtils
			.generateStandardUUID("2A19");

	private static HashMap<UUID, String> sUUIDNameMap = new HashMap<UUID, String>();

	static {
		sUUIDNameMap.put(BATTERY_SERVICE_UUID, "Battery Service");
		sUUIDNameMap.put(BATTERY_LEVEL_UUID, "Battery Level");
	}

	public static HashMap<UUID, String> getsUUIDNameMap() {
		return sUUIDNameMap;
	}

	public static int readBatteryLevel(
			BluetoothGattCharacteristic bluetoothGattCharacteristic) {
		return bluetoothGattCharacteristic.getIntValue(
				BluetoothGattCharacteristic.FORMAT_UINT8, 0);
	}
}
