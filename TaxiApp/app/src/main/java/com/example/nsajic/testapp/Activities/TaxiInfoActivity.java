package com.example.nsajic.testapp.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nsajic.testapp.MainActivity;
import com.example.nsajic.testapp.Models.TaxiSluzba;
import com.example.nsajic.testapp.Models.UserRating;
import com.example.nsajic.testapp.Models.UserRecension;
import com.example.nsajic.testapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TaxiInfoActivity extends AppCompatActivity implements RatingBar.OnRatingBarChangeListener, View.OnClickListener {

    private static final int CALL_PHONE = 1;
    private TextView nazivSluzbe;
    private TextView ocenaSluzbe;
    private TextView brojAutomobilaSluzbe;
    private TextView cenaPoKilometruSluzbe;
    private TextView brojTelefonaSluzbe;
    private TextView userRecensionTextView;
    private RatingBar ratingBar;
    private Button callButton;
    private Button addRecensionShowDialogButton;
    private Button addRecensionButton;
    private AlertDialog addRecensionDialog;

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

        addRecensionShowDialogButton = (Button) findViewById(R.id.addRecensionDialogShowButton);
        callButton = (Button) findViewById(R.id.callButton);

        addRecensionShowDialogButton.setOnClickListener(this);
        callButton.setOnClickListener(this);

        ratingBar = (RatingBar)findViewById(R.id.oceniSluzbuView);
        ratingBar.setOnRatingBarChangeListener(this);

        setProsecnaOcenaSluzbe();
        nazivSluzbe.setText(intent.getStringExtra("imeSluzbe"));
        brojAutomobilaSluzbe.setText(intent.getStringExtra("brojAutomobilaSluzbe"));
        cenaPoKilometruSluzbe.setText(intent.getStringExtra("cenaPoKilometru"));
        brojTelefonaSluzbe.setText(intent.getStringExtra("brojTelefona"));

        String serviceName = intent.getStringExtra("imeSluzbe");
        setCurrentUserRatingForTaxiService();
        //ratingBar.setRating(getCurrentUserRatingBy(serviceName));
    }
    @Override
    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
        String userEmail = firebaseAuth.getCurrentUser().getEmail();
        String serviceName = intent.getStringExtra("imeSluzbe").toString();
        UserRating userRating = new UserRating(userEmail, serviceName, (Float)v);
        writeUserRatingToDatabase(userRating);
    }
    @Override
    public void onClick(View view) {
        if(view == addRecensionShowDialogButton)
        {
            onAddRecensionDialogShowButton();
        }
        else if (view == callButton)
        {
            onCallButtonClick();
        }
        else if (view == addRecensionButton)
        {
            onAddRecensionButton();

        }
    }

    private void setProsecnaOcenaSluzbe () {
        databaseReference.child("korisnici").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Float userRatingsCount = 0f;
                Float sum = 0f;
                Iterable<DataSnapshot> userUids = dataSnapshot.getChildren();
                for (DataSnapshot userUidSnapsot : userUids) {
                    for(DataSnapshot feedbackOrOcenaSnap : userUidSnapsot.getChildren()){
                        if(feedbackOrOcenaSnap.getKey().toString().equals("ocene")) {
                            for(DataSnapshot userRadingDataSnapshot : feedbackOrOcenaSnap.getChildren()) {
                                UserRating ur = userRadingDataSnapshot.getValue(UserRating.class);
                                if (ur.getTaxiServiceName().equals(intent.getStringExtra("imeSluzbe"))) {
                                    userRatingsCount++;
                                    sum += ur.getValue();
                                }
                            }
                        }
                    }
                }
                if(sum != 0) {
                    Float avg = sum/userRatingsCount;
                    ocenaSluzbe.setText(avg.toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void setCurrentUserRatingForTaxiService(){
        String serviceName = intent.getStringExtra("imeSluzbe");
        databaseReference.child("korisnici").child(firebaseAuth.getCurrentUser().getUid()).child("ocene").child(serviceName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserRating userRating = dataSnapshot.getValue(UserRating.class);
                if(userRating!=null) {
                    ratingBar.setRating(userRating.getValue());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void writeUserRecensionToDatabase(String userRecension){
        String userGuid = firebaseAuth.getCurrentUser().getUid();
        String userEmail = firebaseAuth.getCurrentUser().getEmail();
        Date currentTime = Calendar.getInstance().getTime();
        String taxiServiceName = intent.getStringExtra("imeSluzbe");
        UserRecension userRating = new UserRecension(userEmail, userRecension, taxiServiceName, currentTime);
        databaseReference.child("recensions").child(userGuid).child(taxiServiceName).child(currentTime.toString()).setValue(userRating);
    }
    private void writeUserRatingToDatabase(UserRating userRating) {
        databaseReference.child("korisnici").child(firebaseAuth.getCurrentUser().getUid()).child("ocene").child(userRating.getTaxiServiceName()).setValue(userRating);
    }

    private void onCallButtonClick(){
        callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + intent.getStringExtra("brojTelefona")));

        if (ContextCompat.checkSelfPermission(TaxiInfoActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(TaxiInfoActivity.this, new String[]{Manifest.permission.CALL_PHONE}, CALL_PHONE);
        } else {
            startActivity(callIntent);
        }
    }
    private void onAddRecensionDialogShowButton(){
        AlertDialog.Builder mbuilder = new AlertDialog.Builder(TaxiInfoActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_add_recension,null);
        addRecensionButton = (Button) mView.findViewById(R.id.addRecensionButton);
        userRecensionTextView = (TextView) mView.findViewById(R.id.recensionField);

        addRecensionButton.setOnClickListener(this);
        mbuilder.setView(mView);
        addRecensionDialog = mbuilder.create();
        addRecensionDialog.show();
    }
    private void onAddRecensionButton(){
        String userRecension = userRecensionTextView.getText().toString();
        if(!userRecension.isEmpty()) {
            writeUserRecensionToDatabase(userRecension);
            addRecensionDialog.dismiss();
        } else {
            Toast.makeText(this, "Your recension cannot be empty!", Toast.LENGTH_LONG).show();
        }
    }

}
