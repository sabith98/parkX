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

                // Validating Parking registration
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
                if (mDatabaseHelper.isParkingEmailExist(email)) {
                    etEmail.requestFocus();
                    etEmail.setError("Email already exists");
                    return;
                }
                if (password.isEmpty()) {
                    etPassword.requestFocus();
                    etPassword.setError("Password is required");
                    return;
                }
                String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
                if (!password.matches(passwordPattern)) {
                    etPassword.requestFocus();
                    etPassword.setError("Password must contain at least 8 characters, " +
                            "including one uppercase letter, one lowercase letter, one digit, " +
                            "and one special character");
                    return;
                }

                Parking parking = new Parking(0, email, password);
                long parkingIdLong = mDatabaseHelper.addParking(parking);
                int parkingId = (int) parkingIdLong;

                if (parkingId != -1) {
                    Toast.makeText(ParkingRegisterActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                    Intent parkingInfoIntent = new Intent(ParkingRegisterActivity.this, ParkingInfoActivity.class);
                    parkingInfoIntent.putExtra("parking_id", parkingId);
                    startActivity(parkingInfoIntent);
                } else {
                    Toast.makeText(ParkingRegisterActivity.this, "Registration failed!", Toast.LENGTH_SHORT).show();
                }

                mDatabaseHelper.close();
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