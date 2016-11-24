package planmat.externaldata;

import android.app.Activity;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import planmat.datarepresentation.*;

/**
 * Created by Ana Caroline on 03/09/2016.
 */
public abstract class ServerAccessor {

    protected String serverURL;

    protected DataRequester dataRequester;
    protected AuthorizationRequester authorizationRequester;

    protected HashMap<String, String> urlMap;

    protected HashMap<String, JSONObject> objectCache;
    protected HashMap<String, JSONArray> arrayCache;

    public ServerAccessor(String serverURL, DataRequester dr, AuthorizationRequester ar) {
        this.serverURL = serverURL;
        urlMap = new HashMap<>();
        objectCache = new HashMap<>();
        arrayCache = new HashMap<>();
        dataRequester = dr;
        authorizationRequester = ar;
    }

    public abstract User getUser();
    public abstract IDList getMajorList();
    public abstract IDList getRequirementsList(String code);
    public abstract Requirements getRequirements(String code);
    public abstract ClassList getClassList(String code);
    public abstract StatList getStatList(String code);

    public void login(Activity activity) {
        dataRequester.createRequestQueue(activity);
        authorizationRequester.createTokenCredential(activity);
    }

    public void logout(Activity activity) {
        authorizationRequester.logout(activity);
    }

    public String getJsonString(String url) {
        return dataRequester.requestData(authorizationRequester.getAccessToken(), serverURL + url);
    }

    public JSONObject getJsonObject(String path) {
        return getJsonObject(path, "");
    }

    public JSONObject getJsonObject(String path, String arg) {
        JSONObject obj = objectCache.get(path + arg);
        if (obj != null)
            return obj;
        else {
            String url = urlMap.get(path) + arg;
            String json = getJsonString(url);
            try {
                obj = new JSONObject(json);
                objectCache.put(path + arg, obj);
                return obj;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public JSONArray getJsonArray(String path) {
        return getJsonArray(path, "");
    }

    public JSONArray getJsonArray(String path, String arg) {
        JSONArray array = arrayCache.get(path + arg);
        if (array != null) {
            return array;
        } else {
            String url = urlMap.get(path) + arg;
            String json = getJsonString(url);
            try {
                array = new JSONArray(json);
                arrayCache.put(path + arg, array);
                return array;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

}
