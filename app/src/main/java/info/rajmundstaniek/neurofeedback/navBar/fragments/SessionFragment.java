package info.rajmundstaniek.neurofeedback.navBar.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import info.rajmundstaniek.neurofeedback.R;

/**
 * Created by rajmu on 26.02.2018.
 */

public class SessionFragment extends Fragment {

    private ListView sessionList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_session, null);


        return view;
    }
}
