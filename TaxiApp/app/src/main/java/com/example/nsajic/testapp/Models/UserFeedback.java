package com.example.nsajic.testapp.Models;

import java.util.Date;

public class UserFeedback {
    private String userEmail;
    private Date dateCreated;
    private String content;

    public UserFeedback(String userEmail, Date dateCreated, String content) {
        this.userEmail = userEmail;
        this.dateCreated = dateCreated;
        this.content = content;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
