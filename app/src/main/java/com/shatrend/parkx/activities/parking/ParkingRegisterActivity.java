package com.shatrend.parkx.activities.parking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shatrend.parkx.activities.driver.HomeActivity;
import com.shatrend.parkx.R;
import com.shatrend.parkx.helpers.DatabaseHelper;
import com.shatrend.parkx.models.Parking;

public class ParkingRegisterActivity extends AppCompatActivity {

    private Button btnRegister;
    private EditText etEmail, etPassword;
    private TextView tvLogin;

    private DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_register);

        etEmail = findViewById(R.id.activity_parking_register_et_email);
        etPassword = findViewById(R.id.activity_parking_register_et_password);

        mDatabaseHelper = new DatabaseHelper(this);

        // Registration functionality for Parking
        btnRegister = findViewById(R.id.activity_parking_register_btn_register);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                    Parking parking = new Parking(0, email, password);
                    boolean success = mDatabaseHelper.addParking(parking);

                    if (success) {
                        Toast.makeText(ParkingRegisterActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                        Intent homeIntent = new Intent(ParkingRegisterActivity.this, HomeActivity.class);
                    } else {
                        Toast.makeText(ParkingRegisterActivity.this, "Registration failed!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ParkingRegisterActivity.this, "Please enter email and password.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Navigate to Parking login
        tvLogin = findViewById(R.id.activity_parking_register_tv_to_login);
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toLoginIntent = new Intent(ParkingRegisterActivity.this, ParkingLoginActivity.class);
                startActivity(toLoginIntent);
            }
        });
    }
}