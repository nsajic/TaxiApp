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
import android.widget.ListView;

import com.example.nsajic.testapp.Adapters.TaxiSluzbaAdapter;
import com.example.nsajic.testapp.MainActivity;
import com.example.nsajic.testapp.Models.TaxiSluzba;
import com.example.nsajic.testapp.R;

import java.util.ArrayList;

public class TaxiSluzbeActivity extends AppCompatActivity {

    private static final int CALL_PHONE = 1;
    ArrayList<TaxiSluzba> taxiSluzbeNS;
    ListView listViewSluzbe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taxi_sluzbe);
        getSupportActionBar().setTitle("Taxi sluzbe");

        Intent intent = getIntent();

        String postanskiBroj = intent.getStringExtra("postanskiBroj");

        if(postanskiBroj.toString().contentEquals("21000")){
            taxiSluzbeNS = GetSluzbeNS();

            listViewSluzbe = (ListView)findViewById(R.id.taxiSluzbe_listview);

            listViewSluzbe.setAdapter(new TaxiSluzbaAdapter(taxiSluzbeNS, this));

            listViewSluzbe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(view.getContext(), TaxiInfoActivity.class);

                    intent.putExtra("imeSluzbe", taxiSluzbeNS.get(i).getIme());
                    intent.putExtra("brojTelefona", taxiSluzbeNS.get(i).getBrojTelefona());
                    intent.putExtra("ocenaSluzbe", taxiSluzbeNS.get(i).getOcena());

                    startActivity(intent);
                }
            });
        }

    }

    private ArrayList<TaxiSluzba> GetSluzbeNS(){
        ArrayList<TaxiSluzba> taxiSluzbe = new ArrayList<>();

        taxiSluzbe.add(new TaxiSluzba("Sasa taxi", "+381659300975", "5"));
        taxiSluzbe.add(new TaxiSluzba("Maxi", "021222111", "3"));
        taxiSluzbe.add(new TaxiSluzba("Ljubicasti", "0215555333", "4"));
        taxiSluzbe.add(new TaxiSluzba("Zuti", "021555444", "2"));
        taxiSluzbe.add(new TaxiSluzba("Zeleni", "021555333", "5"));
        taxiSluzbe.add(new TaxiSluzba("Crveni", "021555333", "3"));
        taxiSluzbe.add(new TaxiSluzba("Rozi", "021555333", "4"));
        taxiSluzbe.add(new TaxiSluzba("Plavi", "021555333", "4"));
        taxiSluzbe.add(new TaxiSluzba("Bez", "021555233", "4"));
        taxiSluzbe.add(new TaxiSluzba("Braon", "021576333", "1"));
        taxiSluzbe.add(new TaxiSluzba("Beli", "021555213", "3"));
        taxiSluzbe.add(new TaxiSluzba("Sivi", "021556333", "5"));

        return  taxiSluzbe;
    }
}
