package planmat.externaldata;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.webkit.WebView;

import com.android.volley.Response;
import com.android.volley.toolbox.RequestFuture;
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

class SIGAAAuthorizationRequester {

    private Credential credential;

    private String clientId = "plan-mat-id";
    private String clientSecret = "segredo";

    private OAuthManager oauth;

    public String getAccessToken() {
        return credential.getAccessToken();
    }

    public String createTokenCredential(final Activity activity) {
        createOAuth("http://apitestes.info.ufrn.br/authz-server", activity);
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
            OAuthManager.OAuthFuture<Credential> future = oauth.authorizeExplicitly("userId", callback, null);
            credential = future.getResult();
            return credential.getAccessToken();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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
                return "http://localhost/Callback";
            }

            @Override
            public boolean isJavascriptEnabledForWebView() {
                return true;
            }
        };

        oauth = new OAuthManager(flow, controller);
    }

    public void logout(Context context, String url) {
        WebView w= new WebView(context);
        w.loadUrl(url);
        credential = null;
    }

}


