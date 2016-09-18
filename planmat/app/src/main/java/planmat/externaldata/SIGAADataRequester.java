package planmat.externaldata;

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

class SIGAADataRequester {

    private RequestQueue requestQueue;

    public void createRequestQueue(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

    public void requestData(final String accessToken, final Response.Listener<String> listener, String url) {
        requestData(url, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("OUT", "Error: " + error.getMessage());
            }
        }, accessToken);
    }

    private void requestData(String url, Response.Listener<String> listener, Response.ErrorListener errorListener, final String accessToken) {

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, listener, errorListener) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                String auth = "Bearer "+ accessToken;
                headers.put("Authorization", auth);
                return headers;
            }
        };

        // Add the request to the RequestQueue.
        requestQueue.add(stringRequest);
    }

}
