package com.shatrend.parkx.activities.customer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.shatrend.parkx.R;

public class VerifyCodeActivity extends AppCompatActivity {

    private Button btnVerifyCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_code);

        btnVerifyCode = findViewById(R.id.activity_verify_code_btn_verify_code);
        btnVerifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resetPassIntent = new Intent(VerifyCodeActivity.this, ResetPasswordActivity.class);
                startActivity(resetPassIntent);
            }
        });

    }
}