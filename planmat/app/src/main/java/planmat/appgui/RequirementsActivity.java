package planmat.appgui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import planmat.appcore.ApplicationCore;
import planmat.datarepresentation.Component;
import planmat.datarepresentation.Requirements;
import planmat.datarepresentation.Semester;
import ufrn.br.planmat.R;

public class RequirementsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requirements);

        Requirements req = (Requirements) getIntent().getSerializableExtra("Requirements");

        createListView(req);
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
                        getIntent().putExtra("Code", comp.getCode());
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
