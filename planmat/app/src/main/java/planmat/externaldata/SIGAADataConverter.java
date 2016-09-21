package planmat.externaldata;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import planmat.datarepresentation.*;

class SIGAADataConverter {

    public IDList createMajorList(String json) {
        try {
            IDList list = new IDList();
            JSONArray jsonArray = new JSONArray(json);
            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                int id = obj.getInt("idCurso");
                String name = obj.getString("curso") + " (" + obj.getString("municipio") + ")";
                IDList.Entry entry = new IDList.Entry(id, name);
                list.getEntries().add(entry);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public IDList createRequirementsList(String json) {
        try {
            IDList list = new IDList();
            JSONArray jsonArray = new JSONArray(json);
            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                if (obj.getBoolean("ativo")) {
                    int id = obj.getInt("idCurriculo");
                    String name = obj.getString("nome") + " (" + obj.getInt("ano") + ")";
                    String e = obj.getString("enfase");
                    if (e != null && !e.isEmpty() && !e.equals("null")) {
                        name += " - " + e;
                    }
                    IDList.Entry entry = new IDList.Entry(id, name);
                    list.getEntries().add(entry);
                }
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Requirements createRequirements(int id, String json) {
        Requirements requirements = new Requirements(id);
        try {
            JSONArray arr = new JSONArray(json);
            // converter os compomentes e adicionar ao requirements
            // TODO: colocar os componentes nos semestres corretos
            ArrayList<Component> components = new ArrayList<Component>();
            for(int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                Component component = JSONtoComponent(obj);
                components.add(component);
            }
            Semester semester = new Semester(components);
            requirements.getSemesters().add(semester);
            Log.d("Response (requirements)", json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return requirements;
    }

    public Component JSONtoComponent(JSONObject obj) {
        try {
            String name, code;
            name = obj.getString("nome");
            code = obj.getString("codigo");
            Component component = new Component(name, code);
            return component;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User createUser(String jsonUser, String jsonLogin) {
        try {
            JSONObject loginInfo = new JSONObject(jsonLogin);
            JSONObject studentInfo = new JSONObject(jsonUser);
            ArrayList<User.Entry> entries = new ArrayList<>();
            // TODO: preencher as entries com os dados da lista de vínculos do tipo discente
            // alterar entry para ter matricula, curso etc?
            return new User(loginInfo.getInt("id"), studentInfo.getString("nome"), entries);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
