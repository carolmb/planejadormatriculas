package planmat.externaldata;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.webkit.WebView;

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

/**
 * Created by Luisa on 09/11/2016.
 */
public class AuthorizationRequester {

    private Credential credential;

    private String clientId;
    private String clientSecret;

    private String idString;
    private String authURL;
    private String tokenURL;
    private String logoutURL;

    private OAuthManager oauth;

    public AuthorizationRequester(String idString, String clientID, String clientSecret,
                                  String authURL, String tokenURL, String logoutURL) {
        this.idString = idString;
        this.clientId = clientID;
        this.clientSecret = clientSecret;
        this.authURL = authURL;
        this.tokenURL = tokenURL;
        this.logoutURL = logoutURL;
    }

    public String getAccessToken() {
        return credential.getAccessToken();
    }

    public String createTokenCredential(final Activity activity) {
        createOAuth(activity);
        try {
            OAuthManager.OAuthCallback<Credential> callback = new OAuthManager.OAuthCallback<Credential>() {
                @Override public void run(OAuthManager.OAuthFuture<Credential> future) {
                    try {
                        credential = future.getResult();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            OAuthManager.OAuthFuture<Credential> future = oauth.authorizeExplicitly(idString, callback, null);
            credential = future.getResult();
            Log.d("Token", credential.getAccessToken());
            return credential.getAccessToken();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void createOAuth(Activity activity) {

        AuthorizationFlow.Builder builder = new AuthorizationFlow.Builder(
                BearerToken.authorizationHeaderAccessMethod(),
                AndroidHttp.newCompatibleTransport(),
                new JacksonFactory(),
                new GenericUrl(tokenURL),
                new ClientParametersAuthentication(clientId, clientSecret),
                clientId,
                authURL);

        AuthorizationFlow flow = builder.build();

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

    public void logout(Context context) {
        WebView w= new WebView(context);
        w.loadUrl(logoutURL);
        credential = null;
    }

}
