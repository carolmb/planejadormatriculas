package planmat.appgui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Response;

import planmat.appcore.ApplicationCore;
import ufrn.br.oauthandroid.R;

public class PlanningActivity extends AppCompatActivity {

    // Progress dialog
    private ProgressDialog pDialog;

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planning);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Aguarde ...");
        pDialog.setCancelable(false);

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Response (requirements)", response);
                //textView.setText(response);
            }
        };
        ApplicationCore.getInstance().getRequirements(listener);
        
        //textView = (TextView) findViewById(R.id.textoJson);

    }
}
