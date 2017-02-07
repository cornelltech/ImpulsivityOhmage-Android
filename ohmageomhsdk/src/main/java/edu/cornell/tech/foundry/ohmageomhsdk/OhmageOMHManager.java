package edu.cornell.tech.foundry.ohmageomhsdk;


import android.support.annotation.Nullable;

import edu.cornell.tech.foundry.ohmageomhsdk.Exceptions.OhmageOMHAlreadySignedIn;
import edu.cornell.tech.foundry.ohmageomhsdk.Exceptions.OhmageOMHException;
import edu.cornell.tech.foundry.ohmageomhsdk.Exceptions.OhmageOMHInvalidSample;
import edu.cornell.tech.foundry.ohmageomhsdk.Exceptions.OhmageOMHNotSignedIn;
import edu.cornell.tech.foundry.ohmclient.OMHClient;
import edu.cornell.tech.foundry.ohmclient.OMHClientSignInResponse;
import edu.cornell.tech.foundry.ohmclient.OMHDataPoint;

public class OhmageOMHManager {

    private static OhmageOMHManager manager = null;
    private static Object managerLock = new Object();

    private String accessToken;
    private String refreshToken;
    private Object credentialsLock;

    private OMHClient client;

    @Nullable
    public static OhmageOMHManager getInstance() {
        synchronized (managerLock) {
            return manager;
        }
    }

    public static void config(String baseURL, String clientID, String clientSecret) {
        synchronized (managerLock) {
            if (manager == null) {
                manager = new OhmageOMHManager(baseURL, clientID, clientSecret);
            }
        }
    }

    private OhmageOMHManager(String baseURL, String clientID, String clientSecret) {

        this.client = new OMHClient(baseURL, clientID, clientSecret);

        this.credentialsLock = new Object();
        this.accessToken = null;
        this.refreshToken = null;

    }

    public boolean isSignedIn() {
        synchronized (this.credentialsLock) {
            return this.refreshToken != null && !this.refreshToken.isEmpty();
        }
    }

    private void setCredentials(String accessToken, String refreshToken) {
        synchronized (this.credentialsLock) {
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        }
    }



//    public static synchronized OhmageOMHManager getInstance() {
//        if (manager == null) {
//            manager = new OhmageOMHManager();
//        }
//        return manager;
//    }

    public interface Completion {
        void onCompletion(Exception e);
    }

    public void addDatapoint(OMHDataPoint datapoint, Completion completion) {

        if (!this.isSignedIn()) {
            completion.onCompletion(new OhmageOMHNotSignedIn());
            return;
        }

        if (!this.client.validateSample(datapoint)) {
            completion.onCompletion(new OhmageOMHInvalidSample());
            return;
        }

        String localAccessToken;
        synchronized (this.credentialsLock) {
            localAccessToken = this.accessToken;
        }

        this.client.postSample(datapoint.toJson(), localAccessToken, new OMHClient.PostSampleCompletion() {
            @Override
            public void onCompletion(boolean success, Exception e) {

                if (e != null) {

                }

                //

            }
        });

        completion.onCompletion(null);

    }

    public void signIn(String username, String password, final Completion completion) {

        if (this.isSignedIn()) {
            completion.onCompletion(new OhmageOMHAlreadySignedIn());
            return;
        }

        this.client.signIn(username, password, new OMHClient.AuthCompletion() {
            @Override
            public void onCompletion(OMHClientSignInResponse response, Exception e) {
                if (e != null) {
                    completion.onCompletion(e);
                    return;
                }

                if (response != null) {
                    setCredentials(response.getAccessToken(), response.getRefreshToken());
                }

                completion.onCompletion(null);
                return;
            }
        });

    }

    public void signOut(final Completion completion) {
        completion.onCompletion(null);
    }

}