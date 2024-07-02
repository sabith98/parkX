package com.shatrend.parkx.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.splashscreen.SplashScreen;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.shatrend.parkx.R;
import com.shatrend.parkx.activities.driver.DriverLoginActivity;
import com.shatrend.parkx.activities.driver.HomeActivity;
import com.shatrend.parkx.activities.parking.ParkingInfoActivity;
import com.shatrend.parkx.activities.parking.ParkingLoginActivity;
import com.shatrend.parkx.models.ParkingInfo;

public class UserTypeActivity extends AppCompatActivity {

    private Button btnSearch, btnProvide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Handle the splash screen transition.
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_type);

        // Check Location permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Request permissions if not granted
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        btnSearch = findViewById(R.id.btn_search);
        btnProvide = findViewById(R.id.btn_provide);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Todo: uncomment searchIntent and remove homeIntent
//                Intent searchIntent = new Intent(UserTypeActivity.this, DriverLoginActivity.class);
//                startActivity(searchIntent);
                Intent homeIntent = new Intent(UserTypeActivity.this, HomeActivity.class);
                startActivity(homeIntent);
            }
        });
        btnProvide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent provideIntent = new Intent(UserTypeActivity.this, ParkingLoginActivity.class);
//                Intent provideIntent = new Intent(UserTypeActivity.this, ParkingInfoActivity.class);
                startActivity(provideIntent);
            }
        });
    }
}