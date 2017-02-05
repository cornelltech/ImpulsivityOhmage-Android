package edu.cornell.tech.foundry.ohmclient;

import android.util.Base64;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by jameskizer on 2/4/17.
 */
public class OMHClient {

    final static String TAG = OMHClient.class.getSimpleName();

    public interface SignInCompletion {
        void onCompletion(OMHClientSignInResponse response, Exception e);
    }

    private final static OkHttpClient client = new OkHttpClient();

    private String baseURL;
    private String clientID;
    private String clientSecret;

    private String getBasicAuthString(String clientID, String clientSecret) {
        return Base64.encodeToString((clientID + ":" + clientSecret).getBytes(), Base64.NO_WRAP);
    }

    public OMHClient(String baseURL, String clientID, String clientSecret) {
        this.baseURL = baseURL;
        this.clientID = clientID;
        this.clientSecret = clientSecret;
    }

    public void signIn(String username, String password, final SignInCompletion completion) {

        RequestBody body = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .add("grant_type", "password")
                .build();

        Request request = new Request.Builder()
                .header("Authorization", "Basic " + this.getBasicAuthString(this.clientID, this.clientSecret))
                .url(this.baseURL + "/oauth/token")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (completion != null) {
                    completion.onCompletion(null, e);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response != null && response.isSuccessful()) {

                    String responseBody = "";
                    try {
                        responseBody = response.body().string();
                        JSONObject responseJson = new JSONObject(responseBody);

                        String accessToken = responseJson.getString("access_token");
                        String refreshToken = responseJson.getString("refresh_token");

                        if (accessToken != null && refreshToken != null) {
                            completion.onCompletion(new OMHClientSignInResponse(refreshToken, accessToken), null);
                        }

                        else {
                            completion.onCompletion(null, null);
                        }

                    } catch (JSONException e) {
                        Log.e(TAG, "Fail to parse response from omh-sign-in endpoint:" + responseBody, e);
                        completion.onCompletion(null, e);
                    }

                }
            }
        });
    }
}
