package com.example.nwureddrops;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RecentBloodRequests extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BloodRequestAdapter bloodRequestAdapter;
    private List<BloodRequests> bloodRequestList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_blood_requests);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        bloodRequestList = new ArrayList<>();
        bloodRequestAdapter = new BloodRequestAdapter(bloodRequestList);
        recyclerView.setAdapter(bloodRequestAdapter);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("blood_requests");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bloodRequestList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    BloodRequests bloodRequest = postSnapshot.getValue(BloodRequests.class);
                    if (bloodRequest != null) {
                        bloodRequestList.add(bloodRequest);
                    }
                }
                // Reverse the list to display the most recent requests first
                Collections.reverse(bloodRequestList);
                bloodRequestAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors here
            }
        });

        // Back Button
        Button backButton = findViewById(R.id.buttonBack);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Close the activity and go back
            }
        });

    }

    private String getRequestTimeAndDate(String donationDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        try {
            Date date = dateFormat.parse(donationDate);
            SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
            return "Request Time: " + timeFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "Request Time: N/A";
        }
    }
}
