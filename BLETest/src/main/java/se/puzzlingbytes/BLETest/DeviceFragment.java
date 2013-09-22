package se.puzzlingbytes.BLETest;

import android.app.Activity;
import android.app.ListFragment;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class DeviceFragment extends ListFragment {

    private static final int SCAN_TIMEOUT = 10000;
    private BLEDeviceAdapter mBleDeviceAdapter;
    private BluetoothAdapter mBluetoothAdapter;
    private boolean mScanning = false;
    private Handler mHandler;
    private Activity mActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBleDeviceAdapter = new BLEDeviceAdapter(mActivity);
        mBluetoothAdapter = ((MainActivity) mActivity).getBluetoothAdapter();
        setListAdapter(mBleDeviceAdapter);
        mHandler = new Handler();
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setEmptyText(getString(R.string.no_devices_found));
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Override
    public void onStop() {
        super.onStop();
        bleScan(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (R.id.action_refresh == item.getItemId()) {
            bleScan(true);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        bleScan(false);
        ((MainActivity) mActivity)
                .showDeviceServices((BluetoothDevice) listView.getItemAtPosition(position));
    }

    private void bleScan(boolean startScan) {
        if (startScan && !mScanning) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mScanning) {
                        Log.d("DeviceFragment", "Stop Scan");
                        mScanning = false;
                        mBluetoothAdapter.stopLeScan(leScanCallback);
                    }
                }
            }, SCAN_TIMEOUT);
            Log.d("DeviceFragment", "Start Scan");
            mBleDeviceAdapter.clear();
            mScanning = true;
            mBluetoothAdapter
                    .startLeScan(leScanCallback);
        } else {
            Log.d("DeviceFragment", "Stop Scan");
            mScanning = false;
            mBluetoothAdapter.stopLeScan(leScanCallback);
        }
    }

    private final BluetoothAdapter.LeScanCallback leScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mBleDeviceAdapter.add(device);
                    mBleDeviceAdapter.notifyDataSetChanged();
                }
            });
        }
    };

}
