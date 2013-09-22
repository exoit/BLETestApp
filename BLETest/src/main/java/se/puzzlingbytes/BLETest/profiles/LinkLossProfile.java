package se.puzzlingbytes.BLETest.profiles;

import android.bluetooth.BluetoothGattCharacteristic;
import android.util.Log;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by exoit on 2013-09-20.
 */
public class LinkLossProfile {

    public static final UUID LINK_LOSS_SERVICE_UUID = ProfileUtils.generateStandardUUID("1803");

    public static final UUID ALERT_LEVEL_UUID = ProfileUtils.generateStandardUUID("2A06");

    private static HashMap<UUID, String> sUUIDNameMap = new HashMap<UUID, String>();

    static {
        sUUIDNameMap.put(LINK_LOSS_SERVICE_UUID, "Link Loss Service");
        sUUIDNameMap.put(ALERT_LEVEL_UUID, "Alert Level");
    }

    public static HashMap<UUID, String> getsUUIDNameMap() {
        return sUUIDNameMap;
    }

    public static int readAlertLevel(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        Log.d("TXPowerProfile", new String(bluetoothGattCharacteristic.getValue()));
        return bluetoothGattCharacteristic
                .getIntValue(BluetoothGattCharacteristic.FORMAT_UINT8, 0);
    }
}
