package planmat.externaldata;

import android.app.Activity;

import com.android.volley.Response;

import planmat.datarepresentation.Requirements;
import planmat.datarepresentation.Student;
import planmat.datarepresentation.User;

/**
 * Created by Ana Caroline on 03/09/2016.
 */
public interface ServerAccessor {
    void login(Activity activity, final Response.Listener<String> finalListener);
    void logout(Activity activity);
    void getUser(final Response.Listener<User> finalListener);
    void getRequirements(final Response.Listener<Requirements> finalListener);
    void getStudent(final Response.Listener<Student> finalListener);
}
