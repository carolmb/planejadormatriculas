package planmat.appcore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    private ApplicationCore() {
        this.serverAccessor = new SIGAAServerAccessor();
        this.dataConverter = new SIGAADataConverter();
        this.recommender = new RecommenderByDifficulty();
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
        IDList list = dataConverter.createMajorList(array);
        return list;
    }

    public IDList getRequirementsList(String majorID) {
        JSONArray array = serverAccessor.getJsonArray("RequirementsList", majorID);
        IDList list = dataConverter.createRequirementsList(array);
        return list;
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

    public Component getComponent(String code) {
        if (requirementsCache != null) {
            Component comp = requirementsCache.getComponent(code);
            if (comp != null)
                return comp;
        }
        JSONObject obj = serverAccessor.getJsonObject("Component", code);
        Component comp = dataConverter.createComponent(obj);
        JSONArray classArray = serverAccessor.getJsonArray("ClassList", code);
        ClassList classList = dataConverter.createClassList(classArray);
        JSONArray statArray = serverAccessor.getJsonArray("StatList", code);
        StatList statList = dataConverter.createStatList(statArray);
        comp.setClassList(classList);
        comp.setStatList(statList);
        if (requirementsCache != null) {
            requirementsCache.putComponent(code, comp);
        }
        return comp;
    }

}
