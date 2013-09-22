package se.puzzlingbytes.BLETest.profiles;

import android.bluetooth.BluetoothGattCharacteristic;
import android.util.Log;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by exoit on 2013-09-20.
 */
public class ImmediateAlertProfile {

    public static final UUID IMMEDIATE_ALERT_SERVICE_UUID = ProfileUtils.generateStandardUUID("1802");

    public static final UUID ALERT_LEVEL_UUID = ProfileUtils.generateStandardUUID("2A06");

    private static HashMap<UUID, String> sUUIDNameMap = new HashMap<UUID, String>();

    static {
        sUUIDNameMap.put(IMMEDIATE_ALERT_SERVICE_UUID, "Immediate Alert Service");
        sUUIDNameMap.put(ALERT_LEVEL_UUID, "Alert Level");
    }

    public static HashMap<UUID, String> getsUUIDNameMap() {
        return sUUIDNameMap;
    }

}
