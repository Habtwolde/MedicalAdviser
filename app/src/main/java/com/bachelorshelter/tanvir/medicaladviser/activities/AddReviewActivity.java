package com.bachelorshelter.tanvir.medicaladviser.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.SearchView;
import android.view.MenuItem;
import android.widget.TextView;

import com.bachelorshelter.tanvir.medicaladviser.R;

public class AddReviewActivity extends AppCompatActivity {
    private String nn;
    private TextView nnaa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            nn = extras.getString("Name");
        }
        setContentView(R.layout.activity_add_review);

        getSupportActionBar().setTitle("Add Review");

        nnaa = (TextView) findViewById(R.id.tvName);

        nnaa.setText(nn);

        final ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
