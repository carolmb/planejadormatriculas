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

    private Requirements reqCache = null;

    public SIGAAServerAccessor() {
        this.dataRequester = new SIGAADataRequester();
        this.authorizationRequester = new SIGAAAuthorizationRequester();
        this.dataConverter = new SIGAADataConverter();
    }

    // ------------------------------------------------------------------------------
    // Public methods
    // ------------------------------------------------------------------------------

    public User login(Activity activity) {
        dataRequester.createRequestQueue(activity);
        authorizationRequester.createTokenCredential(activity);
        return getUser();
    }

    public void logout(Activity activity) {
        authorizationRequester.logout(activity, "http://apitestes.info.ufrn.br/sso-server/logout");
    }

    public IDList getMajorList() {
        String json = getAllMajors();
        IDList list = dataConverter.createMajorList(json);
        return list;
    }

    public IDList getRequirementsList(int majorID) {
        String json = getRequirementListByMajorID(majorID);
        IDList list = dataConverter.createRequirementsList(json);
        return list;
    }

    public Requirements getRequirements(final int id) {
        if (reqCache != null && reqCache.getID() == id) {
            return reqCache;
        } else {
            String json = getRequirementByID(id);
            reqCache = dataConverter.createRequirements(id, json);
            return reqCache;
        }
    }

    public Component getComponent(final String code) {
        final Component component = reqCache.getComponent(code);
        if (component.getClassList() == null) {
            ClassList classList = getClassList(code);
            component.setClassList(classList);
            StatList statList = getStatList(code);
            component.setStatList(statList);
            return component;
        } else {
            return component;
        }
    }

    public ClassList getClassList(final String code) {
        String json = getClassListByCode(code);
        Log.d("RESPONSE", "Class json");
        return dataConverter.createClassList(json);
    }

    public StatList getStatList(final String code) {
        String json = getStatisticsByCode(code);
        Log.d("RESPONSE", "Stat json");
        return dataConverter.createStatList(json);
    }

    // ------------------------------------------------------------------------------
    // Data request methods
    // ------------------------------------------------------------------------------

    private String getUserInfo() {
        return dataRequester.requestData(authorizationRequester.getAccessToken(),
                "http://apitestes.info.ufrn.br/usuario-services/services/usuario/info");
    }

    private String getStudentInfoByUserID(int id) {
        return dataRequester.requestData(authorizationRequester.getAccessToken(),
                "https://apitestes.info.ufrn.br/ensino-services/services/consulta/perfilusuario/" + id);
    }

    private String getAllMajors() {
        return dataRequester.requestData(authorizationRequester.getAccessToken(),
                "https://apitestes.info.ufrn.br/curso-services/services/consulta/curso/GRADUACAO");
    }

    private String getRequirementListByMajorID(int id) {
        return dataRequester.requestData(authorizationRequester.getAccessToken(),
                "https://apitestes.info.ufrn.br/curso-services/services/consulta/curso/matriz/graduacao/" + id);
    }

    private String getRequirementByID(int id) {
        return dataRequester.requestData(authorizationRequester.getAccessToken(),
                "https://apitestes.info.ufrn.br/curso-services/services/consulta/curso/componentes/" + id);
    }

    private String getInstitutionalRating(int institutionalCode, int year, int semester) {
        return dataRequester.requestData(authorizationRequester.getAccessToken(),
                "https://apitestes.info.ufrn.br/ensino-services/services/consulta/avaliacaoInstitucional/docente/" + institutionalCode + "/" + year + "/" + semester);
    }

    private String getClassListByCode(String code) {
        return dataRequester.requestData(authorizationRequester.getAccessToken(),
                "https://apitestes.info.ufrn.br/ensino-services/services/consulta/turmas/usuario/docente/componente/" + code);
    }

    private String getStatisticsByCode(String code) {
        return dataRequester.requestData(authorizationRequester.getAccessToken(),
                "https://apitestes.info.ufrn.br/ensino-services/services/consulta/turmas/estatisticas/GRADUACAO/" + code);
    }

    // ------------------------------------------------------------------------------
    // Auxiliary methods
    // ------------------------------------------------------------------------------

    private User getUser() {
        String userInfo = getUserInfo();
        int id = getUserIDFromJSON(userInfo);
        String studentInfo = getStudentInfoByUserID(id);
        return dataConverter.createUser(studentInfo, userInfo);
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
