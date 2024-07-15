package com.shatrend.parkx.activities.customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.shatrend.parkx.R;

public class SendResetLinkActivity extends AppCompatActivity {

    private TextView tvToRegister;
    private Button btnSendResetLink;
    private EditText et_reset_email;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_reset_link);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvToRegister = findViewById(R.id.activity_send_reset_link_tv_to_register);
        btnSendResetLink = findViewById(R.id.activity_send_reset_link_btn_send_reset_link);
        et_reset_email = findViewById(R.id.activity_send_reset_link_et_email);
        firebaseAuth = FirebaseAuth.getInstance();

        tvToRegister.setOnClickListener(view -> {
            Intent registerIntent = new Intent(SendResetLinkActivity.this, CustomerRegisterActivity.class);
            startActivity(registerIntent);
        });

        btnSendResetLink.setOnClickListener(view -> {
            String email = et_reset_email.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                et_reset_email.setError("Please enter your email");
                return;
            }

            firebaseAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(SendResetLinkActivity.this, "Password reset email sent", Toast.LENGTH_LONG).show();
                            Intent resetLinkSentSuccessIntent = new Intent(SendResetLinkActivity.this, ResetLinkSentSuccessActivity.class);
                            startActivity(resetLinkSentSuccessIntent);
                        } else {
                            Toast.makeText(SendResetLinkActivity.this, "Failed to send password reset email", Toast.LENGTH_SHORT).show();
                        }
                    });
        });

    }

    // Logic to handle back navigation button
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
//        return super.onSupportNavigateUp();
        onBackPressed();
        return true;
    }
}