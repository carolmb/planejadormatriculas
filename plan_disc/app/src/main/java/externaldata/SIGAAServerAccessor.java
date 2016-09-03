package externaldata;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ana Caroline on 03/09/2016.
 */
public class SIGAAServerAccessor {

    public static String studentInfo;

    public static void getRequeriments() {
        /*try {
            getStudentInfo();
            JSONObject jsonObject = new JSONObject(studentInfo);
            String major = jsonObject.getString("curso");
            Log.d("CURSO", major);
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        getStudentInfo();
    }

    public static void getStudentInfo() {
        ActionRequest action = new ActionRequest() {
            @Override
            public void run(String response) {
                try {
                    String jsonResponse;
                    JSONObject jsonObject = new JSONObject(response);

                    String username = jsonObject.getString("username");
                    String id = jsonObject.getString("id");

                    jsonResponse = "";
                    jsonResponse += "Name: " + username + "\n\n";
                    jsonResponse += "Login: " + id + "\n\n";

                    Log.d("JSON", jsonResponse);
                    ActionRequest action = new ActionRequest() {
                        @Override
                        public void run(String response) {
                            try {
                                String jsonResponse;
                                JSONObject jsonObject = new JSONObject(response);
                                JSONObject jsonObject1 = jsonObject.getJSONObject("listaVinculosUsuario");
                                jsonResponse = jsonObject1.getJSONArray("discentes").get(0).toString();
                                Log.d("JSON", jsonResponse);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    DataRequest.getInstance().requestJson(action, "https://apitestes.info.ufrn.br/ensino-services/services/consulta/perfilusuario/" + jsonObject.getString("id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        DataRequest.getInstance().requestJson(action, "http://apitestes.info.ufrn.br/usuario-services/services/usuario/info");
    }

    public static void setStudentInfo(String s) {
        studentInfo = s;
    }
}
