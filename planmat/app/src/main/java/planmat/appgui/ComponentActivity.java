package planmat.appgui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

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
        showComponentDetails(component);
    }

    private void addText(String string) {
        TextView text = new TextView(this);
        text.setText("Nome: " + string);
        layout.addView(text);
    }

    private void showComponentDetails(Component component) {
        addText("Nome: " + component.getName());
        addText("Código: " + component.getCode());
        addText("Turmas:");
        for (ClassList.Entry entry : component.getClassList().getEntries()) {
            addText(entry.toString());
            addText("   Docente(s):" + entry.getProfessor());
            addText("   Horário:" + entry.getHour());
        }
    }

}
