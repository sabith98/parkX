package com.shatrend.parkx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shatrend.parkx.helpers.DatabaseHelper;
import com.shatrend.parkx.models.Driver;
import com.shatrend.parkx.models.Parking;

public class ParkingLoginActivity extends AppCompatActivity {

    private TextView tvToParkingRegister;
    private Button btnLogin;
    private EditText etEmail, etPassword;

    private DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_login);

        etEmail = findViewById(R.id.activity_parking_login_et_email);
        etPassword = findViewById(R.id.activity_parking_login_et_password);

        mDatabaseHelper = new DatabaseHelper(this);

        // Navigate to Parking register
        tvToParkingRegister = findViewById(R.id.activity_login_tv_to_register);
        tvToParkingRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent parkingRegisterIntent = new Intent(ParkingLoginActivity.this, ParkingRegisterActivity.class);
                startActivity(parkingRegisterIntent);
            }
        });

        // Login functionality for parking
        btnLogin = findViewById(R.id.activity_parking_login_btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                    Parking parking = mDatabaseHelper.getParkingByEmail(email);

                    if (parking != null && password.equals(parking.getPassword())) {
                        Toast.makeText(ParkingLoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                        Intent mapsIntent = new Intent(ParkingLoginActivity.this, HomeActivity.class);
                        startActivity(mapsIntent);
                    } else {
                        Toast.makeText(ParkingLoginActivity.this, "Invalid email or password.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ParkingLoginActivity.this, "Please enter email and password.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}