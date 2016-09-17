package planmat.appcore;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.android.volley.Response;

import planmat.datarepresentation.Student;
import planmat.externaldata.ServerAccessor;
import planmat.externaldata.SIGAAServerAccessor;

public class ApplicationCore {

    private static ApplicationCore instance = new ApplicationCore();
    private ServerAccessor serverAccessor;
    private Student currentUser;

    private ApplicationCore() {
        serverAccessor = new SIGAAServerAccessor();
    }

    public static ApplicationCore getInstance() {
        return instance;
    }

    public Student getCurrentUser() {
        return currentUser;
    }

    public void login(final Activity activity, final Intent i) {
        final Response.Listener<String> studentListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // TODO: transformar string json em um student
                currentUser = null;
                Log.d("Response (user)", response);
                activity.startActivity(i);
            }
        };
        final Response.Listener<String> credentialListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                serverAccessor.getStudent(studentListener);
            }
        };
        serverAccessor.login(activity, credentialListener);
    }

    public void logout(final Activity activity) {
        serverAccessor.logout(activity);
    }

    public void getRequirements(Response.Listener<String> listener) {
        serverAccessor.getRequirements(listener);
    }

}
