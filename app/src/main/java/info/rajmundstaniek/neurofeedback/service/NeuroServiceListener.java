package info.rajmundstaniek.neurofeedback.service;

/**
 * Created by rajmu on 08.02.2018.
 */

public interface NeuroServiceListener {
    void OnMessageReceived(Object sender, NeuroEventArgs e);
}
