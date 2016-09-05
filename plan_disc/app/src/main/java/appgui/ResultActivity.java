package appgui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Response;

import appcore.ApplicationCore;
import externaldata.SIGAAServerAccessor;
import ufrn.br.oauthandroid.R;

public class ResultActivity extends AppCompatActivity {

    // Progress dialog
    private ProgressDialog pDialog;

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Aguarde ...");
        pDialog.setCancelable(false);

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Requirements", response);
                textView.setText(response);
            }
        };
        ApplicationCore.getInstance().getRequirements(listener);

        textView = (TextView) findViewById(R.id.textoJson);

    }
}
