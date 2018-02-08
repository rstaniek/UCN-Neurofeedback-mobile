package info.rajmundstaniek.neurofeedback.businessLogic;

import android.bluetooth.BluetoothDevice;

import com.neurosky.connection.TgStreamHandler;
import com.neurosky.connection.TgStreamReader;

import info.rajmundstaniek.neurofeedback.service.NeuroEventDispatcher;

/**
 * Created by rajmu on 07.02.2018.
 */

public class TgReaderSingleton {
    private static final TgReaderSingleton ourInstance = new TgReaderSingleton();

    public static TgReaderSingleton getInstance() {
        return ourInstance;
    }

    private TgReaderSingleton() {
    }

    private TgStreamReader stream;
    private BluetoothDevice device;

    public TgStreamReader getStream() {
        return stream;
    }

    public void setStream(BluetoothDevice device, TgStreamHandler callback) {
        this.device = device;
        if(stream == null) {
            stream = new TgStreamReader(device, callback);
            stream.startLog();
        }
        else{
            stream.changeBluetoothDevice(device);
        }
        stream.setTgStreamHandler(callback);
    }

    public BluetoothDevice getDevice() {
        return device;
    }

    public void setDevice(BluetoothDevice device) {
        this.device = device;
    }

    private NeuroEventDispatcher neuroEventDispatcher;

    public NeuroEventDispatcher getNeuroEventDispatcher() {
        return neuroEventDispatcher;
    }

    public void setNeuroEventDispatcher(NeuroEventDispatcher neuroEventDispatcher) {
        this.neuroEventDispatcher = neuroEventDispatcher;
    }
}
