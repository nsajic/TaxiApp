package com.example.nsajic.testapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UserGuid extends AppCompatActivity implements  View.OnClickListener{

    private final String upustvo =  "Izvadite batiriju kako biste ugasili aplikaciju. \nDa dozivotno zabranite aplikaciju na svom telefonu, bacite telefon u najblizu reku. \ntemp\ntemp\ntemp\ntemp\ntemp\ntemp\ntemp\ntemp\ntemp\ntemp\ntemp\ntemp\ntemp\ntemp\ntemp\ntemp\ntemp\ntemp";
    private Button backButton;
    private TextView upustvoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_guid);

        backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(this);

        upustvoTextView = (TextView) findViewById(R.id.textUpustvo);
        upustvoTextView.setText(upustvo);
    }

    @Override
    public void onClick(View view) {
        switchToMainActivity();
    }

    private void switchToMainActivity(){
        finish();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}