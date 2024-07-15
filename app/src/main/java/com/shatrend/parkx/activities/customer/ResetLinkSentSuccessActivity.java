package com.shatrend.parkx.activities.customer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.shatrend.parkx.R;

public class ResetLinkSentSuccessActivity extends AppCompatActivity {
    Button btn_Sign_In_Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_link_sent_success);

        btn_Sign_In_Back = findViewById(R.id.activity_send_reset_link_success_btn_to_sign_in);
        btn_Sign_In_Back.setOnClickListener(view -> {
            Intent loginIntent = new Intent(ResetLinkSentSuccessActivity.this, CustomerLoginActivity.class);
            startActivity(loginIntent);
            finish();
        });
    }
}