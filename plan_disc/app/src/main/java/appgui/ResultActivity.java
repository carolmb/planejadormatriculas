package appgui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import externaldata.ActionRequest;
import externaldata.DataRequest;
import externaldata.SIGAAServerAccessor;
import ufrn.br.oauthandroid.R;

public class ResultActivity extends AppCompatActivity {

    // Progress dialog
    private ProgressDialog pDialog;

    private TextView texto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Aguarde ...");
        pDialog.setCancelable(false);

        SIGAAServerAccessor.getInstance().getRequirements();

        texto = (TextView) findViewById(R.id.textoJson);

    }
}
