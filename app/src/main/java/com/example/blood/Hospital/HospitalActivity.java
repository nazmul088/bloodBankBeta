package com.example.blood.Hospital;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintsChangedListener;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.blood.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HospitalActivity extends AppCompatActivity {



    private ConstraintLayout constraintLayout;

    class Available_beds{
        public int hfn;
        public int icu;
        public int hdu;
        public int gb;
    }


    private TextView textView;
    private HospitalApi hospitalApi;
    private Available_beds available_beds;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital);

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



        //get all beds available in bangladesh
        hospitalApi = RetrofitInstanceHospital.getRetrofit().create(HospitalApi.class);
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        hospitalApi.getBedsAvailable().enqueue(new Callback<Available_beds>() {
            @Override
            public void onResponse(Call<Available_beds> call, Response<Available_beds> response) {
                if(response.isSuccessful())
                {
                    available_beds = response.body();
                    textView = (TextView) findViewById(R.id.textView1);
                    textView.setText(String.valueOf(available_beds.icu));

                    textView = (TextView) findViewById(R.id.textView12);
                    textView.setText(String.valueOf(available_beds.hfn));

                    textView = (TextView) findViewById(R.id.textView6);
                    textView.setText(String.valueOf(available_beds.hdu));

                    textView = (TextView) findViewById(R.id.textView4);
                    textView.setText(String.valueOf(available_beds.gb));

                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Available_beds> call, Throwable t) {

            }
        });

        constraintLayout = findViewById(R.id.constraint_layout_3); // ICU beds selected
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HospitalActivity.this,ICUBedsActivity.class);
                intent.putExtra("bed-type","icu_beds");
                startActivity(intent);
            }
        });

        constraintLayout = findViewById(R.id.constraint_layout_4); //high flow nasal canula beds selected
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HospitalActivity.this,ICUBedsActivity.class);
                intent.putExtra("bed-type","hfn_beds");
                startActivity(intent);

            }
        });

        constraintLayout = findViewById(R.id.constraint_layout_5); //high dependency unit beds selected
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HospitalActivity.this,ICUBedsActivity.class);
                intent.putExtra("bed-type","hdu_beds");
                startActivity(intent);

            }
        });

        constraintLayout = findViewById(R.id.constraint_layout_6); //general beds selected
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HospitalActivity.this,ICUBedsActivity.class);
                intent.putExtra("bed-type","general_beds");
                startActivity(intent);
            }
        });








        textView = (TextView) findViewById(R.id.editTextTextPersonName5);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HospitalActivity.this,SearchHospitalActivity.class));
            }
        });







    }
}