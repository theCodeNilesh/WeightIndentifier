package com.example.weightindentifier;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class ReportGeneration extends AppCompatActivity {

    Geocoder geocoder;
    List<Address> addresses;


    double latitude, longitude;

    private TextView txtUserName, txtVehicleNumber, txtStatus, txtVehicleWeight, tvAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_generation);
        getSupportActionBar().setTitle("Vehicle Overload Detection System");
        txtUserName = findViewById(R.id.user_Name_TV);
        txtVehicleNumber = findViewById(R.id.vehicle_No_TV);
        txtStatus = findViewById(R.id.status_TV);
        txtVehicleWeight = findViewById(R.id.vehicle_Weight_TV);
        tvAddress = findViewById(R.id.tvAddress);

        Intent intent = getIntent();

        txtUserName.setText(intent.getStringExtra("userName"));
        txtVehicleNumber.setText(intent.getStringExtra("vehicleNumber"));
        txtStatus.setText(intent.getStringExtra("status"));
        txtVehicleWeight.setText(intent.getStringExtra("vehicleWeight")+" Gms");

        latitude = Double.parseDouble(intent.getStringExtra("latitude"));
        longitude = Double.parseDouble(intent.getStringExtra("longitude"));

        Log.i("latLong", "" + latitude + "," + longitude);
        /* String API_KEY = "DoNA6d4T7UelHye9Bk5zfA";
        String sendrId = "TESTIN";
        String mobileNo = "7387109772";
        String msg = "Hello " + intent.getStringExtra("userName") + " Your Vehicle is Overload and the weight of Your Vehicle is:" + intent.getStringExtra("vehicleWeight")+" Gms";

        String sendSms = "http://sms.innovationshub.in/api/mt/SendSMS?APIKey=" + API_KEY + "&senderid=" + sendrId + "&channel=2&DCS=0&flashsms=0&number=" + mobileNo + "&text=" + msg + "&route=1";

        Log.i("sendSms", "" + sendSms);*/


      /*  geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);

            String address = addresses.get(0).getAddressLine(0);
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();

            tvAddress.setText(""+address+"," +city+", "+state+", "+postalCode);



        } catch (IOException e) {

            e.printStackTrace();
        }*/


    }
}
