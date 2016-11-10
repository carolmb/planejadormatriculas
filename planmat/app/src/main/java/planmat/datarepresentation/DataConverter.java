package planmat.datarepresentation;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Luisa on 10/11/2016.
 */
public interface DataConverter {

    IDList createMajorList(JSONArray array);
    IDList createRequirementsList(JSONArray array);
    Requirements createRequirements(String id, JSONArray arr);
    Component createComponent(JSONObject obj);
    StatList createStatList(JSONArray statArray);
    ClassList createClassList(JSONArray classArray);
    User createUser(JSONObject studentInfo, JSONObject loginInfo);

}
