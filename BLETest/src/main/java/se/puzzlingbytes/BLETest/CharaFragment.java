package se.puzzlingbytes.BLETest;

import android.app.Activity;
import android.app.ListFragment;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

/**
 * Created by Mikael Olsson on 2013-09-20.
 */
public class CharaFragment extends ListFragment {

    private BLECharaAdapter mBleCharaAdapter;
    private BluetoothGatt mBluetoothGatt;
    private Activity mActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBleCharaAdapter = new BLECharaAdapter(mActivity);
        mBluetoothGatt = ((MainActivity) mActivity).getBluetoothGatt();
        mBleCharaAdapter.addAll(((MainActivity) mActivity).getBluetoothCharacteristicList());
        setListAdapter(mBleCharaAdapter);
        setHasOptionsMenu(false);
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        BluetoothGattCharacteristic bluetoothGattCharacteristic = mBleCharaAdapter
                .getItem(position);
        ((MainActivity) mActivity).showCharacteristicsValue(bluetoothGattCharacteristic);
    }

    private final BluetoothGattCallback bluetoothGattCallback = new BluetoothGattCallback() {
        @Override
        public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorRead(gatt, descriptor, status);
        }
    };
}
