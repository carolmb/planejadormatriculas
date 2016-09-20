package planmat.appgui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.android.volley.Response;

import planmat.appcore.ApplicationCore;
import planmat.datarepresentation.Requirements;
import planmat.internaldata.UserPrefs;
import ufrn.br.planmat.R;

public class PlanningActivity extends AppCompatActivity {

    private UserPrefs userPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planning);
        Log.d("oi", "chegou aqui");

        //userPrefs = (UserPrefs) getIntent().getSerializableExtra("UserPrefs");
        //Log.d("User name", userPrefs.getName());

        // TODO: mostrar o planejamento
    }

    /**
     * Recupera a estrutura curricular para mostrar na próxima tela.
     * Chamado ao apertar o botão de ver estrutura.
     * @param view o view que fez a chamada ao método
     */
    public void seeRequirements(View view) {
        final Intent i = new Intent(this, Requirements.class);
        Response.Listener<Requirements> listener = new Response.Listener<Requirements>() {
            @Override
            public void onResponse(Requirements response) {
                i.putExtra("Requirements", response);
                startActivity(i);
            }
        };
        ApplicationCore.getInstance().requestRequirements(listener, userPrefs.getRequirementsID());
    }

}
