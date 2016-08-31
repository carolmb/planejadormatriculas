package externaldata;

import android.content.Context;

import datarepresentation.Component;
import datarepresentation.ComponentClass;
import datarepresentation.Requirements;
import datarepresentation.Student;

/**
 * Created by User on 24/08/2016.
 */
public interface ServerAccessor {
    void getRequirements(final ActionRequest finalAction, final String nameMajor, final int year, final int semester, final Context context);
    Student getStudent(String idStudent);
    Component getComponent(String code);
    ComponentClass getComponentClass();
}
