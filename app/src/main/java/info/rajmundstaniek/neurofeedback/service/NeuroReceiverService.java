package info.rajmundstaniek.neurofeedback.service;

import android.app.IntentService;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.neurosky.connection.ConnectionStates;
import com.neurosky.connection.DataType.MindDataType;
import com.neurosky.connection.EEGPower;
import com.neurosky.connection.TgStreamHandler;
import com.neurosky.connection.TgStreamReader;

import info.rajmundstaniek.neurofeedback.R;
import info.rajmundstaniek.neurofeedback.businessLogic.TgReaderSingleton;

import static com.neurosky.connection.DataType.MindDataType.*;

/**
 * Created by rajmu on 08.02.2018.
 */

public class NeuroReceiverService extends IntentService {
    public static final String TAG = NeuroReceiverService.class.getSimpleName();

    public enum ACTION{
        HEADSET_UPDATE,
        SERVICE_CONNECT,
        SERVICE_DISCONNECT
    }

    private Handler uiHandler;
    private int headsetCurrentState;
    private boolean isReadFilter;
    private static final int MSG_UPDATE_BAD_PACKET = 1001;
    private static final int MSG_UPDATE_STATE = 1002;
    private static final int MSG_CONNECT = 1003;

    private TgStreamReader tgStreamReader;
    private int badPacketCount;
    private TgStreamHandler handler = new TgStreamHandler() {
        @Override
        public void onDataReceived(int i, int i1, Object o) {
            Message msg = linkDetectedHandler.obtainMessage();
            msg.what = i;
            msg.arg1 = i1;
            msg.obj = o;
            linkDetectedHandler.sendMessage(msg);
        }

        @Override
        public void onStatesChanged(int i) {
            headsetCurrentState = i;
            //TODO: implement all cases
            switch (i){
                case ConnectionStates.STATE_CONNECTED:
                    //connected
                    break;
                case ConnectionStates.STATE_WORKING:
                    linkDetectedHandler.sendEmptyMessageDelayed(1234, 5000);
                    break;
                case ConnectionStates.STATE_GET_DATA_TIME_OUT:
                    //get data time out
                    break;
                case ConnectionStates.STATE_COMPLETE:
                    //read file complete
                    break;
                case ConnectionStates.STATE_STOPPED:
                    break;
                case ConnectionStates.STATE_DISCONNECTED:
                    break;
                case ConnectionStates.STATE_ERROR:
                    Log.d(TAG,"Connect error, Please try again!");
                    break;
                case ConnectionStates.STATE_FAILED:
                    Log.d(TAG,"Connect failed, Please try again!");
                    break;
            }
            Message msg = linkDetectedHandler.obtainMessage();
            msg.what = MSG_UPDATE_STATE;
            msg.arg1 = headsetCurrentState;
            linkDetectedHandler.sendMessage(msg);
        }

        @Override
        public void onChecksumFail(byte[] bytes, int i, int i1) {
            badPacketCount ++;
            Message msg = linkDetectedHandler.obtainMessage();
            msg.what = MSG_UPDATE_BAD_PACKET;
            msg.arg1 = badPacketCount;
            linkDetectedHandler.sendMessage(msg);
        }

        @Override
        public void onRecordFail(int i) {
            Log.e(TAG, "OnRecordFail: " + i);
        }
    };

    private Handler linkDetectedHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1234:
                    tgStreamReader.MWM15_getFilterType();
                    isReadFilter = true;
                    Log.d(TAG,"MWM15_getFilterType ");

                    break;
                case 1235:
                    tgStreamReader.MWM15_setFilterType(FilterType.FILTER_60HZ);
                    Log.d(TAG,"MWM15_setFilter  60HZ");
                    linkDetectedHandler.sendEmptyMessageDelayed(1237, 1000);
                    break;
                case 1236:
                    tgStreamReader.MWM15_setFilterType(FilterType.FILTER_50HZ);
                    Log.d(TAG,"MWM15_SetFilter 50HZ ");
                    linkDetectedHandler.sendEmptyMessageDelayed(1237, 1000);
                    break;

                case 1237:
                    tgStreamReader.MWM15_getFilterType();
                    Log.d(TAG,"MWM15_getFilterType ");

                    break;

                case CODE_FILTER_TYPE:
                    Log.d(TAG,"CODE_FILTER_TYPE: " + msg.arg1 + "  isReadFilter: " + isReadFilter);
                    if(isReadFilter){
                        isReadFilter = false;
                        if(msg.arg1 == FilterType.FILTER_50HZ.getValue()){
                            linkDetectedHandler.sendEmptyMessageDelayed(1235, 1000);
                        }else if(msg.arg1 == FilterType.FILTER_60HZ.getValue()){
                            linkDetectedHandler.sendEmptyMessageDelayed(1236, 1000);
                        }else{
                            Log.e(TAG,"Error filter type");
                        }
                    }

                    break;



                case CODE_RAW:
                    //TODO: dispatch update of raw values from the headset
                    break;
                case CODE_MEDITATION:
                    Log.d(TAG, "HeadDataType.CODE_MEDITATION " + msg.arg1);
                    //TODO: update meditation index
                    break;
                case CODE_ATTENTION:
                    Log.d(TAG, "CODE_ATTENTION " + msg.arg1);
                    //TODO: update attention index
                    break;
                case CODE_EEGPOWER:
                    EEGPower power = (EEGPower)msg.obj;
                    if(power.isValidate()){
                        //TODO: update detailed brain wave values
                    }
                    break;
                case CODE_POOR_SIGNAL://
                    int poorSignal = msg.arg1;
                    Log.d(TAG, "poorSignal:" + poorSignal);
                    //TODO: handle poor signal

                    break;
                case MSG_UPDATE_BAD_PACKET:
                    //TODO: handle bad packet

                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    public NeuroReceiverService() {
        super("UCN-Neuro-background-service");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(intent != null){
            synchronized (this){
                badPacketCount = 0;
                Log.i(TAG, "Initializing TgStreamReader");
                BluetoothDevice device = TgReaderSingleton.getInstance().getDevice();
                if(tgStreamReader == null){
                    tgStreamReader = new TgStreamReader(device, handler);
                    tgStreamReader.startLog();
                    tgStreamReader.connectAndStart();
                }
                else {
                    tgStreamReader.stop();
                    tgStreamReader.changeBluetoothDevice(device);
                    tgStreamReader.connectAndStart();
                }
            }
        }
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        uiHandler = new Handler();
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), R.string.headset_connecting,
                        Toast.LENGTH_SHORT).show();
            }
        });
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        if(tgStreamReader != null){
            tgStreamReader.close();
            tgStreamReader = null;
        }
        super.onDestroy();
    }
}
