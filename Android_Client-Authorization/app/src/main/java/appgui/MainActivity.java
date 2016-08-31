package appgui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import appcore.*;
import datarepresentation.Student;
import externaldata.*;
import ufrn.br.oauthandroid.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private Student user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DataReceiver receiver = new DataReceiver() {
            public void onReceive(Student student) {
                user = student;
                printPlanning();
            }
        };
        ApplicationCore.getInstance().getStudent(receiver);
    }

    private void printPlanning() {
        // TODO
        System.out.println("CHEGOU ATÉ AQUI. VALOR DO USER: " + user);
    }

    public void login() {
        Intent i = new Intent(this, ResultActivity.class);
        DataRequest.getInstance().initializeAccess(this, i);
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
