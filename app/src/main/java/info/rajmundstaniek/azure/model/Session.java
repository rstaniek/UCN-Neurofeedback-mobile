package info.rajmundstaniek.azure.model;

import com.microsoft.azure.documentdb.Document;

import java.util.List;

/**
 * Created by rajmu on 27.02.2018.
 */

public class Session extends Document implements Serializable<Session> {

    private String name;
    private long dateCreated;
    private long dateFinished;
    private boolean isAlive;
    private List<Student> users;
    private List<Horn> horns;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(long dateCreated) {
        this.dateCreated = dateCreated;
    }

    public long getDateFinished() {
        return dateFinished;
    }

    public void setDateFinished(long dateFinished) {
        this.dateFinished = dateFinished;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public List<Student> getUsers() {
        return users;
    }

    public void setUsers(List<Student> users) {
        this.users = users;
    }

    public List<Horn> getHorns() {
        return horns;
    }

    public void setHorns(List<Horn> horns) {
        this.horns = horns;
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
    public String serialize(Session obj) {
        //TODO: figure out serializing lists of objects
        return null;
    }

    @Override
    public Session cast(String jsonObject) {
        //TODO: Figure out proper casting to an object
        return null;
    }
}
