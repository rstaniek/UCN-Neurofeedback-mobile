package info.rajmundstaniek.azure.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.sql.Date;

/**
 * Created by rajmu on 27.02.2018.
 */

public class Horn implements Serializable<Horn> {

    @SerializedName("dateHornCreated")
    private Date dateCreated;

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public String serialize(Horn obj) {
        return new Gson().toJson(obj, Horn.class);
    }

    @Override
    public Horn cast(String jsonObject) {
        return new Gson().fromJson(jsonObject, Horn.class);
    }
}
