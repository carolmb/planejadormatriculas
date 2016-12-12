package planmat.appgui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import planmat.appcore.ApplicationCore;
import planmat.datarepresentation.IDList;
import planmat.datarepresentation.Requirements;
import planmat.datarepresentation.User;
import planmat.internaldata.DatabaseAccessor;
import planmat.internaldata.UserPrefs;
import planmat.internaldata.UserPrefsAccessor;
import ufrn.br.planmat.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        login();
        DatabaseAccessor.getInstance().createHandler(this);
    }

    /**
     * Autentica o acesso atrás do login pela página do sigaa.
     * Logo em seguida, redireciona o usuário para a próxima
     * página.
     */
    private void login() {
        final Activity activity = this;
        Thread thread = new Thread(new Runnable() {
           public void run() {
               ApplicationCore.getInstance().login(activity);
               User user = ApplicationCore.getInstance().getUser();
               redirect(user);
           }
        });
        thread.start();
    }

    /**
     * Redireciona o usuário para a próxima tela. Se ele já tiver um
     * arquivo de preferências no celular, pula para a PlanningActivity.
     * Caso contrário, vai para a ChooseMajorActivity para que possa
     * ser criado um arquivo de preferências.
     * @param user o usuário que acabou de logar.
     */
    private void redirect(User user) {
        final Activity activity = this;
        boolean success = UserPrefsAccessor.getInstance().loadUserPrefs(activity, user.getUserName());

        Log.e("Redirect", "" + success);
        if (success) {
            UserPrefs prefs = UserPrefsAccessor.getInstance().getPrefs();
            final Intent i = new Intent(activity, PlanningActivity.class);
            if (prefs.getPlanning() == null) {
                Requirements req = ApplicationCore.getInstance().getRequirements(prefs.getRequirementsID());
                prefs.setPlanning(ApplicationCore.getInstance().getRecommender().getDefaultPlanning(req));
            }
            finish();
            activity.startActivity(i);
        } else {
            final Intent i = new Intent(activity, ChooseMajorActivity.class);
            i.putExtra("User", user);
            finish();
            activity.startActivity(i);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
