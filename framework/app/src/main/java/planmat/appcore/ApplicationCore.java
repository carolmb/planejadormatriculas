package planmat.appcore;

import android.app.Activity;

import com.android.volley.Response;

import planmat.datarepresentation.*;
import planmat.externaldata.ServerAccessor;
import planmat.externaldata.SIGAAServerAccessor;
import planmat.internaldata.UserPrefs;

public class ApplicationCore {

    // private static ApplicationCore instance = new ApplicationCore();
    protected ServerAccessor serverAccessor;

    protected ApplicationCore(ServerAccessor serverAccessor) {
        this.serverAccessor = serverAccessor;
    }

    // public static ApplicationCore getInstance() {return instance;}

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

}
