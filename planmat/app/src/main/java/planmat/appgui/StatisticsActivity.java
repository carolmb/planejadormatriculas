package planmat.appgui;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;

import planmat.appcore.ApplicationCore;
import planmat.datarepresentation.Component;
import planmat.datarepresentation.StatList;
import ufrn.br.planmat.R;

/**
 * Created by User on 25/09/2016.
 */
public class StatisticsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        Component component = (Component) getIntent().getSerializableExtra("Component");
        StatList list = (StatList) getIntent().getSerializableExtra("StatList");
        seeComponentStatistics(component, list);
    }

    private void seeComponentStatistics(Component component, StatList list) {
        Log.e("component", component.getName());
        RelativeLayout relativeLayout = new RelativeLayout(this);

        String[] entries = new String[list.getEntries().size()];
        for(int i = 0; i < list.getEntries().size(); i++) {
            entries[i] = list.getEntries().get(i).toString();
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                R.layout.text_component,
                entries);
        ListView listView = new ListView(this);
        listView.setAdapter(arrayAdapter);
        relativeLayout.addView(listView);
        setContentView(relativeLayout);
    }

    private void error() {
        Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.empty);
        dialog.setTitle("Aviso");

        TextView text = new TextView(this);
        LinearLayout layout = (LinearLayout) dialog.findViewById(R.id.emptyLayout);
        text.setText("Ocorreu um erro interno, tente novamente mais tarde!");
        text.setTextColor(Color.BLACK);
        layout.addView(text);
        dialog.show();
    }
}
