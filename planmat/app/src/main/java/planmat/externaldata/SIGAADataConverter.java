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
            ArrayList<Semester> semesters = requirements.getSemesters();
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                int semesterOffer = obj.getInt("semestreOferta");

                Component component = JSONtoComponent(obj);

                while (semesters.size() <= semesterOffer) { /* Caso ainda não tenha o semestre da componente atual */
                    ArrayList<Component> components = new ArrayList<Component>();
                    Semester newSemester = new Semester(components);
                    semesters.add(newSemester);
                }
                semesters.get(semesterOffer).getComponents().add(component);
            }
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
            Component component = new Component(name, code, null);
            return component;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public StatisticsClass createStatisticsClass(String json) {
        try {
            JSONArray arr = new JSONArray(json);
            StatisticsClass statisticsClass = new StatisticsClass();
            for(int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                StatisticsClass.Component component = JSONtoStatisticsComponent(obj);
                statisticsClass.getStatistics().add(component);
            }
            return statisticsClass;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private StatisticsClass.Component JSONtoStatisticsComponent(JSONObject obj) {
        try {
            String name = obj.getString("nomeComponente");
            String code = obj.getString("codigoComponente");
            int year = obj.getInt("ano");
            int semester = obj.getInt("periodo");
            int passed = obj.getInt("aprovados");
            int failed = obj.getInt("reprovados");
            StatisticsClass.Component component = new StatisticsClass.Component(name, code, year, semester, passed, failed);
            return component;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ClassList createClassList(String json) {
        try {
            ClassList classList = new ClassList();
            JSONArray jsonArray = new JSONArray(json);
            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                ClassList.Entry entry = JSONtoClass(jsonObject);
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

    private ClassList.Entry JSONtoClass(JSONObject jsonObject) {
        try {
            int id = jsonObject.getInt("id");
            String code = jsonObject.getString("codigo");
            String semester = jsonObject.getString("anoPeriodoString");
            ArrayList<String> professors = new ArrayList<>();
            JSONArray array = jsonObject.getJSONArray("docentesList");
            for (int i = 0; i < array.length(); i++) {
                JSONObject prof = array.getJSONObject(i);
                professors.add(prof.getString("nome"));
            }
            String hour = jsonObject.getString("descricaoHorario");
            Log.e("json", jsonObject.toString());
            ClassList.Entry entry = new ClassList.Entry(id, code, semester, professors, hour);
            return entry;
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
