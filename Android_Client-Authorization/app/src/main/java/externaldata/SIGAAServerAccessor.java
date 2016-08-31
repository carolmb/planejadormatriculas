package externaldata;

import android.provider.Settings;
import android.widget.Toast;
import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.google.api.client.json.Json;

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

    abstract class ActionRequest {
        abstract void run(String response);
    }

    private static SIGAAServerAccessor _SIGAAServerAccessor;

    private SIGAAServerAccessor() {}

    public static SIGAAServerAccessor getSIGAAServerAccessor(){
        if(_SIGAAServerAccessor == null){
            _SIGAAServerAccessor = new SIGAAServerAccessor();
        }
        return _SIGAAServerAccessor;
    }

    // GET /consulta/curso/componentes/{idCurriculo}
    // GET /consulta/curso/{nivel}
    public Requirements getRequirements(final String idRequirements, final Context context) {
        final String base = "https://apitestes.info.ufrn.br/curso-services/services";
        //String url = "https://apitestes.info.ufrn.br/curso-services/services/consulta/curso/GRADUACAO";
        final String url = "http://apitestes.info.ufrn.br/usuario-services/services/usuario/info";

        ActionRequest action = new ActionRequest() {
            @Override
            void run(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    System.out.print(jsonObject.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        jsonRequester(url, action, context); //solicita curso
        return null;
    }

    // GET /consulta/matriculacomponente/discente/{idDiscente}/all
    public Student getStudent(String idStudent) {
        return null;
    }

    public Component getComponent(String code) { return null; }

    public ComponentClass getComponentClass() {
        return null;
    }

    private void jsonRequester(String urlRequester, final ActionRequest action, final Context context) {
         DataRequest.getInstance().resourceRequest(context, urlRequester, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                action.run(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("OUTPUT", "Error: " + error.getMessage());
                Toast.makeText(context,
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
            }
        });
    }
}
