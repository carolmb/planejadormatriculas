package planmat.externaldata;

import android.app.Activity;
import android.util.Log;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import planmat.datarepresentation.Requirements;
import planmat.datarepresentation.Student;
import planmat.datarepresentation.User;

/**
 * Created by Ana Caroline on 03/09/2016.
 */
public class SIGAAServerAccessor implements ServerAccessor {

    static private SIGAAServerAccessor instance;

    private SIGAADataRequester dataRequester;
    private SIGAAAuthorizationRequester authorizationRequester;
    private SIGAADataConverter dataConverter;

    public SIGAAServerAccessor() {
        this.dataRequester = new SIGAADataRequester();
        this.authorizationRequester = new SIGAAAuthorizationRequester();
        this.dataConverter = new SIGAADataConverter();
    }

    // ------------------------------------------------------------------------------
    // Public methods
    // ------------------------------------------------------------------------------

    public void login(Activity activity, final Response.Listener<String> listener) {
        dataRequester.createRequestQueue(activity);
        authorizationRequester.createTokenCredential(activity, listener);
    }

    public void logout(Activity activity) {
        authorizationRequester.logout(activity, "http://apitestes.info.ufrn.br/sso-server/logout");
    }

    public void getUser(final Response.Listener<User> finalListener) {
        final Response.Listener<String> studentInfoByUserID = new Response.Listener<String>() {
            public void onResponse(String response) {
                int id = getUserIDFromJSON(response);
                final String loginResponse = response;
                final Response.Listener<String> jsonToUser = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        User user = dataConverter.createUser(response, loginResponse);
                        finalListener.onResponse(user);
                    }
                };
                getStudentInfoByUserID(id, jsonToUser);
            }
        };
        getUserInfo(studentInfoByUserID);
    }

    public void getStudent(final Response.Listener<Student> finalListener) {
        // TODO: pegar informações de disciplinas feitas
    }

    public void getRequirements(final Response.Listener<Requirements> finalListener) {
        final Response.Listener<String> jsonToRequirements = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Requirements requirements = dataConverter.createRequirements(response);
                finalListener.onResponse(requirements);
            }
        };
        final Response.Listener<String> requirementByID = new Response.Listener<String>() {
            public void onResponse(String response) {
                int id = getRequirementsIDFromJSON(response);
                getRequirementByID(id, jsonToRequirements);
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
        dataRequester.requestData(authorizationRequester.getAccessToken(), listener,
                "http://apitestes.info.ufrn.br/usuario-services/services/usuario/info");
    }

    private void getStudentInfoByUserID(int id, Response.Listener<String> listener) {
        dataRequester.requestData(authorizationRequester.getAccessToken(), listener,
                "https://apitestes.info.ufrn.br/ensino-services/services/consulta/perfilusuario/" + id);
    }

    private void getAllMajorList(Response.Listener<String> listener) {
        dataRequester.requestData(authorizationRequester.getAccessToken(), listener,
                "https://apitestes.info.ufrn.br/curso-services/services/consulta/curso/GRADUACAO");
    }

    private void getRequirementListByMajorID(int id, Response.Listener<String> listener) {
        dataRequester.requestData(authorizationRequester.getAccessToken(), listener,
                "https://apitestes.info.ufrn.br/curso-services/services/consulta/curso/matriz/stricto/" + id);
    }

    private void getRequirementByID(int id, Response.Listener<String> listener) {
        dataRequester.requestData(authorizationRequester.getAccessToken(), listener,
                "https://apitestes.info.ufrn.br/curso-services/services/consulta/curso/componentes/" + id);
    }

    // ------------------------------------------------------------------------------
    // Auxiliary methods
    // ------------------------------------------------------------------------------

    private int getRequirementsIDFromJSON(String jsonArray) {
        try {
            //TODO: critério para escolher curriculo do aluno
            JSONArray matrixList = new JSONArray(jsonArray);
            Log.d("CURRICULOS", matrixList.toString());
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
