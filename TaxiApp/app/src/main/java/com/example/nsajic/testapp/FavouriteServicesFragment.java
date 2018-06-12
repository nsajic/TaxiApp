package com.example.nsajic.testapp;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.nsajic.testapp.Activities.TaxiInfoActivity;
import com.example.nsajic.testapp.Activities.TaxiSluzbeActivity;
import com.example.nsajic.testapp.Adapters.FavouriteServiceAdapter;
import com.example.nsajic.testapp.Models.Grad;
import com.example.nsajic.testapp.Models.TaxiSluzba;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteServicesFragment extends Fragment {

    private static final int CALL_PHONE = 1;
    final ArrayList<TaxiSluzba> taxiSluzbe = new ArrayList<TaxiSluzba>();
    final ArrayList<String> omiljeneSluzbe = new ArrayList<String>();

    FavouriteServiceAdapter fsa = null;
    final FavouriteServiceAdapter finalFsa = fsa;

    private DatabaseReference dataBaseReference;
    private FirebaseAuth firebaseAuth;

    public FavouriteServicesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();

        if(fsa != null && finalFsa != null){
            finalFsa.notifyDataSetChanged();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favouriteservices, container, false);

        dataBaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        fsa = new FavouriteServiceAdapter(taxiSluzbe, getActivity());
        final FavouriteServiceAdapter finalFsa = fsa;

        dataBaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        taxiSluzbe.removeAll(taxiSluzbe);
                        omiljeneSluzbe.removeAll(omiljeneSluzbe);

                        if(taxiSluzbe.size() == 0) {
                            Iterable<DataSnapshot> omiljeneSluzbeDB = dataSnapshot.child("korisnici").getChildren();
                            Iterable<DataSnapshot> gradovi = dataSnapshot.child("gradovi").getChildren();

                            for (DataSnapshot coek : omiljeneSluzbeDB) {
                                if (coek.getKey().equals(firebaseAuth.getCurrentUser().getUid())) {
                                    for (DataSnapshot sluzba : coek.child("omiljeneSluzbe").getChildren()) {
                                        omiljeneSluzbe.add(sluzba.getValue(String.class));
                                    }
                                }
                            }

                            for (DataSnapshot grad : gradovi) {
                                for (DataSnapshot sluzbica : grad.child("taxiSluzbe").getChildren()) {
                                    TaxiSluzba tempSluzba = sluzbica.getValue(TaxiSluzba.class);

                                    if (omiljeneSluzbe.contains(tempSluzba.getIme())) {
                                        taxiSluzbe.add(tempSluzba);
                                    }
                                }
                            }

                            finalFsa.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        ListView lv = (ListView)view.findViewById(R.id.favourite_services_listview);
        lv.setAdapter(fsa);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(view.getContext(), TaxiInfoActivity.class);

                intent.putExtra("imeSluzbe", taxiSluzbe.get(i).getIme());
                intent.putExtra("brojTelefona", taxiSluzbe.get(i).getBrojTelefona());
                intent.putExtra("ocenaSluzbe", taxiSluzbe.get(i).getOcena().toString());
                intent.putExtra("brojAutomobilaSluzbe", taxiSluzbe.get(i).getBrojAutomobila().toString());
                intent.putExtra("cenaPoKilometru", taxiSluzbe.get(i).getCenaPoKilometru().toString());

                startActivity(intent);
            }
        });

        return view;
    }

}
