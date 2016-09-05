package externaldata;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Luisa on 05/09/2016.
 */
public class SIGAADataRequester {

    private static SIGAADataRequester instance = new SIGAADataRequester();

    private SIGAADataRequester() {}

    public static SIGAADataRequester getInstance() {
        return instance;
    }

    private RequestQueue requestQueue;

    public void createRequestQueue(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

    public void requestData(final Response.Listener<String> listener, String url) {
        requestData(url, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("OUT", "Error: " + error.getMessage());
            }
        });
    }

    private void requestData(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, listener, errorListener) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                String auth = "Bearer "+ SIGAAAuthorizationRequester.getInstance().getAccessToken();
                headers.put("Authorization", auth);
                return headers;
            }
        };

        // Add the request to the RequestQueue.
        requestQueue.add(stringRequest);
    }

}
