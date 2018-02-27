package info.rajmundstaniek.neurofeedback.businessLogic;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import info.rajmundstaniek.neurofeedback.R;

/**
 * Created by rajmu on 07.02.2018.
 */

public class BluetoothDeviceListAdapter extends BaseAdapter {
    public static final String TARGET_DEVICE_NAME = "MindWave Mobile";

    private LayoutInflater mInflator;
    private ArrayList<BluetoothDevice> mLeDevices;
    private Context mContext;

    public boolean[] itemToggled = null;

    public BluetoothDeviceListAdapter(Context context) {
        super();
        mContext = context;
        mLeDevices = new ArrayList<BluetoothDevice>();
        mInflator = LayoutInflater.from(mContext);

    }

    public void addDevice(BluetoothDevice device) {
        if (!mLeDevices.contains(device)) {
            mLeDevices.add(device);
        }
    }

    public BluetoothDevice getDevice(int position) {
        return mLeDevices.get(position);
    }

    public void clear() {
        mLeDevices.clear();
    }

    @Override
    public int getCount() {
        return mLeDevices.size();
    }

    @Override
    public Object getItem(int i) {
        return mLeDevices.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder;
        // General ListView optimization code.
        if (view == null) {
            view = mInflator.inflate(R.layout.listitem_device, null);
            viewHolder = new ViewHolder();
            viewHolder.img1 = (ImageView) view.findViewById(R.id.img1);
            viewHolder.deviceName = (TextView) view.findViewById(R.id.device_name);
            viewHolder.deviceAddress = (TextView) view.findViewById(R.id.device_address);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        if(itemToggled[i]){
            viewHolder.img1.setImageResource(R.drawable.ic_bluetooth_connected_black_24dp);
        }
        else{
            viewHolder.img1.setImageResource(R.drawable.ic_bluetooth_black_24dp);
        }
        BluetoothDevice device = mLeDevices.get(i);
        final String deviceName = device.getName();
        String deviceAddress = device.getAddress();
        if (deviceName != null && deviceName.length() > 0 && deviceAddress != null) {
            if(deviceName.equals(TARGET_DEVICE_NAME)){
                viewHolder.deviceName.setText(deviceName + " (" + deviceAddress
                        .substring(12, deviceAddress.length()).replace(":", "") + ")");
                viewHolder.deviceAddress.setText(deviceAddress);
            }
            else{
                viewHolder.deviceName.setText(deviceName);
                viewHolder.deviceAddress.setText(deviceAddress);
            }
        }
        else {
            viewHolder.deviceName.setText(R.string.bt_device_unknown);
            viewHolder.deviceAddress.setText(deviceAddress);
        }


        return view;
    }
    static class ViewHolder {
        ImageView img1;
        TextView deviceName;
        TextView deviceAddress;
    }
}
