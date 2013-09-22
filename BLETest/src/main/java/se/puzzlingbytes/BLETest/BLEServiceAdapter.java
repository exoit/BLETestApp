package se.puzzlingbytes.BLETest;

import android.bluetooth.BluetoothGattService;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Collection;

import se.puzzlingbytes.BLETest.profiles.BatteryProfile;
import se.puzzlingbytes.BLETest.profiles.ProfileUtils;

/**
 * Created by Mikael Olsson on 2013-09-20.
 */
public class BLEServiceAdapter extends ArrayAdapter<BluetoothGattService> {

    private Context mContext;

    public BLEServiceAdapter(Context context) {
        super(context, android.R.layout.simple_list_item_2);
        mContext = context;
    }

    @Override
    public void add(BluetoothGattService bluetoothGattService) {
        if (getPosition(bluetoothGattService) == -1) {
            super.add(bluetoothGattService);
        }
    }

    @Override
    public void addAll(Collection<? extends BluetoothGattService> collection) {
        for (BluetoothGattService bluetoothGattService : collection) {
            add(bluetoothGattService);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = View
                    .inflate(mContext, android.R.layout.simple_list_item_2, null);
            viewHolder = new ViewHolder();
            viewHolder.primaryText = (TextView) convertView.findViewById(android.R.id.text1);
            viewHolder.secondaryText = (TextView) convertView.findViewById(android.R.id.text2);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        BluetoothGattService bluetoothGattService = getItem(position);
        viewHolder.primaryText
                .setText(ProfileUtils.lookupUUIDName(bluetoothGattService.getUuid(), "Unknown"));
        viewHolder.secondaryText.setText(bluetoothGattService.getUuid().toString());
        return convertView;
    }

    private static class ViewHolder {
        public TextView primaryText;
        public TextView secondaryText;
    }

}
