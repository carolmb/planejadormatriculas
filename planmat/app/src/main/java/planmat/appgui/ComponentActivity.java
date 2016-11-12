package planmat.appgui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import planmat.appcore.ApplicationCore;
import planmat.datarepresentation.ClassList;
import planmat.datarepresentation.Component;
import ufrn.br.planmat.R;

public class ComponentActivity extends AppCompatActivity {

    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        layout = (LinearLayout) findViewById(R.id.detailsLayout);
        Component component = (Component) getIntent().getSerializableExtra("Component");
        ClassList classList = (ClassList) getIntent().getSerializableExtra("ClassList");
        showComponentDetails(component, classList);
    }

    private void addText(String string) {
        TextView text = new TextView(this);
        text.setText(string);
        layout.addView(text);
    }

    private void showComponentDetails(Component component, ClassList list) {
        addText("Nome: " + component.getName());
        addText("Código: " + component.getCode());
        addText("Turmas:");
        for (ClassList.Entry entry : list.getEntries()) {
            addText(entry.toString());
            addText("   Docente(s):");
            for (String prof : entry.getProfessors()) {
                addText("       " + prof);
            }
            addText("   Horário:" + entry.getHour());
        }
    }

}
