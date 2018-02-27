package info.rajmundstaniek.neurofeedback.navBar.fragments;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Set;

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
    private Intent mIntent;

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
                deviceListAdapter.itemToggled = new boolean[mBluetoothDevices.size()];
                Arrays.fill(deviceListAdapter.itemToggled, false);
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

            //TODO: doesn't mark the right image!!!! NEEDS A FIX
            deviceListAdapter.itemToggled[position] = ! deviceListAdapter.itemToggled[position];
            ImageView img = (ImageView) getView().findViewById(R.id.img1);
            if(deviceListAdapter.itemToggled[position]){
                img.setImageResource(R.drawable.ic_bluetooth_connected_black_24dp);
            }
            else{
                img.setImageResource(R.drawable.ic_bluetooth_black_24dp);
            }

            deviceAddress = deviceListAdapter.getDevice(position).getAddress();
            BluetoothDevice remoteDevice = mBluetoothArapter.getRemoteDevice(deviceAddress);

            TgReaderSingleton.getInstance().setDevice(remoteDevice);
            /*mIntent = new Intent(getActivity(), NeuroReceiverService.class);
            getActivity().startService(mIntent);*/
            Toast.makeText(getContext(), R.string.device_selected, Toast.LENGTH_SHORT).show();
        }
    };
}
