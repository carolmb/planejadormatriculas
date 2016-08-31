package ufrn.br.oauthandroid;

import externaldata.*;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void login(View v) {
        Intent i = new Intent(this, MainActivity.class);
        DataRequest.getInstance().inicializeAccess(this, i);
    }

    public void getData(View v){
        ActionRequest action = new ActionRequest() {
            @Override
            public void run(String response) {
                try {
                    JSONArray jsonObject = new JSONArray(response);
                    JSONtoRequirements(response.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        SIGAAServerAccessor.getSIGAAServerAccessor().getRequirements(action, "TECNOLOGIA DA INFORMAÇÃO", 2014, 1, this.getBaseContext());
        //Intent intent = new Intent(this, SolicitationInfo.class);
        //intent.putExtra("token", credential.getAccessToken());
        //startActivity(intent);
    }

    private void JSONtoRequirements(String json) {
        System.out.print(json);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void logout(View view) {
        DataRequest.getInstance().logout(this, "http://apitestes.info.ufrn.br/sso-server/logout");
    }
}
