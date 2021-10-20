package com.example.blood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class StartWindowActivity extends AppCompatActivity {

    private  static  int SPLASH_SCREEN=3000;

    ImageView start_image;
    TextView logo,slogan;
    Animation topAnim,bottomAnim;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_window);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        topAnim= AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim=AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        start_image=findViewById(R.id.imageView2);
        logo=findViewById(R.id.trackYourBus);
        slogan = findViewById(R.id.textView1);
        start_image.setAnimation(topAnim);
        logo.setAnimation(bottomAnim);
        slogan.setAnimation(bottomAnim);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Intent intent= new Intent(this,LoginActivity.class);
                //startActivity(intent);
                openActivity33();
                finish();

            }
        },SPLASH_SCREEN);


        /*button=(Button) findViewById(R.id.click_to_continue);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity33();
            }
        });*/



    }


    public void openActivity33() {
        //Intent intent=new Intent(this,LoginActivity.class);
        Intent intent=new Intent(this,SignInActivity.class);
        startActivity(intent);

    }
}