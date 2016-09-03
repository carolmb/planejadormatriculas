package appcore;

import android.app.Activity;
import android.content.Intent;

import org.json.JSONArray;

import appgui.ResultActivity;
import externaldata.*;
import datarepresentation.*;
/**
 * Created by User on 03/09/2016.
 */
public class ApplicationCore {
    private static ApplicationCore instance = new ApplicationCore();

    private ApplicationCore() {}

    public static ApplicationCore getInstance() {
        return instance;
    }

    public void login(Activity activity, Intent i) {
        DataRequest.getInstance().createTokenCredential(activity, i);
    }

    public void logout(final Activity activity) {
        DataRequest.getInstance().logout(activity, "http://apitestes.info.ufrn.br/sso-server/logout");
    }

    public void getRequirements() {}

    private Requirements JSONtoRequirements(JSONArray jsonArray) {
        System.out.print(jsonArray);
        return new Requirements(0);
    }

    public void getStudent() {}

}
