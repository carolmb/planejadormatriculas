package externaldata;

import android.provider.Settings;
import android.widget.Toast;
import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.google.api.client.json.Json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import datarepresentation.Component;
import datarepresentation.ComponentClass;
import datarepresentation.Requirements;
import datarepresentation.Student;

/**
 * Created by User on 24/08/2016.
 */
public class SIGAAServerAccessor implements ServerAccessor {

    private static SIGAAServerAccessor _SIGAAServerAccessor;

    private SIGAAServerAccessor() {}

    public static SIGAAServerAccessor getSIGAAServerAccessor(){
        if(_SIGAAServerAccessor == null){
            _SIGAAServerAccessor = new SIGAAServerAccessor();
        }
        return _SIGAAServerAccessor;
    }

    public void getRequirements(final ActionRequest finalAction, final String nameMajor, final int year, final int semester) {
        ActionRequest action = new ActionRequest() {
            @Override
            public void run(String response) {
                try {
                    JSONArray json = new JSONArray(response);
                    System.out.print("Try select idMajor");
                    String idMajor = searchIDMajor(json, nameMajor); //pega o id do curso
                    System.out.print("Selected idMajor");
                    //getAllRequirements(finalAction, idMajor, context, year, semester);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        jsonRequester("https://apitestes.info.ufrn.br/curso-services/services/consulta/curso/GRADUACAO", action);
    }

    private void getAllRequirements(final ActionRequest finalAction, String idMajor, final int year, final int semester) {
        ActionRequest action = new ActionRequest() {
            @Override
            public void run(String response) {
                try {
                    JSONArray json = new JSONArray(response);
                    int idRequirements = searchIDRequirements(json, year, semester); //pega o id curriculo especifico
                    getRequirementsFromId(idRequirements, finalAction);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        jsonRequester("https://apitestes.info.ufrn.br/curso-services/services/consulta/curso/matriz/graduacao/" + idMajor, action);
    }

    private void getRequirementsFromId(int idRequirements, final ActionRequest finalAction) {
        ActionRequest action = new ActionRequest() {
            @Override
            public void run(String response) {
                try {
                    JSONArray json = new JSONArray(response);
                    finalAction.run(json.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        jsonRequester("https://apitestes.info.ufrn.br/curso-services/services/consulta/curso/componentes/" + idRequirements, action);
    }

    private int searchIDRequirements(JSONArray json, int year, int semester) {
        try {
            for (int i = 0; i < json.length(); i++ ) {
                JSONObject j = json.getJSONObject(i);
                if(j.getInt("ano") == year) {
                    if(j.getInt("periodo") == semester) {
                        return j.getInt("idCurriculo");
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private String searchIDMajor(JSONArray json, String major) {
        try {
            for (int i = 0; i < json.length(); i++) {
                JSONObject j = json.getJSONObject(i);
                if(j.getString("curso").equals(major)) {
                    System.out.print(j.getString("idCurso"));
                    return j.getString("idCurso");
                }
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }

    private void getUserInfo() {
        ActionRequest action = new ActionRequest() {
            @Override
            public void run(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String id = jsonObject.getString("id");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        jsonRequester("http://apitestes.info.ufrn.br/usuario-services/services/usuario/info", action);
    }

    // GET /consulta/matriculacomponente/discente/{idDiscente}/all
    public Student getStudent(String idStudent) {
        return null;
    }

    public Component getComponent(String code) { return null; }

    public ComponentClass getComponentClass() {
        return null;
    }

    private void jsonRequester(String urlRequester, final ActionRequest action) {
         DataRequest.getInstance().resourceRequest(urlRequester, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                action.run(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("OUTPUT", "Error: " + error.getMessage());
            }
        });
    }
}
