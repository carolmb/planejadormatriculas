package appgui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import appcore.ApplicationCore;
import appcore.DataReceiver;
import datarepresentation.Requirements;

public class ResultActivity extends AppCompatActivity {

    // Progress dialog
    private ProgressDialog pDialog;

    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Aguarde...");
        pDialog.setCancelable(false);

        text = (TextView) findViewById(R.id.textoJson);

        printRequirementsID();
    }

    private void printRequirementsID() {
        DataReceiver receiver = new DataReceiver() {
            public void onReceive(Requirements requirements) {
                if (requirements == null) {
                    text.setText("NULL");
                } else {
                    text.setText("" + requirements.getID());
                }
            }
        };
        ApplicationCore.getInstance().getRequirements(receiver);
    }

}
