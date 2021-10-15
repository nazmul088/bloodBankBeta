package com.example.blood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blood.Cylinder.CylinderActivity;
import com.example.blood.Hospital.HospitalActivity;
import com.google.android.material.navigation.NavigationView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Button button;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private CardView cardView;
    private ImageView imageView;

    private UserApi userApi;

    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = this.getSharedPreferences("logInPref", Context.MODE_PRIVATE);
        String token = prefs.getString("token", "");
        String Userid = prefs.getString("userid","");





        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) findViewById(R.id.navigationView);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        navigationView.bringToFront();
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);


        cardView = (CardView) findViewById(R.id.card2);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SearchBloodActivity.class);
                startActivity(intent);
            }
        });

        cardView = (CardView) findViewById(R.id.card5);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AmbulanceActivity.class);
                startActivity(intent);
            }
        });

        cardView = (CardView) findViewById(R.id.oxygen_card);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CylinderActivity.class);
                startActivity(intent);
            }
        });

        cardView = (CardView) findViewById(R.id.hospital_card1);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, HospitalActivity.class));
            }
        });
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);

        userApi = RetrofitInstanceUser.getRetrofit().create(UserApi.class);
        userApi.getAParticularUser(Userid).enqueue(new Callback<UserSearch>() {
            @Override
            public void onResponse(Call<UserSearch> call, Response<UserSearch> response) {
                if(response.isSuccessful())
                {
                    progressDialog.dismiss();
                    textView = (TextView) findViewById(R.id.donor_name);
                    textView.setText(response.body().getFirstName()+" "+response.body().getLastName());
                    if(response.body().getGender().equalsIgnoreCase("female"))
                    {
                        imageView = findViewById(R.id.profile_image);
                        imageView.setImageResource(R.drawable.female_icon);
                    }
                }
                else{
                    progressDialog.dismiss();
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

                    Toast.makeText(MainActivity.this, error, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UserSearch> call, Throwable t) {

            }
        });





    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {
            this.finishAffinity();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.upper_right_menu, menu);
        return true;

        //return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.log_out:
                /*Toast.makeText(MainActivity.this,"This is Log Out",Toast.LENGTH_LONG);
                return true;*/

                SharedPreferences prefs = this.getSharedPreferences("logInPref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("token", "");     //RESET TO DEFAULT VALUE
                editor.putString("id","");
                editor.commit();

                Intent intent = new Intent(MainActivity.this,SignInActivity.class);
                startActivity(intent);
        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.homeMenu:
                break;
            case R.id.profile:
                Intent intent = new Intent(MainActivity.this,ProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.LogOut:
                SharedPreferences prefs = this.getSharedPreferences("logInPref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("token", "");     //RESET TO DEFAULT VALUE
                editor.putString("id","");
                editor.commit();

                Intent intent1 = new Intent(MainActivity.this,SignInActivity.class);
                startActivity(intent1);
                break;
            case R.id.team:
                startActivity(new Intent(MainActivity.this,TeamActivity.class));
                break;

            case R.id.contact:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://shovito.absbpeople.com")));
                break;

            case R.id.about:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://absbpeople.com")));
                break;
            case R.id.policy:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://absbpeople.com")));
                break;
            case R.id.terms:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://absbpeople.com")));
                break;
        }

        return true;
    }
}