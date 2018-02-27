package info.rajmundstaniek.azure.model;

import com.google.gson.Gson;

/**
 * Created by rajmu on 27.02.2018.
 */

public interface Serializable<T> {
    String serialize(T obj);
    T cast(String jsonObject);
}
