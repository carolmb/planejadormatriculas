package planmat.appcore;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import planmat.custom.CustomFactory;
import planmat.datarepresentation.*;
import planmat.externaldata.ServerAccessor;
import planmat.internaldata.DatabaseAccessor;

public class ApplicationCore {

    private static ApplicationCore instance = new ApplicationCore();

    private Recommender recommender;

    private User userCache;
    private Requirements requirementsCache;
    private HashMap<String, StatList> statCache;
    private HashMap<String, ClassList> classCache;

    private ApplicationCore() {
        this.recommender = CustomFactory.getInstance().getRecommender();
        statCache = new HashMap<>();
        classCache = new HashMap<>();
    }

    public Recommender getRecommender() {
        return recommender;
    }

    public static ApplicationCore getInstance() {
        return instance;
    }

    public void login(Activity activity) {
        DatabaseAccessor.getInstance().login(activity);
    }

    public User getUser() {
        return DatabaseAccessor.getInstance().getUser();
    }

    public IDList getMajorList() {
        return DatabaseAccessor.getInstance().getMajorList();
    }

    public void getMajorList(final Handler handler) {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                Message msg = new Message();
                msg.obj = getMajorList();
                handler.sendMessage(msg);
            }
        });
        thread.start();
    }

    public IDList getRequirementsList(String majorID) {
        return DatabaseAccessor.getInstance().getRequirementsList(majorID);
    }

    public void getRequirementsList(final Handler handler, final String majorID) {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                Message msg = new Message();
                msg.obj = getRequirementsList(majorID);
                handler.sendMessage(msg);
            }
        });
        thread.start();
    }

    public Requirements getRequirements(String id) {
        requirementsCache = DatabaseAccessor.getInstance().getRequirements(id);
        return requirementsCache;
    }

    public void getRequirements(final Handler handler, final String id) {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                Message msg = new Message();
                msg.obj = getRequirements(id);
                handler.sendMessage(msg);
            }
        });
        thread.start();
    }

    public ClassList getClassList(String code) {
        ClassList list = classCache.get(code);
        if (list == null) {
            list = DatabaseAccessor.getInstance().getClassList(code);
            classCache.put(code, list);
        }
        return list;
    }

    public StatList getStatList(String code) {
        StatList list = statCache.get(code);
        if (list == null) {
            list = DatabaseAccessor.getInstance().getStatList(code);
            statCache.put(code, list);
        }
        return list;
    }

    public Component getComponent(String code) {
        return requirementsCache.getComponent(code);
    }

}
