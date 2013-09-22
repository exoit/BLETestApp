package se.puzzlingbytes.BLETest;

import android.app.Activity;
import android.app.ListFragment;
import android.bluetooth.BluetoothGattService;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.List;

/**
 * Created by Mikael Olsson on 2013-09-20.
 */
public class ServiceFragment extends ListFragment {

    private BLEServiceAdapter mBleServiceAdapter;
    private Activity mActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBleServiceAdapter = new BLEServiceAdapter(mActivity);
        setListAdapter(mBleServiceAdapter);
        setHasOptionsMenu(false);
        setRetainInstance(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mBleServiceAdapter.getCount() == 0) {
            setListShown(false);
        }
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        BluetoothGattService bluetoothGattService = mBleServiceAdapter.getItem(position);
        ((MainActivity) mActivity)
                .showServiceCharacteristics(bluetoothGattService.getCharacteristics());
    }

    public void showBLEService(List<BluetoothGattService> services) {
        mBleServiceAdapter.addAll(services);
        mBleServiceAdapter.notifyDataSetChanged();
        setListShown(true);
    }
}
