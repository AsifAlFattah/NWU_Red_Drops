package com.example.nwureddrops;

public class BloodBank {
    private String name;
    private String address;
    private String contact;
    private String district;

    public BloodBank(String name, String address, String contact, String district) {
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.district = district;
    }

    // Getter methods for the attributes
    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getContact() {
        return contact;
    }

    public String getDistrict() {
        return district;
    }
}
