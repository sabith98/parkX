package com.shatrend.parkx.activities.customer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.shatrend.parkx.R;
import com.shatrend.parkx.models.User;

public class CustomerLoginActivity extends AppCompatActivity {
    private TextView tvToRegister, tvToForgotPassword;
    private Button btnLogin;
    private EditText etEmail, etPassword;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_login);

        tvToForgotPassword = findViewById(R.id.activity_login_btn_to_forgot_password);
        btnLogin = findViewById(R.id.btn_login);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);

        mAuth = FirebaseAuth.getInstance();

        // Navigate to driver Register page
        tvToRegister = findViewById(R.id.activity_login_tv_to_register);
        tvToRegister.setOnClickListener(view -> {
            Intent registerIntent = new Intent(CustomerLoginActivity.this, CustomerRegisterActivity.class);
            startActivity(registerIntent);
        });

        // Navigate to forgot password activity (Send reset link page)
        tvToForgotPassword.setOnClickListener(view -> {
            Intent forgotPasswordIntent = new Intent(CustomerLoginActivity.this, SendResetLinkActivity.class);
            startActivity(forgotPasswordIntent);
        });

        // login functionality
        btnLogin.setOnClickListener(view -> {
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();

            // Validating User login
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
            if (password.isEmpty()) {
                etPassword.requestFocus();
                etPassword.setError("Password is required");
                return;
            }

            User user = new User(email, password);
            signIn(user);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void signIn(User user) {
        mAuth.signInWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = mAuth.getCurrentUser();
                        updateUI(firebaseUser);
                    } else {
                        if (task.getException() instanceof FirebaseAuthInvalidUserException) {
                            Toast.makeText(CustomerLoginActivity.this, "No account found with this email.",
                                    Toast.LENGTH_SHORT).show();
                        } else if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            Toast.makeText(CustomerLoginActivity.this, "Invalid password.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.w("SIGNIN_FAILED", "signInWithEmail:failure", task.getException());
                            Toast.makeText(CustomerLoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        updateUI(null);
                    }
                });
    }

    private void updateUI(FirebaseUser firebaseUser) {
        if (firebaseUser != null) {
            Intent homeIntent = new Intent(CustomerLoginActivity.this, HomeActivity.class);
            homeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(homeIntent);
            finish();
        } else {
            etPassword.setText("");
        }
    }
}