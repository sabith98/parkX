package com.shatrend.parkx.activities.driver;

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
import com.shatrend.parkx.models.Driver;

public class DriverLoginActivity extends AppCompatActivity {

    private TextView tvToRegister, tvToForgotPassword;
    private Button btnLogin;
    private EditText etEmail, etPassword;

    private DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_login);

        tvToForgotPassword = findViewById(R.id.activity_login_btn_to_forgot_password);
        btnLogin = findViewById(R.id.btn_login);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);

        mDatabaseHelper = new DatabaseHelper(this);

        // Navigate to driver Register page
        tvToRegister = findViewById(R.id.activity_login_tv_to_register);
        tvToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(DriverLoginActivity.this, DriverRegisterActivity.class);
                startActivity(registerIntent);
            }
        });

        // Navigate to forgot password activity (Send reset link page)
        tvToForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent forgotPasswordIntent = new Intent(DriverLoginActivity.this, SendResetLinkActivity.class);
                startActivity(forgotPasswordIntent);
            }
        });

        // login functionality
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                // Validating Driver login
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
                if (!mDatabaseHelper.isDriverEmailExist(email)) {
                    etEmail.requestFocus();
                    etEmail.setError("Email not registered");
                    return;
                }
                if (password.isEmpty()) {
                    etPassword.requestFocus();
                    etPassword.setError("Password is required");
                    return;
                }

                Driver driver = mDatabaseHelper.getDriverByEmail(email);
                if (driver != null && password.equals(driver.getPassword())) {
                    Toast.makeText(DriverLoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                    Intent mapsIntent = new Intent(DriverLoginActivity.this, HomeActivity.class);
                    startActivity(mapsIntent);
                } else {
                    Toast.makeText(DriverLoginActivity.this, "Invalid email or password.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}