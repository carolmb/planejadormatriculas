package planmat.appgui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import planmat.datarepresentation.User;
import ufrn.br.planmat.R;

public class ChooseMajorActivity extends AppCompatActivity {

    // Progress dialog
    private ProgressDialog pDialog;

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_major);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Aguarde ...");
        pDialog.setCancelable(false);

        User user = (User) getIntent().getSerializableExtra("User");
        Log.d("User name", user.getName());

        textView = (TextView) findViewById(R.id.textView2);
        textView.setText(user.getName());

        // TODO: criar listinha com opções de curso e de matriz curricular

    }

}
