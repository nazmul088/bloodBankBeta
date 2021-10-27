package com.example.blood;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Build;
import android.os.Bundle;
import android.text.Annotation;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;


public class ProfileActivity extends AppCompatActivity {
    private TextView textView;
    private EditText editText;
    private Spinner spinner;
    private Button button;
    private boolean isEditable = false;


    private DistrictApi districtApi;
    private DivisionApi divisionApi;
    private UpazillaApi upazillaApi;
    private UserApi userApi;

    private ArrayList<District> districts;
    private ArrayList<Division> divisions;
    private ArrayList<Upazilla> upazillas;

    private String[] districtsArrayList;
    private String[] divisionsArrayList;
    private String[] upazillasArrayList;

    int selectedDivisionPosition = 0;
    int selectedDistrictPosition = 0;


    private String selectedDivisionId = null;
    private String selectedDistrictId = null;
    private String selectedUpazillaId = null;

    private UserSearch userSearchData;


    private String Userid;

    int day=-1,month=-1,year=-1;
    int day1=-1,month1=-1,year1=-1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        districts = new ArrayList<>();
        divisions = new ArrayList<>();
        upazillas = new ArrayList<>();
        ProgressDialog progressDialog = new ProgressDialog(ProfileActivity.this);
        progressDialog.setMessage("Loading");
        progressDialog.show();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);


        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // Your code
                finish();
            }
        });

        SharedPreferences prefs = this.getSharedPreferences("logInPref", Context.MODE_PRIVATE);
        String token = prefs.getString("token", "");

        Userid = prefs.getString("userid", "");
        System.out.println("Check user id:" + Userid);
        userApi = RetrofitInstanceUser.getRetrofit().create(UserApi.class);
        Call<UserSearch> call = userApi.getAParticularUser(Userid);
        call.enqueue(new Callback<UserSearch>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<UserSearch> call, Response<UserSearch> response) {
                if (!response.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(ProfileActivity.this, "Request Not Successful", Toast.LENGTH_SHORT).show();


                } else {
                    progressDialog.dismiss();
                    //After getting this user
                    UserSearch userSearch = response.body();
                    userSearchData = userSearch;

                    ShapeDrawable shape = new ShapeDrawable(new RectShape());
                    shape.getPaint().setColor(Color.WHITE);
                    shape.getPaint().setStyle(Paint.Style.STROKE);
                    shape.getPaint().setStrokeWidth(3);


                    textView = (TextView) findViewById(R.id.textView4);
                    textView.setText(userSearch.getFirstName());
                    textView.setPadding(0, 0, 0, 0);

                    textView = (TextView) findViewById(R.id.textView5);
                    textView.setText(userSearch.getLastName());
                    textView.setPadding(0, 0, 0, 0);

                    textView = (TextView) findViewById(R.id.textView6);
                    textView.setText(userSearch.getEmail());
                    textView.setPadding(0, 0, 0, 0);

                    DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
                    DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyy-MM-dd", Locale.ENGLISH);
                    LocalDate date = LocalDate.parse(userSearch.getDOB(), inputFormatter);
                    String formattedDate = outputFormatter.format(date);


                    textView = (TextView) findViewById(R.id.textView10);
                    textView.setText(formattedDate);
                    textView.setPadding(0, 0, 0, 0);
                    if (userSearch.getLastDonationDate() != null) {

                        date = LocalDate.parse(userSearch.getLastDonationDate(), inputFormatter);
                        formattedDate = outputFormatter.format(date);
                    } else {
                        formattedDate = "";
                    }


                    textView = (TextView) findViewById(R.id.editTextTextPersonName2);
                    textView.setText(formattedDate);
                    textView.setPadding(0, 0, 0, 0);

                    textView = (TextView) findViewById(R.id.textView21);
                    if (userSearch.getSmartCard() != null) {
                        textView.setText("Smart Card");
                        textView = (TextView) findViewById(R.id.textView7);
                        textView.setText(userSearch.getSmartCard());
                        textView.setPadding(0, 0, 0, 0);
                    } else if (userSearch.getBirthCertificate() != null) {
                        textView.setText("Birth Certificate");
                        textView = (TextView) findViewById(R.id.textView7);
                        textView.setText(userSearch.getBirthCertificate());
                        textView.setPadding(0, 0, 0, 0);
                    } else if (userSearch.getNID() != null) {
                        textView.setText("NID");
                        textView = (TextView) findViewById(R.id.textView7);
                        textView.setText(userSearch.getNID());
                        textView.setPadding(0, 0, 0, 0);
                    }

                    textView = (TextView) findViewById(R.id.textView1);
                    textView.setText(userSearch.getDivision().getDivision());
                    textView.setPadding(0, 0, 0, 0);

                    textView = (TextView) findViewById(R.id.textView2);
                    textView.setText(userSearch.getDistrict().getDistrict());
                    textView.setPadding(0, 0, 0, 0);

                    textView = (TextView) findViewById(R.id.textView3);
                    if (userSearch.getUpazilla() != null) {
                        textView.setText(userSearch.getUpazilla().getUpazilla());
                    } else {
                        textView.setText("");
                    }

                    textView.setPadding(0, 0, 0, 0);

                    textView = (TextView) findViewById(R.id.textView8);
                    textView.setText(userSearch.getMobile1());
                    textView.setPadding(0, 0, 0, 0);


                    if (userSearch.getMobile2() != null) {
                        textView = (TextView) findViewById(R.id.editTextTextPersonName4);
                        textView.setText(userSearch.getMobile2());
                        textView.setPadding(0, 0, 0, 0);
                    }

                    if (userSearch.getRemarks() != null) {
                        textView = (TextView) findViewById(R.id.textView9);
                        textView.setText(userSearch.getRemarks());
                        textView.setPadding(0, 0, 0, 0);
                        EditText editText = (EditText) findViewById(R.id.textView9);
                    }

                    textView = (TextView) findViewById(R.id.textView40);
                    textView.setText(userSearch.getGender());
                    textView.setPadding(0, 0, 0, 0);

                    textView = (TextView) findViewById(R.id.textView41);
                    textView.setText(userSearch.getBloodGroup());
                    textView.setPadding(0, 0, 0, 0);


                    Toast.makeText(ProfileActivity.this, "Request Successful", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<UserSearch> call, Throwable t) {

            }
        });


        textView = (TextView) findViewById(R.id.textView42);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isEditable = true;

                //Need here to Update SelectedDivisionPostion

                for (int i = 0; i < divisions.size(); i++) {
                    if (divisions.get(i).getDivision().equalsIgnoreCase(userSearchData.getDivision().getDivision())) {
                        selectedDivisionPosition = i;
                        break;
                    }
                }

                for (int i = 0; i < districts.size(); i++) {
                    if (districts.get(i).getDistrict().equalsIgnoreCase(userSearchData.getDistrict().getDistrict())) {
                        selectedDistrictPosition = i;
                        break;
                    }
                }

                EditText editText = (EditText) findViewById(R.id.textView4);
                editText.setBackgroundResource(R.drawable.edit_text_bg);
                editText.setFocusableInTouchMode(true);
                editText.setFocusable(true);
                editText.setPadding(10, 10, 10, 10);


                editText = (EditText) findViewById(R.id.textView5);
                editText.setBackgroundResource(R.drawable.edit_text_bg);
                editText.setFocusableInTouchMode(true);
                editText.setFocusable(true);
                editText.setPadding(10, 10, 10, 10);

                editText = (EditText) findViewById(R.id.textView10);
                editText.setBackgroundResource(R.drawable.edit_text_bg);
                //editText.setFocusableInTouchMode(true);
                editText.setFocusable(true);
                editText.setPadding(10, 10, 10, 10);

                editText = (EditText) findViewById(R.id.editTextTextPersonName2);
                editText.setBackgroundResource(R.drawable.edit_text_bg);
                editText.setHint("Select Date");
                // editText.setFocusableInTouchMode(true);
                editText.setFocusable(true);
                editText.setPadding(10, 10, 10, 10);


                editText = (EditText) findViewById(R.id.textView1);
                editText.setBackgroundResource(R.drawable.edit_text_bg);
                editText.setPadding(10, 10, 10, 10);

                editText = (EditText) findViewById(R.id.textView2);
                editText.setBackgroundResource(R.drawable.edit_text_bg);
                editText.setPadding(10, 10, 10, 10);

                editText = (EditText) findViewById(R.id.textView3);
                editText.setBackgroundResource(R.drawable.edit_text_bg);
                editText.setPadding(10, 10, 10, 10);

                editText = (EditText) findViewById(R.id.editTextTextPersonName4);
                editText.setBackgroundResource(R.drawable.edit_text_bg);
                editText.setFocusableInTouchMode(true);
                editText.setFocusable(true);
                editText.setPadding(10, 10, 10, 10);


                editText = (EditText) findViewById(R.id.textView9);
                editText.setBackgroundResource(R.drawable.edit_text_bg);
                editText.setFocusableInTouchMode(true);
                editText.setFocusable(true);
                editText.setPadding(10, 10, 10, 10);
                if (editText.getText().toString().length() == 0) {
                    editText.setHint("Say something about Yourself");
                }

                //Appear Gender Spinner and change textView Invisible
                spinner = (Spinner) findViewById(R.id.spinner1);
                spinner.setVisibility(View.VISIBLE);

                textView = (TextView) findViewById(R.id.textView40);
                if (textView.getText().toString().equalsIgnoreCase("male")) {
                    spinner.setSelection(1);
                } else if (textView.getText().toString().equalsIgnoreCase("female")) {
                    spinner.setSelection(2);
                } else {
                    spinner.setSelection(3);
                }


                textView = (TextView) findViewById(R.id.textView40);
                textView.setVisibility(View.INVISIBLE);

                textView = (TextView) findViewById(R.id.textView41);
                textView.setVisibility(View.INVISIBLE);


                spinner = (Spinner) findViewById(R.id.spinner2);
                spinner.setVisibility(View.VISIBLE);

                if (textView.getText().toString().equalsIgnoreCase("A+")) {
                    spinner.setSelection(1);

                } else if (textView.getText().toString().equalsIgnoreCase("B+")) {
                    spinner.setSelection(2);

                } else if (textView.getText().toString().equalsIgnoreCase("B-")) {
                    spinner.setSelection(3);

                } else if (textView.getText().toString().equalsIgnoreCase("AB+")) {
                    spinner.setSelection(4);

                } else if (textView.getText().toString().equalsIgnoreCase("AB-")) {
                    spinner.setSelection(5);
                } else if (textView.getText().toString().equalsIgnoreCase("O+")) {
                    spinner.setSelection(6);
                } else if (textView.getText().toString().equalsIgnoreCase("O-")) {
                    spinner.setSelection(7);
                }


                button = (Button) findViewById(R.id.button1);
                button.setVisibility(View.VISIBLE);
            }
        });


        editText = (EditText) findViewById(R.id.textView10);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isEditable) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(ProfileActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                            int mon = i1+1;
                            String date = i + "-" + mon + "-" + i2;
                            day = i2;
                            month = i1;
                            year = i;
                            editText = (EditText) findViewById(R.id.textView10);
                            editText.setText(date);

                        }
                    }, Calendar.getInstance().get(Calendar.YEAR),
                            Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                        if(day != -1 && month != -1 && year !=-1)
                            datePickerDialog.updateDate(year,month,day);

                    datePickerDialog.show();
                }
            }


        });


        editText = (EditText) findViewById(R.id.editTextTextPersonName2);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isEditable) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(ProfileActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                            int mon = i1+1;
                            day1 = i2;
                            month1 = i1;
                            year1 = i;
                            String date = i + "-" + mon + "-" + i2;
                            editText = (EditText) findViewById(R.id.editTextTextPersonName2);
                            editText.setText(date);

                        }
                    }, Calendar.getInstance().get(Calendar.YEAR),
                            Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                    if(day1 != -1 && month1 != -1 && year1 !=-1)
                        datePickerDialog.updateDate(year1,month1,day1);
                    datePickerDialog.show();

                }
            }
        });


        //fetch all divisions
        divisionApi = RetrofitInstanceArea.getRetrofit().create(DivisionApi.class);
        divisionApi.getDivisions().enqueue(new Callback<List<Division>>() {
            @Override
            public void onResponse(Call<List<Division>> call, Response<List<Division>> response) {
                if (response.body().size() > 0) {
                    for (int i = 0; i < response.body().size(); i++) {
                        //    System.out.println(response.body().get(i).getDivision());
                        divisions.add(response.body().get(i));
                    }
                } else {

                }

            }

            @Override
            public void onFailure(Call<List<Division>> call, Throwable t) {

            }
        });

        //fetch all districts
        districtApi = RetrofitInstanceArea.getRetrofit().create(DistrictApi.class);
        districtApi.getDistricts().enqueue(new Callback<List<District>>() {
            @Override
            public void onResponse(Call<List<District>> call, Response<List<District>> response) {
                System.out.println("Code: " + response.code());
                if (response.body().size() > 0) {
                    for (int i = 0; i < response.body().size(); i++) {
                        districts.add(response.body().get(i));
                    }
                } else {
                    System.out.println("Failed");
                    Toast.makeText(ProfileActivity.this, "List is empty", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<List<District>> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "Failed", Toast.LENGTH_SHORT).show();

            }
        });


        //fetch all Upazillas
        upazillaApi = RetrofitInstanceArea.getRetrofit().create(UpazillaApi.class);
        upazillaApi.getUpazillas().enqueue(new Callback<List<Upazilla>>() {
            @Override
            public void onResponse(Call<List<Upazilla>> call, Response<List<Upazilla>> response) {
                if (response.body().size() > 0) {
                    for (int i = 0; i < response.body().size(); i++) {
                        upazillas.add(response.body().get(i));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Upazilla>> call, Throwable t) {

            }
        });


        textView = (TextView) findViewById(R.id.textView1);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isEditable) {
                    selectedDistrictId = null;
                    selectedUpazillaId = null;
                    showDivisionDialog();
                }
            }
        });

        textView = (TextView) findViewById(R.id.textView2);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEditable) {
                    selectedUpazillaId = null;
                    showDistrictDialog();
                }
            }
        });

        textView = (TextView) findViewById(R.id.textView3);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEditable) {
                    showUpazillaDialog();
                }
            }
        });


        //set all genders
        spinner = (Spinner) findViewById(R.id.spinner1);
        ArrayList<String> genders = new ArrayList<>();
        genders.add("Select Any");
        genders.add("Male");
        genders.add("Female");
        genders.add("Others");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,genders){
            @Override
            public boolean isEnabled(int position) {
                if(position==0)
                    return false;
                return true;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view =  super.getDropDownView(position, convertView, parent);
                TextView textView = (TextView) view;
                if(position == 0)
                    textView.setTextColor(Color.GRAY);
                else
                    textView.setTextColor(Color.BLACK);
                return view;

            }
        };
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);




        //Set all blood groups
        spinner = (Spinner) findViewById(R.id.spinner2);
        ArrayList<String> bloodgroups = new ArrayList<>();
        bloodgroups.add("Select Any");
        bloodgroups.add("A+");
        bloodgroups.add("B+");
        bloodgroups.add("B-");
        bloodgroups.add("AB+");
        bloodgroups.add("AB-");
        bloodgroups.add("O+");
        bloodgroups.add("O-");
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, bloodgroups) {

            @Override
            public boolean isEnabled(int position) {
                if (position == 0)
                    return false;
                else
                    return true;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textView = (TextView) view;
                if (position == 0)
                    textView.setTextColor(Color.GRAY);
                else
                    textView.setTextColor(Color.BLACK);
                return view;

            }
        };

        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter1);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Upload data to database
                if (isEditable) {
                    updateDatatoDatabase();
                }

            }
        });


    }

    private void updateDatatoDatabase() {

        textView = (TextView) findViewById(R.id.textView4);
        String firstName = "";
        if (!TextUtils.isEmpty(textView.getText())) {
            firstName = textView.getText().toString();
        }
        String lastName = "";

        textView = findViewById(R.id.textView1);
        if (TextUtils.isEmpty(textView.getText())) {
            textView.setError("Please Select Division");
            return;
        }

        textView = (TextView) findViewById(R.id.textView2);
        if (TextUtils.isEmpty(textView.getText())) {
            textView.setError("Please Select District");
            return;
        }

        textView = (TextView) findViewById(R.id.textView5);
        if (!TextUtils.isEmpty(textView.getText())) {
            lastName = textView.getText().toString();
        }


        spinner = (Spinner) findViewById(R.id.spinner1);
        String gender = spinner.getSelectedItem().toString();
        if (gender.equalsIgnoreCase("Male"))
            gender = "male";
        else if (gender.equalsIgnoreCase("Female"))
            gender = "female";
        else
            gender = "others";
        textView = (TextView) findViewById(R.id.textView10);
        String dateOfBirth = textView.getText().toString();
        spinner = (Spinner) findViewById(R.id.spinner2);
        String bloodGroup = spinner.getSelectedItem().toString();
        textView = (TextView) findViewById(R.id.textView9);
        String remarks = textView.getText().toString();
        String division = selectedDivisionId;
        String district = selectedDistrictId;
        String upazilla = selectedUpazillaId;
        textView = (TextView) findViewById(R.id.editTextTextPersonName4);
        String mobileNo2 = textView.getText().toString();
        textView = (TextView) findViewById(R.id.editTextTextPersonName2);
        String lastDonationDate = textView.getText().toString();


        //Now Make User Object to send Put Request
        User user = new User(firstName, lastName, gender, dateOfBirth, bloodGroup, remarks);
        user.setEmail(userSearchData.getEmail());
        if (selectedDivisionId == null) {
            System.out.println("ID :" + userSearchData.getDivision().get_id());
            user.setDivision(userSearchData.getDivision().get_id());
        } else {
            user.setDivision(division);
        }

        if (selectedDistrictId == null) {
            user.setDistrict(userSearchData.getDistrict().get_id());
        } else {
            user.setDistrict(district);
        }

        if (selectedUpazillaId == null) {
            if (userSearchData.getUpazilla() != null) {
                user.setUpazilla(userSearchData.getUpazilla().get_id());
            }
        } else {
            user.setUpazilla(upazilla);
        }

        String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        user.setLastActiveAt(timeStamp);
        user.setLastUpdateAt(timeStamp);
        user.setPassword(userSearchData.getPassword());
        user.setEmail(userSearchData.getEmail());
        user.setMobile1(userSearchData.getMobile1());

        if (lastDonationDate.length() > 0)
            user.setLastDonationDate(lastDonationDate);
        if (mobileNo2.length() > 0) {
            user.setMobile2(mobileNo2);
        }
        if (userSearchData.getNID() != null)
            user.setNID(userSearchData.getNID());
        else if (userSearchData.getSmartCard() != null)
            user.setSmartCard(userSearchData.getSmartCard());
        else {
            user.setBirthCertificate(userSearchData.getBirthCertificate());
        }


        // User user = new User(firstName, lastName, gender, dateOfBirth, bloodGroup, remarks,mobileNo);
        SharedPreferences prefs = this.getSharedPreferences("logInPref", Context.MODE_PRIVATE);
        String token = prefs.getString("token", "");


        userApi = RetrofitInstanceUser.getRetrofit().create(UserApi.class);
        /*User user = new User("DONOR","BARUA","test@gmail.com","123456","male","1997-08-19T18:00:00.000Z",
                "A+","ready to donate","6101880d89f76f4f25802891","6101975389f76f4f258028e1","0100077979","01521441839",
                false,"1234569870");*/
        Call<User> call = userApi.updateUser(Userid, user, token);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
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

                    Toast.makeText(ProfileActivity.this, error, Toast.LENGTH_LONG).show();
                } else {
                    User userResponse = response.body();

                    Toast.makeText(ProfileActivity.this, "Request Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

    }

    private void showUpazillaDialog() {
        int districtCode = Integer.parseInt(districts.get(selectedDistrictPosition).getDistrictCode());
        ArrayList<Upazilla> upazillaBasedOnDistrict = new ArrayList<>();

        for (int j = 0; j < upazillas.size(); j++) {
            if (upazillas.get(j).getDistrictCode().equalsIgnoreCase(String.valueOf(districtCode))) {
                upazillaBasedOnDistrict.add(upazillas.get(j));
            }
        }

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Upazilla");
        upazillasArrayList = new String[upazillaBasedOnDistrict.size()];
        for (int j = 0; j < upazillaBasedOnDistrict.size(); j++) {
            upazillasArrayList[j] = upazillaBasedOnDistrict.get(j).getUpazilla();
        }
        final int[] position = {-1};
        final String[] data = {""};
        alertDialog.setSingleChoiceItems(upazillasArrayList, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                data[0] = upazillasArrayList[i];
                position[0] = i;
            }
        });
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (position[0] >= 0) {
                    selectedUpazillaId = upazillaBasedOnDistrict.get(position[0]).get_id();
                    textView = (TextView) findViewById(R.id.textView3);
                    textView.setText(data[0]);
                }
            }
        });
        alertDialog.create();
        alertDialog.show();
    }

    private void showDistrictDialog() {
        int divisionCode = divisions.get(selectedDivisionPosition).getDivisionCode();
        ArrayList<District> districtBasedOndivision = new ArrayList<>();

        for (int j = 0; j < districts.size(); j++) {
            if (districts.get(j).getDivCode().equalsIgnoreCase(String.valueOf(divisionCode))) {
                districtBasedOndivision.add(districts.get(j));
            }

        }
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("District");
        districtsArrayList = new String[districtBasedOndivision.size()];
        for (int j = 0; j < districtBasedOndivision.size(); j++) {
            districtsArrayList[j] = districtBasedOndivision.get(j).getDistrict();
        }
        final String[] data = {""};
        alertDialog.setSingleChoiceItems(districtsArrayList, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                data[0] = districtsArrayList[i];
                selectedDistrictPosition = i;
            }
        });
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                textView = (TextView) findViewById(R.id.textView3);
                textView.setText("");

                textView = (TextView) findViewById(R.id.textView2);
                textView.setText(data[0]);

                for (int j = 0; j < districts.size(); j++) {
                    if (districts.get(j).getDistrict().equalsIgnoreCase(data[0])) {
                        selectedDistrictPosition = j;
                        selectedDistrictId = districts.get(j).get_id();
                        break;
                    }
                }
            }
        });

        alertDialog.create();
        alertDialog.show();

    }

    private void showDivisionDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Division");
        divisionsArrayList = new String[divisions.size()];
        for (int i = 0; i < divisions.size(); i++)
            divisionsArrayList[i] = divisions.get(i).getDivision();
        final String[] data = {""};
        final int[] position = {0};

        alertDialog.setSingleChoiceItems(divisionsArrayList, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                data[0] = divisionsArrayList[i];
                selectedDivisionPosition = i;

            }
        });

        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                selectedDivisionId = divisions.get(selectedDivisionPosition).get_id();

                textView = (TextView) findViewById(R.id.textView2);
                textView.setText("");
                textView = (TextView) findViewById(R.id.textView3);
                textView.setText("");
                textView = (TextView) findViewById(R.id.textView1);
                textView.setText(data[0]);


                //Now Showing districts of this division


            }
        });

        alertDialog.create();
        alertDialog.show();


    }


}