package com.example.nsajic.testapp.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.nsajic.testapp.Models.UserRating;
import com.example.nsajic.testapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class TaxiInfoActivity extends AppCompatActivity implements RatingBar.OnRatingBarChangeListener {

    private static final int CALL_PHONE = 1;
    private TextView nazivSluzbe;
    private TextView ocenaSluzbe;
    private TextView brojAutomobilaSluzbe;
    private TextView cenaPoKilometruSluzbe;
    private TextView brojTelefonaSluzbe;
    private RatingBar ratingBar;
    private Button callButton;

    private FirebaseAuth firebaseAuth;
    Intent callIntent;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taxi_info);
        getSupportActionBar().setTitle("Info");
        firebaseAuth = FirebaseAuth.getInstance();

        intent = getIntent();

        nazivSluzbe = (TextView)findViewById(R.id.nazivView);
        ocenaSluzbe = (TextView)findViewById(R.id.ocenaView);
        brojTelefonaSluzbe = (TextView)findViewById(R.id.brojTelefonaView);
        cenaPoKilometruSluzbe = (TextView)findViewById(R.id.cenaPoKilometruView);
        brojAutomobilaSluzbe = (TextView)findViewById(R.id.brojAutomobilaView);

        callButton = (Button) findViewById(R.id.callButton);
        callButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + intent.getStringExtra("brojTelefona")));

                if (ContextCompat.checkSelfPermission(TaxiInfoActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(TaxiInfoActivity.this, new String[]{Manifest.permission.CALL_PHONE},CALL_PHONE);
                }
                else
                {
                    startActivity(callIntent);
                }
            }
        });
        ratingBar = (RatingBar)findViewById(R.id.oceniSluzbuView);
        ratingBar.setOnRatingBarChangeListener(this);
        nazivSluzbe.setText(intent.getStringExtra("imeSluzbe"));
        ocenaSluzbe.setText(intent.getStringExtra("ocenaSluzbe"));
        brojAutomobilaSluzbe.setText(intent.getStringExtra("brojAutomobilaSluzbe"));
        cenaPoKilometruSluzbe.setText(intent.getStringExtra("cenaPoKilometru"));
        brojTelefonaSluzbe.setText(intent.getStringExtra("brojTelefona"));
        ratingBar.setRating(getCurentUserRatingBy(intent.getStringExtra("imeSluzbe")));
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
        String userEmail = firebaseAuth.getCurrentUser().getEmail();
        String serviceName = intent.getStringExtra("imeSluzbe").toString();
        UserRating userRating = new UserRating(userEmail, serviceName, (Float)v);
        writeUserRatingToDatabase(userRating);
    }

    private void writeUserRatingToDatabase(UserRating userRating) {
        //TODO: WriteToDataabase
    }

    private Float getCurentUserRatingBy(String serviceName){
        //TODO: ReadFromDatabase
        return 3.0f;
    }
}
