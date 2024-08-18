package com.example.nwureddrops;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class RegisterPage extends AppCompatActivity {

    TextInputEditText editTextName, editTextEmail, editTextPhone, editTextPassword, editTextConfirmPassword;
    Button buttonRegister;
    FirebaseAuth mAuth;
    Spinner spinnerBloodGroup;
    CheckBox checkBoxDonor;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), HomePage.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        mAuth = FirebaseAuth.getInstance();

        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);

        checkBoxDonor = findViewById(R.id.checkBoxDonor);


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");

        buttonRegister = findViewById(R.id.buttonRegister);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name, email, phone, password, confirmPassword;
                name = String.valueOf(editTextName.getText());
                email = String.valueOf(editTextEmail.getText());
                phone = String.valueOf(editTextPhone.getText());
                password = String.valueOf(editTextPassword.getText());
                confirmPassword = String.valueOf(editTextConfirmPassword.getText());

                spinnerBloodGroup = findViewById(R.id.spinnerBloodGroup);

                // Get the state of the checkbox
                String isCheckboxSelected;
                if(checkBoxDonor.isChecked()) {
                    isCheckboxSelected = "Yes";
                }else{
                    isCheckboxSelected = "No";
                }

                if(TextUtils.isEmpty(name)){
                    Toast.makeText(RegisterPage.this, "Please enter your name.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(RegisterPage.this, "Please enter your email.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(phone)){
                    Toast.makeText(RegisterPage.this, "Please enter your phone no.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(RegisterPage.this, "Please set a password.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(confirmPassword)){
                    Toast.makeText(RegisterPage.this, "Please confirm your password.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    Toast.makeText(RegisterPage.this, "Password did not match!",
                            Toast.LENGTH_SHORT).show();
                    return;
                }



                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser currentUser = mAuth.getCurrentUser();
                                    if (currentUser != null) {
                                        // Create a unique user ID using Firebase's UID
                                        String userId = currentUser.getUid();

                                        String selectedBloodGroup = spinnerBloodGroup.getSelectedItem().toString();

                                        // Create a user data object or HashMap
                                        HashMap<String, Object> userData = new HashMap<>();
                                        userData.put("name", name);
                                        userData.put("email", email);
                                        userData.put("phone", phone);
                                        userData.put("pass", password);
                                        userData.put("blood-group",selectedBloodGroup);
                                        userData.put("donor",isCheckboxSelected);

                                        // Store user data in the database using the userId as the key
                                        databaseReference.child(userId).setValue(userData);

                                        Toast.makeText(RegisterPage.this, "Registration Complete!",
                                                Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(getApplicationContext(), HomePage.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(RegisterPage.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
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





        // Find the "Back to Login Page" button
        Button backButton = findViewById(R.id.buttonBackToLogin);

        // Set an OnClickListener for the button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to navigate back to the LoginActivity
                Intent intent = new Intent(RegisterPage.this, LoginPage.class);
                startActivity(intent);
                finish(); // Close the current activity
            }
        });

    }



}