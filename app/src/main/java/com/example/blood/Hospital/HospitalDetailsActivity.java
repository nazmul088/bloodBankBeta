package com.example.blood.Hospital;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blood.AmbulanceActivity;
import com.example.blood.R;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class HospitalDetailsActivity extends AppCompatActivity {

    private TextView textView;

    private String selectedPhoneNumbertoCall;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_details);

        datum data = (datum) getIntent().getSerializableExtra("hospital");
        System.out.println("PLace name: "+data.getName());


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
            }
        });

        textView =(TextView) findViewById(R.id.textView74);
        textView.setText(data.getName());

        Geocoder geocoder;
        List<Address> addresses = null;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(Double.parseDouble(data.getLatitude()), Double.parseDouble(data.getLongitude()), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }

        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        String city = addresses.get(0).getLocality();
        String state = addresses.get(0).getAdminArea();
        String country = addresses.get(0).getCountryName();
        String postalCode = addresses.get(0).getPostalCode();
        String knownName = addresses.get(0).getFeatureName();
        System.out.println(address +city + state+"" +knownName);

        textView =(TextView) findViewById(R.id.textView75);
        textView.setText(address);

        textView =(TextView) findViewById(R.id.textView76);
        textView.setText(data.getPhone_number());

        textView = (TextView) findViewById(R.id.textView82);
        textView.setText(String.valueOf(data.getHfn_beds_available()));


        textView = (TextView) findViewById(R.id.textView83);
        textView.setText(String.valueOf(data.getHfn_beds()) );

        textView = (TextView) findViewById(R.id.textView85);
        textView.setText(String.valueOf(data.getIcu_beds_available()));

        textView = (TextView) findViewById(R.id.textView86);
        textView.setText(String.valueOf(data.getIcu_beds()));


        textView = (TextView) findViewById(R.id.textView88);
        textView.setText(String.valueOf(data.getHdu_beds_available()));

        textView = (TextView) findViewById(R.id.textView89);
        textView.setText(String.valueOf(data.getHdu_beds()));

        textView = (TextView) findViewById(R.id.textView91);
       textView.setText(String.valueOf(data.getGeneral_beds_available()));

        textView = (TextView) findViewById(R.id.textView92);
        textView.setText(String.valueOf(data.getGeneral_beds()));


        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss", Locale.ENGLISH);

        LocalDateTime date = LocalDateTime.parse(data.getDghs_update(),outputFormatter);
        LocalDateTime today = LocalDateTime.now();
        Duration duration = Duration.between(date,today);

        String last_update="";
        if(duration.toDays()==0)
            last_update = "Last Update about "+String.valueOf(duration.toHours())+" hours ago";

        else if(duration.toDays()==1)
            last_update = "Last Update about "+String.valueOf(duration.toDays())+" day ago";
        else
            last_update = "Last Update about "+String.valueOf(duration.toDays())+" days ago";

        textView = (TextView) findViewById(R.id.textView93);
        textView.setText(last_update);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedPhoneNumbertoCall = data.getPhone_number();
                makePhoneCall();
            }
        });


    }

    private void makePhoneCall() {

        if (ContextCompat.checkSelfPermission(HospitalDetailsActivity.this, Manifest.permission.CALL_PHONE) !=
                PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(HospitalDetailsActivity.this,"Allow to call",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri = Uri.fromParts("package", getPackageName(), null);
            intent.setData(uri);
            startActivity(intent);
            /*ActivityCompat.requestPermissions(AmbulanceActivity.this, new String[]{
                    Manifest.permission.CALL_PHONE
            }, REQUEST_CALL_PERMISSION);*/
        } else {

            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + selectedPhoneNumbertoCall));
            startActivity(intent);
        }


    }

}