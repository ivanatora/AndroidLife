package com.example.ivanatora.life;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.util.HashMap;
import java.util.Map;

import com.example.ivanatora.life.DashboardActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Life1.0";
    public static final String PREFS_NAME = "Life1.0_PrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String sEmail = settings.getString("email", "");
        String sPassword = settings.getString("password", "");

        ((EditText) findViewById(R.id.fldEmail)).setText(sEmail);
        ((EditText) findViewById(R.id.fldPassword)).setText(sPassword);

    }

    protected void login(View view)
    {
        final String sEmail = ((EditText) findViewById(R.id.fldEmail)).getText().toString().trim();
        final String sPassword = ((EditText) findViewById(R.id.fldPassword)).getText().toString().trim();

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("email", sEmail);
        editor.putString("password", sPassword);
        editor.commit();

        try {
            CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));


            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String URL = "http://life.ivanatora.info/admin/users/login";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("VOLLEY", "response: " + response);
                    Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                    startActivity(intent);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", "error: " + error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

//                @Override
//                protected Response<String> parseNetworkResponse(NetworkResponse response) {
//                    String responseString = "";
//                    if (response != null) {
//                        responseString = String.valueOf(response.statusCode);
//                        Log.v(TAG, "got response: " + response.headers.toString());
//                        Log.v(TAG, "data: " + response.data.toString());
//                        // can get more details such as response.headers
//                    }
//                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
//                }

                @Override
                protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("User[username]", sEmail);
                    params.put("User[password]", sPassword);
                    params.put("return_ajax", "1");
                    Log.v(TAG, "params: " + params.toString());
                    return params;
                };

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("Content-Type","application/x-www-form-urlencoded");
                    return params;
                }
            };

            requestQueue.add(stringRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
