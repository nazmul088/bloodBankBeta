package com.example.blood.Reset_Password;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.blood.R;
import com.example.blood.User;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPasswordActivity extends AppCompatActivity {



    class Emailclass{
        public String email;

        public Emailclass(String toString) {
            email = toString;
        }
    }

    private EditText editText;
    private Button button;
    private ResetPasswordApi resetPasswordApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        editText =(EditText) findViewById(R.id.editTextTextPersonName6);

        button = (Button) findViewById(R.id.button3);

        SharedPreferences prefs = this.getSharedPreferences("ResetPasswordPref", Context.MODE_PRIVATE);
        String email = prefs.getString("resetEmail", "");
        if(email.length()>0)
        {
            startActivity(new Intent(ResetPasswordActivity.this,NewPasswordActivity.class));
        }

        resetPasswordApi = RetrofitInstanceReset.getRetrofit().create(ResetPasswordApi.class);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //New email taken as input
                ProgressDialog progressDialog = new ProgressDialog(ResetPasswordActivity.this);
                progressDialog.setMessage("Loading...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                String email = editText.getText().toString();
                System.out.println(editText.getText().toString());
                UserReset userReset = new UserReset();
                userReset.setEmail(editText.getText().toString());
                System.out.println(userReset.getEmail());

                /*resetPasswordApi.SendOtpResetPassowrd(userReset).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        progressDialog.dismiss();
                        if(response.isSuccessful())
                        {
                            SharedPreferences settings1 = getSharedPreferences("ResetPasswordPref", 0);
                            SharedPreferences.Editor editor = settings1.edit();


                            editor.putString("resetEmail", editText.getText().toString());
                            //editor.putString("userid",id);
                            editor.commit();


                            startActivity(new Intent(ResetPasswordActivity.this,NewPasswordActivity.class));

                        }
                        else
                        {
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
                            Toast.makeText(ResetPasswordActivity.this, error, Toast.LENGTH_LONG).show();


                        }

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(ResetPasswordActivity.this, "Connection Failed", Toast.LENGTH_SHORT).show();
                    }
                });*/
            }
        });

    }
}