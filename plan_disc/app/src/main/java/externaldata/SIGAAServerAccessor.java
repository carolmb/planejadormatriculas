package externaldata;

import android.util.Log;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import datarepresentation.Requirements;

/**
 * Created by Ana Caroline on 03/09/2016.
 */
public class SIGAAServerAccessor {

    static private SIGAAServerAccessor instance;

    private SIGAAServerAccessor() {}

    public static SIGAAServerAccessor getInstance() {
        if(instance == null)
            instance = new SIGAAServerAccessor();

        return instance;
    }

    // ------------------------------------------------------------------------------
    // Public methods
    // ------------------------------------------------------------------------------

    public void getRequirements(final Response.Listener<String> finalListener) {

        final Response.Listener<String> requirementByID = new Response.Listener<String>() {
            public void onResponse(String response) {
                int id = getRequirementsIDFromJSON(response);
                getRequirementByID(id, finalListener);
            }
        };

        final Response.Listener<String> requirementListByMajorID = new Response.Listener<String>() {
            public void onResponse(String response) {
                final String majorName = getMajorFromStudentJSON(response);
                getAllMajorList(new Response.Listener<String>() {
                    public void onResponse(String response) {
                        int id = searchMajorIDFromJSON(response, majorName);
                        getRequirementListByMajorID(id, requirementByID);
                    }
                });
            }
        };

        final Response.Listener<String> studentInfoByUserID = new Response.Listener<String>() {
            public void onResponse(String response) {
                int id = getUserIDFromJSON(response);
                getStudentInfoByUserID(id, requirementListByMajorID);
            }
        };

        getUserInfo(studentInfoByUserID);
    }

    // ------------------------------------------------------------------------------
    // Data request methods
    // ------------------------------------------------------------------------------

    private void getUserInfo(Response.Listener<String> listener) {
        SIGAADataRequester.getInstance().requestData(listener,
                "http://apitestes.info.ufrn.br/usuario-services/services/usuario/info");
    }

    private void getStudentInfoByUserID(int id, Response.Listener<String> listener) {
        SIGAADataRequester.getInstance().requestData(listener,
                "https://apitestes.info.ufrn.br/ensino-services/services/consulta/perfilusuario/" + id);
    }

    private void getAllMajorList(Response.Listener<String> listener) {
        SIGAADataRequester.getInstance().requestData(listener,
                "https://apitestes.info.ufrn.br/curso-services/services/consulta/curso/GRADUACAO");
    }

    private void getRequirementListByMajorID(int id, Response.Listener<String> listener) {
        SIGAADataRequester.getInstance().requestData(listener,
                "https://apitestes.info.ufrn.br/curso-services/services/consulta/curso/matriz/stricto/" + id);
    }

    private void getRequirementByID(int id, Response.Listener<String> listener) {
        SIGAADataRequester.getInstance().requestData(listener,
                "https://apitestes.info.ufrn.br/curso-services/services/consulta/curso/componentes/" + id);
    }

    // ------------------------------------------------------------------------------
    // Auxiliary methods
    // ------------------------------------------------------------------------------

    private int getRequirementsIDFromJSON(String jsonArray) {
        try {
            //TODO: critério para escolher curriculo do aluno
            JSONArray matrixList = new JSONArray(jsonArray);
            return matrixList.getJSONObject(0).getInt("idCurriculo");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private String getMajorFromStudentJSON(String response) {
        try {
            JSONObject studentInfo = new JSONObject(response);
            JSONObject list = studentInfo.getJSONObject("listaVinculosUsuario");
            String studentString = list.getJSONArray("discentes").get(0).toString();
            studentInfo = new JSONObject(studentString);
            String major = studentInfo.getString("curso");
            return major.substring(7); //considera que sempre inicia com "CURSO: "
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    private int searchMajorIDFromJSON(String response, String major) {
        try {
            JSONArray jsonArray = new JSONArray(response);
            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject majorJSON = jsonArray.getJSONObject(i);
                if(major.contains(majorJSON.getString("curso"))) {
                    if(major.contains(majorJSON.getString("municipio"))) {
                        return majorJSON.getInt("idCurso");
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private int getUserIDFromJSON(String response) {
        try {
            JSONObject userInfo = new JSONObject(response);
            return userInfo.getInt("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return -1;
    }

}
