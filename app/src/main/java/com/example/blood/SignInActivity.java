package com.example.blood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.blood.Reset_Password.ResetPasswordActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {

    private TextView textView;
    private Button button;
    private CardView cardView;
    private UserApi userApi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences prefs = this.getSharedPreferences("logInPref", Context.MODE_PRIVATE);
        String token = prefs.getString("token", "");
        if(token.length()>0)
        {
            Intent intent =  new Intent(SignInActivity.this,MainActivity.class);
            startActivity(intent);
        }

        prefs = this.getSharedPreferences("ResetPasswordPref",Context.MODE_PRIVATE);
        String email = prefs.getString("resetEmail","");
        if(email.length()>0)
        {
            startActivity(new Intent(SignInActivity.this,ResetPasswordActivity.class));
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);



        textView = (TextView) findViewById(R.id.signUpLabelInSignIn);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });

        textView = (TextView) findViewById(R.id.textView94);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this, ResetPasswordActivity.class));
            }
        });


        cardView = (CardView) findViewById(R.id.cardView);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog progressDialog = new ProgressDialog(SignInActivity.this);
                progressDialog.setMessage("Loading...");
                progressDialog.show();
                progressDialog.setCancelable(false);
                textView = (TextView) findViewById(R.id.editTextTextEmailAddress2);
                String Email = textView.getText().toString();
                textView = (TextView) findViewById(R.id.editTextTextPassword2);
                String password = textView.getText().toString();
                User user = new User(Email,password);

                userApi = RetrofitInstanceAuth.getRetrofit().create(UserApi.class);
                Call<User> call = userApi.logInUser(user);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(!response.isSuccessful())
                        {
                            progressDialog.dismiss();
                            Toast.makeText(SignInActivity.this,"Log in not successful",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            String token =  response.raw().header("x-auth-token");
                            User user1 = response.body();
                            String id = user1.get_id();
                            SharedPreferences settings = getSharedPreferences("logInPref", 0);
                            SharedPreferences.Editor editor = settings.edit();


                            editor.putString("token", token);
                            editor.putString("userid",id);
                            editor.commit();
                            //Received Token
                            //Now Logging In
                            progressDialog.dismiss();

                            Intent intent = new Intent(SignInActivity.this,MainActivity.class);
                            intent.putExtra("id",id);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });




            }
        });



    }

    @Override
    public void onBackPressed() {
        this.finishAffinity();
    }
}