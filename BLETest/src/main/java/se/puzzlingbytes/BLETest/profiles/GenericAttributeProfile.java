package se.puzzlingbytes.BLETest.profiles;

import android.bluetooth.BluetoothGattCharacteristic;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Mikael Olsson on 2013-09-20.
 */
public class GenericAttributeProfile {
    public static final UUID GENERIC_ATTRIBUTE_SERVICE_UUID = ProfileUtils
            .generateStandardUUID("1801");
    public static final UUID SERVICE_CHANGED_UUID = ProfileUtils
            .generateStandardUUID("2A05");

    private static HashMap<UUID, String> sUUIDNameMap = new HashMap<UUID, String>();

    static {
        sUUIDNameMap.put(GENERIC_ATTRIBUTE_SERVICE_UUID, "Generic Attribute Service");
        sUUIDNameMap.put(SERVICE_CHANGED_UUID, "Service Changed");
    }

    public static HashMap<UUID, String> getsUUIDNameMap() {
        return sUUIDNameMap;
    }

}
