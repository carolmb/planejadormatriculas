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
        try {
            JSONArray arr = new JSONArray(json);
            ArrayList<Semester> semesters = new ArrayList<>();
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                int semesterOffer = obj.getInt("semestreOferta");

                Component component = createComponent(obj);

                while (semesters.size() <= semesterOffer) { /* Caso ainda não tenha o semestre da componente atual */
                    ArrayList<Component> components = new ArrayList<Component>();
                    Semester newSemester = new Semester(components);
                    semesters.add(newSemester);
                }
                semesters.get(semesterOffer).getComponents().add(component);
            }
            return new Requirements(id, semesters);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Component createComponent(JSONObject obj) {
        try {
            String name, code;
            name = obj.getString("nome");
            code = obj.getString("codigo");
            Component component = new Component(code, name, null);
            return component;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ClassList createClassList(String classJson, String statJson) {
        try {
            ClassList classList = new ClassList();
            JSONArray classArray = new JSONArray(classJson);
            JSONArray statArray = new JSONArray(statJson);
            for(int i = 0; i < classArray.length(); i++) {
                JSONObject classObj = classArray.getJSONObject(i);
                JSONObject statObj = statArray.getJSONObject(i);
                ClassList.Entry entry = createClassEntry(classObj, statObj);
                if (entry == null) {
                    Log.d("NULL ENTRY", "NULL ENTRY");
                } else {
                    classList.getEntries().add(entry);
                }
            }
            return classList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ClassList.Entry createClassEntry(JSONObject classObj, JSONObject statObj) {
        try {
            int id = classObj.getInt("id");
            String code = classObj.getString("codigo");
            String semester = classObj.getString("anoPeriodoString");
            ArrayList<String> professors = new ArrayList<>();
            JSONArray array = classObj.getJSONArray("docentesList");
            for (int i = 0; i < array.length(); i++) {
                JSONObject prof = array.getJSONObject(i);
                professors.add(prof.getString("nome"));
            }
            String hour = classObj.getString("descricaoHorario");
            Log.e("json", classObj.toString());
            int successes = statObj.getInt("aprovados");
            int quits = statObj.getInt("trancados");
            int fails = statObj.getInt("reprovados");
            return new ClassList.Entry(id, code, semester, professors, hour, successes, quits, fails);
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
            return new User(loginInfo.getInt("id"), studentInfo.getString("nome"), entries);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
