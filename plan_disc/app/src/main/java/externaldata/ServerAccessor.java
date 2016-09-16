package externaldata;

import com.android.volley.Response;

import datarepresentation.Requirements;

/**
 * Created by Ana Caroline on 03/09/2016.
 */
public interface ServerAccessor {
    abstract void getRequirements(final Response.Listener<String> finalAction);
    abstract void getStudent(final Response.Listener<String> finalAction);
    //abstract void getComponent(String code);
    //abstract void getComponentClass();
}
