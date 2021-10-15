package com.example.blood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
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

import com.example.blood.RecyclerViewSize.InsertDivider;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AmbulanceActivity extends AppCompatActivity {


    private static final int REQUEST_CALL_PERMISSION = 1;
    private Spinner spinner;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    private DivisionApi divisionApi;
    private DistrictApi districtApi;
    private AmbulanceApi ambulanceApi;


    private ArrayList<Division> divisions;
    private ArrayList<District> districts;

    private ArrayList<String> divisionsArrayList;
    private ArrayList<String> districtsArrayList;

    private int selectedDivisionPosition;
    private String selectedDivisionCode,  selectedDistrictCode;
    private String selectedName;

    private AmbulanceSearchAdapter ambulanceSearchAdapter;
    private ArrayList<Ambulance> ambulanceSearchArrayList;

    private String selectedPhoneNumbertoCall;


    private boolean isScrolling = false;

    private int pageNo = 1;
    private int current_items,total_items,scroll_out_items;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulance);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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



        divisions = new ArrayList<>();
        districts = new ArrayList<>();
        ambulanceSearchArrayList = new ArrayList<>();

        recyclerView = findViewById(R.id.RecyclerView1);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        InsertDivider insertDivider = new InsertDivider(10);
        recyclerView.addItemDecoration(insertDivider);



        ambulanceSearchAdapter = new AmbulanceSearchAdapter(ambulanceSearchArrayList);
        recyclerView.setAdapter(ambulanceSearchAdapter);
        ambulanceSearchAdapter.notifyDataSetChanged();

        ambulanceSearchAdapter.setOnItemClickListener(new AmbulanceSearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                System.out.println(ambulanceSearchArrayList.get(position).getContactNo());
            }

            @Override
            public void onButtonClick(int position) {
                selectedPhoneNumbertoCall = ambulanceSearchArrayList.get(position).getContactNo();
                makePhoneCall();
            }
        });

        ProgressDialog progressDialog = new ProgressDialog(AmbulanceActivity.this);
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
                Toast.makeText(AmbulanceActivity.this, "Failed", Toast.LENGTH_SHORT).show();
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

        ambulanceApi = RetrofitInstanceAmbulance.getRetrofit().create(AmbulanceApi.class);


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

        ambulanceApi.getAmbulanceByNameDivisionDistrict(selectedName,selectedDivisionCode,selectedDistrictCode,"1").enqueue(new Callback<List<Ambulance>>() {
            @Override
            public void onResponse(Call<List<Ambulance>> call, Response<List<Ambulance>> response) {

                if(response.isSuccessful())
                {
                    if(response.body().size()==0)
                    {
                        ambulanceSearchArrayList.clear();
                        ambulanceSearchAdapter.notifyDataSetChanged();
                    }
                    else{
                        ambulanceSearchArrayList.clear();
                        // userSearchAdapter.notifyDataSetChanged();
                        for(int i=0;i<response.body().size();i++)
                        {
                            ambulanceSearchArrayList.add(response.body().get(i));
                        }
                        ambulanceSearchAdapter.notifyDataSetChanged();

                        System.out.println("Data showed"+ ""+"Size: "+ambulanceSearchArrayList.size());
                    }
                }

            }

            @Override
            public void onFailure(Call<List<Ambulance>> call, Throwable t) {

            }
        });
    }




    public void fetchmoreData()
    {

        pageNo++;
        ambulanceApi.getAmbulanceByNameDivisionDistrict(selectedName,selectedDivisionCode,selectedDistrictCode,String.valueOf(pageNo)).enqueue(new Callback<List<Ambulance>>() {
            @Override
            public void onResponse(Call<List<Ambulance>> call, Response<List<Ambulance>> response) {
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
                        ambulanceSearchArrayList.add(response.body().get(i));
                        ambulanceSearchAdapter.notifyDataSetChanged();
                    }
                }
                else{
                    System.out.println("No Data found");

                }
            }

            @Override
            public void onFailure(Call<List<Ambulance>> call, Throwable t) {

            }
        });

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
                    //sendGetRequest();
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
         //   sendGetRequest();
        }


    }




    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall();
            }
            else {
                Toast.makeText(AmbulanceActivity.this, "Permission Denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void makePhoneCall() {

        if (ContextCompat.checkSelfPermission(AmbulanceActivity.this, Manifest.permission.CALL_PHONE) !=
                PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(AmbulanceActivity.this,"Allow to call",Toast.LENGTH_SHORT).show();
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


    private void sendGetRequest() {
        /*if(selectedName == "")
        {
            if(selectedDivisionCode==-1 && selectedDistrictCode==-1) //Nothing Selected
            {

                ProgressDialog progressDialog = new ProgressDialog(AmbulanceActivity.this);
                progressDialog.setMessage("Loading");
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                ambulanceApi = RetrofitInstanceAmbulance.getRetrofit().create(AmbulanceApi.class);
                ambulanceApi.getAllAmbulance().enqueue(new Callback<List<Ambulance>>() {
                    @Override
                    public void onResponse(Call<List<Ambulance>> call, Response<List<Ambulance>> response) {
                        ambulanceSearchArrayList.clear();
                        if(response.body().size()>0)
                        {
                            for(int i=0;i<response.body().size();i++)
                            {
                                ambulanceSearchArrayList.add(response.body().get(i));
                            }
                            progressDialog.dismiss();
                            ambulanceSearchAdapter.notifyDataSetChanged();
                        }
                        else
                        {
                            ambulanceSearchArrayList.clear();
                            ambulanceSearchAdapter.notifyDataSetChanged();
                            Toast.makeText(AmbulanceActivity.this,"No Data Found",Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();

                        }
                    }

                    @Override
                    public void onFailure(Call<List<Ambulance>> call, Throwable t) {
                        progressDialog.dismiss();
                    }
                });


            }

           else if(selectedDivisionCode!=-1 && selectedDistrictCode==-1) //Only Division Selected
            {
                ProgressDialog progressDialog = new ProgressDialog(AmbulanceActivity.this);
                progressDialog.setMessage("Loading");
                progressDialog.setCancelable(false);
                progressDialog.show();
                ambulanceSearchArrayList.clear();
                ambulanceApi = RetrofitInstanceAmbulance.getRetrofit().create(AmbulanceApi.class);
                ambulanceApi.searchByDivision(selectedDivisionCode).enqueue(new Callback<List<Ambulance>>() {
                    @Override
                    public void onResponse(Call<List<Ambulance>> call, Response<List<Ambulance>> response) {
                        if(response.body().size()>0)
                        {
                            for(int i=0;i<response.body().size();i++)
                            {
                                ambulanceSearchArrayList.add(response.body().get(i));
                            }
                            progressDialog.dismiss();
                            ambulanceSearchAdapter.notifyDataSetChanged();
                        }
                        else
                        {
                            progressDialog.dismiss();
                            ambulanceSearchArrayList.clear();
                            ambulanceSearchAdapter.notifyDataSetChanged();

                        }
                    }

                    @Override
                    public void onFailure(Call<List<Ambulance>> call, Throwable t) {
                        progressDialog.dismiss();
                    }
                });


            }



            else if(selectedDistrictCode!=-1) //Division + District Selected
            {
                ProgressDialog progressDialog = new ProgressDialog(AmbulanceActivity.this);
                progressDialog.setMessage("Loading");
                progressDialog.setCancelable(false);
                progressDialog.show();
                ambulanceSearchArrayList.clear();
                ambulanceApi = RetrofitInstanceAmbulance.getRetrofit().create(AmbulanceApi.class);
                ambulanceApi.searchByDistrict(selectedDistrictCode).enqueue(new Callback<List<Ambulance>>() {
                    @Override
                    public void onResponse(Call<List<Ambulance>> call, Response<List<Ambulance>> response) {
                        if(response.body().size()>0)
                        {
                            for(int i=0;i<response.body().size();i++)
                            {
                                ambulanceSearchArrayList.add(response.body().get(i));
                            }
                            progressDialog.dismiss();
                            ambulanceSearchAdapter.notifyDataSetChanged();
                        }
                        else
                        {
                            progressDialog.dismiss();
                            ambulanceSearchArrayList.clear();
                            ambulanceSearchAdapter.notifyDataSetChanged();

                        }
                    }

                    @Override
                    public void onFailure(Call<List<Ambulance>> call, Throwable t) {
                        progressDialog.dismiss();
                    }
                });


            }

        }
        else
        {
            sendRequestByName();
        }

    */


    }

    private void sendRequestByName() {

       /* if(selectedDivisionCode==-1 && selectedDistrictCode==-1) //Only Name Selected
        {
            ProgressDialog progressDialog = new ProgressDialog(AmbulanceActivity.this);
            progressDialog.setMessage("Loading");
            progressDialog.setCancelable(false);
            progressDialog.show();
            ambulanceSearchArrayList.clear();
            ambulanceSearchAdapter.notifyDataSetChanged();
            ambulanceApi = RetrofitInstanceAmbulance.getRetrofit().create(AmbulanceApi.class);
            ambulanceApi.searchByName(selectedName).enqueue(new Callback<List<Ambulance>>() {
                @Override
                public void onResponse(Call<List<Ambulance>> call, Response<List<Ambulance>> response) {
                    if(response.body().size()>0)
                    {
                        for(int i=0;i<response.body().size();i++)
                        {
                            ambulanceSearchArrayList.add(response.body().get(i));
                        }
                        progressDialog.dismiss();
                        ambulanceSearchAdapter.notifyDataSetChanged();
                    }
                    else
                    {
                        progressDialog.dismiss();
                        ambulanceSearchArrayList.clear();
                        ambulanceSearchAdapter.notifyDataSetChanged();

                    }
                }

                @Override
                public void onFailure(Call<List<Ambulance>> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });


        }



       else if(selectedDivisionCode!=-1 && selectedDistrictCode==-1) //Only Division Selected
        {
            ProgressDialog progressDialog = new ProgressDialog(AmbulanceActivity.this);
            progressDialog.setMessage("Loading");
            progressDialog.setCancelable(false);
            progressDialog.show();
            ambulanceSearchArrayList.clear();
            ambulanceApi = RetrofitInstanceAmbulance.getRetrofit().create(AmbulanceApi.class);
            ambulanceApi.searchByNameDivision(selectedName,selectedDivisionCode).enqueue(new Callback<List<Ambulance>>() {
                @Override
                public void onResponse(Call<List<Ambulance>> call, Response<List<Ambulance>> response) {
                    if(response.body().size()>0)
                    {
                        for(int i=0;i<response.body().size();i++)
                        {
                            ambulanceSearchArrayList.add(response.body().get(i));
                        }
                        progressDialog.dismiss();
                        ambulanceSearchAdapter.notifyDataSetChanged();
                    }
                    else
                    {
                        progressDialog.dismiss();
                        ambulanceSearchArrayList.clear();
                        ambulanceSearchAdapter.notifyDataSetChanged();

                    }
                }

                @Override
                public void onFailure(Call<List<Ambulance>> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });


        }



        else if(selectedDistrictCode!=-1) //Division + District Selected
        {
            ProgressDialog progressDialog = new ProgressDialog(AmbulanceActivity.this);
            progressDialog.setMessage("Loading");
            progressDialog.setCancelable(false);
            progressDialog.show();
            ambulanceSearchArrayList.clear();
            ambulanceApi = RetrofitInstanceAmbulance.getRetrofit().create(AmbulanceApi.class);
            ambulanceApi.searchByNameDistrict(selectedName,selectedDistrictCode).enqueue(new Callback<List<Ambulance>>() {
                @Override
                public void onResponse(Call<List<Ambulance>> call, Response<List<Ambulance>> response) {
                    if(response.body().size()>0)
                    {
                        for(int i=0;i<response.body().size();i++)
                        {
                            ambulanceSearchArrayList.add(response.body().get(i));
                        }
                        progressDialog.dismiss();
                        ambulanceSearchAdapter.notifyDataSetChanged();
                    }
                    else
                    {
                        progressDialog.dismiss();
                        ambulanceSearchArrayList.clear();
                        ambulanceSearchAdapter.notifyDataSetChanged();

                    }
                }

                @Override
                public void onFailure(Call<List<Ambulance>> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });


        }*/

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
                //sendRequestByName();
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