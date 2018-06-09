package com.example.nsajic.testapp.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.nsajic.testapp.Adapters.GradoviAdapter;
import com.example.nsajic.testapp.Models.Grad;
import com.example.nsajic.testapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchedActivity extends AppCompatActivity {

    private String cityTaxi;
    private String name;
    private String postanskiBroj;
    private Double price;

    private DatabaseReference databaseReference;

    private ArrayList<Grad> cities;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searched);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        intent = getIntent();

        cityTaxi = intent.getStringExtra("cityTaxi");
        name = intent.getStringExtra("name");
        price = intent.getDoubleExtra("price", 0);
        postanskiBroj = intent.getStringExtra("selectedCityPC");

        if(cityTaxi.equals("City")){
            databaseReference.child("gradovi").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Iterable<DataSnapshot> gradUids = dataSnapshot.getChildren();
                    ArrayList<Grad> tempList = new ArrayList<>();
                    for (DataSnapshot gradUidSnapsot : gradUids) {
                        tempList.add(gradUidSnapsot.getValue(Grad.class));
                    }

                    if(name.isEmpty()){
                        cities = tempList;
                    }else{
                        cities = cityByName(tempList, name);
                    }

                    ListView lv = (ListView)findViewById(R.id.gradovi_listview);
                    lv.setAdapter(new GradoviAdapter(cities, SearchedActivity.this));

                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent intent = new Intent(view.getContext(), TaxiSluzbeActivity.class);
                            intent.putExtra("imeGrada", cities.get(i).getIme());
                            intent.putExtra("postanskiBroj", cities.get(i).getPostanskiBroj().toString());

                            startActivity(intent);
                        }
                    });
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }else if(cityTaxi.equals(("Taxi"))){
            databaseReference.child("gradovi").child(postanskiBroj).child("taxiSluzbe").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    List<Grad> cities = cityByName((ArrayList<Grad>)dataSnapshot.getValue(), name);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    private ArrayList<Grad> cityByName(List<Grad> cities, String name){

        ArrayList<Grad> retVal = new ArrayList<>();
        for (Grad c :
                cities) {
            if(c.getIme().toLowerCase().contains(name.toLowerCase())){
                retVal.add(c);
            }
        }

        return retVal;
    }
}
