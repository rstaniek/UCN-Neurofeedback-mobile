package info.rajmundstaniek.azure.model;

import com.google.gson.Gson;
import com.microsoft.azure.documentdb.Document;

import java.sql.Date;

/**
 * Created by rajmu on 27.02.2018.
 */

public class Reading extends Document implements Serializable<Reading> {

    private String sessionId;
    private String userId;
    private int meditation;
    private int attention;
    private int signalStrength;
    private Date readingTime;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getMeditation() {
        return meditation;
    }

    public void setMeditation(int meditation) {
        this.meditation = meditation;
    }

    public int getAttention() {
        return attention;
    }

    public void setAttention(int attention) {
        this.attention = attention;
    }

    public int getSignalStrength() {
        return signalStrength;
    }

    public void setSignalStrength(int signalStrength) {
        this.signalStrength = signalStrength;
    }

    public Date getReadingTime() {
        return readingTime;
    }

    public void setReadingTime(Date readingTime) {
        this.readingTime = readingTime;
    }

    @Override
    public String getId() {
        return super.getId();
    }

    @Override
    public void setId(String id) {
        super.setId(id);
    }

    @Override
    public String serialize(Reading obj) {
        return new Gson().toJson(obj, Reading.class);
    }

    @Override
    public Reading cast(String jsonObject) {
        return new Gson().fromJson(jsonObject, Reading.class);
    }
}
