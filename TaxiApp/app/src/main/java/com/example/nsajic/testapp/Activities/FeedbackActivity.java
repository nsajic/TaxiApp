package com.example.nsajic.testapp.Activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.nsajic.testapp.MainActivity;
import com.example.nsajic.testapp.Models.UserFeedback;
import com.example.nsajic.testapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

public class FeedbackActivity extends AppCompatActivity implements View.OnClickListener {

    private Button backButton;
    private Button sendFeedbackButton;
    private EditText feedbackContentField;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference dataBaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        getSupportActionBar().setTitle("Send feedback");

        dataBaseReference = FirebaseDatabase.getInstance().getReference();
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
            //notifyOnEmail(feedback);
            writeOnDatabase(feedback);
            switchToMainActivity();
        }
    }

    private void notifyOnEmail(UserFeedback feedback){
        Intent intent = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
        intent.setType("text/plain");
        //intent.putExtra(Intent.EXTRA_SUBJECT, "onezerobeatz@gmail.com");
        intent.putExtra(Intent.EXTRA_TEXT, "New feedback from: "+feedback.getUserEmail()+ "./n/n"+feedback.getContent()+"/n/n");

        intent.setData(Uri.parse("mailto:onezerobeatz@gmail.com")); // or just "mailto:" for blank
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
        startActivity(intent);
    }

    private void writeOnDatabase(UserFeedback feedback){
        dataBaseReference.child("feedbacks").child(firebaseAuth.getCurrentUser().getUid()).setValue(feedback);
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
