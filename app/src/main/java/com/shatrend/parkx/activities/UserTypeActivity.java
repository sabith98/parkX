package com.shatrend.parkx.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.shatrend.parkx.R;
import com.shatrend.parkx.activities.driver.DriverLoginActivity;
import com.shatrend.parkx.activities.parking.ParkingLoginActivity;

public class UserTypeActivity extends AppCompatActivity {

    private Button btnSearch, btnProvide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Handle the splash screen transition.
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_type);

        btnSearch = findViewById(R.id.btn_search);
        btnProvide = findViewById(R.id.btn_provide);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent searchIntent = new Intent(UserTypeActivity.this, DriverLoginActivity.class);
                startActivity(searchIntent);
            }
        });
        btnProvide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent provideIntent = new Intent(UserTypeActivity.this, ParkingLoginActivity.class);
                startActivity(provideIntent);
            }
        });
    }
}