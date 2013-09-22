package se.puzzlingbytes.BLETest;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import se.puzzlingbytes.BLETest.profiles.BatteryProfile;
import se.puzzlingbytes.BLETest.profiles.GenericAccessProfile;
import se.puzzlingbytes.BLETest.profiles.ProfileUtils;
import se.puzzlingbytes.BLETest.profiles.TXPowerProfile;

public class MainActivity extends Activity {

    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothGatt mBluetoothGatt;
    private List<BluetoothGattCharacteristic> mCharaList;
    private DeviceFragment mDeviceFragment;
    private ServiceFragment mServiceFragment;
    private CharaFragment mCharaFragment;
    private CharaValueDialogFragment mDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initBluetooth();
        mDeviceFragment = new DeviceFragment();
        mServiceFragment = new ServiceFragment();
        mCharaFragment = new CharaFragment();
    }

    private void initBluetooth() {
        if (mBluetoothManager == null || mBluetoothAdapter == null) {
            mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
            mBluetoothAdapter = mBluetoothManager.getAdapter();
        }

        if (!mBluetoothAdapter.isEnabled()) {
            mBluetoothAdapter.enable();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, mDeviceFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    protected void onPause() {
        super.onStop();
        if (mBluetoothGatt != null) {
            Log.d("MainActivity - Stop", "Cleaning up");
            mBluetoothGatt.disconnect();
            mBluetoothGatt.close();
        }

    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }

    public BluetoothAdapter getBluetoothAdapter() {
        return mBluetoothAdapter;
    }

    public BluetoothGatt getBluetoothGatt() {
        return mBluetoothGatt;
    }

    public List<BluetoothGattCharacteristic> getBluetoothCharacteristicList() {
        return mCharaList;
    }

    public void showDeviceServices(BluetoothDevice bluetoothDevice) {
        bluetoothDevice.connectGatt(this, false, bluetoothGattCallback);
        getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                mServiceFragment).addToBackStack(null)
                .commit();
    }

    public void showServiceCharacteristics(List<BluetoothGattCharacteristic> charaList) {
        mCharaList = charaList;
        getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                mCharaFragment).addToBackStack(null)
                .commit();
    }

    public void showCharacteristicsValue(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        mBluetoothGatt.readCharacteristic(bluetoothGattCharacteristic);
        String name = ProfileUtils.lookupUUIDName(bluetoothGattCharacteristic.getUuid(), "Unknown");
        mDialogFragment = CharaValueDialogFragment
                .newInstance(name);
        showDialogFragment(getFragmentManager(), mDialogFragment,
                CharaValueDialogFragment.TAG);
    }

    private final BluetoothGattCallback bluetoothGattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            if (BluetoothGatt.STATE_CONNECTED == newState && BluetoothGatt.GATT_SUCCESS == status) {
                gatt.discoverServices();
            } else {
                Log.d("Connection", "Status: " + status + " NewState: " + newState);
            }
        }

        @Override
        public void onServicesDiscovered(final BluetoothGatt gatt, int status) {
            if (BluetoothGatt.GATT_SUCCESS == status) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mServiceFragment.showBLEService(gatt.getServices());
                    }
                });
            } else {
                Log.d("Discovered", "Failed with status: " + status);
            }
            mBluetoothGatt = gatt;
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            if (BluetoothGatt.GATT_SUCCESS == status) {
                UUID serviceUUID = characteristic.getService().getUuid();
                UUID valueUUID = characteristic.getUuid();
                String value = null;
                if (BatteryProfile.BATTERY_SERVICE_UUID.equals(serviceUUID)) {
                    if (BatteryProfile.BATTERY_LEVEL_UUID.equals(valueUUID)) {
                        value = String
                                .valueOf(BatteryProfile.readBatteryLevel(characteristic) + "%");
                    }
                } else if (GenericAccessProfile.GENERIC_ACCESS_SERVICE_UUID.equals(serviceUUID)) {
                    if (GenericAccessProfile.DEVICE_NAME_UUID.equals(valueUUID)) {
                        value = GenericAccessProfile.readDeviceName(characteristic);
                    } else if (GenericAccessProfile.APPEARANCE_UUID.equals(valueUUID)) {
                        value = String.valueOf(GenericAccessProfile.readApperance(characteristic));
                    } else if (GenericAccessProfile.PERIPHERAL_PRIVACY_FLAG_UUID
                            .equals(valueUUID)) {
                        value = String.valueOf(
                                GenericAccessProfile.readPeripheralPrivacyFlag(characteristic));
                    } else if (GenericAccessProfile.RECONNECTION_ADDRESS_UUID.equals(valueUUID)) {
                        value = GenericAccessProfile.readReconnectionAddress(characteristic);
                    } else if (GenericAccessProfile.PERIPHERAL_PREFERRED_CONNECTION_PARAMETERS_UUID
                            .equals(valueUUID)) {
                        value = Arrays.toString(GenericAccessProfile
                                .readPeripheralPreferredConnectionParameters(characteristic));
                    } else if (TXPowerProfile.TX_POWER_SERVICE_UUID.equals(serviceUUID)) {
                        if (TXPowerProfile.TX_POWER_LEVEL_UUID.equals(valueUUID)) {
                            value = String.valueOf(
                                    TXPowerProfile.readTxPowerLevel(characteristic) + "dBm");
                        }
                    }
                }
                final String finalValue = value;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mDialogFragment.setValue(finalValue);
                    }
                });

            }
        }
    };


    public void showDialogFragment(FragmentManager fm, DialogFragment dialog, String tag) {
        FragmentTransaction ft = fm.beginTransaction();
        Fragment prevDialog = fm.findFragmentByTag(tag);
        if (prevDialog != null) {
            ft.remove(prevDialog);
        }
        ft.addToBackStack(null);

        dialog.show(ft, tag);
    }
}
