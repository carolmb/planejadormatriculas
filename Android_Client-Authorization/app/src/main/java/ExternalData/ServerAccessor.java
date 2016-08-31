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
    Requirements getRequirements(String idRequirements, final Context context);
    Student getStudent(String idStudent);
    Component getComponent(String code);
    ComponentClass getComponentClass();
}
