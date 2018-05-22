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

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class GradoviFragment extends Fragment {

    ArrayList<Grad> gradovi;

    public GradoviFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gradovi, container, false);

        gradovi = GetGradovi();

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

    private ArrayList<Grad> GetGradovi(){
        ArrayList<Grad> gradovi = new ArrayList<>();

        Grad gradNS = new Grad("Novi Sad", "21000");
        Grad gradBG = new Grad("Beograd", "11000");
        Grad gradSM = new Grad("Sremska Mitrovica", "22000");

        gradovi.add(gradNS);
        gradovi.add(gradBG);
        gradovi.add(gradSM);

        return gradovi;
    }
}
