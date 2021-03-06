package planmat.custom;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import planmat.datarepresentation.ClassList;
import planmat.datarepresentation.Component;
import planmat.datarepresentation.IDList;
import planmat.datarepresentation.Requirements;
import planmat.datarepresentation.Semester;
import planmat.datarepresentation.StatList;
import planmat.datarepresentation.User;

public class SIGAADataConverter {

    public IDList createMajorList(JSONArray array) {
        try {
            IDList list = new IDList();
            for(int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                int id = obj.getInt("idCurso");
                String name = obj.getString("curso") + " (" + obj.getString("municipio") + ")";
                IDList.Entry entry = new IDList.Entry("" + id, name);
                list.getEntries().add(entry);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public IDList createRequirementsList(JSONArray array) {
        try {
            IDList list = new IDList();
            for(int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                if (obj.getBoolean("ativo")) {
                    int id = obj.getInt("idCurriculo");
                    String name = obj.getString("nome") + " (" + obj.getInt("ano") + ")";
                    String e = obj.getString("enfase");
                    if (e != null && !e.isEmpty() && !e.equals("null")) {
                        name += " - " + e;
                    }
                    IDList.Entry entry = new IDList.Entry("" + id, name);
                    list.getEntries().add(entry);
                }
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Requirements createRequirements(String id, JSONArray arr) {
        try {
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

    public Component createComponent(JSONObject obj) {
        try {
            String name, code;
            int workload;
            name = obj.getString("nome");
            code = obj.getString("codigo");
            workload = obj.getInt("cargaHorariaTotal");
            Component component = new Component(code, name, workload);
            return component;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public StatList createStatList(JSONArray statArray) {
        try {
            StatList statList = new StatList();
            for(int i = 0; i < statArray.length(); i++) {
                JSONObject statObj = statArray.getJSONObject(i);
                StatList.Entry entry = createStatEntry(statObj);
                if (entry == null) {
                    Log.e("NULL ENTRY", "NULL ENTRY");
                } else {
                    statList.getEntries().add(entry);
                }
            }
            return statList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private StatList.Entry createStatEntry(JSONObject statObj) {
        try {
            String code = statObj.getString("codigo");
            int successes = statObj.getInt("aprovados");
            int quits = statObj.getInt("trancados");
            int fails = statObj.getInt("reprovados");
            int year = statObj.getInt("ano");
            int s = statObj.getInt("periodo");
            return new StatList.Entry(code, successes, quits, fails, year, s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ClassList createClassList(JSONArray classArray) {
        try {
            ClassList classList = new ClassList();
            for(int i = 0; i < classArray.length(); i++) {
                JSONObject classObj = classArray.getJSONObject(i);
                ClassList.Entry entry = createClassEntry(classObj);
                if (entry == null) {
                    Log.e("NULL ENTRY", "NULL ENTRY");
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

    private ClassList.Entry createClassEntry(JSONObject classObj) {
        try {
            int y = classObj.getInt("ano");
            int s = classObj.getInt("periodo");
            String code = classObj.getString("codigo");
            JSONArray array = classObj.getJSONArray("docentesList");
            String professors = "";
            if (array.length() > 0) {
                JSONObject prof = array.getJSONObject(0);
                professors = prof.getString("nome");
                for (int i = 1; i < array.length(); i++) {
                    prof = array.getJSONObject(i);
                    professors += ", " + prof.getString("nome");
                }
            }
            String hour = classObj.getString("descricaoHorario");
            return new ClassList.Entry(code, professors, hour, y, s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User createUser(JSONObject studentInfo, JSONObject loginInfo) {
        try {
            return new User(""+ loginInfo.getInt("id"), studentInfo.getString("nome"), loginInfo.getString("username"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
