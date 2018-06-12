package com.example.nsajic.testapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Filter;

import com.example.nsajic.testapp.Activities.AboutActivity;
import com.example.nsajic.testapp.Activities.FeedbackActivity;
import com.example.nsajic.testapp.Activities.FilterActivity;
import com.example.nsajic.testapp.Activities.LoginActivity;
import com.example.nsajic.testapp.Activities.RegisterActivity;
import com.example.nsajic.testapp.Adapters.ViewPagerAdapter;
import com.example.nsajic.testapp.Models.Grad;
import com.example.nsajic.testapp.Models.TaxiSluzba;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    private static final String FINE_LOCATION = ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;

    String[] permissions = {ACCESS_FINE_LOCATION,
            ACCESS_COARSE_LOCATION};

    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            }else{
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else{
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
        
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){
            switchToLoginActivity();
        }
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setCurrentItem(0);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),this);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                switchToAboutActivity();
                return true;
            case R.id.action_logout:
                logOut();
                return true;
            case R.string.settings:
                return true;
            case R.id.action_userguide:
                switchToUserGuide();
                return true;
            case R.id.action_feedback:
                switchToFeedbackActivity();
                return true;
            case R.id.action_filter:
                switchToFilter();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void switchToAboutActivity() {
        startActivity(new Intent(this, AboutActivity.class));
    }

    private void switchToFeedbackActivity() {
        startActivity(new Intent(this, FeedbackActivity.class));
    }

    private void logOut(){
        firebaseAuth.signOut();
        switchToLoginActivity();
    }

    private void switchToLoginActivity(){
        finish();
        startActivity(new Intent(this, LoginActivity.class));
    }

    private void switchToUserGuide(){
        startActivity(new Intent(this, UserGuid.class));
    }

    private void switchToFilter(){
        startActivity(new Intent(this, FilterActivity.class));
    }
}