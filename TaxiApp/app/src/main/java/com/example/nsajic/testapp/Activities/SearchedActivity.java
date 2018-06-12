package com.example.nsajic.testapp.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.nsajic.testapp.Adapters.GradoviAdapter;
import com.example.nsajic.testapp.Adapters.TaxiSluzbaAdapter;
import com.example.nsajic.testapp.Models.Grad;
import com.example.nsajic.testapp.Models.TaxiSluzba;
import com.example.nsajic.testapp.R;
import com.google.firebase.auth.FirebaseAuth;
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
    private FirebaseAuth firebaseAuth;

    private ArrayList<Grad> cities;
    private ArrayList<TaxiSluzba> taxiSluzbas;
    ArrayList<String> omiljeneSluzbe = new ArrayList<String>();

    private ListView lv;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searched);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        intent = getIntent();

        cityTaxi = intent.getStringExtra("cityTaxi");
        name = intent.getStringExtra("name");
        price = intent.getDoubleExtra("price", 0);
        postanskiBroj = intent.getStringExtra("selectedCityPC");

        if(cityTaxi.equals(getResources().getStringArray(R.array.filter_spinner)[0])){
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

                    lv = (ListView)findViewById(R.id.finded_listview);
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
        }else if(cityTaxi.equals((getResources().getStringArray(R.array.filter_spinner)[1]))){
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //
                    Iterable<DataSnapshot> taxiUids = dataSnapshot.child("gradovi").child(postanskiBroj).child("taxiSluzbe").getChildren();
                    Iterable<DataSnapshot> omiljeneSluzbeDB = dataSnapshot.child("korisnici").getChildren();
                    ArrayList<TaxiSluzba> tempList = new ArrayList<>();

                    for (DataSnapshot taxiUidSnapsot : taxiUids) {
                        tempList.add(taxiUidSnapsot.getValue(TaxiSluzba.class));
                    }

                    if(name.isEmpty() && price == 0){
                        taxiSluzbas = tempList;
                    }else{
                        taxiSluzbas = taxiByNameAndPrice(tempList, name, price);
                    }

                    for(DataSnapshot coek: omiljeneSluzbeDB){
                        if(coek.getKey().equals(firebaseAuth.getCurrentUser().getUid())) {
                            for (DataSnapshot sluzba : coek.child("omiljeneSluzbe").getChildren()) {
                                omiljeneSluzbe.add(sluzba.getValue(String.class));
                            }
                        }
                    }

                    for(TaxiSluzba ts : taxiSluzbas){
                        if (omiljeneSluzbe.contains(ts.getIme())) {
                            ts.setFavouriteChecked(true);
                        } else {
                            ts.setFavouriteChecked(false);
                        }
                    }

                    lv = (ListView)findViewById(R.id.finded_listview);
                    lv.setAdapter(new TaxiSluzbaAdapter(taxiSluzbas, SearchedActivity.this));

                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            //CheckBox cb = (CheckBox) view.findViewById((R.id.favouriteChecked));
                            Intent intent = new Intent(view.getContext(), TaxiInfoActivity.class);

                            intent.putExtra("imeSluzbe", taxiSluzbas.get(i).getIme());
                            intent.putExtra("brojTelefona", taxiSluzbas.get(i).getBrojTelefona());
                            intent.putExtra("ocenaSluzbe", taxiSluzbas.get(i).getOcena().toString());
                            intent.putExtra("brojAutomobilaSluzbe", taxiSluzbas.get(i).getBrojAutomobila().toString());
                            intent.putExtra("cenaPoKilometru", taxiSluzbas.get(i).getCenaPoKilometru().toString());

                            startActivity(intent);
                        }
                    });

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    private ArrayList<Grad> cityByName(List<Grad> cities, String name){

        ArrayList<Grad> retVal = new ArrayList<>();
        name = name.toLowerCase();

        for (Grad c : cities) {
            if(c.getIme().toLowerCase().contains(name)){
                retVal.add(c);
            }
        }

        return retVal;
    }

    private ArrayList<TaxiSluzba> taxiByNameAndPrice(List<TaxiSluzba> taxiSluzbe, String name, Double price){

        ArrayList<TaxiSluzba> retVal = new ArrayList<>();
        name = name.toLowerCase();

        for (TaxiSluzba ts : taxiSluzbe) {
            if(ts.getIme().toLowerCase().contains(name) && ts.getCenaPoKilometru() <= price){
                retVal.add(ts);
            }
        }

        return retVal;
    }
}
