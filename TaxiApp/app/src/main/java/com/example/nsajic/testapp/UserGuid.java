package com.example.nsajic.testapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UserGuid extends AppCompatActivity {

    private final String upustvo =  "#Fragment GRADOVI: \n" +
                                    "\t U ovom fragmentu prikazana je lista svih gradova u kojima se nalaze taxiji iz baze. \n" +
                                    "\t * Odabirom jednog od grada izlistace se sve taxi sluzbe u okviru tog grada. \n" +
                                    "\t * Taxi sluzbe moguce je dodati u omiljene klikom na zvezdicu ili selektovati za prikaz detalja \n" +
                                    "\t * Ulaskom u detalje osim osnovnih informacija moguce je dati ocenu tom taxiju, dadati i pregledati recenzije i pozvati ga." +
                                    "#Fragment OMILJENI TAXI: \n" +
                                    "\t U ovom fragmentu prikazana je lista svih taxija koje ste dodali u omiljene. \n" +
                                    "\t * Omiljene taxi sluzbe je moguce selektovati za prikaz detalja \n" +
                                    "#Fragment STAJALISTA: \n" +
                                    "\t U ovom fragmentu prikazana je mapa sa svim taxi stajalistima iz nase baze. Pomocu nje mozete videti gde se nalazi najblize taxi stajaliste. \n\n" +
                                    "";
    private TextView upustvoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_guid);
        upustvoTextView = (TextView) findViewById(R.id.textUpustvo);
        upustvoTextView.setText(upustvo);
    }

}
