package com.example.blood;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blood.RecyclerViewSize.InsertDivider;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchBloodActivity extends AppCompatActivity {


    private DivisionApi divisionApi;
    private DistrictApi districtApi;
    private ArrayList<Division> divisions;
    private ArrayList<District> districts;
    private ArrayList<String> divisionsArrayList;
    private ArrayList<String> districtsArrayList;
    private UserSearchApi userSearchApi;

    private Spinner spinner;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<UserSearch> userSearchArrayList;


    private String selectedBloodCode;
    private String selectedDivisionCode;
    private String selectedAvailabilityCode;
    private String selectedDistrictCode;
    private String selectedName = "";
    private boolean selectedPhone1 = false;
    private boolean selectedPhone2 = false;

    private int selectedDivisionPosition;


    private static final int REQUEST_CALL_PERMISSION = 1;
    private UserSearch userSearch;

    private UserSearchAdapter userSearchAdapter;


    private boolean isScrolling = false;

    private int pageNo = 1;
    private int current_items,total_items,scroll_out_items;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_blood);

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

        selectedBloodCode = "";
        selectedDivisionCode = "";
        selectedAvailabilityCode = "";
        selectedDistrictCode = "";
        selectedDivisionPosition = -1;

        userSearchArrayList = new ArrayList<>();

        recyclerView =  findViewById(R.id.RecyclerView1);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        InsertDivider insertDivider = new InsertDivider(10);
        recyclerView.addItemDecoration(insertDivider);

        userSearchAdapter = new UserSearchAdapter(this,userSearchArrayList);
        recyclerView.setAdapter(userSearchAdapter);
        userSearchAdapter.notifyDataSetChanged();



        divisions = new ArrayList<>();
        districts = new ArrayList<>();

        divisionApi = RetrofitInstanceArea.getRetrofit().create(DivisionApi.class);
        divisionApi.getDivisions().enqueue(new Callback<List<Division>>() {
            @Override
            public void onResponse(Call<List<Division>> call, Response<List<Division>> response) {
                System.out.println("Response: " + response);
                if (response.body().size() > 0) {
                    for (int i = 0; i < response.body().size(); i++) {
                        divisions.add(response.body().get(i));
                    }
                    showDivisionSpinner();
                } else {

                }

            }

            @Override
            public void onFailure(Call<List<Division>> call, Throwable t) {
                Toast.makeText(SearchBloodActivity.this, "Failed", Toast.LENGTH_SHORT).show();
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
                } else {

                }
            }

            @Override
            public void onFailure(Call<List<District>> call, Throwable t) {

            }
        });


        //Set Blood Group
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayList<String> bloodgroups = new ArrayList<>();
        bloodgroups.add("Select Any");
        bloodgroups.add("A+");
        bloodgroups.add("B+");
        bloodgroups.add("B-");
        bloodgroups.add("AB+");
        bloodgroups.add("AB-");
        bloodgroups.add("O+");
        bloodgroups.add("O-");
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, bloodgroups);

        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter1);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);
                //Selected a blood group
                String item = arrayAdapter1.getItem(i);
                int bloodCode = 0;
                if (item.equalsIgnoreCase("A+")) {
                    bloodCode = 0;
                } else if (item.equalsIgnoreCase("B+")) {
                    bloodCode = 2;
                } else if (item.equalsIgnoreCase("B-")) {
                    bloodCode = 3;
                } else if (item.equalsIgnoreCase("AB+")) {
                    bloodCode = 4;
                } else if (item.equalsIgnoreCase("AB-")) {
                    bloodCode = 5;
                } else if (item.equalsIgnoreCase("O+")) {
                    bloodCode = 6;
                } else if (item.equalsIgnoreCase("O-")) {
                    bloodCode = 7;
                } else {
                    bloodCode = -1;
                }
                if(bloodCode == -1)
                    selectedBloodCode = "";
                selectedBloodCode = String.valueOf(bloodCode);
                fetchData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //Now Setting Availability
        spinner = (Spinner) findViewById(R.id.spinner4);
        ArrayList<String> availables = new ArrayList<>();
        availables.add("Select Any");
        availables.add("Available");
        availables.add("Not Available");
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, availables);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter2);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = arrayAdapter2.getItem(i);
                if (item.equalsIgnoreCase("Select Any"))
                    selectedAvailabilityCode = "";
                else if (item.equalsIgnoreCase("Available"))
                    selectedAvailabilityCode = "1";
                else
                    selectedAvailabilityCode = "0";
                fetchData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //Scrolling event

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


        //fetch some data

        userSearchApi = RetrofitInstanceUserSearch.getRetrofit().create(UserSearchApi.class);
       /* userSearchApi.searchUserByNameBloodDivisionDistrictAvailability("","","","","","1").enqueue(new Callback<List<UserSearch>>() {
            @Override
            public void onResponse(Call<List<UserSearch>> call, Response<List<UserSearch>> response) {
                if(response.isSuccessful())
                {
                    if(response.isSuccessful())
                    {
                        for(int i=0;i<response.body().size();i++)
                        {
                            userSearchArrayList.add(response.body().get(i));
                            userSearchAdapter.notifyDataSetChanged();
                        }

                    }

                }
            }

            @Override
            public void onFailure(Call<List<UserSearch>> call, Throwable t) {

            }
        });
*/




        //RecyclerView on Item click listener


        userSearchAdapter.setOnItemClickListener(new UserSearchAdapter.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemClick(int position) {

                userSearch = userSearchArrayList.get(position);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SearchBloodActivity.this);
                View view1 = getLayoutInflater().inflate(R.layout.details_in_search_blood, null);
                TextView textView;
                textView = (TextView) view1.findViewById(R.id.textView15);
                textView.setText(userSearch.getFirstName());

                textView = (TextView) view1.findViewById(R.id.textView28);
                textView.setText(userSearch.getLastName());

                //Here Setting Mobile 2
                if (userSearch.getMobile2() != null) {
                    textView = (TextView) view1.findViewById(R.id.textView29);
                    textView.setText(userSearch.getMobile2());
                } else {
                    textView = (TextView) view1.findViewById(R.id.textView29);
                    textView.setVisibility(View.INVISIBLE);


                    textView = (TextView) view1.findViewById(R.id.textView61);
                    textView.setVisibility(View.INVISIBLE);
                }

                textView = (TextView) view1.findViewById(R.id.textView30);
                textView.setText(userSearch.getGender());


                //Calculating Age
                DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
                DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyy-MM-dd", Locale.ENGLISH);
                LocalDate date = LocalDate.parse(userSearch.getDOB(), inputFormatter);
                String formattedDate = outputFormatter.format(date);
                LocalDate today = LocalDate.now();

                Period p = Period.between(date, today);

                textView = (TextView) view1.findViewById(R.id.textView31); //Age needed
                textView.setText(p.getYears() + " " + "years");


                //textView.setText(userSearch.getDOB());

                textView = (TextView) view1.findViewById(R.id.textView32);
                textView.setText(userSearch.getBloodGroup());

                textView = (TextView) view1.findViewById(R.id.textView33);
                textView.setText(userSearch.getDivision().getDivision());

                textView = (TextView) view1.findViewById(R.id.textView34);
                textView.setText(userSearch.getDistrict().getDistrict());

                textView = (TextView) view1.findViewById(R.id.textView35);
                textView.setText(userSearch.getMobile1());


                alertDialogBuilder.setView(view1);
                AlertDialog dialog = alertDialogBuilder.create();
                dialog.show();


                Button button = view1.findViewById(R.id.button);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String mobile1 = userSearch.getMobile1();
                        String mobile2 = userSearch.getMobile2();

                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(SearchBloodActivity.this);
                        View view2 = getLayoutInflater().inflate(R.layout.call_in_search_blood, null);
                        TextView textView = (TextView) view2.findViewById(R.id.textView62);
                        textView.setText(userSearch.getMobile1());
                        textView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                selectedPhone1 = true;
                                makePhoneCall();
                            }
                        });

                        textView = (TextView) view2.findViewById(R.id.textView63);
                        if (mobile2 != null) {

                            textView.setText(userSearch.getMobile2());
                            textView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    selectedPhone2 = true;
                                    makePhoneCall2();
                                }
                            });


                        } else {
                            //Remove Mobile 2 textView
                            ConstraintLayout layout = view2.findViewById(R.id.contraint_layout);


                            textView.setVisibility(View.GONE);
                            View view3 = view2.findViewById(R.id.horizontalView);
                            view3.setVisibility(View.INVISIBLE);
                            textView = (TextView) view2.findViewById(R.id.textView65);
                            textView.setVisibility(View.GONE);
                        }

                        alertDialogBuilder.setView(view2);
                        AlertDialog dialog = alertDialogBuilder.create();
                        dialog.show();


                    }
                });


            }

        });

    }

    public void fetchData()
    {
        pageNo = 1;
        System.out.println("Called");

        ProgressDialog progressDialog = new ProgressDialog(SearchBloodActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        userSearchApi.searchUserByNameBloodDivisionDistrictAvailability(selectedName,selectedBloodCode,selectedDivisionCode,selectedDistrictCode,selectedAvailabilityCode,"1").enqueue(new Callback<List<UserSearch>>() {
            @Override
            public void onResponse(Call<List<UserSearch>> call, Response<List<UserSearch>> response) {

                if(response.isSuccessful())
                {
                    if(response.body().size()==0)
                    {
                        userSearchArrayList.clear();
                        userSearchAdapter.notifyDataSetChanged();
                    }
                    else{
                        userSearchArrayList.clear();
                       // userSearchAdapter.notifyDataSetChanged();
                        for(int i=0;i<response.body().size();i++)
                        {
                            userSearchArrayList.add(response.body().get(i));
                        }
                        userSearchAdapter.notifyDataSetChanged();

                    }

                    progressDialog.dismiss();
                }
                else{
                    StringBuilder error = new StringBuilder();
                    try {
                        BufferedReader bufferedReader = null;
                        if (response.errorBody() != null) {
                            bufferedReader = new BufferedReader(new InputStreamReader(
                                    response.errorBody().byteStream()));

                            String eLine = null;
                            while ((eLine = bufferedReader.readLine()) != null) {
                                error.append(eLine);
                            }
                            bufferedReader.close();
                        }

                    } catch (Exception e) {
                        error.append(e.getMessage());
                    }

                    Toast.makeText(SearchBloodActivity.this, error, Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<List<UserSearch>> call, Throwable t) {

            }
        });
    }




    public void fetchmoreData()
    {

       pageNo++;
        userSearchApi.searchUserByNameBloodDivisionDistrictAvailability(selectedName,selectedBloodCode,selectedDivisionCode,selectedDistrictCode,selectedAvailabilityCode,String.valueOf(pageNo)).enqueue(new Callback<List<UserSearch>>() {
                @Override
                public void onResponse(Call<List<UserSearch>> call, Response<List<UserSearch>> response) {
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
                            userSearchArrayList.add(response.body().get(i));
                            userSearchAdapter.notifyDataSetChanged();
                        }
                    }
                    else{
                        StringBuilder error = new StringBuilder();
                        try {
                            BufferedReader bufferedReader = null;
                            if (response.errorBody() != null) {
                                bufferedReader = new BufferedReader(new InputStreamReader(
                                        response.errorBody().byteStream()));

                                String eLine = null;
                                while ((eLine = bufferedReader.readLine()) != null) {
                                    error.append(eLine);
                                }
                                bufferedReader.close();
                            }

                        } catch (Exception e) {
                            error.append(e.getMessage());
                        }

                        Toast.makeText(SearchBloodActivity.this, error, Toast.LENGTH_LONG).show();
                    }
                    }


                @Override
                public void onFailure(Call<List<UserSearch>> call, Throwable t) {

                }
            });

    }

    private void makePhoneCall2() {
        if (ContextCompat.checkSelfPermission(SearchBloodActivity.this, Manifest.permission.CALL_PHONE) !=
                PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(SearchBloodActivity.this,"Allow to call",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri = Uri.fromParts("package", getPackageName(), null);
            intent.setData(uri);
            startActivity(intent);


            /*ActivityCompat.requestPermissions(SearchBloodActivity.this, new String[]{
                    Manifest.permission.CALL_PHONE
            }, REQUEST_CALL_PERMISSION);*/
        } else {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + userSearch.getMobile2()));
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
               // fetchData();
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
                    System.out.println("Dis code "+selectedDistrictCode);
                    fetchData();
                   //  sendGetRequest();
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
           // sendGetRequest();

        }


    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (selectedPhone1) {
                    selectedPhone1 = false;
                    makePhoneCall();
                } else if (selectedPhone2) {
                    selectedPhone2 = false;
                    makePhoneCall2();
                }
            } else {
                Toast.makeText(SearchBloodActivity.this, "Permission Denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void makePhoneCall() {

        if (ContextCompat.checkSelfPermission(SearchBloodActivity.this, Manifest.permission.CALL_PHONE) !=
                PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(SearchBloodActivity.this,"Allow to call",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri = Uri.fromParts("package", getPackageName(), null);
            intent.setData(uri);
            startActivity(intent);

        } else {

            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + userSearch.getMobile1()));
            startActivity(intent);

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
                //sendRequestByName();
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

    /*

     */

}