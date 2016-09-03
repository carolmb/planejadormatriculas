package appgui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import appcore.ApplicationCore;
import externaldata.DataRequest;
import ufrn.br.oauthandroid.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        login();
    }

    public void login() {
        Intent i = new Intent(this, ResultActivity.class);
        ApplicationCore.getInstance().login(this, i);
    }

    public void getData(View v){
        Intent intent = new Intent(this, ResultActivity.class);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void logout(View view) {
        DataRequest.getInstance().logout(this, "http://apitestes.info.ufrn.br/sso-server/logout");
    }
}
