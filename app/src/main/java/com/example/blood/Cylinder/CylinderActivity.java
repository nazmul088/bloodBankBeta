package com.example.blood.Cylinder;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.blood.Ambulance;
import com.example.blood.AmbulanceActivity;
import com.example.blood.District;
import com.example.blood.DistrictApi;
import com.example.blood.Division;
import com.example.blood.DivisionApi;
import com.example.blood.R;
import com.example.blood.RecyclerViewSize.InsertDivider;
import com.example.blood.RetrofitInstanceArea;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CylinderActivity extends AppCompatActivity {


    private Spinner spinner;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    private DivisionApi divisionApi;
    private DistrictApi districtApi;
    private CylinderApi cylinderApi;


    private ArrayList<Division> divisions;
    private ArrayList<District> districts;

    private ArrayList<String> divisionsArrayList;
    private ArrayList<String> districtsArrayList;

    private int selectedDivisionPosition;
    private String selectedDivisionCode,  selectedDistrictCode;
    private String selectedName;
    private String selectedPhoneNumbertoCall = "";

    private CylinderSearchAdapter cylinderSearchAdapter;
    private ArrayList<Cylinder> cylinderSearchArrayList;


    private boolean isScrolling = false;

    private int pageNo = 1;
    private int current_items,total_items,scroll_out_items;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulance);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Cylinder");
        getSupportActionBar().setDisplayShowTitleEnabled(true);



        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
            }
        });

        selectedDivisionCode = "";
        selectedDistrictCode = "";
        selectedName = "";

        recyclerView = findViewById(R.id.RecyclerView1);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        InsertDivider insertDivider = new InsertDivider(10);
        recyclerView.addItemDecoration(insertDivider);

        divisions = new ArrayList<>();
        districts = new ArrayList<>();
        cylinderSearchArrayList= new ArrayList<>();

        cylinderSearchAdapter = new CylinderSearchAdapter( cylinderSearchArrayList);
        recyclerView.setAdapter(cylinderSearchAdapter);
        cylinderSearchAdapter.notifyDataSetChanged();

        cylinderSearchAdapter.setOnItemClickListener(new CylinderSearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }

            @Override
            public void onButtonClick(int position) {
                selectedPhoneNumbertoCall = cylinderSearchArrayList.get(position).getContactNo();
                makePhoneCall();
            }
        });

        ProgressDialog progressDialog = new ProgressDialog(CylinderActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();


        divisionApi = RetrofitInstanceArea.getRetrofit().create(DivisionApi.class);
        divisionApi.getDivisions().enqueue(new Callback<List<Division>>() {
            @Override
            public void onResponse(Call<List<Division>> call, Response<List<Division>> response) {
                System.out.println("Response: " + response);
                if (response.body().size() > 0) {
                    for (int i = 0; i < response.body().size(); i++) {
                        divisions.add(response.body().get(i));
                    }
                    progressDialog.dismiss();
                    showDivisionSpinner();
                } else {
                    progressDialog.dismiss();

                }

            }

            @Override
            public void onFailure(Call<List<Division>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(CylinderActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });

        districtApi = RetrofitInstanceArea.getRetrofit().create(DistrictApi.class);
        districtApi.getDistricts().enqueue(new Callback<List<District>>() {
            @Override
            public void onResponse(Call<List<District>> call, Response<List<District>> response) {

                if (response.body().size() > 0) {
                    for (int i = 0; i < response.body().size(); i++) {
                        districts.add(response.body().get(i));
                    }
                    progressDialog.dismiss();
                } else {

                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<District>> call, Throwable t) {
                progressDialog.dismiss();
            }
        });

        cylinderApi = RetrofitInstanceCylinder.getRetrofit().create(CylinderApi.class);


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
    }

    public void fetchData()
    {
        pageNo = 1;
        System.out.println("Called");

        cylinderApi.getCylinderByNameDivisionDistrict(selectedName,selectedDivisionCode,selectedDistrictCode,"1").enqueue(new Callback<List<Cylinder>>() {
            @Override
            public void onResponse(Call<List<Cylinder>> call, Response<List<Cylinder>> response) {

                if(response.isSuccessful())
                {
                    if(response.body().size()==0)
                    {
                        cylinderSearchArrayList.clear();
                        cylinderSearchAdapter.notifyDataSetChanged();
                    }
                    else{
                        cylinderSearchArrayList.clear();
                        // userSearchAdapter.notifyDataSetChanged();
                        for(int i=0;i<response.body().size();i++)
                        {
                            cylinderSearchArrayList.add(response.body().get(i));
                        }
                        cylinderSearchAdapter.notifyDataSetChanged();

                        System.out.println("Data showed"+ ""+"Size: "+cylinderSearchArrayList.size());
                    }
                }

            }

            @Override
            public void onFailure(Call<List<Cylinder>> call, Throwable t) {

            }
        });
    }




    public void fetchmoreData()
    {

        pageNo++;
        cylinderApi.getCylinderByNameDivisionDistrict(selectedName,selectedDivisionCode,selectedDistrictCode,String.valueOf(pageNo)).enqueue(new Callback<List<Cylinder>>() {
            @Override
            public void onResponse(Call<List<Cylinder>> call, Response<List<Cylinder>> response) {
                if(response.isSuccessful())
                {
                    if(response.body().size()==0)
                    {
                        //Toast.makeText(SearchBloodActivity.this,"No More Data Found",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    System.out.println("Page No: "+pageNo);
                    for(int i=0;i<response.body().size();i++)
                    {
                        cylinderSearchArrayList.add(response.body().get(i));
                        cylinderSearchAdapter.notifyDataSetChanged();
                    }
                }
                else{
                    System.out.println("No Data found");

                }
            }

            @Override
            public void onFailure(Call<List<Cylinder>> call, Throwable t) {

            }
        });

    }




    private void makePhoneCall() {
        if (ContextCompat.checkSelfPermission(CylinderActivity.this, Manifest.permission.CALL_PHONE) !=
                PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(CylinderActivity.this,"Allow to call",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri = Uri.fromParts("package", getPackageName(), null);
            intent.setData(uri);
            startActivity(intent);
            /*ActivityCompat.requestPermissions(AmbulanceActivity.this, new String[]{
                    Manifest.permission.CALL_PHONE
            }, REQUEST_CALL_PERMISSION);*/
        } else {

            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + selectedPhoneNumbertoCall));
            startActivity(intent);
        }

    }

    private void showDivisionSpinner() {
        divisionsArrayList = new ArrayList<>();
        divisionsArrayList.add("Select Any");

        for (int i = 0; i < divisions.size(); i++) {
            System.out.println(divisions.get(i).getDivision());
            divisionsArrayList.add(divisions.get(i).getDivision());
        }
        spinner = (Spinner) findViewById(R.id.spinner3);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, divisionsArrayList) {
        };
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int code;
                selectedDivisionPosition = i;
                if (i > 0) {
                    code = divisions.get(i - 1).getDivisionCode();
                    selectedDivisionCode = String.valueOf(code);
                } else {
                    selectedDivisionCode = "";
                }
                showDistrictsDialog();
                // sendGetRequest();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    public void showDistrictsDialog() {
        if (selectedDivisionPosition > 0) {
            int divisionCode = divisions.get(selectedDivisionPosition - 1).getDivisionCode(); //get division code

            ArrayList<District> districtBasedOndivision = new ArrayList<>();

            for (int j = 0; j < districts.size(); j++) {
                if (districts.get(j).getDivCode().equalsIgnoreCase(String.valueOf(divisionCode))) {
                    districtBasedOndivision.add(districts.get(j));
                }
            }
            spinner = (Spinner) findViewById(R.id.spinner6);
            districtsArrayList = new ArrayList<>();
            districtsArrayList.add("Select Any");
            for (int j = 0; j < districtBasedOndivision.size(); j++) {
                districtsArrayList.add(districtBasedOndivision.get(j).getDistrict());
            }

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, districtsArrayList);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(arrayAdapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    String item = arrayAdapter.getItem(i);
                    int code = 0;
                    if (i > 0) {
                        code = Integer.parseInt(districtBasedOndivision.get(i - 1).getDistrictCode());
                        selectedDistrictCode = String.valueOf(code);
                    } else {
                        selectedDistrictCode = "";
                    }
                    fetchData();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        }
        else{
            //If "Select Any" is Selected
            spinner = (Spinner) findViewById(R.id.spinner6);
            districtsArrayList = new ArrayList<>();
            districtsArrayList.add("Select Any");

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, districtsArrayList);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(arrayAdapter);
            selectedDistrictCode = "";
            fetchData();
        }


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                selectedName = query;
                fetchData();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }



}