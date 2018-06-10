package com.example.nsajic.testapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.nsajic.testapp.Adapters.RecensionsAdapter;
import com.example.nsajic.testapp.Adapters.TaxiSluzbaAdapter;
import com.example.nsajic.testapp.Models.TaxiSluzba;
import com.example.nsajic.testapp.Models.UserRating;
import com.example.nsajic.testapp.Models.UserRecension;
import com.example.nsajic.testapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class RecensionsActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private ListView recensionsListView;
    private ArrayList<UserRecension> recensions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recensions);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        getSupportActionBar().setTitle(R.string.recensions_activity_tittle);
        recensionsListView = (ListView)findViewById(R.id.recensionsListView);
        recensions = new ArrayList<UserRecension>();
        setUserRecensions();
        recensionsListView.setAdapter(new RecensionsAdapter(recensions, this));
    }
    private void setUserRecensions(){
        databaseReference.child("recensions").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> userUids = dataSnapshot.getChildren();
                for (DataSnapshot userUidSnapsot : userUids) {
                    Iterable<DataSnapshot> taxiServiceNamesSnaps = userUidSnapsot.getChildren();
                    for (DataSnapshot taxiServiceNameSnap : taxiServiceNamesSnaps) {
                        Iterable<DataSnapshot> userRecensionSnaps = taxiServiceNameSnap.getChildren();
                        for (DataSnapshot userRecensionSnap : userRecensionSnaps) {
                            UserRecension ur = userRecensionSnap.getValue(UserRecension.class);
                            if (ur.getTaxiServiceName().equals(getTaxiServiceName())) {
                                recensions.add(ur);
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private String getTaxiServiceName() {
        return getIntent().getStringExtra("imeSluzbe");
    }
}