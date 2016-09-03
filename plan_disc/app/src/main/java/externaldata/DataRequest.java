package externaldata;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.webkit.WebView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.json.jackson.JacksonFactory;
import com.wuman.android.auth.AuthorizationFlow;
import com.wuman.android.auth.AuthorizationUIController;
import com.wuman.android.auth.DialogFragmentController;
import com.wuman.android.auth.OAuthManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ana Caroline on 04/11/2015.
 */
public class DataRequest {

    private Credential credential;

    private static DataRequest DataRequest;

    private static RequestQueue requestQueue;

    private static String clientId = "plan-mat-id";
    private static String clientSecret = "segredo";

    private OAuthManager oauth;

    public static DataRequest getInstance(){
        if(DataRequest == null)
            DataRequest = new DataRequest();

        return DataRequest;
    }

    private DataRequest() {}

    public Credential createTokenCredential(final Activity activity, final Intent i) {
        createOAuth("http://apitestes.info.ufrn.br/authz-server", activity);
        createRequestQueue(activity);
        try {
            OAuthManager.OAuthCallback<Credential> callback = new OAuthManager.OAuthCallback<Credential>() {
                @Override public void run(OAuthManager.OAuthFuture<Credential> future) {
                    try {
                        credential = future.getResult();
                        activity.startActivity(i);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            oauth.authorizeExplicitly("userId", callback, null);
            if(credential != null){
                Log.d("TOKEN", credential.getAccessToken());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return credential;
    }

    private void createOAuth(String oauthServerURL, Activity activity) {

        AuthorizationFlow.Builder builder = new AuthorizationFlow.Builder(
                BearerToken.authorizationHeaderAccessMethod(),
                AndroidHttp.newCompatibleTransport(),
                new JacksonFactory(),
                new GenericUrl(oauthServerURL +"/oauth/token"),
                new ClientParametersAuthentication(clientId, clientSecret),
                clientId,
                oauthServerURL +"/oauth/authorize");

        AuthorizationFlow flow = builder.build();

        AuthorizationUIController controller = new DialogFragmentController(activity.getFragmentManager()) {
            @Override
            public String getRedirectUri() throws IOException {
                //return "http://android.local/";
                return "http://localhost/Callback";
            }

            @Override
            public boolean isJavascriptEnabledForWebView() {
                return true;
            }
        };

        oauth = new OAuthManager(flow, controller);
    }

    private void createRequestQueue(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

    public void resourceRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, listener, errorListener) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                String auth = "Bearer "+ getCredential().getAccessToken();
                headers.put("Authorization", auth);
                return headers;
            }
        };

        // Add the request to the RequestQueue.
        requestQueue.add(stringRequest);
    }

    private Credential getCredential() {
        //TODO: implementar refresh
        return credential;
    }

    public void logout(Context context, String url) {
        WebView w= new WebView(context);
        w.loadUrl(url);
        credential = null;
    }

    public void requestJson(final ActionRequest action, String url) {
        resourceRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                action.run(response);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("OUT", "Error: " + error.getMessage());
            }
        });
    }
}


