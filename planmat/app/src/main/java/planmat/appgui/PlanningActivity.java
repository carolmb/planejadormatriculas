package planmat.appgui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;

import java.io.Serializable;

import planmat.appcore.ApplicationCore;
import planmat.datarepresentation.Component;
import planmat.datarepresentation.Requirements;
import planmat.internaldata.UserPrefs;
import planmat.internaldata.UserPrefsAccessor;
import ufrn.br.planmat.R;

public class PlanningActivity extends AppCompatActivity {

    private UserPrefs userPrefs;

    private LinearLayout layout;

    private int selectedID = -1;
    private UserPrefs.Semester selectedSemester = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planning);

        userPrefs = (UserPrefs) getIntent().getSerializableExtra("UserPrefs");
        Log.d("User Prefs", "Nome: " + userPrefs.getName() + ", ID: " + userPrefs.getUserID());

        layout = (LinearLayout) findViewById(R.id.semesters);

        createSemesterList();
    }

    // ------------------------------------------------------------------------------
    // Show planning
    // ------------------------------------------------------------------------------

    private void createSemesterList() {
        final Requirements requirements = ApplicationCore.getInstance().getRequirements();
        layout.removeAllViews();
        int i = 1;
        for (final UserPrefs.Semester s : userPrefs.getPlanning()) {
            TextView newText = new TextView(this);
            newText.setText("Semestre " + i);
            i++;
            layout.addView(newText);
            if (s.getComponents().size() == 0) {
                createComponentButton(-1, s, requirements);
            } else {
                for (int j = 0; j < s.getComponents().size(); j++) {
                    createComponentButton(j, s, requirements);
                }
            }
        }
    }

    private Button createComponentButton(final int id, final UserPrefs.Semester s, Requirements requirements) {
        Button button = new Button(this);
        button.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
        if (id >= 0) {
            String code = s.getComponents().get(id);
            Component comp = requirements.getComponent(code);
            button.setText(code + " - " + comp.getName());
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedID = id;
                    selectedSemester = s;
                    showComponentOptions();
                }
            });
        } else {
            button.setText("Inserir novo");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedID = id;
                    selectedSemester = s;
                    addComponent(v);
                }
            });
        }
        layout.addView(button);
        return button;
    }

    // ------------------------------------------------------------------------------
    // New dialog
    // ------------------------------------------------------------------------------

    public void showComponentOptions() {
        final Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.dialog_component);
        dialog.setTitle("");

        Button btnAdd = (Button) dialog.findViewById(R.id.buttonAdd);
        Button btnRemove = (Button) dialog.findViewById(R.id.buttonRemove);
        Button btnDetails = (Button) dialog.findViewById(R.id.buttonDetails);
        Button btnStatistics = (Button) dialog.findViewById(R.id.buttonStatistics);
        Button btnCancel = (Button) dialog.findViewById(R.id.buttonCancel);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addComponent(v);
                dialog.cancel();
            }
        });
        if (selectedID >= 0) {
            btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeComponent(v);
                    dialog.cancel();
                }
            });
            btnDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDetails(v);
                    dialog.cancel();
                }
            });
        }

        btnStatistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seeStatistics(v);
                dialog.cancel();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    // ------------------------------------------------------------------------------
    // Button calls
    // ------------------------------------------------------------------------------

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

    public void addComponent(View view) {
        final Intent i = new Intent(this, RequirementsActivity.class);
        Response.Listener<Requirements> listener = new Response.Listener<Requirements>() {
            @Override
            public void onResponse(Requirements response) {
                i.putExtra("Requirements", response);
                startActivityForResult(i, 0);
            }
        };
        ApplicationCore.getInstance().requestRequirements(listener, userPrefs.getRequirementsID());
    }

    public void removeComponent(View view) {
        selectedSemester.getComponents().remove(selectedID);
        savePrefs();
        createSemesterList();
    }

    public void addSemester(View view) {
        userPrefs.getPlanning().add(new UserPrefs.Semester());
        savePrefs();
        createSemesterList();
        selectedSemester = null;
    }

    public void removeSemester(View view) {
        int n = userPrefs.getPlanning().size() - 1;
        if (n >= 0) {
            userPrefs.getPlanning().remove(n);
            savePrefs();
            createSemesterList();
            selectedSemester = null;
        }
    }

    public void seeStatistics(View view) {
        final Intent i = new Intent(this, StatisticsActivity.class);
        Response.Listener<Component> listener = new Response.Listener<Component>() {
            @Override
            public void onResponse(Component response) {
                i.putExtra("Component", response);
                startActivity(i);
            }
        };
        String code = selectedSemester.getComponents().get(selectedID);
        ApplicationCore.getInstance().requestComponent(listener, code);
    }

    public void showDetails(View view) {
        final Intent i = new Intent(this, ComponentActivity.class);
        Response.Listener<Component> listener = new Response.Listener<Component>() {
            @Override
            public void onResponse(Component response) {
                i.putExtra("Component", response);
                startActivity(i);
            }
        };
        String code = selectedSemester.getComponents().get(selectedID);
        ApplicationCore.getInstance().requestComponent(listener, code);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            String code = data.getStringExtra("Code");
            if (code != null) {
                selectedSemester.getComponents().add(selectedID + 1, code);
                savePrefs();
                createSemesterList();
            }
        }
    }

    private void savePrefs() {
        UserPrefsAccessor.getInstance().storeUserPrefs(userPrefs, this);
    }

}
