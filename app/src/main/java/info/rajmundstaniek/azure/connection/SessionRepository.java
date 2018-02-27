package info.rajmundstaniek.azure.connection;

import java.util.Dictionary;
import java.util.List;
import java.util.concurrent.Executor;

import info.rajmundstaniek.azure.model.Horn;
import info.rajmundstaniek.azure.model.Reading;
import info.rajmundstaniek.azure.model.Session;
import info.rajmundstaniek.azure.model.Student;

/**
 * Created by rajmu on 27.02.2018.
 */

public class SessionRepository implements ISessionRepository {
    @Override
    public void setClient() {

    }

    @Override
    public void dbEndSession() {

    }

    @Override
    public List<Session> dbGetAllSessions(boolean onlyActive) {
        return null;
    }

    @Override
    public Session dbFindSession(String sessionId) {
        return null;
    }

    @Override
    public List<Reading> getReadingForSession(String sessionId) {
        return null;
    }

    @Override
    public void dbCreateSession(Session session) {

    }

    @Override
    public void dbAddHorn(Session session, Horn horn) {

    }

    @Override
    public void addUser(Session session, Student student) {

    }

    @Override
    public Executor createTimestampTriggerAsync() {
        return null;
    }

    @Override
    public Executor createTImestampTriggerEndAsync() {
        return null;
    }

    @Override
    public Executor createTimestampTriggerHornAsync() {
        return null;
    }

    @Override
    public Executor checkForHornAsync(Dictionary<String, String> checkpoints) {
        return null;
    }
}
