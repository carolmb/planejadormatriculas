package appcore;

/**
 * Created by Luisa on 31/08/2016.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import org.json.JSONArray;
import org.json.JSONException;

import datarepresentation.*;
import externaldata.*;

public class ApplicationCore {

    private static ApplicationCore instance = new ApplicationCore();

    private ApplicationCore() {}

    public static ApplicationCore getInstance() {
        return instance;
    }

    public void login(Activity activity, Intent i) {
        DataRequest.getInstance().initializeAccess(activity, i);
    }

    public void logout(final Activity activity) {
        DataRequest.getInstance().logout(activity, "http://apitestes.info.ufrn.br/sso-server/logout");
    }

    public void getRequirements(final DataReceiver receiver){
        ActionRequest action = new ActionRequest() {
            @Override
            public void run(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    Requirements req = JSONtoRequirements(jsonArray);
                    receiver.onReceive(req);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        //SIGAAServerAccessor.getSIGAAServerAccessor().getRequirements(action, "TECNOLOGIA DA INFORMAÇÃO", 2014, 1);
    }

    private Requirements JSONtoRequirements(JSONArray jsonArray) {
        System.out.print(jsonArray);
        return new Requirements(0);
    }

    public void getStudent(final DataReceiver receiver, final Context context) {
        // TODO: verificar se tá logado, e se tiver retorna o usuário atual
        // TODO: se não, abre a página de login usando o externaldata
        ActionRequest action = new ActionRequest() {
            public void run(String response) {
                Student student = null; // TODO: converter a response para JSON e pegar o dados pra criar o student
                receiver.onReceive(student);
            }
        };
       // SIGAAServerAccessor.getSIGAAServerAccessor().getRequirements(action, "TECNOLOGIA DA INFORMAÇÃO", 2014, 1);
    }

}
