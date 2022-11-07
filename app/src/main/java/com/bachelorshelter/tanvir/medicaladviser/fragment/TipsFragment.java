package com.bachelorshelter.tanvir.medicaladviser.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.bachelorshelter.tanvir.medicaladviser.R;
import com.bachelorshelter.tanvir.medicaladviser.activities.TipsFullActivity;
import com.bachelorshelter.tanvir.medicaladviser.adapter.TipsAdapter;
import com.bachelorshelter.tanvir.medicaladviser.app.AppConfig;
import com.bachelorshelter.tanvir.medicaladviser.app.AppController;
import com.bachelorshelter.tanvir.medicaladviser.model.TipsItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TipsFragment extends Fragment implements TipsAdapter.TipsItemClickCallback {

    private RecyclerView recView;
    private TipsAdapter adapter;
    private ArrayList<TipsItem> tipsItems;
    private ArrayList<TipsItem> forSynchronisePosition;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_tips, container, false);


        forSynchronisePosition = new ArrayList<>();
        tipsItems = new ArrayList<>();
        recView = (RecyclerView)rootView.findViewById(R.id.rec_list_f_tips);
        recView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new TipsAdapter(tipsItems,getContext());
        recView.setAdapter(adapter);
        adapter.setTipsItemClickCallback(this);

        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout_tips);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchTips();
            }
        });

        fetchTips();


        return rootView;
    }

    private void fetchTips(){
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, AppConfig.GET_TIPS,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                tipsItems.clear();

                for (int i = 0; i < response.length(); i++) {
                    try{
                        JSONObject object = response.getJSONObject(i);
                        TipsItem tipsItem = new TipsItem();
                        tipsItem.setTipsId(object.getString("tips_id"));
                        tipsItem.setHeadline(object.getString("headline"));
                        tipsItem.setDescription(object.getString("description"));

                        tipsItems.add(tipsItem);
                    }
                    catch (JSONException e){
                        //Log.e(TAG, "Json parsing error: " + e.getMessage());
                    }
                }
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                swipeRefreshLayout.setRefreshing(false);
            }

        });
        AppController.getInstance().addToRequestQueue(req);
    }

    @Override
    public void onItemClick(int p, View view) {
        final TipsItem item = tipsItems.get(p);
        Intent intent = new Intent(getActivity(), TipsFullActivity.class);
        intent.putExtra("tips_id",item.getTipsId());
        intent.putExtra("headline",item.getHeadline());
        startActivity(intent);
    }
}
