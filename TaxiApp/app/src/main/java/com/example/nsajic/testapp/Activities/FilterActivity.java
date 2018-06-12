package com.example.nsajic.testapp.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.nsajic.testapp.Adapters.GradSearchAdapter;
import com.example.nsajic.testapp.Models.Grad;
import com.example.nsajic.testapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FilterActivity extends AppCompatActivity{

    private Spinner cityTaxiSpinner;
    private EditText nameText;
    private Spinner gradoviSpinner;
    //private Spinner citiesSpinner;
    private EditText priceText;
    private Button searchButton;

    private List<Grad> cities = new ArrayList<>();
    private ArrayList<String> citiesSpinner = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Context context = this;

        setContentView(R.layout.activity_filter);
        getSupportActionBar().setTitle(R.string.filter_activity_tittle);

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

                for(Grad g: cities){
                    citiesSpinner.add(g.getIme() + " #" + g.getPostanskiBroj());
                }

                adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, citiesSpinner);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                gradoviSpinner.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        cityTaxiSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(adapterView.getSelectedItem().equals(getResources().getStringArray(R.array.filter_spinner)[1])){
                    priceText.setVisibility(View.VISIBLE);
                    gradoviSpinner.setVisibility(View.VISIBLE);
                } else if (adapterView.getSelectedItem().equals(getResources().getStringArray(R.array.filter_spinner)[0])) {
                    priceText.setVisibility(View.INVISIBLE);
                    gradoviSpinner.setVisibility(View.INVISIBLE);
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
                if(selectedcityTaxi.equals(getResources().getStringArray(R.array.filter_spinner)[1])) {
                    String postanskiBroj = gradoviSpinner.getSelectedItem().toString().split("#")[1].trim();
                    String price = priceText.getText().toString();

                    intent.putExtra("selectedCityPC", postanskiBroj);

                    if(!price.isEmpty()){
                        intent.putExtra("price", Double.parseDouble(price));
                    }else{
                        intent.putExtra("price", Double.MAX_VALUE);
                    }
                }

                startActivity(intent);
            }
        });
    }
}
