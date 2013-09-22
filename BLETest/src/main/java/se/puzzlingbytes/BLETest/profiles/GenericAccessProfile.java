package se.puzzlingbytes.BLETest.profiles;

import android.bluetooth.BluetoothGattCharacteristic;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Mikael Olsson on 2013-09-20.
 */
public class GenericAccessProfile {
    public static final UUID GENERIC_ACCESS_SERVICE_UUID = ProfileUtils
            .generateStandardUUID("1800");
    public static final UUID DEVICE_NAME_UUID = ProfileUtils
            .generateStandardUUID("2A00");
    public static final UUID APPEARANCE_UUID = ProfileUtils
            .generateStandardUUID("2A01");
    public static final UUID PERIPHERAL_PRIVACY_FLAG_UUID = ProfileUtils
            .generateStandardUUID("2A02");
    public static final UUID RECONNECTION_ADDRESS_UUID = ProfileUtils
            .generateStandardUUID("2A03");
    public static final UUID PERIPHERAL_PREFERRED_CONNECTION_PARAMETERS_UUID = ProfileUtils
            .generateStandardUUID("2A04");

    private static HashMap<UUID, String> sUUIDNameMap = new HashMap<UUID, String>();

    static {
        sUUIDNameMap.put(GENERIC_ACCESS_SERVICE_UUID, "Generic Access Service");
        sUUIDNameMap.put(DEVICE_NAME_UUID, "Device Name");
        sUUIDNameMap.put(APPEARANCE_UUID, "Appearance");
        sUUIDNameMap.put(PERIPHERAL_PRIVACY_FLAG_UUID, "Peripheral Privacy Flag");
        sUUIDNameMap.put(RECONNECTION_ADDRESS_UUID, "Reconnection Address");
        sUUIDNameMap.put(PERIPHERAL_PREFERRED_CONNECTION_PARAMETERS_UUID,
                "Peripheral Preferred Connection Parameters");
    }

    public static HashMap<UUID, String> getsUUIDNameMap() {
        return sUUIDNameMap;
    }

    public static String readDeviceName(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        return bluetoothGattCharacteristic.getStringValue(0);
    }

    public static int readApperance(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        return ProfileUtils.convertByteArrayToInt(bluetoothGattCharacteristic.getValue());
    }

    public static boolean readPeripheralPrivacyFlag(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        return (bluetoothGattCharacteristic.getValue()[0] & 0x1) > 0;
    }

    public static String readReconnectionAddress(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        return ProfileUtils.convertByteArrayToMAC(bluetoothGattCharacteristic.getValue());
    }

    public static int[] readPeripheralPreferredConnectionParameters(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        int values[] = new int[4];
        values[0] = bluetoothGattCharacteristic
                .getIntValue(BluetoothGattCharacteristic.FORMAT_UINT16, 0);
        values[1] = bluetoothGattCharacteristic
                .getIntValue(BluetoothGattCharacteristic.FORMAT_UINT16, 2);
        values[2] = bluetoothGattCharacteristic
                .getIntValue(BluetoothGattCharacteristic.FORMAT_UINT16, 4);
        values[3] = bluetoothGattCharacteristic
                .getIntValue(BluetoothGattCharacteristic.FORMAT_UINT16, 6);
        return values;
    }
}
