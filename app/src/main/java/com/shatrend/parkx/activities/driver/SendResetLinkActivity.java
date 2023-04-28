package com.shatrend.parkx.activities.driver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.shatrend.parkx.R;

public class SendResetLinkActivity extends AppCompatActivity {

    private TextView tvToRegister;
    private Button btnSendResetLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_reset_link);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvToRegister = findViewById(R.id.activity_send_reset_link_tv_to_register);
        btnSendResetLink = findViewById(R.id.activity_send_reset_link_btn_send_reset_link);

        tvToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(SendResetLinkActivity.this, DriverRegisterActivity.class);
                startActivity(registerIntent);
            }
        });

        btnSendResetLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent verifyCodeIntent = new Intent(SendResetLinkActivity.this, VerifyCodeActivity.class);
                startActivity(verifyCodeIntent);
            }
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