package info.rajmundstaniek.azure.connection;

import java.util.Dictionary;
import java.util.concurrent.Callable;

/**
 * Created by rajmu on 27.02.2018.
 */

public class HornCheckpointsCallable implements Callable<Dictionary<String, String>> {

    private Dictionary<String, String> checkpoints;

    public HornCheckpointsCallable(Dictionary<String, String> checkpoints) {
        this.checkpoints = checkpoints;
    }

    @Override
    public Dictionary<String, String> call() throws Exception {
        //TODO: implement callable call() method for horn checkpoints
        return null;
    }
}
