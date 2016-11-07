package planmat.appgui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Response;

import java.util.concurrent.locks.ReentrantLock;

import planmat.appcore.ApplicationCore;
import planmat.datarepresentation.IDList;
import planmat.datarepresentation.User;
import planmat.internaldata.UserPrefs;
import planmat.internaldata.UserPrefsAccessor;
import ufrn.br.planmat.R;

public class ChooseMajorActivity extends AppCompatActivity {

    private TextView textView;
    private User user;

    private Spinner spnMajor;
    private Spinner spnRequirements;

    private int selectedMajorID = -1;
    private int selectedRequirementsID = -1;

    private ReentrantLock fetchLock = new ReentrantLock();

    IDList majorList;
    IDList reqList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_major);

        user = (User) getIntent().getSerializableExtra("User");

        fetchMajorList();
        initializeMajorSpinner(majorList);
    }

    private void fetchMajorList() {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                fetchLock.lock();
                majorList = ApplicationCore.getInstance().getServerAccessor().getMajorList();
                fetchLock.unlock();
            }
        });
        thread.start();
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        fetchLock.lock();
        fetchLock.unlock();
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
                if (selectedMajorID >= 0) {
                    fetchRequirementsList();
                    initializeRequirementsSpinner(list);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedMajorID = -1;
            }
        };
        spnMajor.setOnItemSelectedListener(listener);
        ArrayAdapter<IDList.Entry> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, list.getEntries());
        spnMajor.setAdapter(adapter);
        selectedMajorID = -1;
    }

    private void fetchRequirementsList() {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                fetchLock.lock();
                reqList = ApplicationCore.getInstance().getServerAccessor().getRequirementsList(selectedMajorID);
                fetchLock.unlock();
            }
        });
        thread.start();
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        fetchLock.lock();
        fetchLock.unlock();
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
                selectedRequirementsID = -1;
            }
        };
        spnRequirements.setOnItemSelectedListener(listener);
        ArrayAdapter<IDList.Entry> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, list.getEntries());
        spnRequirements.setAdapter(adapter);
        selectedRequirementsID = -1;
    }

    /**
     * Guardar os IDs do curso e da matriz curricular num UserPrefs e
     * carrega a PlanningActivity.
     * @param view o view que faz a chamada ao método
     */
    public void buttonOK(View view) {
        if (selectedMajorID >= 0 && selectedRequirementsID >= 0) {
            UserPrefs userPrefs = new UserPrefs(user.getName(), user.getID(), selectedMajorID, selectedRequirementsID);
            UserPrefsAccessor.getInstance().storeUserPrefs(userPrefs, this);
            Intent i = new Intent(this, PlanningActivity.class);
            i.putExtra("UserPrefs", userPrefs);
            finish();
            startActivity(i);
        }
    }

}
