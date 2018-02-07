package info.rajmundstaniek.neurofeedback.navBar.fragments;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

import info.rajmundstaniek.neurofeedback.R;

/**
 * Created by rajmu on 07.02.2018.
 */

public class DevicesFragment extends Fragment {

    private ListView listView;
    private ArrayList<String> mDeviceList = new ArrayList<>();
    private BluetoothAdapter mBluetoothArapter;
    private Set<BluetoothDevice> mBluetoothDevices = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_devices, null);

        listView = (ListView)view.findViewById(R.id.bluetoothDeviceList);

        mBluetoothArapter = BluetoothAdapter.getDefaultAdapter();

        if(mBluetoothArapter == null){
            Toast.makeText(view.getContext(), R.string.err_bt_not_supported, Toast.LENGTH_SHORT).show();
        }
        else{
            if(mBluetoothArapter.isEnabled()){
                mBluetoothDevices = mBluetoothArapter.getBondedDevices();

                for(BluetoothDevice device : mBluetoothDevices){
                    mDeviceList.add(device.getName() + "\n" + device.getAddress());
                }

                listView.setAdapter(new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, mDeviceList));
            }
            else{
                Toast.makeText(view.getContext(), R.string.err_bt_not_enabled, Toast.LENGTH_SHORT).show();
            }
        }

        return view;
    }
}
