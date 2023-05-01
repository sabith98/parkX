package com.shatrend.parkx.activities.driver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shatrend.parkx.R;
import com.shatrend.parkx.helpers.DatabaseHelper;
import com.shatrend.parkx.models.Driver;

public class DriverRegisterActivity extends AppCompatActivity {

    private TextView tvLogin;
    private EditText etEmail;
    private EditText etPassword;
    private Button btnRegister;

    private DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_register);

        tvLogin = findViewById(R.id.activity_register_tv_login);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnRegister = findViewById(R.id.activity_driver_register_btn_register);

        mDatabaseHelper = new DatabaseHelper(this);

        // Navigate to login page on click
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DriverRegisterActivity.this, DriverLoginActivity.class);
                startActivity(intent);
            }
        });

        // Register new driver
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                    Driver driver = new Driver(0, email, password);
                    boolean success = mDatabaseHelper.addDriver(driver);

                    if (success) {
                        Toast.makeText(DriverRegisterActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                        Intent homeIntent = new Intent(DriverRegisterActivity.this, HomeActivity.class);
                        startActivity(homeIntent);
                    } else {
                        Toast.makeText(DriverRegisterActivity.this, "Registration failed!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DriverRegisterActivity.this, "Please enter email and password.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}