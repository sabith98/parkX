package com.shatrend.parkx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ResetPasswordActivity extends AppCompatActivity {

    private Button btnResetPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        btnResetPass = findViewById(R.id.activity_reset_password_btn_reset);

        btnResetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ResetPasswordActivity.this, "Password successfully changed", Toast.LENGTH_SHORT).show();
                Intent mapsIntent = new Intent(ResetPasswordActivity.this, MapsActivity.class);
                startActivity(mapsIntent);
            }
        });

    }
}