package com.example.twistytails;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.twistytails.Customer;
import com.example.twistytails.CustomerLoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CustomerRegisterActivity extends AppCompatActivity {

    private EditText usernameEditText, phoneNoEditText, emailEditText, passwordEditText, addressEditText;
    private Button signUpButton;
    private TextView loginLinkTextView;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_register);

        mAuth = FirebaseAuth.getInstance();

        usernameEditText = findViewById(R.id.cu_reg_name);
        phoneNoEditText = findViewById(R.id.cu_reg_phoneNo);
        emailEditText = findViewById(R.id.cu_reg_email);
        passwordEditText = findViewById(R.id.cu_reg_password);
        addressEditText = findViewById(R.id.cu_reg_address);
        signUpButton = findViewById(R.id.cu_reg_btn);
        loginLinkTextView = findViewById(R.id.cu_log_link_btn);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        loginLinkTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomerRegisterActivity.this, CustomerLoginActivity.class));
                finish();
            }
        });
    }

    private void registerUser() {
        String username = usernameEditText.getText().toString().trim();
        String phoneNo = phoneNoEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String address = addressEditText.getText().toString().trim();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(phoneNo) || TextUtils.isEmpty(email) ||
                TextUtils.isEmpty(password) || TextUtils.isEmpty(address)) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            saveUserDataToDatabase(user.getUid(), username, phoneNo, email, address);
                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                emailEditText.setError("User with this email already exists");
                            } else {
                                Toast.makeText(CustomerRegisterActivity.this, "Registration failed. Please try again.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void saveUserDataToDatabase(String userId, String username, String phoneNo, String email, String address) {
        DatabaseReference customersRef = FirebaseDatabase.getInstance().getReference().child("Customer");
        Customer customer = new Customer(userId, username, phoneNo, email, address);
        customersRef.child(userId).setValue(customer)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(CustomerRegisterActivity.this, "Registration successful.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(CustomerRegisterActivity.this, CustomerLoginActivity.class));
                            finish();
                        } else {
                            Toast.makeText(CustomerRegisterActivity.this, "Failed to register. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
