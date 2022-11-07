package com.bachelorshelter.tanvir.medicaladviser.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bachelorshelter.tanvir.medicaladviser.R;
import com.bachelorshelter.tanvir.medicaladviser.app.AppConfig;
import com.bachelorshelter.tanvir.medicaladviser.app.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;

public class TipsFullActivity extends AppCompatActivity {

    private String id,headline;
    private TextView desc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            id = extras.getString("tips_id");
            headline = extras.getString("headline");

        }
        setContentView(R.layout.activity_tips_full);

        //Toast.makeText(getApplicationContext(),id,Toast.LENGTH_SHORT).show();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(headline);

        desc = (TextView)findViewById(R.id.desc);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Like it.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                finish();
            }
        });
        fetchDescription(id);
    }

    private void fetchDescription(final String id){

        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.GET_TIPS_DESC, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Checking Response: " + response);
                //hideDialog();
                desc.setText(response);

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                //hideDialog();
            }
        })

        {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();
                params.put("tips_id", id);
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq);

    }

}


