package info.rajmundstaniek.neurofeedback.service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rajmu on 08.02.2018.
 */

public class NeuroEventDispatcher {
    private List<NeuroServiceListener> listeners = new ArrayList<>();

    public void add(NeuroServiceListener e){
        listeners.add(e);
    }

    public void post(Object sender, NeuroEventArgs e){
        for (NeuroServiceListener listener : listeners){
            listener.OnMessageReceived(sender, e);
        }
    }
}
