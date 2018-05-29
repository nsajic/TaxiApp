package com.example.nsajic.testapp;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by nsajic on 5/24/2018.
 */

public class TaxiApplication extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //Newer version of Firebase
        //if(!FirebaseApp.getApps(this).isEmpty()) {
        //    FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        //}
    }
}

