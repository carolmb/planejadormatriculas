package planmat.appgui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Response;

import java.io.Serializable;

import planmat.appcore.ApplicationCore;
import planmat.datarepresentation.User;
import ufrn.br.planmat.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        login();
    }

    private void login() {
        final Intent i = new Intent(this, ChooseMajorActivity.class);
        Response.Listener<User> listener = new Response.Listener<User>() {
            @Override
            public void onResponse(User response) {
                if (response != null) {
                    i.putExtra("User", (Serializable) response);
                    finish();
                    startActivity(i);
                }
            }
        };
        ApplicationCore.getInstance().login(this, listener);
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

}
