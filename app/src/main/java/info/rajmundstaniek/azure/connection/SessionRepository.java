package info.rajmundstaniek.azure.connection;

import com.microsoft.azure.documentdb.ConnectionPolicy;
import com.microsoft.azure.documentdb.ConsistencyLevel;
import com.microsoft.azure.documentdb.Database;
import com.microsoft.azure.documentdb.Document;
import com.microsoft.azure.documentdb.DocumentClient;
import com.microsoft.azure.documentdb.DocumentClientException;
import com.microsoft.azure.documentdb.DocumentCollection;
import com.microsoft.azure.documentdb.RequestOptions;

import java.util.ArrayList;
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

    public static DocumentClient client;
    public Database db;
    public DocumentCollection sessions, readings;

    public SessionRepository() throws DocumentClientException {
        setClient();
        ensureDataStorage();
    }

    private void ensureDataStorage() throws DocumentClientException {
        dbCreateDatabase();
        readings = createCollection("Readings");
        sessions = createCollection("Sessions");
    }

    private DocumentCollection createCollection(String s) throws DocumentClientException {
        DocumentCollection newCol = client.queryCollections(db.getSelfLink(), s, null)
                .getQueryIterator().next();

        if(newCol == null){
            DocumentCollection d = new DocumentCollection();
            d.setId(s);
            newCol = client.createCollection(db.getSelfLink(), d, null).getResource();
            System.out.println("created new collection: " + newCol.getId());
        }
        else {
            System.out.println("collection already exists: " + newCol.getId());
        }
        return newCol;
    }

    private void dbCreateDatabase() throws DocumentClientException {
        String databaseId = "neurofeedback";

        Database newDb = client.readDatabases(null).getQueryIterator().next();

        if(newDb == null){
            Database d = new Database();
            d.setId(databaseId);
            newDb = client.createDatabase(d, null).getResource();
            System.out.println("created new database: " + newDb.getId());
        }
        else{
            System.out.println("database already exists: " + newDb.getId());
        }
    }

    @Override
    public void setClient() {
        if(client == null){
            client = new DocumentClient(Connection.endpointURI, Connection.primaryKey,
                    ConnectionPolicy.GetDefault(), ConsistencyLevel.Session);
        }
    }

    @Override
    public void dbEndSession(Session session) throws DocumentClientException {
        if(session.isAlive()){
            session.setAlive(false);
            RequestOptions opts = new RequestOptions();
            List<String> optParams = new ArrayList<>();
            optParams.add("SetTimeStampEnd");
            opts.setPreTriggerInclude(optParams);
            client.replaceDocument(null /*TODO not sure what to write there. C# project equivalent SessionRepository.cs line 104 */, opts);
        }
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
