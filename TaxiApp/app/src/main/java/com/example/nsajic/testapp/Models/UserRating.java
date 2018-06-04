package com.example.nsajic.testapp.Models;

public class UserRating {
    private String userEmail;
    private String taxiServiceName;
    private Float value;

    public UserRating(String userEmail, String taxiServiceName, Float value) {
        this.userEmail = userEmail;
        this.taxiServiceName = taxiServiceName;
        this.value = value;
    }

    public UserRating(){}

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getTaxiServiceName() {
        return taxiServiceName;
    }

    public void setTaxiServiceName(String taxiServiceName) {this.taxiServiceName = taxiServiceName; }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }
}
