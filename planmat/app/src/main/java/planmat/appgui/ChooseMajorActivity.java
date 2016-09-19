package planmat.appgui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Response;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_major);

        user = (User) getIntent().getSerializableExtra("User");
        Log.d("User name", user.getName());

        requestMajorList();
    }

    /**
     * Requisita a lista de cursos do sistema e cria a lista a partir do retorno.
     */
    private void requestMajorList() {
        ApplicationCore.getInstance().requestMajorList(new Response.Listener<IDList>() {
            @Override
            public void onResponse(IDList response) {
                initializeMajorSpinner(response);
            }
        });
    }

    /**
     * Inicializa o spinner dos cursos.
     * @param list a lista de cursos
     */
    private void initializeMajorSpinner(IDList list) {
        spnMajor = (Spinner) findViewById(R.id.spMajor);
        AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedMajorID = position;
                requestRequirementsList();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Bla.
            }
        };
        spnMajor.setOnItemSelectedListener(listener);
    }

    /**
     * Requisita a lista de matrizes do sistema e cria a lista a partir do retorno.
     */
    private void requestRequirementsList() {
        ApplicationCore.getInstance().requestRequirementsList(new Response.Listener<IDList>() {
            @Override
            public void onResponse(IDList response) {
                initializeRequirementsSpinner(response);
            }
        }, selectedMajorID);
    }

    /**
     * Inicializa o spinner das matrizes curriculates.
     * @param list a lista de matrizes.
     */
    private void initializeRequirementsSpinner(IDList list) {
        spnRequirements = (Spinner) findViewById(R.id.spnRequirements);
        AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedRequirementsID = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Bla.
            }
        };
        spnRequirements.setOnItemSelectedListener(listener);
    }

    /**
     * Guardar os IDs do curso e da matriz curricular num UserPrefs e
     * carrega a PlanningActivity.
     * @param view o view que faz a chamada ao método
     */
    public void buttonOK(View view) {
        UserPrefs userPrefs = new UserPrefs(user.getName(), user.getID(), selectedMajorID, selectedRequirementsID);
        UserPrefsAccessor.getInstance().storeUserPrefs(userPrefs);
        Intent i = new Intent(this, PlanningActivity.class);
        i.putExtra("UserPrefs", userPrefs);
        startActivity(i);
    }

}
