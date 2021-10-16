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

import java.io.BufferedReader;
import java.io.InputStreamReader;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPasswordActivity2 extends AppCompatActivity {
    private EditText editText;
    private Button button;
    private ResetPasswordApi resetPasswordApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        editText = (EditText) findViewById(R.id.editTextTextPersonName6);

        button = (Button) findViewById(R.id.button3);

        String email = getIntent().getStringExtra("email");
        editText.setText(email);
        editText.setFocusable(false);
        resetPasswordApi = RetrofitInstanceReset.getRetrofit().create(ResetPasswordApi.class);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //New email taken as input
                ProgressDialog progressDialog = new ProgressDialog(ResetPasswordActivity2.this);
                progressDialog.setMessage("Loading...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                UserReset userReset = new UserReset();
                userReset.setEmail(editText.getText().toString());
                resetPasswordApi.SendOtpResetPassowrd(userReset).enqueue(new Callback<UserReset>() {
                    @Override
                    public void onResponse(Call<UserReset> call, Response<UserReset> response) {
                        progressDialog.dismiss();
                        if (response.isSuccessful()) {
                            if (response.body().getMessage().length() > 0) {
                                Toast.makeText(ResetPasswordActivity2.this, "An OTP has been sent to your email", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(ResetPasswordActivity2.this, NewPasswordActivity2.class);
                                intent.putExtra("email",email);

                                startActivity(intent);
                            }
                        } else {
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
                            Toast.makeText(ResetPasswordActivity2.this, error, Toast.LENGTH_LONG).show();


                        }

                    }

                    @Override
                    public void onFailure(Call<UserReset> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(ResetPasswordActivity2.this, "Connection Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}