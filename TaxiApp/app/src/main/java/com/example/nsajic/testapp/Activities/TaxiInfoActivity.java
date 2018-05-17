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
import android.widget.Button;
import android.widget.TextView;

import com.example.nsajic.testapp.R;

public class TaxiInfoActivity extends AppCompatActivity {

    private static final int CALL_PHONE = 1;
    TextView nazivSluzbe;
    TextView ocenaSluzbe;
    TextView brojTelefonaSluzbe;
    Button callButton;
    Intent callIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taxi_info);
        getSupportActionBar().setTitle("Info");

        final Intent intent = getIntent();

        nazivSluzbe = (TextView)findViewById(R.id.nazivView);
        ocenaSluzbe = (TextView)findViewById(R.id.ocenaView);
        brojTelefonaSluzbe = (TextView)findViewById(R.id.brojTelefonaView);
        callButton = (Button) findViewById(R.id.callButton);
        callButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + intent.getStringExtra("brojTelefona")));

                if (ContextCompat.checkSelfPermission(TaxiInfoActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(TaxiInfoActivity.this, new String[]{Manifest.permission.CALL_PHONE},CALL_PHONE);
                }
                else
                {
                    startActivity(callIntent);
                }
            }
        });

        nazivSluzbe.setText(intent.getStringExtra("imeSluzbe"));
        ocenaSluzbe.setText(intent.getStringExtra("ocenaSluzbe"));
        brojTelefonaSluzbe.setText(intent.getStringExtra("brojTelefona"));
    }
}
