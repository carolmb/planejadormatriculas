package planmat.custom;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import planmat.datarepresentation.*;
import planmat.externaldata.AuthorizationRequester;
import planmat.externaldata.DataRequester;
import planmat.externaldata.ServerAccessor;

/**
 * Created by Ana Caroline on 03/09/2016.
 */
public class SIGAAServerAccessor extends ServerAccessor {

    private static String baseURL = "https://apitestes.info.ufrn.br/";
    private SIGAADataConverter converter;

    public SIGAAServerAccessor() {
        super(baseURL, new DataRequester("Bearer ", "Authorization"),
            new AuthorizationRequester("userId",
                    "plan-mat-id", "segredo",
                    baseURL + "authz-server/oauth/authorize",
                    baseURL + "authz-server/oauth/token",
                    baseURL + "sso-server/logout"));
        converter = new SIGAADataConverter();

        urlMap.put("UserInfo", "usuario-services/services/usuario/info");
        urlMap.put("StudentInfo", "ensino-services/services/consulta/perfilusuario/");
        urlMap.put("MajorList", "curso-services/services/consulta/curso/GRADUACAO");
        urlMap.put("RequirementsList", "curso-services/services/consulta/curso/matriz/graduacao/");
        urlMap.put("Requirements", "curso-services/services/consulta/curso/componentes/detalhes/");
        urlMap.put("ClassList", "ensino-services/services/consulta/turmas/usuario/docente/componente/");
        urlMap.put("StatList", "ensino-services/services/consulta/turmas/estatisticas/GRADUACAO/");
    }

    @Override
    public User getUser() {
        JSONObject userInfo = getJsonObject("UserInfo");
        String id = "";
        try {
            id = "" + userInfo.getInt("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject studentInfo = getJsonObject("StudentInfo", id);
        return converter.createUser(studentInfo, userInfo);
    }

    @Override
    public IDList getMajorList() {
        JSONArray array = getJsonArray("MajorList");
        return converter.createMajorList(array);
    }

    @Override
    public IDList getRequirementsList(String code) {
        JSONArray array = getJsonArray("RequirementsList", code);
        IDList list = converter.createRequirementsList(array);
        return list;
    }

    @Override
    public Requirements getRequirements(String code) {
        JSONArray arr = getJsonArray("Requirements", code);
        return converter.createRequirements(code, arr);
    }

    @Override
    public ClassList getClassList(String code) {
        JSONArray classArray = getJsonArray("ClassList", code);
        return converter.createClassList(classArray);
    }

    @Override
    public StatList getStatList(String code) {
        JSONArray statArray = getJsonArray("StatList", code);
        return converter.createStatList(statArray);
    }

}
