package appgui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;

import org.json.JSONException;
import org.json.JSONObject;

import externaldata.DataRequest;
import ufrn.br.oauthandroid.R;

public class ResultActivity extends AppCompatActivity {

    // temporary string to show the parsed response
    private String jsonResponse;

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

        reqJson();

        text = (TextView) findViewById(R.id.textoJson);

    }

    private void reqJson(){

        String urlJsonObj = "http://apitestes.info.ufrn.br/usuario-services/services/usuario/info";
        DataRequest.getInstance().resourceRequest(this, urlJsonObj, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String username = jsonObject.getString("username");
                    String name = jsonObject.getString("pessoa");

                    jsonResponse = "";
                    jsonResponse += "Username: " + username + "\n\n";
                    jsonResponse += "Name: " + name + "\n\n";

                    text.setText(jsonResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("SAIDA", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
            }
        });
    }

}
