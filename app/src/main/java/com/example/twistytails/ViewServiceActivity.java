package com.example.twistytails;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewServiceActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ViewServiceAdapter adapter;
    private List<CusService> serviceList;
    private String userId;
    private String customerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_service);

        // Find the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Enable the back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Set a custom back button icon
        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24); // Replace ic_back_arrow with your actual back button image

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Retrieve customerId from intent
        Intent intent = getIntent();
        if (intent != null) {
            customerId = intent.getStringExtra("customerId");
            if (customerId != null) {
                // Now you have the customerId, you can use it as needed
                Log.d("ViewServiceActivity", "CustomerId: " + customerId);
            } else {
                // Handle if customerId is null
                Log.e("ViewServiceActivity", "CustomerId is null");
                Toast.makeText(this, "Customer ID is null", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Handle if intent is null
            Log.e("ViewServiceActivity", "Intent is null");
            Toast.makeText(this, "Intent is null", Toast.LENGTH_SHORT).show();
        }

        // Get userId passed from CustomerHomeActivity
        userId = getIntent().getStringExtra("userId");
        String storeName = getIntent().getStringExtra("storeName");
        serviceList = new ArrayList<>();
        adapter = new ViewServiceAdapter(this, serviceList, userId, storeName, customerId); // Pass empty strings initially
        recyclerView.setAdapter(adapter);

        // Navigate to the services node under the specified user
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Hospital").child(userId);
        DatabaseReference servicesRef = userRef.child("services");

        servicesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot serviceSnapshot : snapshot.getChildren()) {
                    String userId = getIntent().getStringExtra("userId");
                    CusService service = serviceSnapshot.getValue(CusService.class);
                    service.setUserId(userId); // Set the userId for the service
                    serviceList.add(service);
                }
                // Notify adapter of changes
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled if needed
                Toast.makeText(ViewServiceActivity.this, "Failed to retrieve services", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case android.R.id.home:
                // Navigate back to CustomerHomeActivity when the back button is pressed
                Intent intent = new Intent(this, CustomerHomeActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
