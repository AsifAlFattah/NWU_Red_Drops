package com.example.nwureddrops;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class FindDonor extends AppCompatActivity {

    private TableLayout donorTable;
    Spinner spinnerBloodGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_donor);





        // Initialize the spinner
        spinnerBloodGroup = findViewById(R.id.spinnerBloodGroup);
        // Create an ArrayAdapter using a string array resource and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.blood_group_options, // Create an array resource with blood group options
                android.R.layout.simple_spinner_item
        );
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerBloodGroup.setAdapter(adapter);

        // Initialize the donorTable
        donorTable = findViewById(R.id.donorTable);

        // Set a listener for blood group selection changes
        spinnerBloodGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Call the method to retrieve donor information and populate the table
                retrieveDonorInformation(spinnerBloodGroup.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle no selection if needed
            }
        });

        // Call the method to retrieve donor information and populate the table
        retrieveDonorInformation(spinnerBloodGroup.getSelectedItem().toString());

        // Back Button
        Button backButton = findViewById(R.id.buttonBack);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Close the activity and go back
            }
        });

    }

    private void retrieveDonorInformation(final String selectedBloodGroup) {
        DatabaseReference donorRef = FirebaseDatabase.getInstance().getReference().child("users");


        donorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                donorTable.removeAllViews();

                // Create a header row
                TableRow headerRow = (TableRow) LayoutInflater.from(FindDonor.this).inflate(R.layout.donor_table_row, null);

                // Set header text and color
                ((TextView) headerRow.findViewById(R.id.nameTextView)).setText("Name");
                ((TextView) headerRow.findViewById(R.id.nameTextView)).setTextColor(getResources().getColor(R.color.customPrimaryColor));

                ((TextView) headerRow.findViewById(R.id.addressTextView)).setText("Address");
                ((TextView) headerRow.findViewById(R.id.addressTextView)).setTextColor(getResources().getColor(R.color.customPrimaryColor));

                ((TextView) headerRow.findViewById(R.id.contactTextView)).setText("Contact");
                ((TextView) headerRow.findViewById(R.id.contactTextView)).setTextColor(getResources().getColor(R.color.customPrimaryColor));

                ((TextView) headerRow.findViewById(R.id.bloodGroupTextView)).setText("Blood Group");
                ((TextView) headerRow.findViewById(R.id.bloodGroupTextView)).setTextColor(getResources().getColor(R.color.customPrimaryColor));

                donorTable.addView(headerRow);

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String bloodGroup = snapshot.child("blood-group").getValue(String.class);

                    if (bloodGroup != null && bloodGroup.equals(selectedBloodGroup)) {
                        String name = snapshot.child("name").getValue(String.class);
                        String district = snapshot.child("district").getValue(String.class);
                        String phone = snapshot.child("phone").getValue(String.class);

                        // Create a new row
                        TableRow row = (TableRow) LayoutInflater.from(FindDonor.this).inflate(R.layout.donor_table_row, null);

                        // Set donor information in the row
                        ((TextView) row.findViewById(R.id.nameTextView)).setText(name);
                        ((TextView) row.findViewById(R.id.addressTextView)).setText(district);
                        ((TextView) row.findViewById(R.id.contactTextView)).setText(phone);
                        ((TextView) row.findViewById(R.id.bloodGroupTextView)).setText(bloodGroup);

                        // Add the row to the table
                        donorTable.addView(row);
                    }
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
            }
        });
    }


}