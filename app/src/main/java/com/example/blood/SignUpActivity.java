package com.example.blood;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    private TextView textView;
    private EditText editText;
    private Button button;

    private ArrayList<District> districts;
    private ArrayList<Division> divisions;
    private ArrayList<Upazilla> upazillas;



    private DistrictApi districtApi;
    private DivisionApi divisionApi;
    private UpazillaApi upazillaApi;
    private UserApi userApi;
    private Spinner spinner;



    private String[] districtsArrayList;
    private String[] divisionsArrayList;
    private String[] upazillasArrayList;

    int selectedDivisionPosition = 0;
    int selectedDistrictPosition = 0;


    private String selectedDivisionId=null;
    private String selectedDistrictId=null;
    private String selectedUpazillaId=null;

    private ProgressDialog progressDialog;
    int day = -1;
    int month = -1;
    int year = -1;

    int day1 = -1;
    int month1 = -1;
    int year1 = -1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);



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



        districts = new ArrayList<>();
        divisions = new ArrayList<>();
        upazillas = new ArrayList<>();


        editText = (EditText) findViewById(R.id.textView);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(SignUpActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        int mon = i1+1;
                        day = i2;
                        month=i1;
                        year=i;

                        String date = i+"-"+mon+"-"+i2;
                        //now set datepicker to this date
                        editText = (EditText) findViewById(R.id.textView);
                        editText.setText(date);

                    }
                }, Calendar.getInstance().get(Calendar.YEAR),
                        Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                if(day!=-1 && month!=-1 && year!=-1)
                    datePickerDialog.updateDate(year,month,day);
                datePickerDialog.show();

            }
        });


        editText = (EditText) findViewById(R.id.editTextTextPersonName2);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(SignUpActivity.this,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        int mon = i1+1;
                        day1 = i2;
                        month1=i1;
                        year1=i;
                        String date = i+"-"+mon+"-"+i2;
                        editText = (EditText) findViewById(R.id.editTextTextPersonName2);
                        editText.setText(date);

                    }
                }, Calendar.getInstance().get(Calendar.YEAR),
                        Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                if(day1!=-1 && month1!=-1 && year1!=-1)
                    datePickerDialog.updateDate(year1,month1,day1);
                datePickerDialog.show();

            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage( "Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();


        //fetch all divisions
        divisionApi = RetrofitInstanceArea.getRetrofit().create(DivisionApi.class);
        divisionApi.getDivisions().enqueue(new Callback<List<Division>>() {
            @Override
            public void onResponse(Call<List<Division>> call, Response<List<Division>> response) {
                if(response.body().size()>0)
                {
                    for(int i=0;i<response.body().size();i++)
                    {
                        //    System.out.println(response.body().get(i).getDivision());
                        divisions.add(response.body().get(i));
                    }
                    progressDialog.dismiss();
                }
                else{

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
                System.out.println("Code: "+response.code());
                if(response.body().size()>0)
                {
                    for(int i=0;i<response.body().size();i++)
                    {
                        districts.add(response.body().get(i));
                    }
                    progressDialog.dismiss();
                }
                else{
                    System.out.println("Failed");
                    Toast.makeText(SignUpActivity.this, "List is empty", Toast.LENGTH_SHORT).show();
                }
            }




            @Override
            public void onFailure(Call<List<District>> call, Throwable t) {
                System.out.println("Failed here");
                Toast.makeText(SignUpActivity.this, "Failed", Toast.LENGTH_SHORT).show();

            }
        });




        //fetch all Upazillas
        upazillaApi = RetrofitInstanceArea.getRetrofit().create(UpazillaApi.class);
        upazillaApi.getUpazillas().enqueue(new Callback<List<Upazilla>>() {
            @Override
            public void onResponse(Call<List<Upazilla>> call, Response<List<Upazilla>> response) {
                if(response.body().size()>0)
                {
                    for(int i=0;i<response.body().size();i++)
                    {
                        upazillas.add(response.body().get(i));
                    }
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<Upazilla>> call, Throwable t) {

            }
        });


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
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


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
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,bloodgroups){

            @Override
            public boolean isEnabled(int position) {
                if(position == 0)
                    return false;
                else
                    return true;
            }

            @Override
            public View getDropDownView(int position, @Nullable  View convertView, @NonNull  ViewGroup parent) {
                View view =  super.getDropDownView(position, convertView, parent);
                TextView textView = (TextView) view;
                if(position == 0)
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


        spinner = (Spinner) findViewById(R.id.spinner5);
        ArrayList<String> IdentificationIds = new ArrayList<>();
        IdentificationIds.add("Select Identification");
        IdentificationIds.add("NID");
        IdentificationIds.add("Smart Card");
        IdentificationIds.add("Birth Certificate");
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,IdentificationIds){

            @Override
            public boolean isEnabled(int position) {
                if(position == 0)
                    return false;
                else
                    return true;
            }

            @Override
            public View getDropDownView(int position, @Nullable  View convertView, @NonNull  ViewGroup parent) {
                View view =  super.getDropDownView(position, convertView, parent);
                TextView textView = (TextView) view;
                if(position == 0)
                    textView.setTextColor(Color.GRAY);
                else
                    textView.setTextColor(Color.BLACK);
                return view;

            }
        };

        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter2);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i>0)
                {
                    textView = (TextView) findViewById(R.id.textView7);
                    textView.setFocusable(true);
                    textView.setFocusableInTouchMode(true);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        textView = (TextView) findViewById(R.id.textView1);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDivisionDialog();
            }
        });

        textView = (TextView) findViewById(R.id.textView2);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                textView =(TextView)findViewById(R.id.textView1);
                if(textView.getText().toString().length()!=0)
                {
                    showDistrictDialog();

                }
            }
        });

        textView = (TextView) findViewById(R.id.textView3);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                textView = (TextView) findViewById(R.id.textView2);
                if(textView.getText().toString().length()!=0)
                    showUpazillaDialog();
            }
        });


        button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Upload data to database
                updateDatatoDatabase();

            }
        });









    }

    private void updateDatatoDatabase() {

        textView = (TextView) findViewById(R.id.textView4);
        if(TextUtils.isEmpty(textView.getText()))
        {
            textView.setError("First Name is Required");
            return;
        }
        String firstName = textView.getText().toString();
        textView = (TextView) findViewById(R.id.textView5);
        if(TextUtils.isEmpty(textView.getText()))
        {
            textView.setError("Last Name is Required");
            return;
        }

        String lastName = textView.getText().toString();
        textView = (TextView) findViewById(R.id.textView6);
        if(TextUtils.isEmpty(textView.getText()))
        {
            textView.setError("E-mail is Required");
            return;
        }
        String email = textView.getText().toString();

        textView = (TextView) findViewById(R.id.editTextTextPersonName);
        if(TextUtils.isEmpty(textView.getText()))
        {
            textView.setError("Password is Required");
            return;
        }


        String password = textView.getText().toString();

        textView = (TextView) findViewById(R.id.editTextTextPersonName3);
        if(TextUtils.isEmpty(textView.getText()))
        {
            textView.setError("Re-type Password is Required");
            return;
        }
        String anotherPassword = textView.getText().toString();
        if(!password.equals(anotherPassword))
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
            builder.setMessage("Password is not equal").setCancelable(true).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                    return;
                }
            });

            AlertDialog alert = builder.create();
            //Setting the title manually
           // alert.setTitle();
            alert.show();
        }

        textView = (TextView) findViewById(R.id.editTextTextPersonName2);

        String lastDonationDate = textView.getText().toString();

        spinner = (Spinner) findViewById(R.id.spinner1);
        String gender = null;
        if(spinner!=null && spinner.getSelectedItem()!=null) {
            gender = (String) spinner.getSelectedItem();
            if (gender.equalsIgnoreCase("Male"))
                gender = "male";
            else if (gender.equalsIgnoreCase("Female"))
                gender = "female";
            else if (gender.equalsIgnoreCase("Others"))
                gender = "others";

            else {
                AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                builder.setMessage("Gender is Required").setCancelable(true).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        return;
                    }
                });


                AlertDialog alert = builder.create();
                //Setting the title manually
                // alert.setTitle();
                alert.show();
                return;


            }
        }


        textView = (TextView)findViewById(R.id.textView);
        if(TextUtils.isEmpty(textView.getText()))
        {
            textView.setError("Date of Birth is Required");
           // return;
        }
        String dateOfBirth = textView.getText().toString();


        spinner = (Spinner) findViewById(R.id.spinner2);
        String bloodGroup = null;
        if(spinner!=null && spinner.getSelectedItem()!=null) {
            bloodGroup = spinner.getSelectedItem().toString();
            System.out.println("Blood:" + bloodGroup);
            if(bloodGroup.equalsIgnoreCase("Select Any"))
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                builder.setMessage("Blood Group is Required").setCancelable(true).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        return;
                    }
                });


                AlertDialog alert = builder.create();
                alert.show();
                return;
            }
        }



        textView = (TextView) findViewById(R.id.textView9);
        String remarks = textView.getText().toString();
        String division = selectedDivisionId;
        String district = selectedDistrictId;
        String upazilla = selectedUpazillaId;
        //Check Division and District


        textView = (TextView) findViewById(R.id.textView1);
        if(TextUtils.isEmpty(textView.getText()))
        {
            textView.setError("Division is Required");
          //  return;
        }

        textView = (TextView) findViewById(R.id.textView2);
        if(TextUtils.isEmpty(textView.getText()))
        {
            textView.setError("District is Required");
         //   return;
        }




        textView = (TextView)findViewById(R.id.textView8);
        if(TextUtils.isEmpty(textView.getText()))
        {
            textView.setError("Phone 1 is required");
         //   return;
        }
        String mobileNo1 = textView.getText().toString();



        textView = (TextView) findViewById(R.id.editTextTextPersonName4);
        String mobileNo2 = textView.getText().toString();


        spinner = (Spinner) findViewById(R.id.spinner5);
        String IdentificationType = null;
        if(spinner!=null && spinner.getSelectedItem()!=null) {
            IdentificationType = spinner.getSelectedItem().toString();
            if(IdentificationType.equalsIgnoreCase("Select Identification"))
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                builder.setMessage("Identification Type is Required").setCancelable(true).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        return;
                    }
                });


                AlertDialog alert = builder.create();
                alert.show();
                return;
            }
        }





        textView = (TextView)  findViewById(R.id.textView7);
        String NID = textView.getText().toString();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        User user = new User(firstName,lastName,email,password,gender,dateOfBirth,dateFormat.format(date),dateFormat.format(date),bloodGroup
                ,division,district,upazilla,mobileNo1);
        if(mobileNo2.length()!=0)
        {
            user.setMobile2(mobileNo2);
        }
        if(IdentificationType.equalsIgnoreCase("NID"))
        {
            user.setNID(NID);

        }
        else if(IdentificationType.equalsIgnoreCase("Smart Card"))
        {
            user.setSmartCard(NID);

        }

        else {
            user.setBirthCertificate(NID);

        }
        if(lastDonationDate.length()>0)
            user.setLastDonationDate(lastDonationDate);

        if(remarks.length()>0)
            user.setRemarks(remarks);



        System.out.println("User: "+ user);

        userApi = RetrofitInstanceUser.getRetrofit().create(UserApi.class);
        Call<User> call = userApi.addUser(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                System.out.println(response);
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

                    Toast.makeText(SignUpActivity.this, error, Toast.LENGTH_LONG).show();
                }
                else
                {
                    User userResponse = response.body();

                    Toast.makeText(SignUpActivity.this, "Request Successful", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(SignUpActivity.this,SignInActivity.class);
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

        for(int j=0;j<upazillas.size();j++)
        {
            if(upazillas.get(j).getDistrictCode().equalsIgnoreCase(String.valueOf(districtCode)))
            {
                upazillaBasedOnDistrict.add(upazillas.get(j));
            }
        }

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Upazilla");
        upazillasArrayList = new String[upazillaBasedOnDistrict.size()];
        for(int j=0;j<upazillaBasedOnDistrict.size();j++)
        {
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
            public void onClick(DialogInterface dialogInterface, int i){
                if(position[0]>=0)
                {
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

        for(int j=0;j<districts.size();j++)
        {
            if(districts.get(j).getDivCode().equalsIgnoreCase(String.valueOf(divisionCode)))
            {
                districtBasedOndivision.add(districts.get(j));
            }

        }
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("District");
        districtsArrayList = new String[districtBasedOndivision.size()];
        for(int j=0;j<districtBasedOndivision.size();j++)
        {
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

                for(int j=0;j<districts.size();j++)
                {
                    if(districts.get(j).getDistrict().equalsIgnoreCase(data[0]))
                    {
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
        for(int i=0;i<divisions.size();i++)
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






   /* private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                int month = i1+1;
                String date = i+"-"+String.valueOf(month)+"-"+i2;
                textView = (TextView)findViewById(R.id.textView);
                textView.setText(date);

            }
        }, Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }*/


}