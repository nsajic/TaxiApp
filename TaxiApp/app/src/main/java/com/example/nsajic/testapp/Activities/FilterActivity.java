package com.example.nsajic.testapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.nsajic.testapp.Adapters.GradSearchAdapter;
import com.example.nsajic.testapp.Models.Grad;
import com.example.nsajic.testapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FilterActivity extends AppCompatActivity{

    private Spinner cityTaxiSpinner;
    private EditText nameText;
    private Spinner gradoviSpinner;
    //private Spinner citiesSpinner;
    private EditText priceText;
    private Button searchButton;

    private List<Grad> cities = new ArrayList<>();

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        getSupportActionBar().setTitle("Search");

        databaseReference = FirebaseDatabase.getInstance().getReference();

        cityTaxiSpinner = (Spinner)findViewById(R.id.cityTaxiSpinner);
        nameText = (EditText)findViewById(R.id.nameText);
        gradoviSpinner = (Spinner) findViewById(R.id.gradovi_sppiner);
        //citiesSpinner = (Spinner)findViewById(R.id.citiesSpinner);
        priceText = (EditText) findViewById(R.id.priceText);
        searchButton = (Button) findViewById(R.id.searchButton);

        databaseReference.child("gradovi").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> gradUids = dataSnapshot.getChildren();
                for (DataSnapshot gradUidSnapsot : gradUids) {
                    cities.add(gradUidSnapsot.getValue(Grad.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        gradoviSpinner.setAdapter(new GradSearchAdapter(cities, this));

        cityTaxiSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getSelectedItem().equals("Taxi")){

                    priceText.setVisibility(View.VISIBLE);
                    gradoviSpinner.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SearchedActivity.class);

                String selectedcityTaxi = cityTaxiSpinner.getSelectedItem().toString();

                intent.putExtra("cityTaxi", selectedcityTaxi);
                intent.putExtra("name", nameText.getText().toString());
                if(selectedcityTaxi.equals("Taxi")) {
                    intent.putExtra("price", Double.parseDouble(priceText.getText().toString()));
                    intent.putExtra("selectedCityPC", ((Grad) gradoviSpinner.getSelectedItem()).getPostanskiBroj());
                }

                startActivity(intent);
            }
        });
    }

}
