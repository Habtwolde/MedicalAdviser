package com.bachelorshelter.tanvir.medicaladviser.activities;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bachelorshelter.tanvir.medicaladviser.R;

public class SearchActivity extends AppCompatActivity {

    private EditText where,what;
    private Toolbar toolbar;
    private Spinner actionType;
    String searchType;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            searchType = extras.getString("searchType");
        }
        setContentView(R.layout.activity_search);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        final ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        actionType = (Spinner)findViewById(R.id.spinner_nav);
        actionType.setSelection(Integer.parseInt(searchType));



        what = (EditText)findViewById(R.id.what);
        where = (EditText)findViewById(R.id.where);

        where.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    if (actionType.getSelectedItem().toString().equals("Blood Bank")){

                        String searchItem = actionType.getSelectedItem().toString()+","+"HIV"+","+where.getText();
                        //Toast.makeText(getApplicationContext(),ext,Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(SearchActivity.this,SearchResultActivity.class);
                        intent.putExtra("searchItem",searchItem);
                        startActivity(intent);
                        //finish();
                    }
                    else {
                        String searchItem = actionType.getSelectedItem().toString()+","+what.getText()+","+where.getText();
                        //Toast.makeText(getApplicationContext(),ext,Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(SearchActivity.this,SearchResultActivity.class);
                        intent.putExtra("searchItem",searchItem);
                        startActivity(intent);
                        //finish();
                    }


                    return true;
                }
                return false;
            }
        });



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
