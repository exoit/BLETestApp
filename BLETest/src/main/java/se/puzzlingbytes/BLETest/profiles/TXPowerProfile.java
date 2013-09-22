package se.puzzlingbytes.BLETest.profiles;

import android.bluetooth.BluetoothGattCharacteristic;
import android.util.Log;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by exoit on 2013-09-20.
 */
public class TXPowerProfile {

    public static final UUID TX_POWER_SERVICE_UUID = ProfileUtils.generateStandardUUID("1804");

    public static final UUID TX_POWER_LEVEL_UUID = ProfileUtils.generateStandardUUID("2A07");

    private static HashMap<UUID, String> sUUIDNameMap = new HashMap<UUID, String>();

    static {
        sUUIDNameMap.put(TX_POWER_SERVICE_UUID, "Tx Power Service");
        sUUIDNameMap.put(TX_POWER_LEVEL_UUID, "Tx Power Level");
    }

    public static HashMap<UUID, String> getsUUIDNameMap() {
        return sUUIDNameMap;
    }

    public static int readTxPowerLevel(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        Log.d("TXPowerProfile", new String(bluetoothGattCharacteristic.getValue()));
        return bluetoothGattCharacteristic
                .getIntValue(BluetoothGattCharacteristic.FORMAT_SINT8, 0);
    }
}
