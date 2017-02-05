package edu.cornell.tech.foundry.ohmageomhsdk;


import edu.cornell.tech.foundry.ohmclient.OMHDataPoint;

public class OhmageOMHManager {

    private static OhmageOMHManager manager;

    public static synchronized OhmageOMHManager getInstance() {
        if (manager == null) {
            manager = new OhmageOMHManager();
        }
        return manager;
    }

    public interface Completion {
        void onCompletion(Error error);
    }

    public void addDatapoint(OMHDataPoint datapoint, Completion completion) {

        completion.onCompletion(null);

    }

}