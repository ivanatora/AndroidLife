package com.example.ivanatora.life;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.GridLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.HashMap;
import java.util.Map;

public class DailyStatsActivity extends AppCompatActivity {
    private static final String TAG = "Life1.0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_stats);

        try {

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String URL = "http://life.ivanatora.info/admin/daily_stats/list_categories";


            JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, URL, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            JSONArray jsonArray = null;
                            Log.v(TAG, "JSON Response: " + response.toString());
                            try {
                                GridLayout layout = (GridLayout) findViewById(R.id.layoutParentDailyStats);
                                layout.removeAllViews();

                                GridLayout.LayoutParams mRparams = new GridLayout.LayoutParams();
                                EditText myEditText = new EditText(DailyStatsActivity.this);
                                myEditText.setLayoutParams(mRparams);
                                layout.addView(myEditText);

                                jsonArray = response.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                                    String sTitle = jsonObject.getString("title");
                                    Integer id = jsonObject.getInt("id");
                                    Log.d(TAG, sTitle+": " + id.toString());
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG, "Error: " + error.getMessage());
                }
            });


            requestQueue.add(req);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
