package com.example.nwureddrops;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class BloodRequest extends AppCompatActivity {

    private Calendar calendar;
    private int year, month, day;
    Button buttonSelectDonationDate;
    // Assume users are initialized appropriately
    private List<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_request);

        buttonSelectDonationDate = findViewById(R.id.buttonSelectDonationDate);
        buttonSelectDonationDate.setOnClickListener(view -> openDatePickerDialog(view));

        // Initialize the spinner
        Spinner spinnerBloodGroup = findViewById(R.id.spinnerBloodGroup);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.blood_group_options,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBloodGroup.setAdapter(adapter);

        // Initialize the spinner
        Spinner spinnerDistrict = findViewById(R.id.spinnerDistrict);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
                this,
                R.array.district_options,
                android.R.layout.simple_spinner_item
        );
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDistrict.setAdapter(adapter2);

        Button buttonSubmitRequest = findViewById(R.id.buttonSubmitRequest);
        buttonSubmitRequest.setOnClickListener(this::onSubmitRequest);

        Button backButton = findViewById(R.id.buttonBack);
        backButton.setOnClickListener(v -> finish());
    }

    private void openDatePickerDialog(View view) {
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) -> {
                    String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                    buttonSelectDonationDate.setText(selectedDate);
                },
                year, month, day
        );

        datePickerDialog.show();
    }

    private void onSubmitRequest(View view) {
        Spinner bloodGroupSpinner = findViewById(R.id.spinnerBloodGroup);
        String bloodGroupOfRequest = bloodGroupSpinner.getSelectedItem().toString();

        Spinner districtSpinner = findViewById(R.id.spinnerDistrict);
        String district = districtSpinner.getSelectedItem().toString();

        String donationDate = buttonSelectDonationDate.getText().toString();

        EditText contactEditText = findViewById(R.id.editTextContact);
        String contact = contactEditText.getText().toString();

        EditText hospitalDetailsEditText = findViewById(R.id.editTextHospitalDetails);
        String hospitalDetails = hospitalDetailsEditText.getText().toString();

        EditText patientDetailsEditText = findViewById(R.id.editTextPatientDetails);
        String patientDetails = patientDetailsEditText.getText().toString();

        if (bloodGroupOfRequest.isEmpty() || district.isEmpty() || donationDate.isEmpty()
                || contact.isEmpty() || hospitalDetails.isEmpty() || patientDetails.isEmpty()) {
            Toast.makeText(this, "Please fill in all the required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String submissionDateTime = getCurrentDateTime();

        Map<String, Object> bloodRequestData = new HashMap<>();
        bloodRequestData.put("requestTime", submissionDateTime);
        bloodRequestData.put("bloodGroup", bloodGroupOfRequest);
        bloodRequestData.put("district", district);
        bloodRequestData.put("donationDate", donationDate);
        bloodRequestData.put("contact", contact);
        bloodRequestData.put("hospitalDetails", hospitalDetails);
        bloodRequestData.put("patientDetails", patientDetails);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("blood_requests")
                .child(submissionDateTime);
        databaseReference.setValue(bloodRequestData)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Blood request submitted successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to submit blood request.", Toast.LENGTH_SHORT).show());


    }

    private String getCurrentDateTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
        return dateFormat.format(calendar.getTime());
    }
}
