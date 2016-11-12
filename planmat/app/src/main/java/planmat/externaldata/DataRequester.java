package planmat.externaldata;

import android.content.Context;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by Luisa on 09/11/2016.
 */
public class DataRequester {

    private RequestQueue requestQueue;
    private String bearerString;
    private String authString;

    public DataRequester(String bearerString, String authString) {
        this.bearerString = bearerString;
        this.authString = authString;
    }

    public void createRequestQueue(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

    public String requestData(final String accessToken, final String url) {
        RequestThread thread = new RequestThread();
        thread.execute(url, bearerString + accessToken, authString);
        try {
            return thread.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    private class RequestThread extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            final String url = params[0];
            final String tokenString = params[1];
            final String authString = params[2];
            RequestFuture<String> future = RequestFuture.newFuture();
            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            };
            Log.d("URL", url);
            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, future, errorListener) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    String auth = tokenString;
                    headers.put(authString, auth);
                    return headers;
                }
            };
            // Add the request to the RequestQueue.
            requestQueue.add(stringRequest);
            try {
                String response = future.get(3, TimeUnit.SECONDS);
                Log.d("Successful fetch", response);
                return response;
            } catch (InterruptedException e) {
                Log.d("Failed fetch", e.getMessage());
                return null;
            } catch (ExecutionException e) {
                Log.d("Failed fetch", e.getMessage());
                return null;
            } catch (TimeoutException e) {
                Log.d("Failed fetch", "Time out");
                return null;
            }
        }
    }

}
