package com.example.nsajic.testapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.nsajic.testapp.Adapters.RecensionsAdapter;
import com.example.nsajic.testapp.Adapters.TaxiSluzbaAdapter;
import com.example.nsajic.testapp.Models.TaxiSluzba;
import com.example.nsajic.testapp.Models.UserRecension;
import com.example.nsajic.testapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class RecensionsActivity extends AppCompatActivity {
    private DatabaseReference dataBaseReference;
    private ListView recensionsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recensions);

        getSupportActionBar().setTitle("Taxi sluzbe");

        recensionsListView = (ListView)findViewById(R.id.recensionsListView);



        recensionsListView.setAdapter(new RecensionsAdapter(getUserRecensions(), this));
        dataBaseReference = FirebaseDatabase.getInstance().getReference();
    }
    private ArrayList<UserRecension> getUserRecensions(){
        ArrayList<UserRecension> userRecensions = new ArrayList<UserRecension>();
        userRecensions.add(new UserRecension("sasa.momcilovic@gmail.com", "Dobri ste stvarno. Jedina zamerka je to sto su vam svi taksisti muskarci. :)","str3", new Date()));
        userRecensions.add(new UserRecension("str1", "str2","str3", new Date()));
        return userRecensions;
    }
    private String getTaxiServiceName() {
        return getIntent().getStringExtra("imeSluzbe");
    }
}