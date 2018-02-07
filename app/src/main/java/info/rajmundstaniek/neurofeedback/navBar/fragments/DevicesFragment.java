package info.rajmundstaniek.neurofeedback.navBar.fragments;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

import info.rajmundstaniek.neurofeedback.MainActivity;
import info.rajmundstaniek.neurofeedback.R;
import info.rajmundstaniek.neurofeedback.businessLogic.BluetoothDeviceListAdapter;
import info.rajmundstaniek.neurofeedback.businessLogic.TgReaderSingleton;

/**
 * Created by rajmu on 07.02.2018.
 */

public class DevicesFragment extends Fragment {

    private ListView listView;
    private BluetoothAdapter mBluetoothArapter;
    private Set<BluetoothDevice> mBluetoothDevices = null;

    private BluetoothDeviceListAdapter deviceListAdapter;
    private String deviceAddress;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_devices, null);

        listView = (ListView)view.findViewById(R.id.bluetoothDeviceList);
        listView.setOnItemClickListener(selectDeviceItemClickListener);

        mBluetoothArapter = BluetoothAdapter.getDefaultAdapter();

        if(mBluetoothArapter == null){
            Toast.makeText(view.getContext(), R.string.err_bt_not_supported, Toast.LENGTH_SHORT).show();
        }
        else{
            if(mBluetoothArapter.isEnabled()){
                mBluetoothDevices = mBluetoothArapter.getBondedDevices();
                deviceListAdapter = new BluetoothDeviceListAdapter(view.getContext());
                for(BluetoothDevice device : mBluetoothDevices){
                    deviceListAdapter.addDevice(device);
                }

                listView.setAdapter(deviceListAdapter);
            }
            else{
                Toast.makeText(view.getContext(), R.string.err_bt_not_enabled, Toast.LENGTH_SHORT).show();
            }
        }

        return view;
    }

    private AdapterView.OnItemClickListener selectDeviceItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if(mBluetoothArapter.isDiscovering()){
                mBluetoothArapter.cancelDiscovery();
            }
            deviceAddress = deviceListAdapter.getDevice(position).getAddress();
            BluetoothDevice remoteDevice = mBluetoothArapter.getRemoteDevice(deviceAddress);

            TgReaderSingleton.getInstance().setDevice(remoteDevice);
        }
    };
}
