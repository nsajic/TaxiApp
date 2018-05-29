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

    final List<Grad> gradovi = new ArrayList<Grad>();

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
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ListView lv = (ListView)view.findViewById(R.id.gradovi_listview);
        lv.setAdapter(new GradoviAdapter(gradovi, getActivity()));

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

        gradovi.add(gradNS);
        gradovi.add(gradBG);
        gradovi.add(gradSM);


        dataBaseReference.child("gradovi").child(gradNS.getPostanskiBroj()).setValue(gradNS);
        dataBaseReference.child("gradovi").child(gradBG.getPostanskiBroj()).setValue(gradBG);
        dataBaseReference.child("gradovi").child(gradSM.getPostanskiBroj()).setValue(gradSM);

        //return gradovi;

        //return gradovi;
    }
}
