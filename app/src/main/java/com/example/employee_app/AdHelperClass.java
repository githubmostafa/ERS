package com.example.employee_app;

import com.google.firebase.auth.FirebaseAuth;

public class AdHelperClass {
    String email,title,subtitle,phone,address,details;


    public AdHelperClass(){

    }

    public AdHelperClass(String title, String subtitle, String phone, String address, String details) {

        this.email = email;
        this.title = title;
        this.subtitle = subtitle;
        this.phone = phone;
        this.address = address;
        this.details = details;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
