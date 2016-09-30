package planmat.appgui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Response;

import java.io.Serializable;

import planmat.appcore.ApplicationCore;
import planmat.datarepresentation.Requirements;
import planmat.datarepresentation.User;
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
    }

    /**
     * Autentica o acesso atrás do login pela página do sigaa.
     * Logo em seguida, redireciona o usuário para a próxima
     * página.
     */
    private void login() {
        Response.Listener<User> listener = new Response.Listener<User>() {
            @Override
            public void onResponse(User response) {
                if (response != null) {
                    redirect(response);
                }
            }
        };
        ApplicationCore.getInstance().login(this, listener);
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
        final UserPrefs prefs = UserPrefsAccessor.getInstance().loadUserPrefs(activity);
        if (prefs != null) {
            final Intent i = new Intent(activity, PlanningActivity.class);
            Response.Listener<Requirements> listener = new Response.Listener<Requirements>() {
                @Override
                public void onResponse(Requirements response) {
                    i.putExtra("UserPrefs", prefs);
                    finish();
                    activity.startActivity(i);
                }
            };
            ApplicationCore.getInstance().requestRequirements(listener, prefs.getRequirementsID());
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
