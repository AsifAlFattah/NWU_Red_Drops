package com.example.nwureddrops;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BloodBanks extends AppCompatActivity {
    private ArrayList<BloodBank> bloodBanks;
    private RecyclerView recyclerView;
    private BloodBankAdapter bloodBankAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_banks);

        // Initialize the spinner
        Spinner spinnerDistrict = findViewById(R.id.spinnerDistrict);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.district_options,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDistrict.setAdapter(adapter);

        // Initialize RecyclerView and Adapter
        recyclerView = findViewById(R.id.recyclerView);
        bloodBankAdapter = new BloodBankAdapter(this);
        recyclerView.setAdapter(bloodBankAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize blood bank data
        bloodBanks = new ArrayList<>();
        bloodBanks.add(new BloodBank("Central Blood Bank", "5676+J4C, Maulana Mohammad Ali Rd", "0421-63353", "Jessore"));
        bloodBanks.add(new BloodBank("Red Crescent", "Jessore", "01788-295595 ", "Jessore"));
        bloodBanks.add(new BloodBank("SSS Foundation Blood Bank", "Jashore General Hospital", "01791-601409", "Jessore"));
        bloodBanks.add(new BloodBank("JUST Blood Bank", "JUST, Administrative Building of JUST, Jessore University of Science and Technology Campus Rd", "01781-355720", "Jessore"));
        bloodBanks.add(new BloodBank("Jashore Blood Bank", "২৫০ শয্যা বিশিষ্ট জেনারেল হসপিটাল", "01620-002333", "Jessore"));
        bloodBanks.add(new BloodBank("Khulna Medical Blood Bank", "9100 M A Bari St", "01767-938531", "Khulna"));
        bloodBanks.add(new BloodBank("Khulna General (Sodor) Hospital Blood Bank", "6no হাসপাতাল রোড, Khulna 9100", "01920-518586", "Khulna"));
        bloodBanks.add(new BloodBank("Khulna city medical college hospital blood bank", "Khulna City Medical College Hospital, Moylapota", "01858-209392", "Khulna"));
        bloodBanks.add(new BloodBank("SANDHANI DONOR CLUB KHULNA", "Khulna Government General Hospital, Hospital Rd, Khulna 9100", "01885-455592", "Khulna"));
        bloodBanks.add(new BloodBank("Khulna University Unit", "Khulna University, Khulna.", "01534-982674", "Khulna"));
        bloodBanks.add(new BloodBank("Bagerhat Blood Bank", "Bagerhat Sadar", "01614-430836", "Bagerhat"));
        // Add more blood banks as needed

        // Back Button
        Button backButton = findViewById(R.id.buttonBack);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Close the activity and go back
            }
        });

        // Set an OnItemSelectedListener for the district spinner
        spinnerDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedDistrict = (String) parentView.getItemAtPosition(position);
                loadBloodBanksForDistrict(selectedDistrict);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Handle nothing selected (if needed)
            }
        });
    }

    private void loadBloodBanksForDistrict(String selectedDistrict) {
        ArrayList<BloodBank> filteredBloodBanks = new ArrayList<>();
        for (BloodBank bloodBank : bloodBanks) {
            if (bloodBank.getDistrict().equals(selectedDistrict)) {
                filteredBloodBanks.add(bloodBank);
            }
        }
        bloodBankAdapter.setBloodBanks(filteredBloodBanks);
    }
}
