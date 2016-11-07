package planmat.externaldata;

import android.app.Activity;

import com.android.volley.Response;

import planmat.datarepresentation.*;

/**
 * Created by Ana Caroline on 03/09/2016.
 */
public interface ServerAccessor {
    User login(Activity activity);
    void logout(Activity activity);
    IDList getMajorList();
    IDList getRequirementsList(int majorID);
    Requirements getRequirements(int id);
    Component getComponent(final String code);
    ClassList getClassList(final String code);
    StatList getStatList(final String code);
}
