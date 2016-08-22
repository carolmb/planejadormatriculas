package ufrn.br.oauthandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;

import br.ufrn.oauth.android.request.OAuthTokenRequest;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void logar(View v) {
        Intent i = new Intent(this, ResultActivity.class);
        OAuthTokenRequest.getInstance().getTokenCredential(this,"http://apitestes.info.ufrn.br/authz-server","client_id", "client_secret", i);
    }

    public void obterDados(View v){
        Intent intent = new Intent(this, ResultActivity.class);
        //intent.putExtra("token", credential.getAccessToken());
        startActivity(intent);
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

    public void sair(View view) {
        OAuthTokenRequest.getInstance().logout(this, "http://apitestes.info.ufrn.br/sso-server/logout");
    }
}
