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

import com.example.nsajic.testapp.Models.TaxiSluzba;
import com.example.nsajic.testapp.Models.UserRating;
import com.example.nsajic.testapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TaxiInfoActivity extends AppCompatActivity implements RatingBar.OnRatingBarChangeListener, View.OnClickListener {

    private static final int CALL_PHONE = 1;
    private TextView nazivSluzbe;
    private TextView ocenaSluzbe;
    private TextView brojAutomobilaSluzbe;
    private TextView cenaPoKilometruSluzbe;
    private TextView brojTelefonaSluzbe;
    private RatingBar ratingBar;
    private Button callButton;
    private UserRating userRatingForSpecifiedService = new UserRating("","",3f);

    private DatabaseReference databaseReference;

    private FirebaseAuth firebaseAuth;

    private Intent callIntent;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taxi_info);
        getSupportActionBar().setTitle("Info");
        firebaseAuth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference();
        intent = getIntent();

        nazivSluzbe = (TextView)findViewById(R.id.nazivView);
        ocenaSluzbe = (TextView)findViewById(R.id.ocenaView);
        brojTelefonaSluzbe = (TextView)findViewById(R.id.brojTelefonaView);
        cenaPoKilometruSluzbe = (TextView)findViewById(R.id.cenaPoKilometruView);
        brojAutomobilaSluzbe = (TextView)findViewById(R.id.brojAutomobilaView);

        callButton = (Button) findViewById(R.id.callButton);
        callButton.setOnClickListener(this);

        ratingBar = (RatingBar)findViewById(R.id.oceniSluzbuView);
        ratingBar.setOnRatingBarChangeListener(this);

        nazivSluzbe.setText(intent.getStringExtra("imeSluzbe"));
        ocenaSluzbe.setText(intent.getStringExtra("ocenaSluzbe"));
        brojAutomobilaSluzbe.setText(intent.getStringExtra("brojAutomobilaSluzbe"));
        cenaPoKilometruSluzbe.setText(intent.getStringExtra("cenaPoKilometru"));
        brojTelefonaSluzbe.setText(intent.getStringExtra("brojTelefona"));

        String serviceName = intent.getStringExtra("imeSluzbe");
        setCurrentUserRatingBy(serviceName);
        //ratingBar.setRating(getCurrentUserRatingBy(serviceName));
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
        String userEmail = firebaseAuth.getCurrentUser().getEmail();
        String serviceName = intent.getStringExtra("imeSluzbe").toString();
        UserRating userRating = new UserRating(userEmail, serviceName, (Float)v);
        writeUserRatingToDatabase(userRating);
    }

    private void writeUserRatingToDatabase(UserRating userRating) {
        databaseReference.child("korisnici").child(firebaseAuth.getCurrentUser().getUid()).child("ocene").child(userRating.getTaxiServiceName()).setValue(userRating);
    }
    private Float getCurrentUserRatingBy (String serviceName){
        return 3f;
        //TODO: Read concrete value from database without any listeners
        //databaseReference.child("korisnici").child(firebaseAuth.getCurrentUser().getUid()).child("ocene").child(serviceName).
    }
    private void setCurrentUserRatingBy(final String serviceName){
        databaseReference.child("korisnici").child(firebaseAuth.getCurrentUser().getUid()).child("ocene").child(serviceName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserRating userRating = dataSnapshot.getValue(UserRating.class);
                userRatingForSpecifiedService = userRating;
                //ratingBar.setRating(userRatingForSpecifiedService.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

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
}
