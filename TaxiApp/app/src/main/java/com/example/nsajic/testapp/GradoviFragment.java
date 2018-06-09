package com.example.nsajic.testapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.nsajic.testapp.Activities.TaxiSluzbeActivity;
import com.example.nsajic.testapp.Adapters.GradoviAdapter;
import com.example.nsajic.testapp.Models.Grad;
import com.example.nsajic.testapp.Models.TaxiSluzba;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class GradoviFragment extends Fragment {

    final ArrayList<Grad> gradovi = new ArrayList<Grad>();

    private DatabaseReference dataBaseReference;
    //private Firebase mRootRef;

    public GradoviFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gradovi, container, false);

        dataBaseReference = FirebaseDatabase.getInstance().getReference();

        //GetGradovi();

        GradoviAdapter ga = new GradoviAdapter(gradovi, getActivity());
        final GradoviAdapter finalGa = ga;

        dataBaseReference.child("gradovi").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                if(gradovi.size() == 0) {
                    for (DataSnapshot child : children) {
                        Grad grad = child.getValue(Grad.class);
                        gradovi.add(grad);
                    }
                }

                finalGa.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ListView lv = (ListView)view.findViewById(R.id.gradovi_listview);
        lv.setAdapter(ga);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(view.getContext(), TaxiSluzbeActivity.class);
                intent.putExtra("imeGrada", gradovi.get(i).getIme());
                intent.putExtra("postanskiBroj", gradovi.get(i).getPostanskiBroj().toString());

                startActivity(intent);
            }
        });

        return view;
    }

    private void GetGradovi(){

        //final ArrayList<Grad> gradovi = new ArrayList<>();



        ArrayList<Grad> gradovi = new ArrayList<>();

        Grad gradNS = new Grad("Novi Sad", "21000");
        Grad gradBG = new Grad("Beograd", "11000");
        Grad gradSM = new Grad("Sremska Mitrovica", "22000");

        TaxiSluzba taxi1 = new TaxiSluzba("SasaTaxi", "+381659300975", 5.0, 80.0, 9, false);
        TaxiSluzba taxi2 = new TaxiSluzba("RedTaxi", "+38121445577", 4.0, 85.0, 5, false);
        TaxiSluzba taxi3 = new TaxiSluzba("Delta taxi", "+38166355555", 3.0, 70.0, 8, false);
        TaxiSluzba taxi4 = new TaxiSluzba("Grand taxi", "+38163330330", 4.5, 80.0, 10, false);

        //dataBaseReference.child("gradovi").child(gradNS.getPostanskiBroj()).setValue(gradNS);
        //dataBaseReference.child("gradovi").child(gradBG.getPostanskiBroj()).setValue(gradBG);
        //dataBaseReference.child("gradovi").child(gradSM.getPostanskiBroj()).setValue(gradSM);

        //dataBaseReference.child("gradovi").child(gradNS.getPostanskiBroj()).child("taxiSluzbe").child(taxi1.getIme()).setValue(taxi1);
        //dataBaseReference.child("gradovi").child(gradNS.getPostanskiBroj()).child("taxiSluzbe").child(taxi2.getIme()).setValue(taxi2);
        //dataBaseReference.child("gradovi").child(gradNS.getPostanskiBroj()).child("taxiSluzbe").child(taxi3.getIme()).setValue(taxi3);
        //dataBaseReference.child("gradovi").child(gradNS.getPostanskiBroj()).child("taxiSluzbe").child(taxi4.getIme()).setValue(taxi4);

        //return gradovi;

        //return gradovi;
    }
}
