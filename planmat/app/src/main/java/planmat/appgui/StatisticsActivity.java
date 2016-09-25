package planmat.appgui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;

import planmat.appcore.ApplicationCore;
import planmat.datarepresentation.Component;
import planmat.datarepresentation.StatisticsClass;
import planmat.internaldata.UserPrefs;
import ufrn.br.planmat.R;

/**
 * Created by User on 25/09/2016.
 */
public class StatisticsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        UserPrefs.Component component = (UserPrefs.Component) getIntent().getSerializableExtra("Component");
        seeComponentStatistics(component);
    }

    private void seeComponentStatistics(final UserPrefs.Component component) {
        Log.e("component", component.getName());
        Response.Listener<StatisticsClass> listener = new Response.Listener<StatisticsClass>() {
            @Override
            public void onResponse(StatisticsClass response) {
                component.setAveragePassed(response.averagePassed());
                component.setAverageFailed(response.averageFailed());
                for(StatisticsClass.Component c : response.getStatistics()) {
                    Log.e("estatisticas", c.toString());
                }
                //TODO: exibir as estatisticas
            }
        };
        ApplicationCore.getInstance().requestStatistics(listener, component.getCode());
    }

}
