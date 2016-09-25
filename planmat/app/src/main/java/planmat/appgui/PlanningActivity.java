package planmat.appgui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

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

        userPrefs = (UserPrefs) getIntent().getSerializableExtra("UserPrefs");
        Log.d("User Prefs", "Nome: " + userPrefs.getName() + ", ID: " + userPrefs.getUserID());

        seePlanning();
        // TODO: mostrar o planejamento
    }

    /**
     * Recupera a estrutura curricular para mostrar na próxima tela.
     * Chamado ao apertar o botão de ver estrutura.
     * @param view o view que fez a chamada ao método
     */
    public void seeRequirements(View view) {
        final Intent i = new Intent(this, RequirementsActivity.class);
        Response.Listener<Requirements> listener = new Response.Listener<Requirements>() {
            @Override
            public void onResponse(Requirements response) {
                i.putExtra("Requirements", response);
                startActivity(i);
            }
        };
        ApplicationCore.getInstance().requestRequirements(listener, userPrefs.getRequirementsID());
    }

    private void seePlanning() {
        RelativeLayout relativeLayout = new RelativeLayout(this);
        String[] semesters = new String[userPrefs.getSemesters().size()];
        for(int i = 0; i < userPrefs.getSemesters().size(); i++) {
            semesters[i] = "Semestre " + i;
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                R.layout.text_component,
                semesters);
        ListView listView = new ListView(this);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RelativeLayout relativeLayout = new RelativeLayout(view.getContext());
                String[] componentsOfSemesterPlanning = (String[]) userPrefs.getSemesters().get(position).getComponentIDs().toArray();
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                        view.getContext(),
                        R.layout.text_component,
                        componentsOfSemesterPlanning);
                ListView listView = new ListView(view.getContext());
                listView.setAdapter(arrayAdapter);
                relativeLayout.addView(listView);
                setContentView(relativeLayout);
            }
        });

        relativeLayout.addView(listView);
        setContentView(relativeLayout);
    }

}
