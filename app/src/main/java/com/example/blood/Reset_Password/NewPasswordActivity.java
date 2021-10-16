package com.example.blood.Reset_Password;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.blood.R;

public class NewPasswordActivity extends AppCompatActivity {

    private TextView textView;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);

        SharedPreferences sharedPreferences = this.getSharedPreferences("ResetPasswordPref", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("resetEmail", "");

        textView = (TextView) findViewById(R.id.textView95);
        textView.setText(email);


    }
}