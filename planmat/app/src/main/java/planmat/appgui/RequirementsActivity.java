package planmat.appgui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import planmat.datarepresentation.Requirements;
import ufrn.br.planmat.R;

public class RequirementsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requirements);

        Requirements requirements = (Requirements) getIntent().getSerializableExtra("Requirements");
        Log.d("Requirements ID", "" + requirements.getID());

        // TODO: mostrar os componentes da estrutura curricular
    }

}
