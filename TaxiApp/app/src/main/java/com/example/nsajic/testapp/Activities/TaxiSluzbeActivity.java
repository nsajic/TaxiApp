package com.example.nsajic.testapp.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.service.autofill.Dataset;
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
import com.google.firebase.auth.FirebaseAuth;
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
    ArrayList<String> omiljeneSluzbe = new ArrayList<String>();
    ListView listViewSluzbe;

    private DatabaseReference dataBaseReference;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taxi_sluzbe);
        getSupportActionBar().setTitle(R.string.taxi_sluzbe_title);

        Intent intent = getIntent();

        final String postanskiBroj = intent.getStringExtra("postanskiBroj");

        firebaseAuth = FirebaseAuth.getInstance();
        dataBaseReference = FirebaseDatabase.getInstance().getReference();

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                dataBaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        taxiSluzbeNS.removeAll(taxiSluzbeNS);
                        omiljeneSluzbe.removeAll(omiljeneSluzbe);

                        Iterable<DataSnapshot> omiljeneSluzbeDB = dataSnapshot.child("korisnici").getChildren();
                        Iterable<DataSnapshot> gradovi = dataSnapshot.child("gradovi").getChildren();
                        //Iterable<DataSnapshot> gradovi = dataSnapshot.getChildren();

                        for(DataSnapshot coek: omiljeneSluzbeDB){
                            if(coek.getKey().equals(firebaseAuth.getCurrentUser().getUid())) {
                                for (DataSnapshot sluzba : coek.child("omiljeneSluzbe").getChildren()) {
                                    omiljeneSluzbe.add(sluzba.getValue(String.class));
                                }
                            }
                        }

                        for(DataSnapshot grad : gradovi){
                            if(grad.getKey().equals(postanskiBroj)) {
                                for (DataSnapshot sluzbica : grad.child("taxiSluzbe").getChildren()) {
                                    TaxiSluzba taxiSluzba = sluzbica.getValue(TaxiSluzba.class);
                                    if (omiljeneSluzbe.contains(taxiSluzba.getIme())) {
                                        taxiSluzba.setFavouriteChecked(true);
                                    } else {
                                        taxiSluzba.setFavouriteChecked(false);
                                    }

                                    taxiSluzbeNS.add(taxiSluzba);
                                }
                            }
                        }

                /*
                dataBaseReference.child("korisnici").child(firebaseAuth.getCurrentUser().getUid()).child("omiljeneSluzbe").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                if(omiljeneSluzbe.size() == 0) {
                    for (DataSnapshot child : children) {
                        omiljeneSluzbe.add(child.getValue(String.class));
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




                for(DataSnapshot grad : gradovi){
                    if(grad.getKey().equals("gradovi")) {
                        for (DataSnapshot sluzba : grad.getChildren()) {
                                for (DataSnapshot sluzbica : sluzba.getChildren()) {
                                    if (sluzbica.getKey().equals("taxiSluzbe")) {
                                        for(DataSnapshot jednaSluzba : sluzbica.getChildren()) {
                                            TaxiSluzba taxiSluzba = jednaSluzba.getValue(TaxiSluzba.class);
                                            if (omiljeneSluzbe.contains(taxiSluzba.getIme())) {
                                                taxiSluzba.setFavouriteChecked(true);
                                            } else {
                                                taxiSluzba.setFavouriteChecked(false);
                                            }
                                            taxiSluzbeNS.add(taxiSluzba);
                                        }
                                    }
                                }
                        }
                    }
                }*/
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });



        /*dataBaseReference.child("gradovi").child(postanskiBroj).child("taxiSluzbe").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                if(taxiSluzbeNS.size() == 0) {
                    for (DataSnapshot child : children) {
                        TaxiSluzba taxiSluzba = child.getValue(TaxiSluzba.class);
                        if(omiljeneSluzbe.contains(taxiSluzba.getIme())){
                            taxiSluzba.setFavouriteChecked(true);
                        }else{
                            taxiSluzba.setFavouriteChecked(false);
                        }
                        taxiSluzbeNS.add(taxiSluzba);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        dataBaseReference.child("korisnici").child(firebaseAuth.getCurrentUser().getUid()).child("omiljeneSluzbe").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                if(omiljeneSluzbe.size() == 0) {
                    for (DataSnapshot child : children) {
                        omiljeneSluzbe.add(child.getValue(String.class));
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

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
