package planmat.externaldata;

import android.app.Activity;
import android.util.Log;

import com.android.volley.Response;
import com.google.api.client.googleapis.auth.clientlogin.ClientLogin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import planmat.datarepresentation.*;

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

    public void getMajorList(final Response.Listener<IDList> finalListener) {
        final Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                IDList list = dataConverter.createMajorList(response);
                finalListener.onResponse(list);
            }
        };
        getAllMajors(listener);
    }

    public void getRequirementsList(final Response.Listener<IDList> finalListener, int majorID) {
        final Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                IDList list = dataConverter.createRequirementsList(response);
                finalListener.onResponse(list);
            }
        };
        getRequirementListByMajorID(majorID, listener);
    }

    public void getRequirements(final Response.Listener<Requirements> finalListener, final int id) {
        final Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Requirements list = dataConverter.createRequirements(id, response);
                finalListener.onResponse(list);
            }
        };
        getRequirementByID(id, listener);
    }

    @Override
    public void getClassList(Response.Listener<ClassList> finalListener, String code) {

    }

    public void getInstitutionalRatingByProfessor(final Response.Listener<String> finalListener, final int institutionalCode, final int year, final int semester) {
        final Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        };
        getInstitutionalRating(institutionalCode, year, semester, listener);
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

    private void getAllMajors(Response.Listener<String> listener) {
        dataRequester.requestData(authorizationRequester.getAccessToken(), listener,
                "https://apitestes.info.ufrn.br/curso-services/services/consulta/curso/GRADUACAO");
    }

    private void getRequirementListByMajorID(int id, Response.Listener<String> listener) {
        dataRequester.requestData(authorizationRequester.getAccessToken(), listener,
                "https://apitestes.info.ufrn.br/curso-services/services/consulta/curso/matriz/graduacao/" + id);
    }

    private void getRequirementByID(int id, Response.Listener<String> listener) {
        dataRequester.requestData(authorizationRequester.getAccessToken(), listener,
                "https://apitestes.info.ufrn.br/curso-services/services/consulta/curso/componentes/" + id);
    }

    private void getInstitutionalRating(int institutionalCode, int year, int semester, Response.Listener<String> listener) {
        dataRequester.requestData(authorizationRequester.getAccessToken(), listener,
                "https://apitestes.info.ufrn.br/ensino-services/services/consulta/avaliacaoInstitucional/docente/" + institutionalCode + "/" + year + "/" + semester);
    }

    // ------------------------------------------------------------------------------
    // Auxiliary methods
    // ------------------------------------------------------------------------------

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
