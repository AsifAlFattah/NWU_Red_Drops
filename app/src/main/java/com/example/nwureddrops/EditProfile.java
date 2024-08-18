package com.example.nwureddrops;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class EditProfile extends AppCompatActivity {

    TextInputEditText editTextName, editTextEmail, editTextPhone;
    private Button buttonSelectBirthDate;
    private Calendar calendar;
    private int year, month, day;
    private RadioGroup radioGroupGender, radioGroupDonor;
    private RadioButton radioButtonMale, radioButtonFemale, radioButtonYes, radioButtonNo;
    Spinner spinnerBloodGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // Initialize components
        buttonSelectBirthDate = findViewById(R.id.buttonSelectBirthDate);
        radioGroupGender = findViewById(R.id.radioGroupGender);
        radioButtonMale = findViewById(R.id.radioButtonMale);
        radioButtonFemale = findViewById(R.id.radioButtonFemale);
        radioGroupDonor = findViewById(R.id.radioGroupDonor);
        radioButtonYes = findViewById(R.id.radioButtonYes);
        radioButtonNo = findViewById(R.id.radioButtonNo);

        // Set click listeners for date buttons
        buttonSelectBirthDate.setOnClickListener(view -> openDatePickerDialog(view));


        // Set click listener for gender radio group
        radioGroupGender.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radioButtonMale) {
                // Handle Male selection
            } else if (checkedId == R.id.radioButtonFemale) {
                // Handle Female selection
            }
        });

        // Set click listener for donor radio group
        radioGroupDonor.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radioButtonYes) {
                // Handle Yes selection
            } else if (checkedId == R.id.radioButtonNo) {
                // Handle No selection
            }
        });

        // Initialize the spinner
        Spinner spinnerBloodGroup = findViewById(R.id.spinnerBloodGroup);
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


        // Initialize the spinner
        Spinner spinnerDistrict;
        spinnerDistrict = findViewById(R.id.spinnerDistrict);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
                this,
                R.array.district_options,
                android.R.layout.simple_spinner_item
        );
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDistrict.setAdapter(adapter2);

        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPhone = findViewById(R.id.editTextPhone);


        // Retrieve the current user's UID
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser userAuth = auth.getCurrentUser();

        if (userAuth != null) {
            String uid = userAuth.getUid();

            // Get a reference to the user's data
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(uid);

            // Read the data from the database
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {


                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Retrieve user information from the database
                        String name = dataSnapshot.child("name").getValue(String.class);
                        String email = dataSnapshot.child("email").getValue(String.class);
                        String phone = dataSnapshot.child("phone").getValue(String.class);
                        String bloodGroup = dataSnapshot.child("blood-group").getValue(String.class);
                        String gender = dataSnapshot.child("gender").getValue(String.class);
                        String isDonor = dataSnapshot.child("donor").getValue(String.class);
                        String dateOfBirth = dataSnapshot.child("dateOfBirth").getValue(String.class);
                        String district = dataSnapshot.child("district").getValue(String.class);

                        // Populate the EditText fields
                        editTextName.setText(name);
                        editTextEmail.setText(email);
                        editTextPhone.setText(phone);

                        // Set the selected item for the spinners
                        int bloodGroupPosition = adapter.getPosition(bloodGroup);
                        spinnerBloodGroup.setSelection(bloodGroupPosition);

                        // Set the selected radio buttons
                        if (gender!=null && gender.equals("Male")) {
                            radioButtonMale.setChecked(true);
                        } else if(gender!=null) {
                            radioButtonFemale.setChecked(true);
                        }

                        if (isDonor!=null && isDonor.equals("Yes")) {
                            radioButtonYes.setChecked(true);
                        } else {
                            radioButtonNo.setChecked(true);
                        }

                        // ... (populate other fields similarly)

                        // Set the date of birth button text
                        if(dateOfBirth!=null){
                            buttonSelectBirthDate.setText(dateOfBirth);
                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            });
        }


        // Save Button
        Button saveButton = findViewById(R.id.buttonSaveProfile);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Retrieve the data from the UI components
                String name = editTextName.getText().toString();
                String email = editTextEmail.getText().toString();
                String phone = editTextPhone.getText().toString();
                String bloodGroup = spinnerBloodGroup.getSelectedItem().toString();
                int genderRadioButtonId = radioGroupGender.getCheckedRadioButtonId();
                String gender = "";
                if (genderRadioButtonId == R.id.radioButtonMale) {
                    gender = "Male";
                } else if (genderRadioButtonId == R.id.radioButtonFemale) {
                    gender = "Female";
                }
                int donorRadioButtonId = radioGroupDonor.getCheckedRadioButtonId();
                String isCheckboxSelected = "";
                if (donorRadioButtonId == R.id.radioButtonYes) {
                    isCheckboxSelected = "Yes";
                } else if (donorRadioButtonId == R.id.radioButtonNo) {
                    isCheckboxSelected = "No";
                }

                // Retrieve the selected date of birth and last donation date
                String dateOfBirth = buttonSelectBirthDate.getText().toString();


                // Retrieve the selected district
                String district = spinnerDistrict.getSelectedItem().toString();

                // Ensure the user is authenticated
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
                FirebaseAuth auth = FirebaseAuth.getInstance();
                FirebaseUser userAuth = auth.getCurrentUser();

                if (userAuth != null) {
                    // Retrieve the UID
                    String uid = userAuth.getUid();

                    // Create a user data object or HashMap
                    HashMap<String, Object> userData = new HashMap<>();
                    userData.put("name", name);
                    userData.put("email", email);
                    userData.put("phone", phone);
//                    userData.put("pass", password)
                    userData.put("blood-group",bloodGroup);
                    userData.put("donor",isCheckboxSelected);
                    userData.put("gender",gender);
//                    userData.put("dateOfBirth",dateOfBirth);
                    userData.put("district",district);
                    userData.put("dateOfBirth",dateOfBirth);


                    // Store user data in the database using the userId as the key
                    databaseReference.child(uid).setValue(userData);

                    // Display a success message
                    Toast.makeText(EditProfile.this, "Profile saved successfully", Toast.LENGTH_SHORT).show();
                }else {
                    // Handle the case where the user is not authenticated
                    Toast.makeText(EditProfile.this, "Please Register First!", Toast.LENGTH_SHORT).show();

                }
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

    private void openDatePickerDialog(View view) {
        // Get the current date
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create a date picker dialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) -> {
                    // Handle the selected date
                    String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                    buttonSelectBirthDate.setText(selectedDate); // Update the button text
                },
                year, month, day
        );

        // Show the date picker dialog
        datePickerDialog.show();
    }


}
