package planmat.externaldata;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import planmat.datarepresentation.*;

class SIGAADataConverter {

    public MajorList createMajorList(String json) {
        try {
            JSONArray array = new JSONArray(json);
            // TODO
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public RequirementsList createRequirementsList(String json) {
        try {
            JSONArray array = new JSONArray(json);
            //TODO
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Requirements createRequirements(String json) {
        Log.d("Response (requirements)", json);
        // TODO: converter para requirements
        return null;
    }

    public User createUser(String jsonUser, String jsonLogin) {
        try {
            JSONObject loginInfo = new JSONObject(jsonLogin);
            JSONObject studentInfo = new JSONObject(jsonUser);
            ArrayList<User.Entry> entries = new ArrayList<>();
            // TODO: preencher as entries com os dados da lista de vínculos do tipo discente
            return new User(loginInfo.getInt("id"), studentInfo.getString("nome"), entries);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
