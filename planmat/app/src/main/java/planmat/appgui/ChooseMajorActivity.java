package planmat.appgui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Response;

import planmat.datarepresentation.User;
import planmat.internaldata.UserPrefs;
import planmat.internaldata.UserPrefsAccessor;
import ufrn.br.planmat.R;

public class ChooseMajorActivity extends AppCompatActivity {

    private TextView textView;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_major);

        user = (User) getIntent().getSerializableExtra("User");
        Log.d("User name", user.getName());

        textView = (TextView) findViewById(R.id.textView2);
        textView.setText(user.getName());

        // TODO: criar listinha com opções de curso e de matriz curricular
    }

    /**
     * Guardar os IDs do curso e da matriz curricular num UserPrefs e
     * carrega a PlanningActivity.
     * @param view o view que faz a chamada ao método
     */
    public void buttonOK(View view) {
        // TODO: pegar IDs dos curso e matriz curricular escolhidos
        UserPrefs userPrefs = new UserPrefs(user.getName(), user.getID(), -1, -1);
        UserPrefsAccessor.getInstance().storeUserPrefs(userPrefs);
        Intent i = new Intent(this, PlanningActivity.class);
        i.putExtra("UserPrefs", userPrefs);
        startActivity(i);
    }

}
