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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by User on 26/08/2016.
 */
public class DataRequest {
    // Thread-safe OAuth 2.0 helper for accessing protected resources using an access token.
    private Credential credential;

    // The Default OAuth Authorization Server class that validates whether
    // a given HttpServletRequest is a valid OAuth Token request.
    private static DataRequest dataRequest;

    // OAuth authorization flow for an installed Android app that persists end-user credentials.
    private OAuthManager oauth;

    public static DataRequest getInstance() {
        if(dataRequest == null)
            dataRequest = new DataRequest();

        return dataRequest;
    }

    private DataRequest() {
    }

    private AuthorizationFlow getAuthFlow(String oauthServerURL, String clientId, String clientSecret) {
        AuthorizationFlow.Builder builder = new AuthorizationFlow.Builder(
                BearerToken.authorizationHeaderAccessMethod(),
                AndroidHttp.newCompatibleTransport(),
                new JacksonFactory(),
                new GenericUrl(oauthServerURL +"/oauth/token"),
                new ClientParametersAuthentication(clientId, clientSecret),
                clientId,
                oauthServerURL +"/oauth/authorize");

        return builder.build();
    }

    void createOAuthManager(String oauthServerURL, String clientId, String clientSecret, final Activity activity) {

        AuthorizationFlow flow = getAuthFlow(oauthServerURL, clientId, clientSecret);

        AuthorizationUIController controller = new DialogFragmentController(activity.getFragmentManager()) {
            @Override
            public String getRedirectUri() throws IOException {
                return "http://localhost/Callback";
            }

            @Override
            public boolean isJavascriptEnabledForWebView() {
                return true;
            }
        };

        oauth = new OAuthManager(flow, controller);
    }

    Credential getTokenCredential(final Activity activity, final Intent i) {
        try {
            OAuthManager.OAuthCallback<Credential> callback = new OAuthManager.OAuthCallback<Credential>() {
                @Override public void run(OAuthManager.OAuthFuture<Credential> future) {
                    try {
                        credential = future.getResult();
                        activity.startActivity(i);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // make API queries with credential.getAccessToken()
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

    public void resourceRequest(Context context, String url, Response.Listener<String> listener, Response.ErrorListener errorListener){
        RequestQueue queue = Volley.newRequestQueue(context);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, listener, errorListener) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                String auth = "Bearer "+ credential.getAccessToken();
                headers.put("Authorization", auth);
                return headers;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest); //por que não apenas retornar a string com resposta?
    }

    public void initializeAccess(Activity activity, Intent intent){
        createOAuthManager("http://apitestes.info.ufrn.br/authz-server", "plan-mat-id", "segredo", activity);
        getTokenCredential(activity, intent);
    }

    public void logout(Context context, String url) {
        WebView w = new WebView(context);
        w.loadUrl(url);
        credential = null;
    }
}
