package appcore;

import android.app.Activity;
import android.content.Intent;

import com.android.volley.Response;

import datarepresentation.Requirements;
import externaldata.*;

/**
 * Created by User on 03/09/2016.
 */
public class ApplicationCore {
    private static ApplicationCore instance = new ApplicationCore();

    private ApplicationCore() {}

    public static ApplicationCore getInstance() {
        return instance;
    }

    public void login(final Activity activity, final Intent i) {
        SIGAADataRequester.getInstance().createRequestQueue(activity);
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                activity.startActivity(i);
            }
        };
        SIGAAAuthorizationRequester.getInstance().createTokenCredential(activity, listener);
    }

    public void logout(final Activity activity) {
        SIGAAAuthorizationRequester.getInstance().logout(activity, "http://apitestes.info.ufrn.br/sso-server/logout");
    }

    public void getRequirements(Response.Listener<String> listener) {
        SIGAAServerAccessor.getInstance().getRequirements(listener);
    }

    public void getStudent() {}

}
