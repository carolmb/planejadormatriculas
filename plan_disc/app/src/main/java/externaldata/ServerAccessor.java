package externaldata;

import com.android.volley.Response;

import datarepresentation.Requirements;

/**
 * Created by Ana Caroline on 03/09/2016.
 */
public interface ServerAccessor {
    abstract void getRequirements(final Response.Listener<Requirements> finalAction, final String nameMajor, final int year, final int semester);
    abstract void getStudent(String idStudent);
    abstract void getComponent(String code);
    abstract void getComponentClass();
}
