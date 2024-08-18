package com.example.nwureddrops;

public class User {
    private String name;
    private String email;
    private String phone;
    private String password;
    private String bloodGroup;
    private String isCheckboxSelected;
    private String gender;
    private String dateOfBirth;
    private String lastDonationDate;
    private String district;
    private String fcmToken;

//    public User(String name, String email, String phone, String bloodGroup, String isCheckboxSelected, String gender, String dateOfBirth, String lastDonationDate, String district) {
//        // Default constructor required for Firebase
//    }

    // Required no-argument constructor
    public User() {
        // Default constructor required for Firebase
    }

    public User(String name, String email, String phone, String bloodGroup, String isCheckboxSelected, String gender, String dateOfBirth, String lastDonationDate, String district) {
        this.name = name;
        this.email = email;
        this.phone = phone;
//        this.password = password;
        this.bloodGroup = bloodGroup;
        this.isCheckboxSelected = isCheckboxSelected;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.lastDonationDate = lastDonationDate;
        this.district = district;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public String getBloodGroup(){
        return bloodGroup;
    }

    public String isCheckboxSelected() {
        return isCheckboxSelected;
    }

    public String getGender() { return gender; }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getLastDonationDate() {
        return lastDonationDate;
    }

    public String getDistrict() {
        return district;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }
}

