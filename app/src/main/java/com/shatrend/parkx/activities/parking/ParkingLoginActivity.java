package com.shatrend.parkx.activities.parking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shatrend.parkx.R;
import com.shatrend.parkx.helpers.DatabaseHelper;
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
                Intent parkingRegisterIntent = new Intent(
                        ParkingLoginActivity.this,
                        ParkingRegisterActivity.class
                );
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

                // Validating Parking login
                if (email.isEmpty()) {
                    etEmail.requestFocus();
                    etEmail.setError("Email is required");
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    etEmail.requestFocus();
                    etEmail.setError("Invalid email format");
                    return;
                }
                if (!mDatabaseHelper.isParkingEmailExist(email)) {
                    etEmail.requestFocus();
                    etEmail.setError("Email not registered");
                    return;
                }
                if (password.isEmpty()) {
                    etPassword.requestFocus();
                    etPassword.setError("Password is required");
                    return;
                }

//                Parking parking = mDatabaseHelper.getParkingByEmail(email);
//                if (parking != null && password.equals(parking.getPassword())) {
//                    int parkingId = parking.getId();
//                    Toast.makeText(ParkingLoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
////                    Intent mapsIntent = new Intent(ParkingLoginActivity.this, ParkingHomeActivity.class);
////                    startActivity(mapsIntent);
//                    Intent parkingInfoIntent = new Intent(ParkingLoginActivity.this, ParkingHomeActivity.class);
//                    parkingInfoIntent.putExtra("parkingId", parkingId);
//                    startActivity(parkingInfoIntent);
//                } else {
//                    Toast.makeText(ParkingLoginActivity.this, "Invalid email or password.", Toast.LENGTH_SHORT).show();
//                }
//
//                mDatabaseHelper.close();
            }
        });
    }
}