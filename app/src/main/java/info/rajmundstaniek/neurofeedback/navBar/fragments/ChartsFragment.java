package info.rajmundstaniek.neurofeedback.navBar.fragments;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.neurosky.connection.ConnectionStates;
import com.neurosky.connection.DataType.MindDataType;
import com.neurosky.connection.DataType.MindDataType.FilterType;
import com.neurosky.connection.EEGPower;
import com.neurosky.connection.TgStreamHandler;
import com.neurosky.connection.TgStreamReader;

import info.rajmundstaniek.neurofeedback.MainActivity;
import info.rajmundstaniek.neurofeedback.R;
import info.rajmundstaniek.neurofeedback.businessLogic.TgReaderSingleton;
import info.rajmundstaniek.neurofeedback.service.NeuroEventArgs;
import info.rajmundstaniek.neurofeedback.service.NeuroServiceListener;

/**
 * Created by rajmu on 07.02.2018.
 */

public class ChartsFragment extends Fragment{
    public static final String TAG = ChartsFragment.class.getSimpleName();

    private TextView meditationText, attentionText, deltaText, rawText, signalErrorsText;
    private ImageButton btnStart, btnStop;

    private TgStreamReader tgStreamReader;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        final View view = inflater.inflate(R.layout.fragment_charts, null);

        meditationText = (TextView) view.findViewById(R.id.textMeditationVal);
        attentionText = (TextView) view.findViewById(R.id.textAttentionVal);
        deltaText = (TextView) view.findViewById(R.id.textDeltaVal);
        rawText = (TextView) view.findViewById(R.id.textRawVal);
        signalErrorsText = (TextView) view.findViewById(R.id.textSignalErrVal);
        resetValues();
        btnStart = (ImageButton) view.findViewById(R.id.btnStart_img);
        btnStop = (ImageButton) view.findViewById(R.id.btnStop_img);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(prepareBtInterface() && tgStreamReader != null){
                    tgStreamReader.connectAndStart();
                }
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tgStreamReader != null){
                    tgStreamReader.stop();
                    tgStreamReader.close();
                    tgStreamReader = null;
                }
            }
        });

        return view;
    }

    private int currentState = -1;
    private int badPacketCount = 0;
    private TgStreamHandler callback = new TgStreamHandler() {
        @Override
        public void onDataReceived(int datatype, int data, Object obj) {
            Message msg = LinkDetectedHandler.obtainMessage();
            msg.what = datatype;
            msg.arg1 = data;
            msg.obj = obj;
            LinkDetectedHandler.sendMessage(msg);
        }

        @Override
        public void onStatesChanged(int connectionStates) {
            Log.d(TAG, "connectionStates change to: " + connectionStates);
            currentState  = connectionStates;
            switch (connectionStates) {
                case ConnectionStates.STATE_CONNECTED:
                    //sensor.start();
                    Toast.makeText(getContext(), "Connected", Toast.LENGTH_SHORT).show();
                    break;
                case ConnectionStates.STATE_WORKING:
                    LinkDetectedHandler.sendEmptyMessageDelayed(1234, 5000);
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
            Message msg = LinkDetectedHandler.obtainMessage();
            msg.what = MSG_UPDATE_STATE;
            msg.arg1 = connectionStates;
            LinkDetectedHandler.sendMessage(msg);
        }

        @Override
        public void onChecksumFail(byte[] bytes, int i, int i1) {
            badPacketCount ++;
            Message msg = LinkDetectedHandler.obtainMessage();
            msg.what = MSG_UPDATE_BAD_PACKET;
            msg.arg1 = badPacketCount;
            LinkDetectedHandler.sendMessage(msg);
        }

        @Override
        public void onRecordFail(int i) {
            Log.e(TAG,"onRecordFail: " + i);
        }
    };

    private static final int MSG_UPDATE_BAD_PACKET = 1001;
    private static final int MSG_UPDATE_STATE = 1002;
    private static final int MSG_CONNECT = 1003;
    private boolean isReadFilter = false;

    private Handler LinkDetectedHandler = new Handler() {

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
                    LinkDetectedHandler.sendEmptyMessageDelayed(1237, 1000);
                    break;
                case 1236:
                    tgStreamReader.MWM15_setFilterType(FilterType.FILTER_50HZ);
                    Log.d(TAG,"MWM15_SetFilter 50HZ ");
                    LinkDetectedHandler.sendEmptyMessageDelayed(1237, 1000);
                    break;

                case 1237:
                    tgStreamReader.MWM15_getFilterType();
                    Log.d(TAG,"MWM15_getFilterType ");

                    break;

                case MindDataType.CODE_FILTER_TYPE:
                    Log.d(TAG,"CODE_FILTER_TYPE: " + msg.arg1 + "  isReadFilter: " + isReadFilter);
                    if(isReadFilter){
                        isReadFilter = false;
                        if(msg.arg1 == FilterType.FILTER_50HZ.getValue()){
                            LinkDetectedHandler.sendEmptyMessageDelayed(1235, 1000);
                        }else if(msg.arg1 == FilterType.FILTER_60HZ.getValue()){
                            LinkDetectedHandler.sendEmptyMessageDelayed(1236, 1000);
                        }else{
                            Log.e(TAG,"Error filter type");
                        }
                    }

                    break;



                case MindDataType.CODE_RAW:
                    rawText.setText(msg.arg1);
                    break;
                case MindDataType.CODE_MEDITATION:
                    Log.d(TAG, "HeadDataType.CODE_MEDITATION " + msg.arg1);
                    meditationText.setText("" +msg.arg1 );
                    break;
                case MindDataType.CODE_ATTENTION:
                    Log.d(TAG, "CODE_ATTENTION " + msg.arg1);
                    attentionText.setText("" +msg.arg1 );
                    break;
                case MindDataType.CODE_EEGPOWER:
                    EEGPower power = (EEGPower)msg.obj;
                    if(power.isValidate()){
                        deltaText.setText("" +power.delta);
                    }
                    break;
                case MindDataType.CODE_POOR_SIGNAL://
                    int poorSignal = msg.arg1;
                    Log.d(TAG, "poorSignal:" + poorSignal);
                    signalErrorsText.setText(""+msg.arg1);

                    break;
                case MSG_UPDATE_BAD_PACKET:
                    //tv_badpacket.setText("" + msg.arg1);

                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    //TODO: initialize connection and start receiving data.
    private void resetValues(){
        meditationText.setText("N/A");
        attentionText.setText("N/A");
        deltaText.setText("N/A");
        rawText.setText("N/A");
        signalErrorsText.setText("N/A");
    }

    private boolean prepareBtInterface(){
        if(TgReaderSingleton.getInstance().getDevice() != null){
            BluetoothDevice bd = BluetoothAdapter.getDefaultAdapter()
                    .getRemoteDevice(TgReaderSingleton.getInstance().getDevice().getAddress());

            if(tgStreamReader == null){
                tgStreamReader = new TgStreamReader(bd,callback);
                tgStreamReader.startLog();
            }else{
                tgStreamReader.changeBluetoothDevice(bd);
                tgStreamReader.setTgStreamHandler(callback);
            }
            return true;
        }
        else{
            Toast.makeText(getContext(), "Please choose the device first.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    @Override
    public void onDestroy() {
        if(tgStreamReader != null){
            tgStreamReader.stop();
            tgStreamReader.close();
            tgStreamReader = null;
        }
        super.onDestroy();
    }
}
