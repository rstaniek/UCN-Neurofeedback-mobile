package info.rajmundstaniek.neurofeedback.model;

import com.neurosky.connection.EEGPower;

/**
 * Created by rajmu on 09.02.2018.
 */

public class MindWaveData {
    private int meditationLevel, attentionLevel, deltaLevel;
    private EEGPower eegPower;

    public MindWaveData(int meditationLevel, int attentionLevel, int deltaLevel) {
        this.meditationLevel = meditationLevel;
        this.attentionLevel = attentionLevel;
        this.deltaLevel = deltaLevel;
    }

    public MindWaveData(EEGPower eegPower) {
        this.eegPower = eegPower;
    }

    public int getMeditationLevel() {
        return meditationLevel;
    }

    public void setMeditationLevel(int meditationLevel) {
        this.meditationLevel = meditationLevel;
    }

    public int getAttentionLevel() {
        return attentionLevel;
    }

    public void setAttentionLevel(int attentionLevel) {
        this.attentionLevel = attentionLevel;
    }

    public int getDeltaLevel() {
        return deltaLevel;
    }

    public void setDeltaLevel(int deltaLevel) {
        this.deltaLevel = deltaLevel;
    }

    public EEGPower getEegPower() {
        return eegPower;
    }

    public void setEegPower(EEGPower eegPower) {
        this.eegPower = eegPower;
    }
}
