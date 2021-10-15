package com.example.blood.Hospital;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.blood.R;
import com.example.blood.RecyclerViewSize.InsertDivider;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchHospitalActivity extends AppCompatActivity {

    private String selectedName = "";
    private HospitalApi hospitalApi;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<datum> datumArrayList;

    private ProgressBar progressBar;


    private SearchHospitalAdapter searchHospitalAdapter;

    private boolean isScrolling = false;

    private int pageNo = 1;
    private int total_page=0;
    private int current_items,total_items,scroll_out_items; //for scrolling

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_hospital);

        progressBar = findViewById(R.id.progressBar);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
            }
        });

        recyclerView = findViewById(R.id.RecyclerView1);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        InsertDivider insertDivider = new InsertDivider(10);
        recyclerView.addItemDecoration(insertDivider);
        datumArrayList = new ArrayList<>();

        searchHospitalAdapter = new SearchHospitalAdapter(datumArrayList,"total_beds");
        recyclerView.setAdapter(searchHospitalAdapter);
        searchHospitalAdapter.notifyDataSetChanged();



        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                current_items = linearLayoutManager.getChildCount();
                total_items = linearLayoutManager.getItemCount();
                scroll_out_items = linearLayoutManager.findLastCompletelyVisibleItemPosition();

                if(isScrolling && (current_items + scroll_out_items >= total_items))
                {
                    isScrolling = false;
                    fetchmoreData();
                }
            }
        });





        hospitalApi = RetrofitInstanceHospital.getRetrofit().create(HospitalApi.class);
        hospitalApi.searchByDistrict(selectedName).enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                if(response.isSuccessful())
                {
                    //got all hospitals
                    int current_page = response.body().getHospitals().getCurrent_page();
                    int per_page = response.body().getHospitals().getPer_page();
                    int total_item = response.body().getHospitals().getTotal();
                    total_page = (int) Math.ceil(total_item/per_page);
                    System.out.println("Total page: "+total_page);
                    for(int i=0;i<response.body().getHospitals().getData().size();i++)
                    {
                        datumArrayList.add(response.body().getHospitals().getData().get(i));
                    }
                    searchHospitalAdapter.notifyDataSetChanged();

                }
                else{

                }
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                Toast.makeText(SearchHospitalActivity.this, "No Result Found", Toast.LENGTH_SHORT).show();

            }
        });

        searchHospitalAdapter.setOnItemClickListener(new SearchHospitalAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(SearchHospitalActivity.this,HospitalDetailsActivity.class);
                System.out.println("Last update time: "+datumArrayList.get(position).getUpdated_at());
                intent.putExtra("hospital",datumArrayList.get(position));
                startActivity(intent);
                /*
                Geocoder geocoder;
                List<Address> addresses = null;
                geocoder = new Geocoder(SearchHospitalActivity.this, Locale.getDefault());

                try {
                    addresses = geocoder.getFromLocation(23.4515415, 91.2029603, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();
                System.out.println(address +city + state+"" +knownName);*/
            }
        });






    }


    public void fetchmoreData()
    {
        //hospitalApi.searchByDistrictPage("Dhaka",)
        pageNo++;
        progressBar.setVisibility(View.VISIBLE);

        if(pageNo<=total_page)
        {
            System.out.println("Fetching data page: "+pageNo);

            hospitalApi.searchByDistrictPage(selectedName,pageNo).enqueue(new Callback<Root>() {
                @Override
                public void onResponse(Call<Root> call, Response<Root> response) {
                    if(response.isSuccessful())
                    {
                        for(int i=0;i<response.body().getHospitals().getData().size();i++)
                        {
                            datumArrayList.add(response.body().getHospitals().getData().get(i));
                            searchHospitalAdapter.notifyDataSetChanged();
                        }
                        progressBar.setVisibility(View.GONE);

                    }
                }

                @Override
                public void onFailure(Call<Root> call, Throwable t) {

                }
            });
        }
        }


        public void fetchData()
        {
            datumArrayList.clear();
            searchHospitalAdapter.notifyDataSetChanged();

            hospitalApi.searchByDistrict(selectedName).enqueue(new Callback<Root>() {
                @Override
                public void onResponse(Call<Root> call, Response<Root> response) {
                    if(response.isSuccessful())
                    {
                        //got all hospitals
                        int current_page = response.body().getHospitals().getCurrent_page();
                        double per_page = response.body().getHospitals().getPer_page();
                        double total_item = response.body().getHospitals().getTotal();
                        System.out.println(per_page + "" +total_item);
                        total_page = (int) Math.ceil(total_item/per_page);
                        pageNo = 1;
                        System.out.println("Total page: "+total_page);
                        for(int i=0;i<response.body().getHospitals().getData().size();i++)
                        {
                            datumArrayList.add(response.body().getHospitals().getData().get(i));
                        }
                        searchHospitalAdapter.notifyDataSetChanged();

                    }
                    else{

                    }
                }

                @Override
                public void onFailure(Call<Root> call, Throwable t) {
                    Toast.makeText(SearchHospitalActivity.this, "No Result Found", Toast.LENGTH_SHORT).show();

                }
            });


        }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search by district or hospital");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                selectedName = query;
                fetchData();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // System.out.println("CHANGED");
                return false;
            }
        });
        return true;
    }

}