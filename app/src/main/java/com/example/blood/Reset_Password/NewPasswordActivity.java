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
import android.widget.TextView;
import android.widget.Toast;

import com.example.blood.ProfileActivity;
import com.example.blood.R;
import com.example.blood.SignInActivity;
import com.example.blood.User;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewPasswordActivity extends AppCompatActivity {

    private TextView textView;
    private EditText editText;
    private Button button;

    private ResetPasswordApi resetPasswordApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);

        SharedPreferences sharedPreferences = this.getSharedPreferences("ResetPasswordPref", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("resetEmail", "");

        textView = (TextView) findViewById(R.id.textView95);
        textView.setText(email);

        button = (Button) findViewById(R.id.button5);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText = (EditText) findViewById(R.id.editTextTextPersonName7);
                String newPassword = editText.getText().toString();
                editText = (EditText) findViewById(R.id.editTextTextPersonName8);
                String retypenewPassword = editText.getText().toString();
                if(!newPassword.equals(retypenewPassword))
                {
                    Toast.makeText(NewPasswordActivity.this, "Please Enter Valid Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                    resetPasswordApi = RetrofitInstanceReset.getRetrofit().create(ResetPasswordApi.class);
                    editText = (EditText) findViewById(R.id.editTextTextPersonName9);
                    String token = editText.getText().toString();
                    editText = (EditText) findViewById(R.id.editTextTextPersonName7);
                    String password = editText.getText().toString();
                    UserReset userReset = new UserReset(email,token,password);
                    ProgressDialog progressDialog = new ProgressDialog(NewPasswordActivity.this);
                    progressDialog.setMessage("Loading...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    resetPasswordApi.verifyOtp(userReset).enqueue(new Callback<UserReset>() {
                        @Override
                        public void onResponse(Call<UserReset> call, Response<UserReset> response) {
                            progressDialog.dismiss();
                            if(response.isSuccessful())
                            {
                                SharedPreferences prefs = NewPasswordActivity.this.getSharedPreferences("ResetPasswordPref", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putString("resetEmail", "");     //RESET TO DEFAULT VALUE
                                editor.commit();
                                Toast.makeText(NewPasswordActivity.this, "Password Changed Successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(NewPasswordActivity.this, SignInActivity.class));
                            }
                            else{
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

                                Toast.makeText(NewPasswordActivity.this, error, Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<UserReset> call, Throwable t) {
                            progressDialog.dismiss();
                            Toast.makeText(NewPasswordActivity.this, "Connection Failed", Toast.LENGTH_SHORT).show();

                        }
                    });

                }
            }
        });

    }
}