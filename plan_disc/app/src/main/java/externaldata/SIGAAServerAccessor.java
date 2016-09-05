package externaldata;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ana Caroline on 03/09/2016.
 */
public class SIGAAServerAccessor {

    static private SIGAAServerAccessor sigaaServerAccessor;

    private SIGAAServerAccessor() {}

    public static SIGAAServerAccessor getInstance() {
        if(sigaaServerAccessor == null)
            sigaaServerAccessor = new SIGAAServerAccessor();

        return sigaaServerAccessor;
    }

    public void getRequirements() {
        getStudentInfo();
    }

    public String getMajor(String studentInfo) {
        try {
            JSONObject jsonObject = new JSONObject(studentInfo);
            String major = jsonObject.getString("curso");
            return major.substring(7); //considera que sempre inicia com "CURSO: "
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void getRequirementsFromMajor(final String major) {
        ActionRequest action = new ActionRequest() {
            @Override
            public void run(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    Log.e("CURSOS", jsonArray.toString());
                    String idMajor = searchIdMajor(jsonArray, major);
                    Log.e("IDCURSO", idMajor);
                    getCurriculumMatrices(idMajor);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        DataRequest.getInstance().requestJson(action, "https://apitestes.info.ufrn.br/curso-services/services/consulta/curso/GRADUACAO");
    }

    private void getCurriculumMatrices(String idMajor) {
        ActionRequest action = new ActionRequest() {
            @Override
            public void run(String response) {
                 try {
                     JSONArray jsonArray = new JSONArray(response);
                     String curriculumMatrix = searchCurriculumMatrix(jsonArray);
                     getRequirements(curriculumMatrix);
                     //Log.d("CURRICULUM", jsonArray.toString());
                 } catch (JSONException e) {
                     e.printStackTrace();
                 }
            }
        };
        DataRequest.getInstance().requestJson(action, "https://apitestes.info.ufrn.br/curso-services/services/consulta/curso/matriz/stricto/" + idMajor);
    }

    private void getRequirements(String idRequirements) {
        ActionRequest action = new ActionRequest() {
            @Override
            public void run(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    Log.d("CURRICULO DO ALUNO", jsonArray.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        DataRequest.getInstance().requestJson(action, "https://apitestes.info.ufrn.br/curso-services/services/consulta/curso/componentes/" + idRequirements);
    }

    private String searchCurriculumMatrix(JSONArray jsonArray) {
        try {
            //TODO: critério para escolher curriculo do aluno
            return jsonArray.getJSONObject(0).getString("idCurriculo");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String searchIdMajor(JSONArray jsonArray, String major) {
        try {
            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject majorJSON = jsonArray.getJSONObject(i);
                if(major.contains(majorJSON.getString("curso"))) {
                    if(major.contains(majorJSON.getString("municipio"))) {
                        return majorJSON.getString("idCurso");
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void getStudentInfo() {
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
                                String major = SIGAAServerAccessor.getInstance().getMajor(jsonResponse);
                                SIGAAServerAccessor.getInstance().getRequirementsFromMajor(major);
                                Log.d("JSON", jsonResponse.toString());
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
}
