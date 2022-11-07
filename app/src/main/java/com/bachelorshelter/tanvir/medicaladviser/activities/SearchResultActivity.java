package com.bachelorshelter.tanvir.medicaladviser.activities;

import android.content.Intent;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bachelorshelter.tanvir.medicaladviser.R;
import com.bachelorshelter.tanvir.medicaladviser.adapter.SearchResultAdapter;
import com.bachelorshelter.tanvir.medicaladviser.app.AppConfig;
import com.bachelorshelter.tanvir.medicaladviser.app.AppController;
import com.bachelorshelter.tanvir.medicaladviser.model.SearchResultItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchResultActivity extends AppCompatActivity implements SearchResultAdapter.SearchResultItemClickCallback,SearchView.OnQueryTextListener {

    private String searchItem;
    private String[] stt;

    private RecyclerView recView;
    private SearchResultAdapter adapter;
    private ArrayList<SearchResultItem> searchResultItems;
    private ArrayList<SearchResultItem> forSynchronisePosition;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            searchItem = extras.getString("searchItem");
        }
        setContentView(R.layout.activity_search_result);

        //Toast.makeText(getApplicationContext(),searchItem,Toast.LENGTH_SHORT).show();


        getSupportActionBar().setTitle("Search Result");

        final ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        stt = searchItem.split(",");
        //Toast.makeText(getApplicationContext(),stt[0],Toast.LENGTH_SHORT).show();


        forSynchronisePosition = new ArrayList<>();
        searchResultItems = new ArrayList<>();
        recView = (RecyclerView)findViewById(R.id.rec_list_f_search_result);
        recView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SearchResultAdapter(searchResultItems,this);
        recView.setAdapter(adapter);
        adapter.setSearchResultItemClickCallback(this);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout_search_result);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchSearch("has_test_price");
            }
        });

        fetchSearch("has_test_price");

        //fetchSearch();

    }



    private void fetchSearch(final String sort){

        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.GET_SEARCH_RESULT, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {


                try {
                    searchResultItems.clear();

                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0; i <jsonArray.length() ; i++) {

                        JSONObject object = jsonArray.getJSONObject(i);
                        SearchResultItem searchResultItem = new SearchResultItem();
                        searchResultItem.setName(object.getString("name"));
                        searchResultItem.setAddress(object.getString("address"));
                        searchResultItem.setPhone(object.getString("phone"));
                        searchResultItem.setTest_name(object.getString("test_name"));
                        searchResultItem.setPrice(object.getString("price"));
                        searchResultItem.setDelivery_time(object.getString("delivery_time"));

                        searchResultItems.add(searchResultItem);
                    }


                } catch (JSONException e) {
                    //e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

                forSynchronisePosition = searchResultItems;
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                //hideDialog();
            }
        })

        {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();
                params.put("category", stt[0]);
                params.put("what", stt[1]);
                params.put("where", stt[2]);
                params.put("sort", sort);
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.just_search_menu, menu);
        return true;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case R.id.action_search:
                SearchView searchView = (SearchView) item.getActionView();
                searchView.setOnQueryTextListener(this);
                return true;
            case R.id.action_edit_location:
                openSearchFilterDialog();
                return true;
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemClick(int p, View view) {
        final SearchResultItem item = forSynchronisePosition.get(p);
        PopupMenu popupMenu = new PopupMenu(view.getContext(),view);
        popupMenu.getMenuInflater().inflate(R.menu.add_review, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.popup_menu_add_review) {
                    Intent intent = new Intent(SearchResultActivity.this,AddReviewActivity.class);
                    intent.putExtra("Name",item.getName());
                    startActivity(intent);
                    return true;
                }

                return onMenuItemClick(menuItem);
            }
        });
        popupMenu.show();
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        newText = newText.toLowerCase();
        ArrayList<SearchResultItem> newList = new ArrayList<>();
        for (SearchResultItem searchResultItem:searchResultItems) {
            if(searchResultItem.getName().toLowerCase().contains(newText)){
                newList.add(searchResultItem);
            }
        }
        forSynchronisePosition = newList;
        adapter.setFilter(newList);
        return true;
    }


    private void openSearchFilterDialog(){


        final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(swipeRefreshLayout.getContext());
        final LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_f_search_sheet, null);
        Button btn_dialog_bottom_sheet_ok = (Button) dialogView.findViewById(R.id.btn_dialog_bottom_sheet_ok);
        Button btn_dialog_bottom_sheet_cancel = (Button) dialogView.findViewById(R.id.btn_dialog_bottom_sheet_cancel);
        final Spinner spinner = (Spinner)dialogView.findViewById(R.id.spinner) ;
        mBottomSheetDialog.setContentView(dialogView);

        btn_dialog_bottom_sheet_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(spinner.getSelectedItem().toString().equals("Investigation Cost")){
                    fetchSearch("has_test_price");

                }
                else {
                    fetchSearch("has_test_delivery_time");
                }
                mBottomSheetDialog.dismiss();
            }
        });
        btn_dialog_bottom_sheet_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
            }
        });
        mBottomSheetDialog.show();

    }


}
