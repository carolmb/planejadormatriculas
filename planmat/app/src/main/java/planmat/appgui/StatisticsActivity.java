package planmat.appgui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.Response;

import planmat.datarepresentation.Component;
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
        seeComponentStatistics(component);
    }

    private void seeComponentStatistics(Component component) {
        Log.e("component", component.getName());
        //ApplicationCore.getInstance().requestStatistics(listener, component.getCode());
    }

}
