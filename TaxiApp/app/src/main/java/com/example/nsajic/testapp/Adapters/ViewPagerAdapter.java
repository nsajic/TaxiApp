package com.example.nsajic.testapp.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.nsajic.testapp.GradoviFragment;
import com.example.nsajic.testapp.MapFragment;
import com.example.nsajic.testapp.FavouriteServicesFragment;
import com.example.nsajic.testapp.R;

/**
 * Created by nsajic on 4/17/2018.
 */

public class ViewPagerAdapter  extends FragmentPagerAdapter {
    Context context;
    public ViewPagerAdapter(FragmentManager fm, Context nContext) {
        super(fm);
        context = nContext;
    }
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0)
        {
            fragment = new GradoviFragment();
        }
        else if (position == 1)
        {
            fragment = new FavouriteServicesFragment();
        }
        else if (position == 2)
        {
            fragment = new MapFragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0)
        {
            title = context.getString(R.string.view_pager_cities);
        }
        else if (position == 1)
        {
            title = context.getString(R.string.view_pager_favourite_taxi_service);
        }
        else if (position == 2)
        {
            title = context.getString(R.string.view_pager_taxi_stops);
        }
        return title;
    }

}
