package info.rajmundstaniek.neurofeedback.service;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.widget.Toast;

import info.rajmundstaniek.neurofeedback.R;
import info.rajmundstaniek.neurofeedback.businessLogic.TgReaderSingleton;

/**
 * Created by rajmu on 08.02.2018.
 */

public class NeuroReceiverService extends IntentService {

    public enum ACTION{
        HEADSET_UPDATE,
        SERVICE_CONNECT,
        SERVICE_DISCONNECT
    }

    private Handler uiHandler;

    public NeuroReceiverService() {
        super("UCN-Neuro-background-service");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(intent != null){
            synchronized (this){
                try{
                    for(int i = 0; i < 10; i++){
                        wait(2000);
                        TgReaderSingleton.getInstance().getNeuroEventDispatcher().post(this, null);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //TODO: do sth
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
        super.onDestroy();
    }

    class HeadsetServiceReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }
}
