package com.example.nsajic.testapp.Models;

import java.util.Date;

public class UserRecension {

    private String userEmail;
    private String content;
    private String taxiServiceName;
    private Date dateCreated;

    public UserRecension(String userEmail, String content, String taxiServiceName, Date dateCreated) {
        this.userEmail = userEmail;
        this.content = content;
        this.taxiServiceName = taxiServiceName;
        this.dateCreated = dateCreated;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTaxiServiceName() {
        return taxiServiceName;
    }

    public void setTaxiServiceName(String taxiServiceName) {
        this.taxiServiceName = taxiServiceName;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}
