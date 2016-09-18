package planmat.externaldata;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import planmat.datarepresentation.ComponentClass;
import planmat.datarepresentation.Requirements;
import planmat.datarepresentation.Student;
import planmat.datarepresentation.User;

class SIGAADataConverter {

    public Requirements createRequirements(String json) {
        Log.d("Response (requirements)", json);
        return null;
    }

    public User createUser(String jsonStudent) {
        try {
            JSONObject studentInfo = new JSONObject(jsonStudent);
            ArrayList<User.Entry> entries = new ArrayList<>();
            // TODO: preencher as entries com os dados da lista de vínculos do tipo discente
            return new User(studentInfo.getString("nome"), entries);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("Response (user)", jsonStudent);
        return null;
    }

}
