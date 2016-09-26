package planmat.appcore;

import android.app.Activity;

import com.android.volley.Response;

import planmat.datarepresentation.*;
import planmat.externaldata.ServerAccessor;
import planmat.externaldata.SIGAAServerAccessor;
import planmat.internaldata.UserPrefs;

public class ApplicationCore {

    private static ApplicationCore instance = new ApplicationCore();
    private ServerAccessor serverAccessor;
    private Requirements requirements = null;

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
        if (requirements != null && requirements.getID() == id) {
            listener.onResponse(requirements);
        } else {
            serverAccessor.getRequirements(new Response.Listener<Requirements>() {
                @Override
                public void onResponse(Requirements response) {
                    requirements = response;
                    listener.onResponse(requirements);
                }
            }, id);
        }
    }

    public void requestComponent(final Response.Listener<Component> listener, final String code) {
        final Component component = requirements.getComponent(code);
        if (component.getClassList() == null) {
            final Response.Listener<ClassList> classListener = new Response.Listener<ClassList>() {
                @Override
                public void onResponse(ClassList response) {
                    component.setClassList(response);
                    final Response.Listener<StatList> statListener = new Response.Listener<StatList>() {
                        @Override
                        public void onResponse(StatList response) {
                            component.setStatList(response);
                            listener.onResponse(component);
                        }
                    };
                    serverAccessor.getStatList(statListener, code);
                }
            };
            serverAccessor.getClassList(classListener, code);
        } else {
            listener.onResponse(component);
        }
    }

    public Requirements getRequirements() {
        return requirements;
    }

}
