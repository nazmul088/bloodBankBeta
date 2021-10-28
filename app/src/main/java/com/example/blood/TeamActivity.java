package com.example.blood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.text.method.LinkMovementMethod;
import android.view.View;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.google.android.material.imageview.ShapeableImageView;


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


        ShapeableImageView shapeableImageView = findViewById(R.id.imageView1);
        ShapeableImageView shapeableImageView1 = findViewById(R.id.imageView);


        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade);
        //animation for us
        textView = (TextView)findViewById(R.id.textView);
        textView.startAnimation(animation1);

        textView = (TextView)findViewById(R.id.textView9);
        textView.startAnimation(animation1);

        textView = (TextView)findViewById(R.id.textView10);
        textView.startAnimation(animation1);

        textView = (TextView)findViewById(R.id.textView1);
        textView.startAnimation(animation1);

        textView = (TextView)findViewById(R.id.textView11);
        textView.startAnimation(animation1);
        //rotate animation
        Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate);


       // shapeableImageView.startAnimation(animation2);
        //shapeableImageView1.startAnimation(animation2);


        //animation for organizers
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_more_time);
        constraintLayout = (ConstraintLayout) findViewById(R.id.constraint_layout_11);
        constraintLayout.startAnimation(animation);
        constraintLayout = (ConstraintLayout) findViewById(R.id.constraint_layout_12);
        constraintLayout.startAnimation(animation);



        constraintLayout = (ConstraintLayout) findViewById(R.id.constraint_layout_9);
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://shovito.absbpeople.com/")));
            }
        });

        constraintLayout = (ConstraintLayout) findViewById(R.id.constraint_layout_10);
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/nazmul.islam.3517563/")));
            }
        });



    }

}