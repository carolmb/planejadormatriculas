package planmat.externaldata;

import android.app.Activity;

import com.android.volley.Response;

import planmat.datarepresentation.*;

/**
 * Created by Ana Caroline on 03/09/2016.
 */
public interface ServerAccessor {
    void login(Activity activity, final Response.Listener<String> finalListener);
    void logout(Activity activity);
    void getUser(final Response.Listener<User> finalListener);
    void getMajorList(final Response.Listener<IDList> finalListener);
    void getRequirementsList(final Response.Listener<IDList> finalListener, int majorID);
    void getRequirements(final Response.Listener<Requirements> finalListener, int id);
    void getClassList(final Response.Listener<ClassList> finalListener, final String code);
    void getStatList(final Response.Listener<StatList> finalListener, final String code);
    void getInstitutionalRatingByProfessor(final Response.Listener<String> finalListener,
                                           final int institutionalCode, final int year, final int semester);
    void getTestDate(final Response.Listener<String> finalListener, final String classID);
    void getSurvey(final Response.Listener<String> finalListener, final String classID);
    void getHomeworkList(final Response.Listener<String> finalListener, final String classID);
    void getStudentCalendar(final Response.Listener<String> finalListener, final String studentID);
    void getRegistrationStatement(final Response.Listener<String> finalListener, final String studentID);
    void getAcademicRecord(final Response.Listener<String> finalListener, final String studentID);
}