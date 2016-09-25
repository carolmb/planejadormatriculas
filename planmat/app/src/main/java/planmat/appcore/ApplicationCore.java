package planmat.appcore;

import android.app.Activity;

import com.android.volley.Response;

import planmat.datarepresentation.*;
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

    public void requestMajorList(final Response.Listener<IDList> listener) {
        serverAccessor.getMajorList(listener);
    }

    public void requestRequirementsList(final Response.Listener<IDList> listener, int majorID) {
        serverAccessor.getRequirementsList(listener, majorID);
    }

    public void requestRequirements(final Response.Listener<Requirements> listener, int id) {
        serverAccessor.getRequirements(listener, id);
    }

    public void requestComponent(final Response.Listener<Component> listener, String code) {
        // TODO
    }

}
