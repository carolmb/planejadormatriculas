package planmat.appcore;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.android.volley.Response;

import planmat.datarepresentation.Requirements;
import planmat.datarepresentation.Student;
import planmat.datarepresentation.User;
import planmat.externaldata.ServerAccessor;
import planmat.externaldata.SIGAAServerAccessor;

public class ApplicationCore {

    private static ApplicationCore instance = new ApplicationCore();
    private ServerAccessor serverAccessor;

    private ApplicationCore() {
        serverAccessor = new SIGAAServerAccessor();
    }

    public static ApplicationCore getInstance() {
        return instance;
    }

    public void login(final Activity activity, final Response.Listener<User> finalListener) {
        final Response.Listener<String> credentialListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                serverAccessor.getUser(finalListener);
            }
        };
        serverAccessor.login(activity, credentialListener);
    }

    public void logout(final Activity activity) {
        serverAccessor.logout(activity);
    }

    public void requestRequirements(final Response.Listener<Requirements> listener) {
        serverAccessor.getRequirements(listener);
    }

}
