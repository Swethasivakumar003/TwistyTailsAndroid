package com.example.twistytails;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.FirebaseDatabase;

public class SelectLoginActivity extends AppCompatActivity {
    Button adminLogin,hospitalLogin,customerLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_login);

        adminLogin = findViewById(R.id.admin);
        hospitalLogin = findViewById(R.id.hospital);
        customerLogin = findViewById(R.id.customer);

        adminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Move to the next activity
                Intent b = new Intent(SelectLoginActivity.this,AdminLoginActivity.class);
                startActivity(b);
            }
        });

        hospitalLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Move to the next activity
                Intent b = new Intent(SelectLoginActivity.this,HospitalLoginActivity.class);
                startActivity(b);
            }
        });

        customerLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Move to the next activity
                Intent b = new Intent(SelectLoginActivity.this,CustomerRegisterActivity.class);
                startActivity(b);
            }
        });
    }
}