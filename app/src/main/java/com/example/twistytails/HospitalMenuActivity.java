package com.example.twistytails;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HospitalMenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DatabaseReference userBookingsRef;
    private String userId;
    ImageView cartImageView, profileImageView, logoutImageView;

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_menu);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cartImageView = findViewById(R.id.cart);
        profileImageView = findViewById(R.id.profile);
        logoutImageView = findViewById(R.id.cusLogout);

        // Change status bar color and battery icon color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = getWindow();
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.orange));
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.Open_nav, R.string.Close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setTitle("HOME");

        // Get current user's ID
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            userId = currentUser.getUid();
        } else {
            // Handle the case where the user is not authenticated
            Toast.makeText(this, "User is not authenticated.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Set click listeners for the buttons
        cartImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click on cart button
                // Example: Start CartActivity
                startActivity(new Intent(HospitalMenuActivity.this, HospitalMenuActivity.class));
            }
        });

        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click on profile button
                // Example: Start ProfileActivity
                startActivity(new Intent(HospitalMenuActivity.this, AddServiceActivity.class));
            }
        });

        logoutImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click on logout button
                // Example: Implement logout functionality
                startActivity(new Intent(HospitalMenuActivity.this, OwnerHomeActivity.class));
            }
        });

        // Initialize Firebase database reference
        userBookingsRef = FirebaseDatabase.getInstance().getReference("Hospital").child(userId).child("appointments");

        // Retrieve appointment details from Firebase and display in the layout
        userBookingsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                LinearLayout containerLayout = findViewById(R.id.owner_appointments_container);
                containerLayout.removeAllViews(); // Clear previous views

                // Loop through each appointment
                for (DataSnapshot appointmentSnapshot : dataSnapshot.getChildren()) {
                    // Get appointment details
                    String customerId = appointmentSnapshot.child("customerId").getValue(String.class);
                    String serviceName = appointmentSnapshot.child("serviceName").getValue(String.class);
                    String customerName = appointmentSnapshot.child("customerName").getValue(String.class);
                    String phoneNumber = appointmentSnapshot.child("phoneNumber").getValue(String.class);
                    String bookingDate = appointmentSnapshot.child("bookingDate").getValue(String.class);
                    String selectedTime = appointmentSnapshot.child("selectedTime").getValue(String.class);
                    String storePreference = appointmentSnapshot.child("storePreference").getValue(String.class);

                    // Inflate the layout for each appointment
                    View appointmentView = LayoutInflater.from(HospitalMenuActivity.this).inflate(R.layout.appointment_item, containerLayout, false);

                    // Find TextViews inside the inflated layout
                    TextView customerIdTextView = appointmentView.findViewById(R.id.customerIdd);
                    TextView serviceNameTextView = appointmentView.findViewById(R.id.cartServiceName);
                    TextView customerNameTextView = appointmentView.findViewById(R.id.cartCustomerName);
                    TextView preferencesNameTextView = appointmentView.findViewById(R.id.preferences);
                    TextView phoneNumberTextView = appointmentView.findViewById(R.id.cartPhoneNumber);
                    TextView bookingDateTextView = appointmentView.findViewById(R.id.cartBookingDate);
                    TextView selectedTimeTextView = appointmentView.findViewById(R.id.cartSelectedTime);

                    // Set appointment details to TextViews
                    customerIdTextView.setText("Customer ID: " + customerId);
                    serviceNameTextView.setText("Service: " + serviceName);
                    customerNameTextView.setText("Customer: " + customerName);
                    phoneNumberTextView.setText("Phone: " + phoneNumber);
                    preferencesNameTextView.setText("Service: " + storePreference); // Display store preference
                    bookingDateTextView.setText("Date: " + bookingDate);
                    selectedTimeTextView.setText("Time: " + selectedTime);

                    // Add OnClickListener to the inflated layout
                    appointmentView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Start CustomerStaffScheduleActivity and pass appointment details
                            Intent intent = new Intent(HospitalMenuActivity.this, HospitalMenuActivity.class);
                            intent.putExtra("customerId", customerId);
                            intent.putExtra("serviceName", serviceName);
                            intent.putExtra("customerName", customerName);
                            intent.putExtra("phoneNumber", phoneNumber);
                            intent.putExtra("bookingDate", bookingDate);
                            intent.putExtra("selectedTime", selectedTime);
                            intent.putExtra("storePreference", storePreference);
                            startActivity(intent);
                        }
                    });

                    // Add the inflated layout to the container layout
                    containerLayout.addView(appointmentView);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("OwnerAppointments", "Error fetching appointments: " + databaseError.getMessage());
                Toast.makeText(HospitalMenuActivity.this, "Error fetching appointments", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // Go back when the home button in the toolbar is clicked
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.add_hospital) {
            Intent intent = new Intent(this, HospitalMenuActivity.class);
            startActivity(intent);
        } else if (id == R.id.add_service) {
            Intent intent = new Intent(this, OwnerHomeActivity.class);
            startActivity(intent);
        } else if (id == R.id.add_category) {
            Intent intent = new Intent(this, AddServiceActivity.class);
            startActivity(intent);
        } else if (id == R.id.admin_home_logout) {
            Intent intent = new Intent(this, SelectLoginActivity.class);
            startActivity(intent);
            startActivity(intent);
        }

        drawerLayout.closeDrawer(GravityCompat.START); // Change here to GravityCompat.START
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) { // Change here to GravityCompat.START
            drawerLayout.closeDrawer(GravityCompat.START); // Change here to GravityCompat.START
        } else {
            super.onBackPressed();
        }
    }
}