package com.shatrend.parkx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private TextView tvToRegister, tvToForgotPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Handle the splash screen transition.
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tvToRegister = findViewById(R.id.activity_login_tv_to_register);
        tvToForgotPassword = findViewById(R.id.activity_login_btn_to_forgot_password);
        btnLogin = findViewById(R.id.activity_login_btn_login);

        // Navigate to Register page on click
        tvToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });

        // Navigate to forgot password activity (Send reset link page)
        tvToForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent forgotPasswordIntent = new Intent(LoginActivity.this, SendResetLinkActivity.class);
                startActivity(forgotPasswordIntent);
            }
        });

        // login functionality
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mapsIntent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(mapsIntent);
            }
        });
    }

//    public void navigateToSignup() {
//        Intent intent = new Intent(this, RegisterActivity.class);
//        startActivity(intent);
//    }
}