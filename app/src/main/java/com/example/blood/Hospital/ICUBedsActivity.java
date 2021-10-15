package com.example.blood.Hospital;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blood.R;
import com.example.blood.RecyclerViewSize.InsertDivider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


//Though name of this class is ICU, It works for all types of Beds


public class ICUBedsActivity extends AppCompatActivity {

    private HospitalApi hospitalApi;
    private SearchHospitalAdapter searchHospitalAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<datum> datumArrayList;

    private String selectedName = "";

    private ProgressBar progressBar;

    private boolean isScrolling = false;

    private int pageNo = 1;
    private int total_page=0;
    private int current_items,total_items,scroll_out_items;


    private TextView textView;
    private String[] districtsArrayList = new String[]{"Dhaka", "Kishoregonj","Bagerhat","Bandarban","Barguna","Barishal","Bhola","Bogura","Brahmanbaria","Chandpur",
            "Chapai Nawabganj","Chattogram","Chuadanga","Comilla","Cox's Bazar","Dinajpur","Faridpur","Feni","Gaibandha","Gazipur",
            "Gopalganj","Habiganj","Jamalpur","Jashor","Jhalokathi","Jhenaidah","Joypurhat",
            "Khagrachhari","Khulna","Kishoreganj","Kurigram","Kushtia","Laksmipur",
            "Lalmonirhat","Madaripur","Magura","Manikganj","Maulavi Bazar","Meherpur",
            "Munshiganj","Mymensingh","Naogaon","Narail","Narayanganj","Narsingdi","Natore",
            "Netrokona","Nilphamari","Noakhali","Pabna","Panchaghar","Patuakhali","Pirojpur","Rajbari",
            "Rajshahi","Rangamati","Rangpur","Satkhira","Shariatpur","Sherpur","Sirajganj","Sunamganj",
            "Sylhet","Tangail","Thakurgaon"};

    private boolean[] selectedDistrict;
    ArrayList<Integer> selectedDistrictPosition;

    private String bedtypes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icubeds);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        bedtypes = getIntent().getStringExtra("bed-type");

        if(bedtypes.equalsIgnoreCase("icu_beds"))
        {
            toolbar.setTitle("ICU Beds");
        }
        else if(bedtypes.equalsIgnoreCase("hfn_beds"))
        {
            toolbar.setTitle("High Flow Nasal Canula Beds");
        }
        else if(bedtypes.equalsIgnoreCase("hdu_beds"))
        {
            toolbar.setTitle("High Dependency Unit Beds");

        }
        else
        {
            toolbar.setTitle("General Beds");
        }


        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
            }
        });


        progressBar = new ProgressBar(this);

        selectedDistrictPosition =  new ArrayList<>();


        recyclerView = findViewById(R.id.RecyclerView1);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        InsertDivider insertDivider = new InsertDivider(10);
        recyclerView.addItemDecoration(insertDivider);
        datumArrayList = new ArrayList<>();

        searchHospitalAdapter = new SearchHospitalAdapter(datumArrayList,bedtypes);
        recyclerView.setAdapter(searchHospitalAdapter);
        searchHospitalAdapter.notifyDataSetChanged();

        selectedDistrict = new boolean[districtsArrayList.length];


        textView = findViewById(R.id.tv_district);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ICUBedsActivity.this);
                builder.setTitle("Select District");

                builder.setCancelable(false);
                builder.setMultiChoiceItems(districtsArrayList, selectedDistrict, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        if(b)
                        {
                            selectedDistrictPosition.add(i);
                            Collections.sort(selectedDistrictPosition);
                        }
                        else{
                            selectedDistrictPosition.remove(new Integer(i));
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        StringBuilder stringBuilder = new StringBuilder();
                        for(int j=0;j<selectedDistrictPosition.size();j++)
                        {
                            stringBuilder.append(districtsArrayList[selectedDistrictPosition.get(j)]);

                            if(j!=selectedDistrictPosition.size()-1)
                            {
                                stringBuilder.append(",");
                            }
                        }
                        textView.setText(stringBuilder.toString());
                        selectedName = stringBuilder.toString();
                        fetchData();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for(int j=0;j<selectedDistrict.length;j++)
                        {
                            selectedDistrict[j]=false;
                            selectedDistrictPosition.clear();
                            textView.setText("");
                        }

                        selectedName = "";
                        pageNo=1;
                        fetchData();
                    }
                });

                builder.show();
            }
        });



        searchHospitalAdapter.setOnItemClickListener(new SearchHospitalAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(ICUBedsActivity.this,HospitalDetailsActivity.class);
                intent.putExtra("hospital",datumArrayList.get(position));
                startActivity(intent);
            }
        });






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
        hospitalApi.searchByMultipleDistrictsAndTypes(selectedName, 1,bedtypes,"last_update").enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                if(response.isSuccessful())
                {
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
                    Toast.makeText(ICUBedsActivity.this,"No Data Available",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                Toast.makeText(ICUBedsActivity.this, "No Result Found", Toast.LENGTH_SHORT).show();
            }
        });


    }


    public void fetchData()
    {
        datumArrayList.clear();
        searchHospitalAdapter.notifyDataSetChanged();

        hospitalApi.searchByMultipleDistrictsAndTypes(selectedName,1,bedtypes,"last_update").enqueue(new Callback<Root>() {
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
                    Toast.makeText(ICUBedsActivity.this,"No Data Available",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                Toast.makeText(ICUBedsActivity.this, "No Result Found", Toast.LENGTH_SHORT).show();

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
            System.out.println("Current page No:"+pageNo);
            hospitalApi.searchByMultipleDistrictsAndTypes(selectedName,pageNo,bedtypes,"last_update").enqueue(new Callback<Root>() {
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
                    else{
                        Toast.makeText(ICUBedsActivity.this,"No Data Available",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Root> call, Throwable t) {

                }
            });
        }
    }

}