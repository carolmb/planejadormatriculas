package planmat.appgui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

import planmat.datarepresentation.Component;
import planmat.datarepresentation.Requirements;
import planmat.datarepresentation.Semester;
import planmat.internaldata.UserPrefs;
import ufrn.br.planmat.R;

public class RequirementsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requirements);

        Requirements requirements = (Requirements) getIntent().getSerializableExtra("Requirements");
        createListView(requirements);
    }

    private void createListView(final Requirements requirements) {
        RelativeLayout relativeLayout = new RelativeLayout(this);
        String[] semesters = new String[requirements.getSemesters().size()];
        for(int i = 0; i < requirements.getSemesters().size(); i++) {
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
                final Semester selectedSemester = requirements.getSemesters().get(position);
                String[] componentsOfSemester = selectedSemester.getComponentsToString();
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                        view.getContext(),
                        R.layout.text_component,
                        componentsOfSemester);
                ListView listView = new ListView(view.getContext());
                listView.setAdapter(arrayAdapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Component comp = selectedSemester.getComponents().get(position);
                        getIntent().putExtra("Component", new UserPrefs.Component(comp.getCode(), comp.getName()));
                        setResult(RESULT_OK, getIntent());
                        finish();
                    }
                });

                relativeLayout.addView(listView);
                setContentView(relativeLayout);
            }
        });

        relativeLayout.addView(listView);
        setContentView(relativeLayout);
    }
}
