package planmat.appgui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;

import planmat.appcore.ApplicationCore;
import planmat.datarepresentation.Requirements;
import planmat.internaldata.UserPrefs;
import ufrn.br.planmat.R;

public class PlanningActivity extends AppCompatActivity {

    private UserPrefs userPrefs;

    private LinearLayout layout;
    private TextView textView;
    private ListView listView;

    private int selectedID = -1;
    private UserPrefs.Semester selectedSemester = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planning);

        userPrefs = (UserPrefs) getIntent().getSerializableExtra("UserPrefs");
        Log.d("User Prefs", "Nome: " + userPrefs.getName() + ", ID: " + userPrefs.getUserID());

        textView = (TextView) findViewById(R.id.textView);
        listView = (ListView) findViewById(R.id.listView);
        layout = (LinearLayout) findViewById(R.id.semesters);

        createSemesterList();
    }

    private void createSemesterList() {
        layout.removeAllViews();
        for (final UserPrefs.Semester s : userPrefs.getSemesters()) {
            TextView newText = new TextView(this);
            ListView newList = new ListView(this);
            layout.addView(newText);
            layout.addView(newList);
            AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selectedID = position;
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    selectedID = -1;
                }
            };
            newList.setOnItemSelectedListener(listener);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_dropdown_item, s.getComponents());
            newList.setAdapter(adapter);
        }
    }

    public void add(View view) {

    }

    public void remove(View view) {

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

}
