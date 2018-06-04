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
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;

import com.example.nsajic.testapp.Adapters.TaxiSluzbaAdapter;
import com.example.nsajic.testapp.MainActivity;
import com.example.nsajic.testapp.Models.Grad;
import com.example.nsajic.testapp.Models.TaxiSluzba;
import com.example.nsajic.testapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TaxiSluzbeActivity extends AppCompatActivity {

    private static final int CALL_PHONE = 1;
    ArrayList<TaxiSluzba> taxiSluzbeNS = new ArrayList<TaxiSluzba>();
    ListView listViewSluzbe;
    private DatabaseReference dataBaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taxi_sluzbe);
        getSupportActionBar().setTitle("Taxi sluzbe");

        Intent intent = getIntent();

        String postanskiBroj = intent.getStringExtra("postanskiBroj");

        dataBaseReference = FirebaseDatabase.getInstance().getReference();

        dataBaseReference.child("gradovi").child(postanskiBroj).child("taxiSluzbe").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                if(taxiSluzbeNS.size() == 0) {
                    for (DataSnapshot child : children) {
                        TaxiSluzba taxiSluzba = child.getValue(TaxiSluzba.class);
                        taxiSluzbeNS.add(taxiSluzba);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listViewSluzbe = (ListView)findViewById(R.id.taxiSluzbe_listview);
        listViewSluzbe.setAdapter(new TaxiSluzbaAdapter(taxiSluzbeNS, this));

        listViewSluzbe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //CheckBox cb = (CheckBox) view.findViewById((R.id.favouriteChecked));
                Intent intent = new Intent(view.getContext(), TaxiInfoActivity.class);

                intent.putExtra("imeSluzbe", taxiSluzbeNS.get(i).getIme());
                intent.putExtra("brojTelefona", taxiSluzbeNS.get(i).getBrojTelefona());
                intent.putExtra("ocenaSluzbe", taxiSluzbeNS.get(i).getOcena().toString());
                intent.putExtra("brojAutomobilaSluzbe", taxiSluzbeNS.get(i).getBrojAutomobila().toString());
                intent.putExtra("cenaPoKilometru", taxiSluzbeNS.get(i).getCenaPoKilometru().toString());

                startActivity(intent);
            }
        });


        /*if(postanskiBroj.toString().contentEquals("21000")){
            taxiSluzbeNS = GetSluzbeNS();

            listViewSluzbe = (ListView)findViewById(R.id.taxiSluzbe_listview);

            listViewSluzbe.setAdapter(new TaxiSluzbaAdapter(taxiSluzbeNS, this));

            listViewSluzbe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(view.getContext(), TaxiInfoActivity.class);

                    intent.putExtra("imeSluzbe", taxiSluzbeNS.get(i).getIme());
                    intent.putExtra("brojTelefona", taxiSluzbeNS.get(i).getBrojTelefona());
                    intent.putExtra("ocenaSluzbe", taxiSluzbeNS.get(i).getOcena().toString());
                    intent.putExtra("brojAutomobilaSluzbe", taxiSluzbeNS.get(i).getBrojAutomobila().toString());
                    intent.putExtra("cenaPoKilometru", taxiSluzbeNS.get(i).getCenaPoKilometru().toString());

                    startActivity(intent);
                }
            });
        }*/

    }

    private ArrayList<TaxiSluzba> GetSluzbeNS(){
        ArrayList<TaxiSluzba> taxiSluzbe = new ArrayList<>();

        taxiSluzbe.add(new TaxiSluzba("Sasa taxi", "+381659300975", 5.0));
        taxiSluzbe.add(new TaxiSluzba("Maxi", "021222111", 3.0));
        taxiSluzbe.add(new TaxiSluzba("Ljubicasti", "0215555333", 4.0));
        taxiSluzbe.add(new TaxiSluzba("Zuti", "021555444", 2.0));
        taxiSluzbe.add(new TaxiSluzba("Zeleni", "021555333", 5.0));
        taxiSluzbe.add(new TaxiSluzba("Crveni", "021555333", 3.0));
        taxiSluzbe.add(new TaxiSluzba("Rozi", "021555333", 4.0));
        taxiSluzbe.add(new TaxiSluzba("Plavi", "021555333", 4.0));
        taxiSluzbe.add(new TaxiSluzba("Bez", "021555233", 4.0));
        taxiSluzbe.add(new TaxiSluzba("Braon", "021576333", 1.0));
        taxiSluzbe.add(new TaxiSluzba("Beli", "021555213", 3.0));
        taxiSluzbe.add(new TaxiSluzba("Sivi", "021556333", 5.0));

        return  taxiSluzbe;
    }
}
