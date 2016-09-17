package planmat.externaldata;

import android.app.Activity;

import com.android.volley.Response;

/**
 * Created by Ana Caroline on 03/09/2016.
 */
public interface ServerAccessor {
    void login(Activity activity, final Response.Listener<String> finalListener);
    void logout(Activity activity);
    void getRequirements(final Response.Listener<String> finalListener);
    void getStudent(final Response.Listener<String> finalListener);
}
