package com.example.nwureddrops;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private TextView textName, bloodGroup, dateOfBirth, phoneNo, address, emailId, passwordText, isDonor, lastDonation, gender;

    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference databaseReference;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);



        // Initialize Firebase components
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        if (user != null) {
            databaseReference = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());


            // Initialize TextViews
            textName = view.findViewById(R.id.textName);
            bloodGroup = view.findViewById(R.id.bloodGroup);
            dateOfBirth = view.findViewById(R.id.dateOfBirth);
            phoneNo = view.findViewById(R.id.phoneNo);
            address = view.findViewById(R.id.Address);
            emailId = view.findViewById(R.id.emailId);
            isDonor = view.findViewById(R.id.isDonor);
            gender = view.findViewById(R.id.viewGender);
//            lastDonation = view.findViewById(R.id.lastDonation);

            // Retrieve and display user information from the RTDB
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    // Clear the TextViews initially
                    textName.setText("");
                    bloodGroup.setText("");
                    phoneNo.setText("");
                    emailId.setText("");
                    isDonor.setText("");
                    address.setText("");
                    dateOfBirth.setText("");

                    if (dataSnapshot.exists()) {
                        // Retrieve user information from the database
                        String name = dataSnapshot.child("name").getValue(String.class);
                        String bloodGroupValue = dataSnapshot.child("blood-group").getValue(String.class);
                        String dateOfBirthValue = dataSnapshot.child("dateOfBirth").getValue(String.class);
                        String phoneNoValue = dataSnapshot.child("phone").getValue(String.class);
                        String addressValue = dataSnapshot.child("district").getValue(String.class);
                        String emailValue = dataSnapshot.child("email").getValue(String.class);
                        String isDonorValue = dataSnapshot.child("donor").getValue(String.class);
                        String genderValue = dataSnapshot.child("gender").getValue(String.class);  // Retrieve gender
                        // ...

                        // Update TextViews with user information, or empty string if data is null
                        textName.setText(name != null ? name : "");
                        bloodGroup.setText(bloodGroupValue != null ? bloodGroupValue : "");
                        phoneNo.setText(phoneNoValue != null ? phoneNoValue : "");
                        emailId.setText(emailValue != null ? emailValue : "");
                        isDonor.setText((isDonorValue != null ? isDonorValue : ""));
                        address.setText(addressValue != null ? addressValue : "");  // Display address
                        dateOfBirth.setText(dateOfBirthValue != null ? dateOfBirthValue : "");
                        gender.setText(genderValue != null ? genderValue : "");  // Display gender

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle database error
                }
            });
        }

        user = auth.getCurrentUser();

        Button btnEditProfile;
        btnEditProfile = view.findViewById(R.id.btnEditProfile);
        btnEditProfile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(user!=null) {
                    Intent intent = new Intent(getContext(), EditProfile.class);
                    startActivity(intent);
                }else{
                    // Handle the case where the user is not authenticated
                    Toast.makeText(getContext(), "Please Register First!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), RegisterPage.class);
                    startActivity(intent);
                }
            }
        });



        Button buttonSignOut;

        buttonSignOut = view.findViewById(R.id.btnSignOut);

        user = auth.getCurrentUser();

        buttonSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getContext(), LoginPage.class);
                startActivity(intent);

            }
        });

        return view;
    }
}