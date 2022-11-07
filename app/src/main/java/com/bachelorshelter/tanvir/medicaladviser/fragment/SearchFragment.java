package com.bachelorshelter.tanvir.medicaladviser.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.bachelorshelter.tanvir.medicaladviser.R;
import com.bachelorshelter.tanvir.medicaladviser.activities.SearchActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    private Button operation,test,bloodBank;
    private EditText search;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        // Inflate the layout for this fragment

        operation = (Button)rootView.findViewById(R.id.operation);
        test = (Button)rootView.findViewById(R.id.test);
        bloodBank = (Button)rootView.findViewById(R.id.blood_bank);


        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SearchActivity.class);
                intent.putExtra("searchType","0");
                startActivity(intent);
            }
        });

        operation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SearchActivity.class);
                intent.putExtra("searchType","1");
                startActivity(intent);
            }
        });

        search = (EditText)rootView.findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SearchActivity.class);
                intent.putExtra("searchType","0");
                startActivity(intent);
            }
        });



        bloodBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SearchActivity.class);
                intent.putExtra("searchType","2");
                startActivity(intent);
            }
        });

        return rootView;
    }

}
