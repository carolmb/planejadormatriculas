package planmat.appgui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.concurrent.locks.ReentrantLock;

import planmat.appcore.ApplicationCore;
import planmat.datarepresentation.IDList;
import planmat.datarepresentation.Requirements;
import planmat.datarepresentation.User;
import planmat.internaldata.DatabaseAccessor;
import planmat.internaldata.UserPrefs;
import planmat.internaldata.UserPrefsAccessor;
import ufrn.br.planmat.R;

public class ChooseMajorActivity extends AppCompatActivity {

    private User user;

    private Spinner spnMajor;
    private Spinner spnRequirements;

    private String selectedMajorID = null;
    private String selectedRequirementsID = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_major);

        user = (User) getIntent().getSerializableExtra("User");

        fetchMajorList();
    }

    private void fetchMajorList() {
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                IDList list = (IDList) msg.obj;
                initializeMajorSpinner(list);
            }
        };
        ApplicationCore.getInstance().getMajorList(handler);
    }

    /**
     * Inicializa o spinner dos cursos.
     * @param list a lista de cursos
     */
    private void initializeMajorSpinner(final IDList list) {
        spnMajor = (Spinner) findViewById(R.id.spMajor);
        AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedMajorID = list.getEntries().get(position).getID();
                if (selectedMajorID != null) {
                    fetchRequirementsList();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedMajorID = null;
            }
        };
        spnMajor.setOnItemSelectedListener(listener);
        ArrayAdapter<IDList.Entry> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, list.getEntries());
        spnMajor.setAdapter(adapter);
        selectedMajorID = null;
    }

    /**
     * Salva a lista dos IDs das matrizes curriculares.
     * Bloqueia até que a lista seja retornada.
     */
    private void fetchRequirementsList() {
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                IDList list = (IDList) msg.obj;
                initializeRequirementsSpinner(list);
            }
        };
        ApplicationCore.getInstance().getRequirementsList(handler, selectedMajorID);
    }

    /**
     * Inicializa o spinner das matrizes curriculates.
     * @param list a lista de matrizes.
     */
    private void initializeRequirementsSpinner(final IDList list) {
        spnRequirements = (Spinner) findViewById(R.id.spnRequirements);
        AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedRequirementsID = list.getEntries().get(position).getID();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedRequirementsID = null;
            }
        };
        spnRequirements.setOnItemSelectedListener(listener);
        ArrayAdapter<IDList.Entry> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, list.getEntries());
        spnRequirements.setAdapter(adapter);
        selectedRequirementsID = null;
    }

    /**
     * Guardar os IDs do curso e da matriz curricular num UserPrefs e
     * carrega a PlanningActivity.
     * @param view o view que faz a chamada ao método
     */
    public void buttonOK(View view) {
        if (selectedMajorID != null && selectedRequirementsID != null) {
            final Intent i = new Intent(this, PlanningActivity.class);
            final UserPrefs userPrefs = new UserPrefs(user.getUserName(), user.getName(), user.getID(),
                    selectedMajorID, selectedRequirementsID, 1);
            UserPrefsAccessor.getInstance().setPrefs(userPrefs);
            UserPrefsAccessor.getInstance().saveUserPrefs(this);
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    final Requirements req = ApplicationCore.getInstance().getRequirements(selectedRequirementsID);
                    userPrefs.setPlanning(ApplicationCore.getInstance().getRecommender().getDefaultPlanning(req));
                    Thread dbthread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            DatabaseAccessor.getInstance().storeRequirements(req);
                        }
                    });
                    dbthread.start();
                    finish();
                    startActivity(i);
                }
            });
            thread.start();
        }
    }

}
