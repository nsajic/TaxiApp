package com.example.nsajic.testapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.nsajic.testapp.MainActivity;
import com.example.nsajic.testapp.Models.UserFeedback;
import com.example.nsajic.testapp.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;
import java.util.Date;

public class FeedbackActivity extends AppCompatActivity implements View.OnClickListener {

    private Button backButton;
    private Button sendFeedbackButton;
    private EditText feedbackContentField;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        getSupportActionBar().setTitle("Send feedback");
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){
            switchToLoginActivity();
        }

        backButton = (Button) findViewById(R.id.backButton);
        sendFeedbackButton = (Button) findViewById(R.id.sendButton);
        feedbackContentField = (EditText) findViewById(R.id.feedbackField);

        backButton.setOnClickListener(this);
        sendFeedbackButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.equals(backButton)) {
            switchToMainActivity();
        } else if (view.equals(sendFeedbackButton))
        {
            String userEmail = firebaseAuth.getCurrentUser().getEmail();
            Date currentTime = Calendar.getInstance().getTime();
            String feedbackContentText = feedbackContentField.getText().toString();
            UserFeedback feedback = new UserFeedback(userEmail, currentTime,feedbackContentText);

        }
    }

    private void switchToMainActivity(){
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }

    private void switchToLoginActivity(){
        finish();
        startActivity(new Intent(this, LoginActivity.class));
    }
}
