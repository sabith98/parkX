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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.shatrend.parkx.R;
import com.shatrend.parkx.models.User;

public class CustomerRegisterActivity extends AppCompatActivity {
    private TextView tvLogin;
    private EditText etEmail;
    private EditText etPassword;
    private Button btnRegister;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_register);

        tvLogin = findViewById(R.id.activity_register_tv_login);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnRegister = findViewById(R.id.activity_driver_register_btn_register);

        mAuth = FirebaseAuth.getInstance();

        // Navigate to login page on click
        tvLogin.setOnClickListener(view -> {
            Intent intent = new Intent(CustomerRegisterActivity.this, CustomerLoginActivity.class);
            startActivity(intent);
        });

        // Register new driver
        btnRegister.setOnClickListener(view -> {
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();

            // Validating Driver registration
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

            String passwordPattern = "^.{6,}$";
            if (!password.matches(passwordPattern)) {
                etPassword.requestFocus();
                etPassword.setError("Password must contain at least 6 characters");
                return;
            }

            User user = new User(email, password);
            signUp(user);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void signUp(User user) {
        mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = mAuth.getCurrentUser();
                        Toast.makeText(CustomerRegisterActivity.this, "Authentication succeeded.",
                                Toast.LENGTH_SHORT).show();
                        updateUI(firebaseUser);
                    } else {
                        if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                            Toast.makeText(CustomerRegisterActivity.this, "This email is already registered.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // Other errors
                            Log.w("SIGNUP_FAILED", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(CustomerRegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        updateUI(null);
                    }
                });
    }

    private void updateUI(FirebaseUser firebaseUser) {
        if (firebaseUser != null) {
            Intent homeIntent = new Intent(CustomerRegisterActivity.this, HomeActivity.class);
            homeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(homeIntent);
            finish();
        } else {
            etPassword.setText("");
        }
    }
}