package info.rajmundstaniek.neurofeedback.navBar.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import info.rajmundstaniek.neurofeedback.R;
import info.rajmundstaniek.neurofeedback.businessLogic.TgReaderSingleton;
import info.rajmundstaniek.neurofeedback.service.NeuroEventArgs;
import info.rajmundstaniek.neurofeedback.service.NeuroServiceListener;

/**
 * Created by rajmu on 07.02.2018.
 */

public class ChartsFragment extends Fragment implements NeuroServiceListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        //subscribe to custom event
        TgReaderSingleton.getInstance().getNeuroEventDispatcher().add(this);

        return inflater.inflate(R.layout.fragment_charts, null);
    }

    @Override
    public void OnMessageReceived(Object sender, NeuroEventArgs e) {
        //TODO: handle TgStream messages from the background service
        Toast.makeText(getActivity(), "event test", Toast.LENGTH_SHORT).show();
    }

    //TODO: initialize connection and start receiving data.
}
