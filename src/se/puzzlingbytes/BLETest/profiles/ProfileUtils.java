package se.puzzlingbytes.BLETest.profiles;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Mikael Olsson on 2013-09-20.
 */
public class ProfileUtils {

	public static final String BASE_UUID_STRING = "-0000-1000-8000-00805F9B34FB";

	private static HashMap<UUID, String> sUUIDNameMap = new HashMap<UUID, String>();

	static {
		sUUIDNameMap.putAll(BatteryProfile.getsUUIDNameMap());
		sUUIDNameMap.putAll(GenericAccessProfile.getsUUIDNameMap());
		sUUIDNameMap.putAll(GenericAttributeProfile.getsUUIDNameMap());
		sUUIDNameMap.putAll(ImmediateAlertProfile.getsUUIDNameMap());
		sUUIDNameMap.putAll(LinkLossProfile.getsUUIDNameMap());
		sUUIDNameMap.putAll(TXPowerProfile.getsUUIDNameMap());
	}

	public static String lookupUUIDName(UUID uuid, String defaultName) {
		if (sUUIDNameMap.containsKey(uuid)) {
			return sUUIDNameMap.get(uuid);
		}
		return defaultName;
	}

	public static UUID generateStandardUUID(String assignedNumber) {
		return UUID.fromString("0000" + assignedNumber + BASE_UUID_STRING);
	}

	public static int convertByteArrayToInt(byte[] bytes) {
		int value = 0;
		for (byte b : bytes) {
			value = (value << 8) + (b & 0xFF);
		}
		return value;
	}

	public static String convertByteArrayToMAC(byte[] bytes) {
		StringBuilder stringBuilder = new StringBuilder();
		for (byte b : bytes) {
			if (stringBuilder.length() > 0)
				stringBuilder.append(':');
			stringBuilder.append(String.format("%02x", b));
		}
		return stringBuilder.toString();
	}
}
