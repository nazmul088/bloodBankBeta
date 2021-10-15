package com.example.blood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.widget.Toolbar;

import android.text.method.LinkMovementMethod;
import android.view.View;

import android.os.Bundle;
import android.widget.TextView;


public class TeamActivity extends AppCompatActivity {

    private ConstraintLayout constraintLayout;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);


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


        textView =(TextView)findViewById(R.id.nazmul_profile);
        textView.setMovementMethod(LinkMovementMethod.getInstance());

        textView = (TextView) findViewById(R.id.soummo_profile);
        textView.setMovementMethod(LinkMovementMethod.getInstance());



        constraintLayout = (ConstraintLayout) findViewById(R.id.constraint_layout_9);
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        constraintLayout = (ConstraintLayout) findViewById(R.id.constraint_layout_10);
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



    }

}