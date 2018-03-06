package info.rajmundstaniek.azure.connection;

import com.microsoft.azure.documentdb.DocumentClientException;

import java.util.Dictionary;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

import info.rajmundstaniek.azure.model.Horn;
import info.rajmundstaniek.azure.model.Reading;
import info.rajmundstaniek.azure.model.Session;
import info.rajmundstaniek.azure.model.Student;

/**
 * Created by rajmu on 27.02.2018.
 */

public interface ISessionRepository {

    void setClient();
    void dbEndSession(Session session) throws DocumentClientException;
    List<Session> dbGetAllSessions(boolean onlyActive);
    Session dbFindSession(String sessionId);

    List<Reading> getReadingForSession(String sessionId);

    void dbCreateSession(Session session);
    void dbAddHorn(Session session, Horn horn);
    void addUser(Session session, Student student);

    Executor createTimestampTriggerAsync();
    Executor createTImestampTriggerEndAsync();
    Executor createTimestampTriggerHornAsync();

    Executor checkForHornAsync(Dictionary<String, String> checkpoints);
}
