package planmat.appgui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.android.volley.Response;

import planmat.appcore.ApplicationCore;
import planmat.datarepresentation.ClassList;
import planmat.datarepresentation.Component;
import planmat.datarepresentation.Requirements;
import planmat.datarepresentation.Semester;
import planmat.internaldata.UserPrefs;
import ufrn.br.planmat.R;

public class ComponentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Component component = (Component) getIntent().getSerializableExtra("Component");
        showComponentDetails(component);
    }

    private void showComponentDetails(Component component) {
        LinearLayout layout = (LinearLayout) findViewById(R.id.detailsLayout);

        Response.Listener<ClassList> listener = new Response.Listener<ClassList>() {
            @Override
            public void onResponse(ClassList classList) {
                Log.e("CLASS LIST", "here");
                //TODO : precisa testar aqui
            }
        };
        ApplicationCore.getInstance().requestClassList(listener, component.getCode());
    }

}
