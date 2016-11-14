package planmat.externaldata;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import planmat.datarepresentation.*;

/**
 * Created by Ana Caroline on 03/09/2016.
 */
public class SIGAAServerAccessor extends ServerAccessor {

    private static String baseURL = "https://apitestes.info.ufrn.br/";

    public SIGAAServerAccessor() {
        super(baseURL, new DataRequester("Bearer ", "Authorization"),
            new AuthorizationRequester("userId",
                    "plan-mat-id", "segredo",
                    baseURL + "authz-server/oauth/authorize",
                    baseURL + "authz-server/oauth/token",
                    baseURL + "sso-server/logout"));
        urlMap.put("UserInfo", "usuario-services/services/usuario/info");
        urlMap.put("StudentInfo", "ensino-services/services/consulta/perfilusuario/");
        urlMap.put("MajorList", "curso-services/services/consulta/curso/GRADUACAO");
        urlMap.put("RequirementsList", "curso-services/services/consulta/curso/matriz/graduacao/");
        urlMap.put("Requirements", "curso-services/services/consulta/curso/componentes/detalhes/");
        urlMap.put("ClassList", "ensino-services/services/consulta/turmas/usuario/docente/componente/");
        urlMap.put("StatList", "ensino-services/services/consulta/turmas/estatisticas/GRADUACAO/");
    }

}
