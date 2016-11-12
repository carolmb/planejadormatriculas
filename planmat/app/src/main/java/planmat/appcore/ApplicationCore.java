package planmat.appcore;

import android.os.Handler;
import android.os.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import planmat.datarepresentation.*;
import planmat.externaldata.SIGAAServerAccessor;
import planmat.externaldata.ServerAccessor;
import planmat.datarepresentation.SIGAADataConverter;

public class ApplicationCore {

    private static ApplicationCore instance = new ApplicationCore();

    private ServerAccessor serverAccessor;
    private DataConverter dataConverter;
    private Recommender recommender;

    private Requirements requirementsCache;
    private HashMap<String, StatList> statCache;
    private HashMap<String, ClassList> classCache;

    private ApplicationCore() {
        this.serverAccessor = new SIGAAServerAccessor();
        this.dataConverter = new SIGAADataConverter();
        this.recommender = new RecommenderByDifficulty();
        statCache = new HashMap<>();
        classCache = new HashMap<>();
    }

    public ServerAccessor getServerAccessor() {
        return serverAccessor;
    }

    public DataConverter getConverter() { return dataConverter; }

    public Recommender getRecommender() {
        return recommender;
    }

    public static ApplicationCore getInstance() {
        return instance;
    }

    public User getUser() {
        JSONObject userInfo = serverAccessor.getJsonObject("UserInfo");
        String id = "";
        try {
            id = "" + userInfo.getInt("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject studentInfo = ApplicationCore.getInstance().getServerAccessor().getJsonObject("StudentInfo", id);
        return dataConverter.createUser(studentInfo, userInfo);
    }

    public IDList getMajorList() {
        JSONArray array = serverAccessor.getJsonArray("MajorList");
        return dataConverter.createMajorList(array);
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
        JSONArray array = serverAccessor.getJsonArray("RequirementsList", majorID);
        IDList list = dataConverter.createRequirementsList(array);
        return list;
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
        if (requirementsCache != null && requirementsCache.getID().equals(id))
            return requirementsCache;
        else {
            JSONArray arr = ApplicationCore.getInstance().getServerAccessor().getJsonArray("Requirements", id);
            requirementsCache = ApplicationCore.getInstance().getConverter().createRequirements(id, arr);
            return requirementsCache;
        }
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
        if (list == null){
            JSONArray classArray = serverAccessor.getJsonArray("ClassList", code);
            list = dataConverter.createClassList(classArray);
            classCache.put(code, list);
        }
        return list;
    }

    public StatList getStatList(String code) {
        StatList list = statCache.get(code);
        if (list == null) {
            JSONArray statArray = serverAccessor.getJsonArray("StatList", code);
            list = dataConverter.createStatList(statArray);
            statCache.put(code, list);
        }
        return list;
    }

    public Component getComponent(String code) {
        Component comp = null;
        if (requirementsCache != null) {
            comp = requirementsCache.getComponent(code);
        }
        if (comp == null) {
            JSONObject obj = serverAccessor.getJsonObject("Component", code);
            comp = dataConverter.createComponent(obj);
            if (requirementsCache != null) {
                requirementsCache.putComponent(code, comp);
            }
        }
        return comp;
    }

}
