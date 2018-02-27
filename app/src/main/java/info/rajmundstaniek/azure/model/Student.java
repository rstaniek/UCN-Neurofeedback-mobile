package info.rajmundstaniek.azure.model;

import com.google.gson.Gson;

/**
 * Created by rajmu on 27.02.2018.
 */

public class Student implements Serializable<Student> {

    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String serialize(Student obj) {
        return new Gson().toJson(obj, Student.class);
    }

    @Override
    public Student cast(String jsonObject) {
        return new Gson().fromJson(jsonObject, Student.class);
    }
}
